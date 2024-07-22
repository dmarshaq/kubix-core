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
    ;
}
