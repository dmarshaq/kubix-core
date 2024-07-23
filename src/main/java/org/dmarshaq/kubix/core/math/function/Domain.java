package org.dmarshaq.kubix.core.math.function;

import lombok.Setter;
import lombok.ToString;
import org.dmarshaq.kubix.core.math.base.MathCore;

import java.util.Objects;

@Setter
@ToString
public class Domain<T extends Number> implements AbstractDomain<T> {
    private T min;
    private T max;

    public Domain(T min, T max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public T getMin() {
        return min;
    }

    @Override
    public T getMax() {
        return max;
    }

    @Override
    public boolean isOutside(T value) {
        return MathCore.isOutsideDomain(this, value);
    }

    @Override
    public boolean isInside(T value) {
        return MathCore.isInsideDomain(this, value);
    }

    @Override
    public boolean isOnBoundary(T value) {
        return Objects.equals(value, min) || Objects.equals(value, max);
    }
}
