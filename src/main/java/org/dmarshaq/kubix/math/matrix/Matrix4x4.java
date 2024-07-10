package org.dmarshaq.kubix.math.matrix;

import org.dmarshaq.kubix.math.matrix.Matrix;

public class Matrix4x4 extends Matrix<Float> {
    /**
     * Builds default identity matrix 4x4.
     */
    public Matrix4x4() {
        super(new Float[][] {
                {1f, 0f, 0f, 0f},
                {0f, 1f, 0f, 0f},
                {0f, 0f, 1f, 0f},
                {0f, 0f, 0f, 1f}});
    }

}
