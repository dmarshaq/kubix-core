package org.dmarshaq.graphics;


import org.dmarshaq.app.Context;
import org.dmarshaq.utils.BufferUtils;

import java.awt.image.BufferedImage;
import static org.dmarshaq.utils.FileUtils.loadAsImage;

import static org.lwjgl.opengl.GL11.*;

public class Texture {
    private int width, height, pixelsPerUnit;
    private final int textureID;
    private SubTexture[] subTextures;

    public Texture(String Path) {
        textureID = load(Path);
        pixelsPerUnit = Context.getUnitSize();
    }

    public Texture(String Path, int xSlices, int ySlices) {
        textureID = load(Path);
        pixelsPerUnit = Context.getUnitSize();
        sliceTexture(xSlices, ySlices);
    }

    private int load(String path) {
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

        int result = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, result);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);  // make it sharp, disable antilising
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexImage2D(GL_TEXTURE_2D, 0 , GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, BufferUtils.createIntBuffer(data));
        glBindTexture(GL_TEXTURE_2D, 0);
        return result;
    }

    /**
     * Slices Texture <p>
     * Precondition: xSlices > 0 <p>
     * Precondition: ySlices > 0
     */
    private void sliceTexture(int xSlices, int ySlices) {
        float sliceWidth = (float) (1.00 / xSlices);
        float sliceHeight = (float) (1.00 / ySlices);
        subTextures = new SubTexture[xSlices * ySlices];
        for (int y = 0; y < ySlices; y++) {
            for (int x = 0; x < xSlices; x++) {
                subTextures[x + y * xSlices] = new SubTexture(x * sliceWidth, y * sliceHeight, sliceWidth, sliceHeight);
            }
        }
    }
    /**
     * Slices Texture Custom<p>
     * Intended to be used when texture is created first time
     */
    public void sliceTexture(SubTexture[] customSlices) {
        subTextures = customSlices;
    }

    public int getPixelWidth() {
        return width;
    }

    public int getPixelHeight() {
        return height;
    }

    public float getUnitWidth() {
        return (float) width / pixelsPerUnit;
    }

    public float getUnitHeight() {
        return (float) height / pixelsPerUnit;
    }

    public void setPixelsPerUnit(int pixels) {
        pixelsPerUnit = pixels;
    }

    public int getID() {
        return textureID;
    }

    public SubTexture[] getSubTextures() {
        return subTextures;
    }
}
