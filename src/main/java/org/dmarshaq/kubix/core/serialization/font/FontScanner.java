package org.dmarshaq.kubix.core.serialization.font;

import org.dmarshaq.kubix.core.app.Context;
import org.dmarshaq.kubix.core.graphic.base.text.CharacterData;
import org.dmarshaq.kubix.core.graphic.base.text.Font;
import org.dmarshaq.kubix.core.graphic.base.texture.Texture;
import org.dmarshaq.kubix.core.graphic.base.texture.TextureCroppedRegion;
import org.dmarshaq.kubix.core.math.vector.Vector2;
import org.dmarshaq.kubix.core.util.FileUtils;

import java.util.HashMap;
import java.util.Scanner;


public class FontScanner {

    private static final String FONT_LINE_HEIGHT_KEY = "lineHeight";
    private static final String FONT_BASE_KEY = "base";
    private static final String FONT_FILE_KEY = "file";
    private static final String FONT_CHARACTERS_COUNT_KEY = "count";
    private static final String CHARACTER_ID_KEY = "id";
    private static final String CHARACTER_X_KEY = "x";
    private static final String CHARACTER_Y_KEY = "y";
    private static final String CHARACTER_WIDTH_KEY = "width";
    private static final String CHARACTER_HEIGHT_KEY = "height";
    private static final String CHARACTER_X_OFFSET_KEY = "xoffset";
    private static final String CHARACTER_Y_OFFSET_KEY = "yoffset";
    private static final String CHARACTER_X_ADVANCE_KEY = "xadvance";

    public static Font loadFontFromFile(String path) {
        return fontFromScanner(FileUtils.loadAsScanner(path));

    }

    private static Font fontFromScanner(Scanner scanner) {
        int lineHeight = scanIntValueOf(scanner, FONT_LINE_HEIGHT_KEY);
        int base = scanIntValueOf(scanner, FONT_BASE_KEY);
        String textureFileName = scanStringValueOf(scanner, FONT_FILE_KEY);
        Texture texture = Context.getInstance().TEXTURE_MANAGER.TEXTURE_MAP.get(textureFileName);

        int count = scanIntValueOf(scanner, FONT_CHARACTERS_COUNT_KEY);
        HashMap<Character, CharacterData> atlas = new HashMap<>(count, 1.0f);
        for (int i = 0; i < count; i++) {
            Character key = scanCharValueOf(scanner, CHARACTER_ID_KEY);
            int x = scanIntValueOf(scanner, CHARACTER_X_KEY);
            int y = scanIntValueOf(scanner, CHARACTER_Y_KEY);
            int width = scanIntValueOf(scanner, CHARACTER_WIDTH_KEY);
            int height = scanIntValueOf(scanner, CHARACTER_HEIGHT_KEY);
            int xoffset = scanIntValueOf(scanner, CHARACTER_X_OFFSET_KEY);
            int yoffset = scanIntValueOf(scanner, CHARACTER_Y_OFFSET_KEY);
            int xadvance = scanIntValueOf(scanner, CHARACTER_X_ADVANCE_KEY);
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
