package org.dmarshaq.kubix.serialization.shader;

import org.dmarshaq.kubix.core.graphic.Shader;
import org.dmarshaq.kubix.core.util.IndexedHashMap;

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
            StringBuilder name = new StringBuilder(path);
            name.delete(name.length() - 5, name.length());
            name.delete(0, name.lastIndexOf("/") + 1);

            SHADER_MAP.put(name.toString(), ShaderScanner.loadShaderFromFile(path));
        }

        List<File> files = findAllFilesInResources("shader", ".glsl");
        for (File file : files) {
            StringBuilder name = new StringBuilder(file.getName());
            name.delete(name.length() - 5, name.length());

            SHADER_MAP.put(name.toString(), ShaderScanner.loadShaderFromFile(file));
        }
    }
}
