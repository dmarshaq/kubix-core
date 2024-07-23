package org.dmarshaq.kubix.core.math.matrix;

import org.dmarshaq.kubix.core.math.base.MathCore;
import org.dmarshaq.kubix.core.math.array.FloatArray;
import org.dmarshaq.kubix.core.math.vector.Vector3;

/**
 * Matrix4x4 is a complete float matrix that is used for storing various data that can define transforms in 3D space.
 * It's also used to act as projection matrix.
 */
public class Matrix4x4 extends Matrix<Float> implements AbstractFloatMatrixTransform<Vector3, Matrix4x4> {

    /**
     * Builds default identity float matrix 4x4.
     */
    public Matrix4x4() {
        super(new FloatArray(new float[] {
                1f, 0f, 0f, 0f,
                0f, 1f, 0f, 0f,
                0f, 0f, 1f, 0f,
                0f, 0f, 0f, 1f}), 4, 4);
    }

    /**
     * Builds specified float matrix 4x4.
     */
    public Matrix4x4(float[] elements) {
        super(new FloatArray(elements), 4, 4);
    }

    /**
     * Casts Matrix<Float> to Matrix4x4.
     * Note: new Matrix4x4 will have reference to the same FloatArray object.
     */
    public Matrix4x4(Matrix<Float> matrix) {
        super(matrix.getElements(), 4, 4);
    }

    @Override
    public float[] getArrayOfElements() {
        return getElements().floatArray();
    }

    @Override
    public Matrix4x4 multiply(Matrix4x4 matrix) {
        float[] arr1 = getArrayOfElements();
        float[] arr2 = matrix.getArrayOfElements();
        float[] result = new float[16];

        for (int rCol = 0; rCol < matrix.getColumns(); rCol++) {
            for (int rRow = 0; rRow < getRows(); rRow++) {
                float res = 0.0f;
                for (int col = 0; col < getColumns(); col++) {
                    res += arr1[col + rRow * getColumns()] * arr2[rCol + col * matrix.getColumns()];
                }
                result[rCol + rRow * matrix.getColumns()] = res;
            }
        }

        setElements(new FloatArray(result), 4, 4);
        return this;
    }


    @Override
    public Vector3 axisVectorX() {
        return new Vector3(getArrayOfElements()[0], getArrayOfElements()[4], getArrayOfElements()[8]);
    }

    @Override
    public Vector3 axisVectorY() {
        return new Vector3(getArrayOfElements()[1], getArrayOfElements()[5], getArrayOfElements()[9]);
    }

    @Override
    public Vector3 axisVectorZ() {
        return new Vector3(getArrayOfElements()[2], getArrayOfElements()[6], getArrayOfElements()[10]);
    }

    @Override
    public Vector3 originVector() {
        return new Vector3(getArrayOfElements()[3], getArrayOfElements()[7], getArrayOfElements()[11]);
    }

    @Override
    public Vector3 localToWorld(Vector3 localVector) {
        Vector3 xVector = axisVectorX().multiply(localVector.getValues().floatArray()[0]);
        Vector3 yVector = axisVectorY().multiply(localVector.getValues().floatArray()[1]);
        Vector3 zVector = axisVectorZ().multiply(localVector.getValues().floatArray()[2]);

        return xVector.add(yVector).add(zVector).add(originVector());
    }

    @Override
    public Vector3 worldToLocal(Vector3 worldVector) {
        Vector3 relativeVector = new Vector3(MathCore.subtraction(worldVector, originVector()));

        float x = MathCore.dotProduct(relativeVector, right()) / MathCore.magnitude(axisVectorX());
        float y = MathCore.dotProduct(relativeVector, up()) / MathCore.magnitude(axisVectorY());
        float z = MathCore.dotProduct(relativeVector, forward()) / MathCore.magnitude(axisVectorZ());

        return new Vector3(x, y, z);
    }
}
