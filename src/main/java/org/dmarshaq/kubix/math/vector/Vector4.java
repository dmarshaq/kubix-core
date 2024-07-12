package org.dmarshaq.kubix.math.vector;

import org.dmarshaq.kubix.math.MathCore;
import org.dmarshaq.kubix.math.array.FloatArray;

import java.util.Arrays;


public class Vector4 extends Vector<Float> implements  AbstractFloatVector<Vector4> {

    /**
     * Builds 4D float vector based on the specified values.
     */
    public Vector4(float x, float y, float z, float w) {
        super(new FloatArray(new float[] {x, y, z, w}));
    }

    /**
     * Casts Vector<Float> to Vector4.
     * Note: new Vector4 will have reference to the same FloatArray object.
     */
    public Vector4(Vector<Float> vector4) {
        super(vector4.getValues());
    }

    @Override
    public float[] getArrayOfValues() {
        return getValues().floatArray();
    }

    @Override
    public Vector4 add(Vector<Float> vector) {
        getArrayOfValues()[0] += vector.getValues().floatArray()[0];
        getArrayOfValues()[1] += vector.getValues().floatArray()[1];
        getArrayOfValues()[2] += vector.getValues().floatArray()[2];
        getArrayOfValues()[3] += vector.getValues().floatArray()[3];
        return this;
    }

    @Override
    public Vector4 subtract(Vector<Float> vector) {
        getArrayOfValues()[0] -= vector.getValues().floatArray()[0];
        getArrayOfValues()[1] -= vector.getValues().floatArray()[1];
        getArrayOfValues()[2] -= vector.getValues().floatArray()[2];
        getArrayOfValues()[3] -= vector.getValues().floatArray()[3];
        return this;
    }

    @Override
    public Vector4 multiply(float scalar) {
        getArrayOfValues()[0] *= scalar;
        getArrayOfValues()[1] *= scalar;
        getArrayOfValues()[2] *= scalar;
        getArrayOfValues()[3] *= scalar;
        return this;
    }

    @Override
    public Vector4 divide(float scalar) {
        getArrayOfValues()[0] /= scalar;
        getArrayOfValues()[1] /= scalar;
        getArrayOfValues()[2] /= scalar;
        getArrayOfValues()[3] /= scalar;
        return this;
    }

    @Override
    public Vector4 normalize() {
        divide(MathCore.magnitude(this));
        return this;
    }

    @Override
    public Vector4 negate() {
        multiply(-1);
        return this;
    }
}
