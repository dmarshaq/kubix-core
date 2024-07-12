package org.dmarshaq.kubix.math.matrix;

import org.dmarshaq.kubix.math.MathCore;
import org.dmarshaq.kubix.math.array.FloatArray;
import org.dmarshaq.kubix.math.vector.Vector;
import org.dmarshaq.kubix.math.vector.Vector2;
import org.dmarshaq.kubix.math.vector.Vector3;

/**
 * Matrix3x4 is a complete float matrix that is used for storing data that represents transforms in 3D space.
 */
public class Matrix3x4 extends Matrix<Float> implements AbstractFloatMatrixTransform<Vector3> {

    /**
     * Builds default identity float matrix 3x4.
     */
    public Matrix3x4() {
        super(new FloatArray(new float[] {
                1f, 0f, 0f, 0f,
                0f, 1f, 0f, 0f,
                0f, 0f, 1f, 0f}), 3, 4);
    }


    @Override
    public float[] getArrayOfElements() {
        return getElements().floatArray();
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
        Vector3 relativeVector = worldVector.subtract(originVector());

        float x = MathCore.dotProduct(relativeVector, right()) / MathCore.magnitude(axisVectorX());
        float y = MathCore.dotProduct(relativeVector, up()) / MathCore.magnitude(axisVectorY());
        float z = MathCore.dotProduct(relativeVector, forward()) / MathCore.magnitude(axisVectorZ());

        return new Vector3(x, y, z);
    }
}
