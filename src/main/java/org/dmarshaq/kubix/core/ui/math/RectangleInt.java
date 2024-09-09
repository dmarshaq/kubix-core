package org.dmarshaq.kubix.core.ui.math;

import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.ToString;
import org.dmarshaq.kubix.core.math.base.AbstractRectangle;
import org.dmarshaq.kubix.core.math.base.AbstractShape;
import org.dmarshaq.kubix.core.math.vector.Vector2;


/**
 * This class help to define any int Axis Aligned Rectangle.
 * By implementing AbstractRectangle interface.
 */
@Setter
@ToString
@EqualsAndHashCode
public class RectangleInt implements AbstractRectangle<Integer, Vector2Int>, AbstractShape<Integer, Vector2Int> {
    private Vector2Int position;
    private int width;
    private int height;

    /**
     * Builds 2D int rectangle based on values specified.
     * Note: position refers to bottom left corner of the rectangle.
     */
    public RectangleInt(Vector2Int position, int width, int height) {
        this.position = position;
        this.width = width;
        this.height = height;
    }

    /**
     * Returns new Vector2Int that represents the center of the rectangle.
     */
    public Vector2Int center() {
        return new Vector2Int(width/2, height/2).add(position);
    }

    public void setCenter(Vector2Int position) {
        this.position = new Vector2Int(width/-2, height/-2).add(position);
    }

    @Override
    public Integer getWidth() {
        return width;
    }

    @Override
    public Integer getHeight() {
        return height;
    }

    @Override
    public Vector2Int getPosition() {
        return position;
    }

    @Override
    public Vector2Int[] getVertices() {
        return new Vector2Int[] {
                position,
                new Vector2Int(width, 0).add(position),
                new Vector2Int(0, height).add(position),
                new Vector2Int(width, height).add(position)
        };
    }
}
