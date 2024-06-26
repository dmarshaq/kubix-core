package org.dmarshaq.graphics;

import org.dmarshaq.mathj.Matrix4f;
import org.dmarshaq.mathj.Vector4f;

import static org.dmarshaq.mathj.MathJ.Math2D.toVector4f;

public class SpriteDTO {

    private final Matrix4f transform;
    private final float width;
    private final float height;
    private final Texture texture;
    private final SubTexture subTexture;
    private final Layer layer;
    private final Vector4f color;
    private final Shader shader;

    public SpriteDTO(Sprite sprite) {
        this.transform = Matrix4f.duplicate(sprite.getTransform());
        this.texture = sprite.getTexture();
        this.layer = sprite.getLayer();
        this.subTexture = sprite.getSubTexture();
        this.color = toVector4f(sprite.getColor());
        this.shader = sprite.getShader();
        this.width = sprite.getWidth();
        this.height = sprite.getHeight();
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
    public SubTexture getSubTexture() {
        return subTexture;
    }
    public Vector4f getColor() {
        return color;
    }
    public Shader getShader() {
        return shader;
    }
    public float getWidth() {
        return width;
    }
    public float getHeight() {
        return height;
    }

}
