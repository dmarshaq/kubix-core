package org.dmarshaq.graphics;


import org.dmarshaq.app.GameContext;
import org.dmarshaq.mathj.Rect;
import org.dmarshaq.utils.BufferUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.dmarshaq.utils.FileUtils.loadAsImage;
import static org.lwjgl.opengl.GL11.*;

public class Texture {
    private int width, height;
    private int textureID;

    public static final Texture SLIME_TEXTURE = new Texture(GameContext.Slime.SLIME_TEXTURE_PATH);
    public static final Texture GROUND_TEXTURE = new Texture(GameContext.GROUND_TEXTURE_PATH);

    public Texture(String Path) {
        textureID = load(Path, null);
    }

    public Texture(String Path, Rect cropRegion) {
        textureID = load(Path, cropRegion);
    }

    private int load(String path, Rect cropRegion) {
        int[] pixels = null;

        BufferedImage image = loadAsImage(path);
        if (cropRegion != null) {
            image = cropImage(image, cropRegion);
        }
        width = image.getWidth();
        height = image.getHeight();
        pixels = new int[width * height];
        image.getRGB(0 ,0, width, height, pixels, 0 , width);

        int[] data = new int[width * height];
        for (int i = 0; i < width * height; i++) {
            int a = (pixels[i] & 0xff000000) >> 24;
            int r = (pixels[i] & 0xff0000) >> 16;
            int g = (pixels[i] & 0xff00) >> 8;
            int b = (pixels[i] & 0xff);

            data[i] = a << 24 | b << 16 | g << 8 | r;
        }

        int result = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, result);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);  // make it sharp, disable antilising
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexImage2D(GL_TEXTURE_2D, 0 , GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, BufferUtils.createIntBuffer(data));
        glBindTexture(GL_TEXTURE_2D, 0);
        return result;
    }

    private BufferedImage cropImage(BufferedImage img, Rect cropRegion) {
        BufferedImage image = img.getSubimage((int)cropRegion.x(), (int)cropRegion.y(), (int)cropRegion.width, (int)cropRegion.height);

        BufferedImage copyOfImage = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        Graphics g = copyOfImage.getGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();

        return copyOfImage;
    }

    public void bind() {
        glBindTexture(GL_TEXTURE_2D, textureID);
    }

    public void unbind() {
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getID() {
        return textureID;
    }


}
