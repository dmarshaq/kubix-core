package org.dmarshaq.kubix.math;

import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.ToString;

import org.dmarshaq.kubix.math.vector.Vector2;

@Setter
@ToString
@EqualsAndHashCode
public class Rectangle implements AbstractRectangle<Float, Vector2> {
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
     * Returns new Vector2 that
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

}
