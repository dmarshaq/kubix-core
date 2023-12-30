package org.dmarshaq.graphics.font;


import org.dmarshaq.graphics.Shader;
import org.dmarshaq.graphics.Sprite;
import org.dmarshaq.graphics.Texture;
import org.dmarshaq.mathj.Rect;
import org.dmarshaq.mathj.Vector3f;
import org.dmarshaq.mathj.Vector3int;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

import static org.dmarshaq.app.GameContext.*;


public class Font {


    public static Font BASIC_PUP_BLACK, BASIC_PUP_WHITE;
    private Character[] characters;
    private Texture[] textures;
    private String font;
    private int size;

    public static void loadFonts() {
        BASIC_PUP_BLACK = new Font(FONT_BASIC_PUP_BLACK_DATA_PATH, FONT_BASIC_PUP_BLACK_ATLAS_PATH, 1);
        BASIC_PUP_WHITE = new Font(FONT_BASIC_PUP_WHITE_DATA_PATH, FONT_BASIC_PUP_WHITE_ATLAS_PATH, 1);
    }

    private Font(String fontDataPath, String fontAtlasPath, int fontSize) {
        this.size = fontSize;
        characters = FontReader.constructFontData(fontDataPath);
        StringBuilder fontBuilder = new StringBuilder();

        int atlasHeight;
        try {
            BufferedImage atlas = ImageIO.read(new FileInputStream(fontAtlasPath));
            atlasHeight = atlas.getHeight();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        textures = new Texture[characters.length];
        int i = 0;
        for (Character c : characters) {
            fontBuilder.append((char) c.getId());

            textures[i] = new Texture(fontAtlasPath, new Rect( new Vector3f(c.getX(), c.getY()) , c.getWidth(), c.getHeight()));
            i++;
        }
        font = fontBuilder.toString();
    }

    public Sprite[] buildSpriteText(String text, Vector3int position) {
        Sprite[] result = new Sprite[text.length()];
        int line = position.y;
        int cursor = position.x;
        for(int i = 0; i < result.length; i++) {
            int c = font.indexOf(text.charAt(i));
            int height = characters[c].getHeight() * size;
            int width = characters[c].getWidth() * size;
            int xoffset = characters[c].getXoffset() * size;
            int yoffset = characters[c].getYoffset() * size;

            result[i] = new Sprite( new Rect(new Vector3f( cursor + xoffset, line - height - yoffset ), width, height), textures[c], Shader.BASIC_UI);
            cursor += characters[c].getXadvance() * size;
        }
        return result;
    }
}

















