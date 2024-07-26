package org.dmarshaq.kubix.core.graphic.base.texture;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.dmarshaq.kubix.core.app.Context;
import org.dmarshaq.kubix.core.math.base.AbstractRectangle;
import org.dmarshaq.kubix.core.math.vector.Vector2;

@Getter
@EqualsAndHashCode
@ToString
public class TextureCroppedRegion implements AbstractRectangle<Float, Vector2> {
    private final Texture host;
    private final Vector2 position;
    private final float width;
    private final float height;

    public TextureCroppedRegion(Vector2 position, float width, float height, Texture host) {
        this.host = host;
        this.position = position;
        this.width = width;
        this.height = height;
    }

    public Vector2 getPercentPosition() {
        return new Vector2(position.x() / host.getWidth(), position.y() / host.getHeight());
    }

    public float getPercentWidth() {
        return width / host.getWidth();
    }

    public float getPercentHeight() {
        return height / host.getHeight();
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