package org.dmarshaq.kubix.core.graphic.element;

import org.dmarshaq.kubix.core.math.base.AbstractRectangle;
import org.dmarshaq.kubix.core.math.vector.Vector;

public class TextMetrics<T extends Number, E extends Vector<T>> implements AbstractRectangle<T, E> {
    private final E position;
    private final T width;
    private final T height;

    public TextMetrics(E position, T width, T height) {
        this.position = position;
        this.width = width;
        this.height = height;
    }

    @Override
    public T getWidth() {
        return width;
    }

    @Override
    public T getHeight() {
        return height;
    }

    @Override
    public E getPosition() {
        return position;
    }
}
