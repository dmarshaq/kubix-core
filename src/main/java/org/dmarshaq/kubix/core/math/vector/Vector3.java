package org.dmarshaq.kubix.core.math.vector;

import org.dmarshaq.kubix.core.math.base.MathCore;
import org.dmarshaq.kubix.core.math.array.FloatArray;

public class Vector3 extends Vector<Float> implements AbstractFloatVector<Vector3> {

    /**
     * Builds 3D float vector based on the specified values.
     */
    public Vector3(float x, float y, float z) {
        super(new FloatArray(new float[] {x, y, z}));
    }

    /**
     * Casts Vector<Float> to Vector3.
     * Note: new Vector3 will have reference to the same FloatArray object.
     */
    public Vector3(Vector<Float> vector3) {
        super(vector3.getValues());
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
    public float z() {
        return getArrayOfValues()[2];
    }

    @Override
    public Vector3 add(Vector<Float> vector) {
        getArrayOfValues()[0] += vector.getValues().floatArray()[0];
        getArrayOfValues()[1] += vector.getValues().floatArray()[1];
        getArrayOfValues()[2] += vector.getValues().floatArray()[2];
        return this;
    }

    @Override
    public Vector3 subtract(Vector<Float> vector) {
        getArrayOfValues()[0] -= vector.getValues().floatArray()[0];
        getArrayOfValues()[1] -= vector.getValues().floatArray()[1];
        getArrayOfValues()[2] -= vector.getValues().floatArray()[2];
        return this;
    }

    @Override
    public Vector3 multiply(float scalar) {
        getArrayOfValues()[0] *= scalar;
        getArrayOfValues()[1] *= scalar;
        getArrayOfValues()[2] *= scalar;
        return this;
    }

    @Override
    public Vector3 divide(float scalar) {
        getArrayOfValues()[0] /= scalar;
        getArrayOfValues()[1] /= scalar;
        getArrayOfValues()[2] /= scalar;
        return this;
    }

    @Override
    public Vector3 normalize() {
        divide(MathCore.magnitude(this));
        return this;
    }

    @Override
    public Vector3 negate() {
        multiply(-1);
        return this;
    }
}
