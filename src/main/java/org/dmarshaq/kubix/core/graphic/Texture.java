package org.dmarshaq.kubix.core.graphic;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.dmarshaq.kubix.core.app.Context;
import org.dmarshaq.kubix.core.util.BufferUtils;
import org.dmarshaq.kubix.core.util.Ordarable;
import org.dmarshaq.kubix.math.vector.Vector2;

import static org.lwjgl.opengl.GL11.*;

@Getter
@ToString
public class Texture implements Ordarable {
    private final int order;
    private final int width;
    private final int height;
    private final int textureID;
    @Setter
    private TextureCroppedRegion[] tileSet;

    public Texture(int[] pixels, int width, int height, int order) {
        this.order = order;
        textureID = generateID(pixels);
        this.width = width;
        this.height = height;
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
}
