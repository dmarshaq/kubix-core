package org.dmarshaq.kubix.core.math.function;

public interface AbstractDomain<T extends Number> {
    T getMin();
    T getMax();
    boolean isOutside(T value);
    boolean isInside(T value);
    boolean isOnBoundary(T value);
}
