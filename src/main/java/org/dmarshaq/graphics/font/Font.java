package org.dmarshaq.graphics.font;


import org.dmarshaq.graphics.Shader;
import org.dmarshaq.graphics.Sprite;
import org.dmarshaq.graphics.Texture;
import org.dmarshaq.mathj.Rect;
import org.dmarshaq.mathj.RectComponent;
import org.dmarshaq.mathj.Vector3f;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

import static org.dmarshaq.app.GameContext.*;
import static org.dmarshaq.utils.FileUtils.getResourcePath;


public class Font {


    public static Font BASIC_PUP_BLACK, BASIC_PUP_WHITE;
    private Character[] characters;
    private Texture[] textures;
    private String font;
    private float scale;

    public static void loadFonts() {
        BASIC_PUP_BLACK = new Font(FONT_BASIC_PUP_BLACK_DATA_PATH, FONT_BASIC_PUP_BLACK_ATLAS_PATH, FONT_BASIC_PUP_BLACK_SCALE);
        BASIC_PUP_WHITE = new Font(FONT_BASIC_PUP_WHITE_DATA_PATH, FONT_BASIC_PUP_WHITE_ATLAS_PATH, FONT_BASIC_PUP_WHITE_SCALE);
    }

    private Font(String fontDataPath, String fontAtlasPath, float fontScale) {
        this.scale = fontScale;
        characters = FontReader.constructFontData(fontDataPath);
        StringBuilder fontBuilder = new StringBuilder();

        int atlasHeight;
        try {
            BufferedImage atlas = ImageIO.read(new FileInputStream(getResourcePath(fontAtlasPath)));
            atlasHeight = atlas.getHeight();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        textures = new Texture[characters.length];
        int i = 0;
        for (Character c : characters) {
            fontBuilder.append((char) c.getId());

            textures[i] = new Texture(fontAtlasPath, new Rect( c.getX(), c.getY(), c.getWidth(), c.getHeight()));
            i++;
        }
        font = fontBuilder.toString();
    }

    public Sprite[] buildSpriteText(String text, Vector3f position) {
        Sprite[] result = new Sprite[text.length()];
        int line = (int) position.y;
        int cursor = (int) position.x;
        for(int i = 0; i < result.length; i++) {
            int c = font.indexOf(text.charAt(i));
            int height = (int) (characters[c].getHeight() * scale);
            int width = (int) (characters[c].getWidth() * scale);
            int xoffset = (int) (characters[c].getXoffset() * scale);
            int yoffset = (int) (characters[c].getYoffset() * scale);

//            result[i] = new Sprite( new RectComponent(new Vector3f( cursor + xoffset, line - height - yoffset ), width, height), textures[c], Shader.BASIC_UI);
            cursor += (int) (characters[c].getXadvance() * scale);
        }
        return result;
    }
}

















