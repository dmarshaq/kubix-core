package org.dmarshaq.graphics;

import org.dmarshaq.app.Layer;
import org.dmarshaq.mathj.Matrix4f;

public class Sprite {

    private Matrix4f transform;
    private Texture texture;
    private Layer layer;

    public Sprite(SpriteDTO spriteDTO) {
        this.transform = new Matrix4f();
        this.transform.copy(spriteDTO.getTransform());
        this.texture = spriteDTO.getTexture();
        this.layer = spriteDTO.getLayer();
    }

    public Matrix4f getTransform() {
        return transform;
    }
    public Texture getTexture() {
        return texture;
    }
    public Layer getLayer() {
        return layer;
    }

}
