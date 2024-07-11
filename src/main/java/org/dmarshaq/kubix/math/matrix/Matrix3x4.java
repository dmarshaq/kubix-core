package org.dmarshaq.kubix.math.matrix;

import org.dmarshaq.kubix.math.array.FloatArray;

/**
 * Matrix3x4 is a complete float matrix that is used for storing data that represents transforms in 3D space.
 */
public class Matrix3x4 extends Matrix<Float> {

    /**
     * Builds default identity float matrix 3x4.
     */
    public Matrix3x4() {
        super(new FloatArray(new float[] {
                1f, 0f, 0f, 0f,
                0f, 1f, 0f, 0f,
                0f, 0f, 1f, 0f}), 3, 4);
    }
}
