package org.dmarshaq.kubix.core.serialization.shader;

import org.dmarshaq.kubix.core.graphic.base.Shader;
import org.dmarshaq.kubix.core.serialization.ResourceManager;

import java.util.HashMap;
import java.util.List;

import static org.dmarshaq.kubix.core.util.FileUtils.*;

public class ShaderManager implements ResourceManager {
    public final HashMap<String, Shader> SHADER_MAP = new HashMap<>();
    int shaderCounter = 0;

    @Override
    public void loadResources(List<String> resources) {
        for (String name : ResourceManager.extractResourcesOfFiletype(resources, SHADER_TYPE)) {
            SHADER_MAP.put(name, ShaderScanner.loadShaderFromFile(name));
        }
    }

    @Override
    public void loadResourcesJar() {
        List<String> paths = findAllFilesInResourcesJar("shader", SHADER_TYPE);
        for (String path : paths) {
            SHADER_MAP.put(path, ShaderScanner.loadShaderFromFile(path));
        }
    }
}
