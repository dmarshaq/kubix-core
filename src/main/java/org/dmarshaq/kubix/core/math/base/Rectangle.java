package org.dmarshaq.kubix.core.math.base;

import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.ToString;

import org.dmarshaq.kubix.core.math.vector.Vector2;


/**
 * This class help to define any float Axis Aligned Rectangle.
 * By implementing AbstractRectangle interface.
 */
@Setter
@ToString
@EqualsAndHashCode
public class Rectangle implements AbstractRectangle<Float, Vector2>, AbstractShape<Float, Vector2> {
    private Vector2 position;
    private float width;
    private float height;

    /**
     * Builds 2D float rectangle based on values specified.
     * Note: position refers to bottom left corner of the rectangle.
     */
    public Rectangle(Vector2 position, float width, float height) {
        this.position = position;
        this.width = width;
        this.height = height;
    }

    /**
     * Returns new Vector2 that represents the center of the rectangle.
     */
    public Vector2 center() {
        return new Vector2(width/2, height/2).add(position);
    }

    public void setCenter(Vector2 position) {
        this.position = new Vector2(width/-2, height/-2).add(position);
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

    @Override
    public Vector2[] getVertices() {
        return new Vector2[] {
                position,
                new Vector2(width, 0).add(position),
                new Vector2(0, height).add(position),
                new Vector2(width, height).add(position)
        };
    }
}
