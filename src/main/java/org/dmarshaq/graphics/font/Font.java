package org.dmarshaq.graphics.font;

import org.dmarshaq.graphics.SubTexture;
import org.dmarshaq.graphics.Texture;


public class Font {
    private final Character[] characters;
    private final Texture fontTexture;
    private final String font;


    public Texture getFontTexture() {
        return fontTexture;
    }

    public Font(String fontDataPath, Texture fontAtlas) {
        characters = FontReader.constructFontData(fontDataPath);
        fontTexture = fontAtlas;

        StringBuilder fontBuilder = new StringBuilder();
        SubTexture[] slices = new SubTexture[characters.length];
        int textureWidth = fontAtlas.getWidth();
        int textureHeight = fontAtlas.getHeight();

        int i = 0;
        for (Character c : characters) {
            fontBuilder.append((char) c.getId());

            float x, y, width, height;
            x = (float) c.getX() / textureWidth;
            y = (float) (textureHeight - c.getY() - c.getHeight()) / textureHeight;
            width = (float) c.getWidth() / textureWidth;
            height = (float) c.getHeight() / textureHeight;

            slices[i] = new SubTexture(x, y, width, height);

            i++;
        }

        fontTexture.sliceTexture(slices);
        font = fontBuilder.toString();
    }

    public Character getCharacterData(char character) {
        return characters[font.indexOf(character)];
    }

    public SubTexture getSubTexture(char character) {
        return fontTexture.getSubTextures()[ font.indexOf(character) ];
    }
}

















