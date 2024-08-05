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

    @Override
    public int stateOfValueInclusive(Float value) {
        return super.stateOfValueInclusive(value);
    }

    @Override
    public int stateOfValueExclusive(Float value) {
        if (isInside(value)) {
            return 0;
        }
        else if (value < getMax()) {
            return -1;
        }
        return 1;
    }

}
