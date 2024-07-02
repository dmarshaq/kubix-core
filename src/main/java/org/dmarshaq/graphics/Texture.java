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
    private final int width;
    private final int height;
    private final int textureID;

    public Texture(int[] pixels, int width, int height) {
        textureID = generateID(pixels);
        this.width = width;
        this.height = height;
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
