package org.dmarshaq.graphics;


import lombok.Builder;
import lombok.Getter;
import org.dmarshaq.app.Context;
import org.dmarshaq.utils.BufferUtils;

import java.awt.image.BufferedImage;
import static org.dmarshaq.utils.FileUtils.loadAsImage;

import static org.lwjgl.opengl.GL11.*;

@Getter
public class Texture {
    private int width, height;
    private int textureID;

    public Texture(String path) {
        textureID = generateID(loadDataFromImage(path));
    }

//    public Texture deserializeFromFile(String path) {
//        Texture texture = new Texture();
//        texture.width = 1000;
//        texture.height = 1000;
//        texture.generateID(new int[] {1, 2, 3});
//        return texture;
//    }

    private int[] loadDataFromImage(String path) {
        int[] pixels = null;

        BufferedImage image = loadAsImage(path);
        width = image.getWidth();
        height = image.getHeight();
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

        return data;
    }

    private int generateID(int[] data) {
        int result = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, result);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);  // make it sharp, disable antilising
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexImage2D(GL_TEXTURE_2D, 0 , GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, BufferUtils.createIntBuffer(data));
        glBindTexture(GL_TEXTURE_2D, 0);
        return result;
    }

    public float getUnitWidth() {
        return (float) width / Context.getUnitSize();
    }

    public float getUnitHeight() {
        return (float) height / Context.getUnitSize();
    }

}
