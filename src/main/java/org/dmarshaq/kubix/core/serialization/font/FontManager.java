package org.dmarshaq.kubix.core.serialization.font;

import org.dmarshaq.kubix.core.graphic.base.text.Font;
import org.dmarshaq.kubix.core.serialization.ResourceManager;
import org.dmarshaq.kubix.core.serialization.shader.ShaderScanner;
import org.dmarshaq.kubix.core.util.FileUtils;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import static org.dmarshaq.kubix.core.util.FileUtils.FONT_TYPE;


public class FontManager implements ResourceManager {
    public final HashMap<String, Font> FONT_MAP = new HashMap<>();

    @Override
    public void loadResources(List<String> resources) {
        for (String name : ResourceManager.extractResourcesOfFiletype(resources, FONT_TYPE)) {
            FONT_MAP.put(name, FontScanner.loadFontFromFile(name));
        }
    }

    @Override
    public void loadResourcesJar() {
        List<String> paths = FileUtils.findAllFilesInResourcesJar("font", ".fnt");
        for (String path : paths) {
            FONT_MAP.put(path, FontScanner.loadFontFromFile(path));
        }
    }
}
