package org.dmarshaq.graphics;

import org.dmarshaq.app.Layer;
import org.dmarshaq.mathj.*;

public class Sprite {

    private Matrix4f transform;
    private Layer layer;
    private Texture texture;
    private SubTexture subTexture;
    private Vector4f color;
    private Shader shader;
    private Animation animation;

    private float width, height;

    public Sprite(Matrix4f transform, Layer layer, Vector4f color, Shader shader) {
        this.transform = transform;
        this.layer = layer;
        this.texture = null;
        this.subTexture = null;
        this.color = color;
        this.animation = null;
        this.shader = shader;

        width = 1.0f;
        height = 1.0f;

        layer.incrementSpriteCount();
    }

    public Sprite(Matrix4f transform, Layer layer, Texture texture, Shader shader) {
        this.transform = transform;
        this.layer = layer;
        this.texture = texture;
        this.subTexture = null;
        this.color = null;
        this.animation = null;
        this.shader = shader;


        width = MathJ.pixelToWorld( texture.getWidth() );
        height = MathJ.pixelToWorld( texture.getHeight() );

        layer.incrementSpriteCount();
    }

    public Sprite(Matrix4f transform, Layer layer,Texture texture, SubTexture subTexture, Shader shader) {
        this.transform = transform;
        this.layer = layer;
        this.texture = texture;
        this.subTexture = subTexture;
        this.color = null;
        this.animation = null;
        this.shader = shader;

        width = MathJ.pixelToWorld( (int) (texture.getSubTextures()[0].width() * texture.getWidth()) );
        height = MathJ.pixelToWorld( (int) (texture.getSubTextures()[0].height() * texture.getHeight()) );

        layer.incrementSpriteCount();
    }

    public Sprite(Matrix4f transform, Layer layer, Animation animation, Shader shader) {
        this.transform = transform;
        this.layer = layer;
        this.color = null;

        this.animation = animation;
        this.texture = this.animation.getTexture();
        this.subTexture = this.animation.getTexture().getSubTextures()[0];

        this.shader = shader;

        width = MathJ.pixelToWorld( (int) (texture.getSubTextures()[0].width() * texture.getWidth()) );
        height = MathJ.pixelToWorld( (int) (texture.getSubTextures()[0].height() * texture.getHeight()) );

        layer.incrementSpriteCount();
    }

    public Matrix4f getTransform() {
        return transform;
    }

    public Texture getTexture() {
        return texture;
    }

    public SubTexture getSubTexture() {
        return subTexture;
    }

    public Layer getLayer() {
        return layer;
    }

    public Vector4f getColor() {
        return color;
    }

    public Shader getShader() {
        return shader;
    }

    public float getWidth() {
        return width * MathJ.Math2D.magnitude(transform.getXAxisVector2());
    }

    public float getHeight() {
        return height * MathJ.Math2D.magnitude(transform.getYAxisVector2());
    }

    public Vector2f getCenter() {
        return MathJ.Math2D.sum(transform.getPositionXY(), new Vector2f(getWidth() / 2, getHeight() / 2));
    }

    public void setSubTexture(SubTexture sub) {
        subTexture = sub;
    }

    public void setAnimation(Animation animation) {
        this.animation = animation;
        texture = this.animation.getTexture();
    }

    public void playAnimation(MathJ.Easing easing) {
        subTexture = texture.getSubTextures()[ animation.playCycle(easing) ];
    }

    public void setTransform(Matrix4f transform) {
        this.transform = transform;
    }
}
