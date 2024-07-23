package org.dmarshaq.kubix.core.math.base;

import org.dmarshaq.kubix.core.math.vector.Vector;

/**
 * AbstractRectangle interface provides getter methods that define rectangle in 2D space.
 */
public interface AbstractRectangle<T extends Number, E extends Vector<T>> {
    T getWidth();
    T getHeight();
    E getPosition();
}
