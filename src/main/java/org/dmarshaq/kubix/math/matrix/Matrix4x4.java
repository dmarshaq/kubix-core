package org.dmarshaq.kubix.math.matrix;

import org.dmarshaq.kubix.math.array.FloatArray;
import org.dmarshaq.kubix.math.array.NumberArray;

/**
 * Matrix4x4 is a complete float matrix that is used for storing various data that can define projections, transforms in 3D space.
 * It's main advantage is ability to act as projection matrix.
 */
public class Matrix4x4 extends Matrix<Float> {

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
}
