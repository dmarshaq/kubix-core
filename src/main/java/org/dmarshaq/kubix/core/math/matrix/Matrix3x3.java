package org.dmarshaq.kubix.core.math.matrix;

import org.dmarshaq.kubix.core.math.base.MathCore;
import org.dmarshaq.kubix.core.math.array.FloatArray;
import org.dmarshaq.kubix.core.math.vector.Vector2;

/**
 * Matrix3x3 is a complete float matrix that is used for storing data that represents transforms in 2D space.
 */
public class Matrix3x3 extends Matrix<Float> implements AbstractFloatMatrixTransform<Vector2, Matrix3x3> {

    /**
     * Builds default identity float matrix 2x3.
     */
    public Matrix3x3() {
        super(new FloatArray(new float[] {
                1f, 0f, 0f,
                0f, 1f, 0f,
                0f, 0f, 1f}), 3, 3);
    }

    /**
     * Builds specified float matrix 3x3.
     */
    public Matrix3x3(float[] elements) {
        super(new FloatArray(elements), 3, 3);
    }

    /**
     * Casts Matrix<Float> to Matrix3x3.
     * Note: new Matrix3x3 will have reference to the same FloatArray object.
     */
    public Matrix3x3(Matrix<Float> matrix) {
        super(matrix.getElements(), 2, 3);
    }

    @Override
    public float[] getArrayOfElements() {
        return getElements().floatArray();
    }

    @Override
    public Matrix3x3 multiply(Matrix3x3 matrix) {
        float[] arr1 = getArrayOfElements();
        float[] arr2 = matrix.getArrayOfElements();
        float[] result = new float[9];

        for (int rCol = 0; rCol < matrix.getColumns(); rCol++) {
            for (int rRow = 0; rRow < getRows(); rRow++) {
                float res = 0.0f;
                for (int col = 0; col < getColumns(); col++) {
                    res += arr1[col + rRow * getColumns()] * arr2[rCol + col * matrix.getColumns()];
                }
                result[rCol + rRow * matrix.getColumns()] = res;
            }
        }

        setElements(new FloatArray(result), 3, 3);
        return this;
    }

    @Override
    public Vector2 axisVectorX() {
        return new Vector2(getArrayOfElements()[0], getArrayOfElements()[3]);
    }

    @Override
    public Vector2 axisVectorY() {
        return new Vector2(getArrayOfElements()[1], getArrayOfElements()[4]);
    }

    @Override
    public Vector2 originVector() {
        return new Vector2(getArrayOfElements()[2], getArrayOfElements()[5]);
    }

    @Override
    public Vector2 localToWorld(Vector2 localVector) {
        Vector2 xVector = axisVectorX().multiply(localVector.getValues().floatArray()[0]);
        Vector2 yVector = axisVectorY().multiply(localVector.getValues().floatArray()[1]);

        return xVector.add(yVector).add(originVector());
    }

    @Override
    public Vector2 worldToLocal(Vector2 worldVector) {
        Vector2 relativeVector = new Vector2(MathCore.subtraction(worldVector, originVector()));

        float x = MathCore.dotProduct(relativeVector, right()) / MathCore.magnitude(axisVectorX());
        float y = MathCore.dotProduct(relativeVector, up()) / MathCore.magnitude(axisVectorY());

        return new Vector2(x, y);
    }
}

