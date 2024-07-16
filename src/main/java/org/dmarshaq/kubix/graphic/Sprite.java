package org.dmarshaq.kubix.graphic;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.dmarshaq.kubix.core.app.Context;
import org.dmarshaq.kubix.core.graphic.Shader;
import org.dmarshaq.kubix.core.graphic.TextureCroppedRegion;
import org.dmarshaq.kubix.graphic.render.Layer;
import org.dmarshaq.kubix.math.AbstractRectangle;
import org.dmarshaq.kubix.math.vector.Vector2;

import java.awt.*;

@Getter
@Setter
@ToString
public class Sprite implements AbstractRectangle<Float, Vector2> {
    private final Vector2 position;
    private TextureCroppedRegion texture;
    private Shader shader;
    private Color color;
    private Layer layer;

    public Sprite(Vector2 position, TextureCroppedRegion texture, Shader shader, Layer layer) {
        this.texture = texture;
        this.position = position;
        this.shader = shader;
        this.layer = layer;
    }

    @Override
    public Float getWidth() {
        return texture.getWidth() / Context.getUnitSize();
    }

    @Override
    public Float getHeight() {
        return texture.getHeight() / Context.getUnitSize();
    }

    @Override
    public Vector2 getPosition() {
        return position;
    }
}
