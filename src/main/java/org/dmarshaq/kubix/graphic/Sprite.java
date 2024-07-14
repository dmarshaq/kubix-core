package org.dmarshaq.kubix.graphic;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.dmarshaq.kubix.core.app.Context;
import org.dmarshaq.kubix.core.graphic.Shader;
import org.dmarshaq.kubix.core.graphic.Texture;
import org.dmarshaq.kubix.math.AbstractRectangle;
import org.dmarshaq.kubix.math.vector.Vector2;

import java.awt.*;

@Getter
@Setter
@ToString
public class Sprite implements AbstractRectangle<Float, Vector2> {
    private final Vector2 position;
    private Texture texture;
    private Shader shader;
    private Color color;

    public Sprite(Vector2 position, Texture texture, Shader shader) {
        this.texture = texture;
        this.position = position;
        this.shader = shader;
    }

    @Override
    public Float getWidth() {
        return (float) (texture.getWidth() / Context.getUnitSize());
    }

    @Override
    public Float getHeight() {
        return (float) (texture.getHeight() / Context.getUnitSize());
    }

    @Override
    public Vector2 getPosition() {
        return position;
    }
}
