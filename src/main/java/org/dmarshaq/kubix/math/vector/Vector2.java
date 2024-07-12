package org.dmarshaq.kubix.math.vector;

import org.dmarshaq.kubix.math.MathCore;
import org.dmarshaq.kubix.math.array.FloatArray;


public class Vector2 extends Vector<Float> implements AbstractFloatVector {

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
    public void add(Vector<Float> vector) {
        getArrayOfValues()[0] += vector.getValues().floatArray()[0];
        getArrayOfValues()[1] += vector.getValues().floatArray()[1];
    }

    @Override
    public void subtract(Vector<Float> vector) {
        getArrayOfValues()[0] -= vector.getValues().floatArray()[0];
        getArrayOfValues()[1] -= vector.getValues().floatArray()[1];
    }

    @Override
    public void multiply(float scalar) {
        getArrayOfValues()[0] *= scalar;
        getArrayOfValues()[1] *= scalar;
    }

    @Override
    public void divide(float scalar) {
        getArrayOfValues()[0] /= scalar;
        getArrayOfValues()[1] /= scalar;
    }

    @Override
    public void normalize() {
        divide(MathCore.magnitude(this));
    }

    @Override
    public void negate() {
        multiply(-1);
    }
}
