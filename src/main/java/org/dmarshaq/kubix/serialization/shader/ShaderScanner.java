package org.dmarshaq.kubix.serialization.shader;

import org.dmarshaq.kubix.core.graphic.Shader;
import org.dmarshaq.kubix.core.serialization.SerializationScanner;
import org.dmarshaq.kubix.core.util.ShaderUtils;

import java.io.File;
import java.nio.file.Path;
import java.util.Scanner;

import static org.dmarshaq.kubix.core.util.FileUtils.loadAsString;

public class ShaderScanner extends SerializationScanner {

    static final String HEADER = "SHAD";

    public static Shader loadShaderFromFile(Path path) {
        String shader = loadAsString(path.toString());
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

        return new Shader(ShaderUtils.create(vertex.toString(), fragment.toString()));
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

        return new Shader(ShaderUtils.create(vertex.toString(), fragment.toString()));
    }
}
