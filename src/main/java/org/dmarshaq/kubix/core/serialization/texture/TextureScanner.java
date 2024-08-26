package org.dmarshaq.kubix.core.serialization.texture;

import org.dmarshaq.kubix.core.app.Context;
import org.dmarshaq.kubix.core.graphic.base.texture.Texture;
import org.dmarshaq.kubix.core.util.BufferUtils;
import java.awt.image.BufferedImage;

import static org.dmarshaq.kubix.core.util.FileUtils.loadAsImage;
import static org.lwjgl.opengl.GL11.*;

public class TextureScanner {

    static Texture loadTextureFromImage(String texture) {
        BufferedImage image = loadAsImage(texture);
        int[] pixels = null;
        int width = image.getWidth();
        int height = image.getHeight();
        pixels = new int[width * height];
        image.getRGB(0 ,0, width, height, pixels, 0 , width);

        int[] data = new int[width * height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int index = x + y * width;
                int a = (pixels[index] & 0xff000000) >> 24;
                int r = (pixels[index] & 0xff0000) >> 16;
                int g = (pixels[index] & 0xff00) >> 8;
                int b = (pixels[index] & 0xff);
                data[x + (height - 1 - y) * width] = a << 24 | b << 16 | g << 8 | r;
            }
        }

        return new Texture(buildTextureId(data, width, height), width, height, Context.getInstance().TEXTURE_MANAGER.textureCounter++);
    }

    private static int buildTextureId(int[] data, int width, int height) {
        int id = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, id);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);  // make it sharp, disable antialiasing
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexImage2D(GL_TEXTURE_2D, 0 , GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, BufferUtils.createIntBuffer(data));
        glBindTexture(GL_TEXTURE_2D, 0);
        return id;
    }
}
