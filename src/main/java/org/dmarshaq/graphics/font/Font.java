package org.dmarshaq.graphics.font;

import org.dmarshaq.graphics.SubTexture;
import org.dmarshaq.graphics.Texture;


public class Font {
    private final Character[] characters;
    private final Texture fontTexture;
    private final String font;
    private int maxCharacterHeight;
    private int maxCharacterWidth;


    public Texture getFontTexture() {
        return fontTexture;
    }

    public Font(String fontDataPath, Texture fontAtlas) {
        characters = FontReader.constructFontData(fontDataPath);
        fontTexture = fontAtlas;
        fontTexture.setPixelsPerUnit(1);
        maxCharacterHeight = 0;

        StringBuilder fontBuilder = new StringBuilder();
        SubTexture[] slices = new SubTexture[characters.length];
        int textureWidth = fontAtlas.getPixelWidth();
        int textureHeight = fontAtlas.getPixelHeight();

        int i = 0;
        for (Character c : characters) {
            fontBuilder.append((char) c.getId());

            float x, y, width, height;
            x = (float) c.getX() / textureWidth;
            y = (float) (textureHeight - c.getY() - c.getHeight()) / textureHeight;
            width = (float) c.getWidth() / textureWidth;
            height = (float) c.getHeight() / textureHeight;

            slices[i] = new SubTexture(x, y, width, height);

            if (maxCharacterHeight < c.getHeight()) {
                maxCharacterHeight = c.getHeight();
            }
            if (maxCharacterWidth < c.getWidth()) {
                maxCharacterWidth = c.getWidth();
            }

            i++;
        }

        fontTexture.sliceTexture(slices);
        font = fontBuilder.toString();
    }

    public Character getCharacterData(char character) {
        return characters[font.indexOf(character)];
    }

    public int getMaxCharacterHeight() {
        return maxCharacterHeight;
    }
    public int getMaxCharacterWidth() {
        return maxCharacterWidth;
    }

    public SubTexture getSubTexture(char character) {
        return fontTexture.getSubTextures()[ font.indexOf(character) ];
    }
}

















