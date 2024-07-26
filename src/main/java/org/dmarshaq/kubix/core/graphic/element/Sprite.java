package org.dmarshaq.kubix.core.graphic.element;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.dmarshaq.kubix.core.app.Context;
import org.dmarshaq.kubix.core.graphic.base.layer.Layer;
import org.dmarshaq.kubix.core.graphic.base.Shader;
import org.dmarshaq.kubix.core.graphic.base.texture.Texture;
import org.dmarshaq.kubix.core.graphic.base.texture.TextureCroppedRegion;
import org.dmarshaq.kubix.core.math.base.AbstractRectangle;
import org.dmarshaq.kubix.core.math.vector.Vector2;

import java.awt.*;


@Getter
@Setter
@ToString
public class Sprite implements AbstractRectangle<Float, Vector2> {
    public static final Color DEFAULT_COLOR = new Color(255, 255, 255, 255);

    private final Vector2 position;
    private TextureCroppedRegion texture;
    private Shader shader;
    private Layer layer;
    private Color color;


    public Sprite(Vector2 position, TextureCroppedRegion texture, Shader shader, Layer layer) {
        this.color = DEFAULT_COLOR;
        this.texture = texture;
        this.position = position;
        this.shader = shader;
        this.layer = layer;
    }

    public Sprite(Vector2 position, Color color, Shader shader, Layer layer) {
        this.color = color;
        this.texture = Texture.NO_TEXTURE_REGION;
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
