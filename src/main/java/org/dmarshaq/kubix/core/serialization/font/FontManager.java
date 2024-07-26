package org.dmarshaq.kubix.core.serialization.font;

import org.dmarshaq.kubix.core.graphic.base.text.Font;
import org.dmarshaq.kubix.core.serialization.shader.ShaderScanner;
import org.dmarshaq.kubix.core.util.FileUtils;

import java.io.File;
import java.util.HashMap;
import java.util.List;


public class FontManager {
    public static final HashMap<String, Font> FONT_MAP = new HashMap<>();

    public static void loadFontsFromFiles()  {
        List<String> paths = FileUtils.findAllFilesInResourcesJar("font", ".fnt");
        for (String path : paths) {
            FONT_MAP.put(path.substring(path.lastIndexOf('/') + 1, path.length() - 4), FontScanner.loadFontFromFile(path));
        }

        List<File> files = FileUtils.findAllFilesInResources("font", ".fnt");
        for (File file : files) {
            String name = file.getName();
            FONT_MAP.put(name.substring(0, name.length() - 4), FontScanner.loadFontFromFile(file));
        }
    }

}
