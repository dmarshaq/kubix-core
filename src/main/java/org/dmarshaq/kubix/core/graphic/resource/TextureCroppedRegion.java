package org.dmarshaq.kubix.core.graphic.resource;

import lombok.Getter;
import org.dmarshaq.kubix.core.math.AbstractRectangle;
import org.dmarshaq.kubix.core.math.vector.Vector2;

@Getter
public class TextureCroppedRegion implements AbstractRectangle<Float, Vector2> {
    private final Texture host;
    private final Vector2 position;
    private final float width;
    private final float height;
    private final Vector2 percentPosition;
    private final float percentWidth;
    private final float percentHeight;

    public TextureCroppedRegion(Vector2 position, float width, float height, Texture host) {
        this.host = host;
        this.position = position;
        this.width = width;
        this.height = height;
        percentPosition = new Vector2(position.x() / host.getWidth(), position.y() / host.getHeight());
        percentWidth = width / host.getWidth();
        percentHeight = height / host.getHeight();
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