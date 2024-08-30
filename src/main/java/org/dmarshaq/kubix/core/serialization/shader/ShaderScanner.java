package org.dmarshaq.kubix.core.serialization.shader;

import org.dmarshaq.kubix.core.app.Context;
import org.dmarshaq.kubix.core.graphic.base.Shader;

import java.util.Scanner;

import static org.dmarshaq.kubix.core.util.FileUtils.loadAsString;
import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL20.glDeleteShader;

public class ShaderScanner {

    public static Shader loadShaderFromFile(String path) {
        return shaderFromString(loadAsString(path));

    }

    private static final String TAG_VERSION = "#version";
    private static final String TAG_RENDER_ORDER = "#render_order";
    private static final String TAG_VERTEX_SHADER = "#vert";
    private static final String TAG_FRAGMENT_SHADER = "#frag";
    private static Shader shaderFromString(String program) {
        // Actual program
        StringBuilder vertex = new StringBuilder();
        StringBuilder fragment = new StringBuilder();
        // Params
        int order = Context.getInstance().SHADER_MANAGER.shaderCounter++;

        // Reading shader
        Scanner scanner = new Scanner(program);
        String line;

        while(true) {
            line = scanner.nextLine();
            if (line.startsWith(TAG_VERSION)) {
                vertex.append(line);
                fragment.append(line);
            }
            else if (line.startsWith(TAG_RENDER_ORDER)) {
                order = Integer.parseInt(line.substring(TAG_RENDER_ORDER.length() + 1));
            }
            else if (line.startsWith(TAG_VERTEX_SHADER)) {
                break;
            }
        }

        vertex.append(program, program.indexOf("#vert") + 5, program.indexOf("#frag"));
        fragment.append(program, program.indexOf("#frag") + 5, program.length());

        return new Shader(buildShaderId(vertex.toString(), fragment.toString()), order);
    }

    private static int buildShaderId(String vert, String frag) {
        int program = glCreateProgram();
        int vertID = glCreateShader(GL_VERTEX_SHADER);
        int fragID = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(vertID, vert);
        glShaderSource(fragID, frag);

        glCompileShader(vertID);
        if (glGetShaderi(vertID, GL_COMPILE_STATUS) == GL_FALSE) {
            System.err.println("Failed to compile vertex shader!");
            System.err.println(glGetShaderInfoLog(vertID));
            return -1;
        }

        glCompileShader(fragID);
        if (glGetShaderi(fragID, GL_COMPILE_STATUS) == GL_FALSE) {
            System.err.println("Failed to compile fragment shader!");
            System.err.println(glGetShaderInfoLog(fragID));
            return -1;
        }

        glAttachShader(program, vertID);
        glAttachShader(program, fragID);
        glLinkProgram(program);
        glValidateProgram(program);

        glDeleteShader(vertID);
        glDeleteShader(fragID);

        return program;
    }
}
