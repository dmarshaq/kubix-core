package org.dmarshaq.kubix.core.graphic.element;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.dmarshaq.kubix.core.app.Context;
import org.dmarshaq.kubix.core.graphic.base.Layer;
import org.dmarshaq.kubix.core.graphic.base.Shader;
import org.dmarshaq.kubix.core.graphic.base.TextureCroppedRegion;
import org.dmarshaq.kubix.core.math.base.AbstractRectangle;
import org.dmarshaq.kubix.core.math.vector.Vector2;

import java.awt.*;

import static org.dmarshaq.kubix.core.graphic.base.Texture.NO_TEXTURE;

@Getter
@Setter
@ToString
public class Sprite implements AbstractRectangle<Float, Vector2> {
    private final Vector2 position;
    private TextureCroppedRegion texture;
    private Shader shader;
    private Layer layer;
    private Color color;


    public Sprite(Vector2 position, TextureCroppedRegion texture, Shader shader, Layer layer) {
        this.texture = texture;
        this.position = position;
        this.shader = shader;
        this.layer = layer;
    }

    public Sprite(Vector2 position, Color color, Shader shader, Layer layer) {
        this.color = color;
        this.texture = NO_TEXTURE.getTileSet()[0];
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
