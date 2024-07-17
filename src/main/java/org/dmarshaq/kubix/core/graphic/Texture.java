package org.dmarshaq.kubix.core.graphic;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.dmarshaq.kubix.core.app.Context;
import org.dmarshaq.kubix.core.util.BufferUtils;
import org.dmarshaq.kubix.core.util.Ordarable;
import org.dmarshaq.kubix.math.vector.Vector2;
import org.dmarshaq.kubix.serialization.texture.TextureDto;
import org.dmarshaq.kubix.serialization.texture.TextureScanner;

import java.awt.image.BufferedImage;

import static org.dmarshaq.kubix.core.util.FileUtils.loadAsImage;
import static org.lwjgl.opengl.GL11.*;

@Getter
@ToString
public class Texture implements Ordarable {
    private final int order;
    private int width;
    private int height;
    private final int textureID;
    @Setter
    private TextureCroppedRegion[] tileSet;

    public static final Texture NO_TEXTURE = new Texture(Context.getUnitSize(), Context.getUnitSize(), 0);
    public static Texture TREE;

    private Texture(int width, int height, int order) {
        this.order = order;
        this.width = width;
        this.height = height;
        this.textureID = 0;
        tileSet = new TextureCroppedRegion[] {
                new TextureCroppedRegion(new Vector2(0, 0), width, height, this)
        };
    }

    public Texture(int[] pixels, int width, int height, int order) {
        this.order = order;
        this.width = width;
        this.height = height;
        textureID = generateID(pixels);
        tileSet = new TextureCroppedRegion[] {
                new TextureCroppedRegion(new Vector2(0, 0), width, height, this)
        };
    }

    private int generateID(int[] data) {
        int id = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, id);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);  // make it sharp, disable antialiasing
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexImage2D(GL_TEXTURE_2D, 0 , GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, BufferUtils.createIntBuffer(data));
        glBindTexture(GL_TEXTURE_2D, 0);
//        System.out.println(id);
//        int err = glGetError();
//        if (err != 0) {
//            System.out.println(err);
//        }
        return id;
    }

    public float getUnitWidth() {
        return (float) width / Context.getUnitSize();
    }

    public float getUnitHeight() {
        return (float) height / Context.getUnitSize();
    }

    @Override
    public int ordinal() {
        return order;
    }

//    public Texture(String Path, int order) {
//        textureID = generateID(load(Path));
//        width = 840;
//        height = 859;
//        tileSet = new TextureCroppedRegion[] {
//                new TextureCroppedRegion(new Vector2(0, 0), width, height, this)
//        };
//        this.order = order;
//    }
//
//    private int[] load(String path) {
//        int[] pixels = null;
//
//        BufferedImage image = loadAsImage(path);
//        int width = image.getWidth();
//        int height = image.getHeight();
//        pixels = new int[width * height];
//        image.getRGB(0 ,0, width, height, pixels, 0 , width);
//
//        int[] data = new int[width * height];
//        for (int y = 0; y < height; y++) {
//            for (int x = 0; x < width; x++) {
//                int index = x + y * width;
//                int a = (pixels[index] & 0xff000000) >> 24;
//                int r = (pixels[index] & 0xff0000) >> 16;
//                int g = (pixels[index] & 0xff00) >> 8;
//                int b = (pixels[index] & 0xff);
//                data[x + (height - 1 - y) * width] = a << 24 | b << 16 | g << 8 | r;
//            }
//        }
//
////        int result = glGenTextures();
////        glBindTexture(GL_TEXTURE_2D, result);
////        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);  // make it sharp, disable antilising
////        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
////        glTexImage2D(GL_TEXTURE_2D, 0 , GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, BufferUtils.createIntBuffer(data));
////        glBindTexture(GL_TEXTURE_2D, 0);
//        return data;
//    }
}
