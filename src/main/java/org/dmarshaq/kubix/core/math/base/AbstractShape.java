package org.dmarshaq.kubix.core.math.base;

import org.dmarshaq.kubix.core.math.vector.Vector;
import org.dmarshaq.kubix.core.math.vector.Vector2;

/**
 * Interface that helps define any shape;
 */
public interface AbstractShape<T extends Number, E extends Vector<T>> {
    /**
     * Gets array of Vector positions of each vertex in the shape.
     * @return Vertices positions.
     */
    E[] getVertices();
}
