package org.dmarshaq.graphics;


import org.dmarshaq.app.GameContext;
import org.dmarshaq.app.GameContext.HelloWorld.*;
import org.dmarshaq.mathj.Rect;
import org.dmarshaq.utils.BufferUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

import static org.dmarshaq.utils.FileUtils.getResourcePath;
import static org.lwjgl.opengl.GL11.*;

public class Texture {
    private int width, height;
    private int textureID;

    public static Texture SLIME_TEXTURE;

    public static void loadTextures() {
        SLIME_TEXTURE = new Texture(Slime.SLIME_TEXTURE_PATH);
    }

    private Texture(String Path) {
        textureID = load(Path, null);
    }

    private int load(String path, Rect cropRegion) {
        int[] pixels = null;
        try {
            BufferedImage image = ImageIO.read(new FileInputStream(getResourcePath(path)));
            if (cropRegion != null) {
                image = cropImage(image, cropRegion);
            }
            width = image.getWidth();
            height = image.getHeight();
            pixels = new int[width * height];
            image.getRGB(0, 0, width, height, pixels, 0 , width);
        } catch (IOException e) {
            e.printStackTrace();
        }

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
