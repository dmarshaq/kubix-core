package org.dmarshaq.kubix.core.math.matrix;

import org.dmarshaq.kubix.core.util.BufferUtils;

import java.nio.FloatBuffer;

public interface AbstractFloatMatrix {

    /**
     * Returns direct access to float array of matrix elements.
     */
    float[] getArrayOfElements();

    /**
     * Returns FloatBuffer from matrix elements.
     */
    default FloatBuffer toFloatBuffer() {
        return BufferUtils.createFloatBuffer(getArrayOfElements());
    }
}
