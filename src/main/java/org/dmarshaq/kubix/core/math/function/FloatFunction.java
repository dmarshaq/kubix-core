package org.dmarshaq.kubix.core.math.function;

public enum FloatFunction implements AbstractFunction<Float> {
    LINEAR {
        @Override
        public Float function(Float x) {
            return x;
        }
    },
    PARABOLA {
        @Override
        public Float function(Float x) {
            return x * x;
        }
    },
    EXPONENT {
        @Override
        public Float function(Float x) {
            return (float) Math.pow(Math.E, x);
        }
    },
    SIN {
        @Override
        public Float function(Float x) {
            return (float) Math.sin(x);
        }
    },
    COS {
        @Override
        public Float function(Float x) {
            return (float) Math.cos(x);
        }
    },
    EASE_IN_OUT_CUBIC {
        @Override
        public Float function(Float x) {
            return x < 0.5 ? 4 * x * x * x : (float) (1 - Math.pow(-2 * x + 2, 3) / 2);
        }
    },
}
