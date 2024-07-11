package org.dmarshaq.kubix.math.matrix;

import org.dmarshaq.kubix.math.array.FloatArray;

/**
 * Matrix2x3 is a complete float matrix that is used for storing data that represents transforms in 2D space.
 */
public class Matrix2x3 extends Matrix<Float> {

    /**
     * Builds default identity float matrix 2x3.
     */
    public Matrix2x3() {
        super(new FloatArray(new float[] {
                1f, 0f, 0f,
                0f, 1f, 0f}), 2, 3);
    }
}
