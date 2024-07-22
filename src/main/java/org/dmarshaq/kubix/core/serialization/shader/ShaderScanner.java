package org.dmarshaq.kubix.core.serialization.shader;

import org.dmarshaq.kubix.core.graphic.base.Shader;
import org.dmarshaq.kubix.core.serialization.SerializationScanner;

import java.io.File;
import java.util.Scanner;

import static org.dmarshaq.kubix.core.util.FileUtils.loadAsString;
import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL20.glDeleteShader;

public class ShaderScanner extends SerializationScanner {

    static final String HEADER = "SHAD";

    public static Shader loadShaderFromFile(String path) {
        String shader = loadAsString(path);
        StringBuilder vertex = new StringBuilder();
        StringBuilder fragment = new StringBuilder();


        Scanner scanner = new Scanner(shader);
        String line;

        while(true) {
            line = scanner.nextLine();
            if (line.startsWith("#version")) {
                vertex.append(line);
                fragment.append(line);
                break;
            }
        }

        vertex.append(shader, shader.indexOf("#vert") + 5, shader.indexOf("#frag"));
        fragment.append(shader, shader.indexOf("#frag") + 5, shader.length());

        return new Shader(buildShaderId(vertex.toString(), fragment.toString()), ShaderManager.shaderCounter++);
    }

    public static Shader loadShaderFromFile(File file) {
        String shader = loadAsString(file);
        StringBuilder vertex = new StringBuilder();
        StringBuilder fragment = new StringBuilder();


        Scanner scanner = new Scanner(shader);
        String line;

        while(true) {
            line = scanner.nextLine();
            if (line.startsWith("#version")) {
                vertex.append(line);
                fragment.append(line);
                break;
            }
        }

        vertex.append(shader, shader.indexOf("#vert") + 5, shader.indexOf("#frag"));
        fragment.append(shader, shader.indexOf("#frag") + 5, shader.length());

        return new Shader(buildShaderId(vertex.toString(), fragment.toString()), ShaderManager.shaderCounter++);
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
