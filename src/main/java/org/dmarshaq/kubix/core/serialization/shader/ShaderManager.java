package org.dmarshaq.kubix.core.serialization.shader;

import org.dmarshaq.kubix.core.graphic.base.Shader;

import java.io.File;

import java.util.HashMap;
import java.util.List;

import static org.dmarshaq.kubix.core.util.FileUtils.*;

public class ShaderManager {
    public static final HashMap<String, Shader> SHADER_MAP = new HashMap<>();
    static int shaderCounter = 0;

    public static void loadShadersFromFiles()  {
        List<String> paths = findAllFilesInResourcesJar("shader", ".glsl");
        for (String path : paths) {
            SHADER_MAP.put(path.substring(path.lastIndexOf('/') + 1, path.length() - 5), ShaderScanner.loadShaderFromFile(path));
        }

        List<File> files = findAllFilesInResources("shader", ".glsl");
        for (File file : files) {
            String name = file.getName();
            SHADER_MAP.put(name.substring(0, name.length() - 5), ShaderScanner.loadShaderFromFile(file));
        }
    }
}
