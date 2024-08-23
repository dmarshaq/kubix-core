package org.dmarshaq.kubix.core.math.base;

import org.dmarshaq.kubix.core.math.vector.Vector2;

/**
 * Interface that helps define any shape;
 */
public interface AbstractShape {
    /**
     * Gets array of Vector2 positions of each vertex in the shape.
     * @return Vertices positions.
     */
    Vector2[] getVertices();
}
