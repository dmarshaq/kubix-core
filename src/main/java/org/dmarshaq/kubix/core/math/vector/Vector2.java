package org.dmarshaq.kubix.core.math.vector;

import org.dmarshaq.kubix.core.math.base.MathCore;
import org.dmarshaq.kubix.core.math.array.FloatArray;

public class Vector2 extends Vector<Float> implements AbstractFloatVector<Vector2> {

    /**
     * Builds 2D float vector based on the specified values.
     */
    public Vector2(float x, float y) {
        super(new FloatArray(new float[] {x, y}));

    }

    /**
     * Casts Vector<Float> to Vector2.
     * Note: new Vector2 will have reference to the same FloatArray object.
     */
    public Vector2(Vector<Float> vector2) {
        super(vector2.getValues());
    }

    @Override
    public float[] getArrayOfValues() {
        return getValues().floatArray();
    }

    @Override
    public float x() {
        return getArrayOfValues()[0];
    }

    @Override
    public float y() {
        return getArrayOfValues()[1];
    }

    @Override
    public Vector2 add(Vector<Float> vector) {
        getArrayOfValues()[0] += vector.getValues().floatArray()[0];
        getArrayOfValues()[1] += vector.getValues().floatArray()[1];
        return this;
    }

    @Override
    public Vector2 subtract(Vector<Float> vector) {
        getArrayOfValues()[0] -= vector.getValues().floatArray()[0];
        getArrayOfValues()[1] -= vector.getValues().floatArray()[1];
        return this;
    }

    @Override
    public Vector2 multiply(float scalar) {
        getArrayOfValues()[0] *= scalar;
        getArrayOfValues()[1] *= scalar;
        return this;
    }

    @Override
    public Vector2 divide(float scalar) {
        getArrayOfValues()[0] /= scalar;
        getArrayOfValues()[1] /= scalar;
        return this;
    }

    @Override
    public Vector2 normalize() {
        divide(MathCore.magnitude(this));
        return this;
    }

    @Override
    public Vector2 negate() {
        multiply(-1);
        return this;
    }
}
