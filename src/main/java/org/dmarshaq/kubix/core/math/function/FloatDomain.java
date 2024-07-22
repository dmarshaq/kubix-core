package org.dmarshaq.kubix.core.math.function;

public class FloatDomain extends Domain<Float> {
    public FloatDomain(float min, float max) {
        super(min, max);
    }

    @Override
    public boolean isOutside(Float value) {
        return value < getMin() || value > getMax();
    }

    @Override
    public boolean isInside(Float value) {
        return value > getMin() && value < getMax();
    }

}
