package org.dmarshaq.kubix.core.math.matrix;

import org.dmarshaq.kubix.core.math.MathCore;
import org.dmarshaq.kubix.core.math.array.FloatArray;
import org.dmarshaq.kubix.core.math.vector.Vector;
import org.dmarshaq.kubix.core.math.vector.Vector2;
import org.dmarshaq.kubix.core.math.vector.Vector3;

/**
 * Matrix2x3 is a complete float matrix that is used for storing data that represents transforms in 2D space.
 */
public class Matrix2x3 extends Matrix<Float> implements AbstractFloatMatrixTransform<Vector2> {

    /**
     * Builds default identity float matrix 2x3.
     */
    public Matrix2x3() {
        super(new FloatArray(new float[] {
                1f, 0f, 0f,
                0f, 1f, 0f}), 2, 3);
    }

    /**
     * Casts Matrix<Float> to Matrix2x3.
     * Note: new Matrix2x3 will have reference to the same FloatArray object.
     */
    public Matrix2x3(Matrix<Float> matrix) {
        super(matrix.getElements(), 2, 3);
    }

    @Override
    public float[] getArrayOfElements() {
        return getElements().floatArray();
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

