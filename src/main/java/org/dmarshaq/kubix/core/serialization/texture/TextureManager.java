package org.dmarshaq.kubix.core.serialization.texture;

import org.dmarshaq.kubix.core.graphic.base.texture.Texture;
import org.dmarshaq.kubix.core.serialization.ResourceManager;

import java.util.*;

import static org.dmarshaq.kubix.core.util.FileUtils.IMAGE_TYPE;
import static org.dmarshaq.kubix.core.util.FileUtils.findAllFilesInResourcesJar;


public class TextureManager implements ResourceManager {
    public final HashMap<String, Texture> TEXTURE_MAP = new HashMap<>();
    // Ordinal 0 is for NO_TEXTURE.
    int textureCounter = 0;

    @Override
    public void loadResources(List<String> resources) {
        for (String name : ResourceManager.extractResourcesOfFiletype(resources, IMAGE_TYPE)) {
            TEXTURE_MAP.put(name, TextureScanner.loadTextureFromImage(name));
        }
    }

    @Override
    public void loadResourcesJar() {
        List<String> paths = findAllFilesInResourcesJar("texture", IMAGE_TYPE);
        for (String path : paths) {
            TEXTURE_MAP.put(path, TextureScanner.loadTextureFromImage(path));
        }
    }
}
