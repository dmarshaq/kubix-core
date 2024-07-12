package org.dmarshaq.kubix.math.matrix;

import org.dmarshaq.kubix.math.MathCore;
import org.dmarshaq.kubix.math.array.FloatArray;
import org.dmarshaq.kubix.math.vector.Vector;
import org.dmarshaq.kubix.math.vector.Vector2;

/**
 * Matrix2x3 is a complete float matrix that is used for storing data that represents transforms in 2D space.
 */
public class Matrix2x3 extends Matrix<Float> implements AbstractFloatMatrixTransform {

    /**
     * Builds default identity float matrix 2x3.
     */
    public Matrix2x3() {
        super(new FloatArray(new float[] {
                1f, 0f, 0f,
                0f, 1f, 0f}), 2, 3);
    }

    @Override
    public float[] getArrayOfElements() {
        return getElements().floatArray();
    }

    @Override
    public Vector<Float> axisVectorX() {
        return new Vector2(getArrayOfElements()[0], getArrayOfElements()[3]);
    }

    @Override
    public Vector<Float> axisVectorY() {
        return new Vector2(getArrayOfElements()[1], getArrayOfElements()[4]);
    }

    @Override
    public Vector<Float> originVector() {
        return new Vector2(getArrayOfElements()[2], getArrayOfElements()[5]);
    }

    @Override
    public Vector<Float> localToWorld(Vector<Float> localVector) {
        Vector2 xVector = new Vector2(axisVectorX());
        Vector2 yVector = new Vector2(axisVectorY());

        xVector.multiply(localVector.getValues().floatArray()[0]);
        yVector.multiply(localVector.getValues().floatArray()[1]);

        xVector.add(yVector);
        xVector.add(originVector());

        return xVector;
    }

    @Override
    public Vector<Float> worldToLocal(Vector<Float> worldVector) {
        Vector2 relativeVector = new Vector2(originVector());
        relativeVector.negate();
        relativeVector.add(worldVector);

        float x = MathCore.dotProduct(relativeVector, right()) / MathCore.magnitude(axisVectorX());
        float y = MathCore.dotProduct(relativeVector, up()) / MathCore.magnitude(axisVectorY());

        return new Vector2(x, y);
    }
}

