package org.dmarshaq.kubix.core.serialization.font;

import org.dmarshaq.kubix.core.graphic.base.text.CharacterData;
import org.dmarshaq.kubix.core.graphic.base.text.Font;
import org.dmarshaq.kubix.core.graphic.base.texture.Texture;
import org.dmarshaq.kubix.core.graphic.base.texture.TextureCroppedRegion;
import org.dmarshaq.kubix.core.math.vector.Vector2;
import org.dmarshaq.kubix.core.serialization.texture.TextureManager;
import org.dmarshaq.kubix.core.util.FileUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Scanner;

import static org.dmarshaq.kubix.core.util.FileUtils.loadAsString;

public class FontScanner {



    public static Font loadFontFromFile(String path) {
        Scanner scanner = FileUtils.loadAsScanner(path);

        int lineHeight = scanIntValueOf(scanner, "lineHeight");
        int base = scanIntValueOf(scanner, "base");
        String textureFileName = scanStringValueOf(scanner, "file");
        Texture texture = TextureManager.TEXTURE_MAP.get(textureFileName.substring(0, textureFileName.lastIndexOf('.')));

        String idKey = "id";
        String xKey = "x";
        String yKey = "y";
        String widthKey = "width";
        String heightKey = "height";
        String xoffsetKey = "xoffset";
        String yoffsetKey = "yoffset";
        String xadvanceKey = "xadvance";
        int count = scanIntValueOf(scanner, "count");
        HashMap<Character, CharacterData> atlas = new HashMap<>(count, 1.0f);
        for (int i = 0; i < count; i++) {
            Character key = scanCharValueOf(scanner, idKey);
            int x = scanIntValueOf(scanner, xKey);
            int y = scanIntValueOf(scanner, yKey);
            int width = scanIntValueOf(scanner, widthKey);
            int height = scanIntValueOf(scanner, heightKey);
            int xoffset = scanIntValueOf(scanner, xoffsetKey);
            int yoffset = scanIntValueOf(scanner, yoffsetKey);
            int xadvance = scanIntValueOf(scanner, xadvanceKey);
            CharacterData value = new CharacterData(
                    new TextureCroppedRegion(
                            new Vector2(
                                    x,
                                    texture.getHeight() - y - height
                            ),
                            width,
                            height,
                            texture
                    ),
                    xoffset,
                    yoffset,
                    xadvance
            );
            atlas.put(key, value);
        }
        return new Font(atlas, lineHeight, base);
    }

    public static Font loadFontFromFile(File file) {
        Scanner scanner = FileUtils.loadAsScanner(file);

        int lineHeight = scanIntValueOf(scanner, "lineHeight");
        int base = scanIntValueOf(scanner, "base");
        String textureFileName = scanStringValueOf(scanner, "file");
        Texture texture = TextureManager.TEXTURE_MAP.get(textureFileName.substring(0, textureFileName.lastIndexOf('.')));

        String idKey = "id";
        String xKey = "x";
        String yKey = "y";
        String widthKey = "width";
        String heightKey = "height";
        String xoffsetKey = "xoffset";
        String yoffsetKey = "yoffset";
        String xadvanceKey = "xadvance";
        int count = scanIntValueOf(scanner, "count");
        HashMap<Character, CharacterData> atlas = new HashMap<>(count, 1.0f);
        for (int i = 0; i < count; i++) {
            Character key = scanCharValueOf(scanner, idKey);
            int x = scanIntValueOf(scanner, xKey);
            int y = scanIntValueOf(scanner, yKey);
            int width = scanIntValueOf(scanner, widthKey);
            int height = scanIntValueOf(scanner, heightKey);
            int xoffset = scanIntValueOf(scanner, xoffsetKey);
            int yoffset = scanIntValueOf(scanner, yoffsetKey);
            int xadvance = scanIntValueOf(scanner, xadvanceKey);
            CharacterData value = new CharacterData(
                    new TextureCroppedRegion(
                            new Vector2(
                                    x,
                                    texture.getHeight() - y - height
                            ),
                            width,
                            height,
                            texture
                    ),
                    xoffset,
                    yoffset,
                    xadvance
            );
            atlas.put(key, value);
        }
        return new Font(atlas, lineHeight, base);
    }

    private static int scanIntValueOf(Scanner scanner, String key) {
        while (scanner.hasNext()) {
            String pair = scanner.next();
            if (pair.contains(key)) {
                return Integer.parseInt(pair.substring(pair.lastIndexOf('=') + 1));
            }
        }
        return 0;
    }
    private static char scanCharValueOf(Scanner scanner, String key) {
        return (char) scanIntValueOf(scanner, key);
    }
    private static String scanStringValueOf(Scanner scanner, String key) {
        while (scanner.hasNext()) {
            String pair = scanner.next();
            if (pair.contains(key)) {
                return pair.substring(pair.lastIndexOf('=') + 2, pair.length() - 1);
            }
        }
        return "";
    }
}
