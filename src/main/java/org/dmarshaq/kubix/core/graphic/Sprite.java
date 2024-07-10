package org.dmarshaq.kubix.core.graphic;

import org.dmarshaq.kubix.core.mathj.*;

import java.awt.*;

public class Sprite {

    private Matrix4f transform;
    private Layer layer;
    private Texture texture;
    private SubTexture subTexture;
    private Color color;
    private Shader shader;
    private float width, height;

    public Sprite(Matrix4f transform, Layer layer, Texture texture, SubTexture subTexture, Shader shader) {
        this.transform = transform;
        this.layer = layer;
        this.texture = texture;
        this.subTexture = subTexture;
        this.color = null;
        this.shader = shader;

        width = texture.getUnitWidth();
        height = texture.getUnitHeight();
    }

    public Sprite(Matrix4f transform, Layer layer, Color color, Shader shader) {
        this.transform = transform;
        this.layer = layer;
        this.texture = null;
        this.color = color;
        this.shader = shader;

        this.width = 1.0f;
        this.height = 1.0f;
    }

    public Sprite(Matrix4f transform, Layer layer, Texture texture, Shader shader) {
        this.transform = transform;
        this.layer = layer;
        this.texture = texture;
        this.color = null;
        this.shader = shader;


        width = texture.getUnitWidth();
        height = texture.getUnitHeight();
    }

    // Interaction methods
    public void setWidth(float unitWidth) {
        width = unitWidth;
    }
    public void setHeight(float unitHeight) {
        height = unitHeight;
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

    public Color getColor() {
        return color;
    }

    public Shader getShader() {
        return shader;
    }

    public float getScaledWidth() {
        return width * MathJ.Math2D.magnitude(transform.getXAxisVector2());
    }

    public float getScaledHeight() {
        return height * MathJ.Math2D.magnitude(transform.getYAxisVector2());
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public Vector2f getCenter() {
        return MathJ.Math2D.sum(transform.getPositionXY(), new Vector2f(getScaledWidth() / 2, getScaledHeight() / 2));
    }

    public void setTransform(Matrix4f transform) {
        this.transform = transform;
    }

    public SubTexture getSubTexture() {
        return subTexture;
    }
}
