package org.dmarshaq.kubix.math;

import org.dmarshaq.kubix.math.vector.Vector;

public interface AbstractRectangle<T extends Number, E extends Vector<T>> {
    T getWidth();
    T getHeight();
    E getPosition();
}
