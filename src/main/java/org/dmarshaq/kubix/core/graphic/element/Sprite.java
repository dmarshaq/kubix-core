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
    private float width;
    private float height;
    private TextureCroppedRegion texture;
    private Color color;
    private Shader shader;
    private Layer layer;

    public Sprite(Vector2 position, TextureCroppedRegion texture, Shader shader, Layer layer) {
        this.position = position;
        this.width = texture.getWidth() / Context.getUnitSize();
        this.height = texture.getHeight() / Context.getUnitSize();
        this.texture = texture;
        this.color = DEFAULT_COLOR;
        this.shader = shader;
        this.layer = layer;

    }

    public Sprite(Vector2 position, float width, float height, Color color, Shader shader, Layer layer) {
        this.position = position;
        this.width = width;
        this.height = height;
        this.texture = Texture.NO_TEXTURE_REGION;
        this.color = color;
        this.shader = shader;
        this.layer = layer;
    }

    @Override
    public Float getWidth() {
        return width;
    }

    @Override
    public Float getHeight() {
        return height;
    }

    @Override
    public Vector2 getPosition() {
        return position;
    }
}
