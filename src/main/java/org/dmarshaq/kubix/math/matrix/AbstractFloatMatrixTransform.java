package org.dmarshaq.kubix.math.matrix;

import org.dmarshaq.kubix.core.util.BufferUtils;
import org.dmarshaq.kubix.math.MathCore;
import org.dmarshaq.kubix.math.vector.Vector;

import java.nio.FloatBuffer;

public interface AbstractFloatMatrixTransform extends AbstractFloatMatrix {

    /**
     * Returns vector representing x-axis in transform matrix, if defined.
     */
    default Vector<Float> axisVectorX() {
        return null;
    }

    /**
     * Returns vector representing y-axis in transform matrix, if defined.
     */
    default Vector<Float> axisVectorY() {
        return null;
    }

    /**
     * Returns vector representing z-axis in transform matrix, if defined.
     */
    default Vector<Float> axisVectorZ() {
        return null;
    }

    /**
     * Returns vector representing origin position in transform matrix.
     */
    Vector<Float> originVector();

    /**
     * Returns direction of x-axis in transform matrix, if axisVectorX() is defined.
     */
    default Vector<Float> right() {
        if (axisVectorX() != null)
            return MathCore.normalization(axisVectorX());
        return null;
    }

    /**
     * Returns direction of y-axis in transform matrix, if axisVectorY() is defined.
     */
    default Vector<Float> up() {
        if (axisVectorY() != null)
            return MathCore.normalization(axisVectorY());
        return null;
    }

    /**
     * Returns direction of z-axis in transform matrix, if axisVectorZ() is defined.
     */
    default Vector<Float> forward() {
        if (axisVectorZ() != null)
            return MathCore.normalization(axisVectorZ());
        return null;
    }

    /**
     * Returns world space vector from local space vector.
     */
    Vector<Float> localToWorld(Vector<Float> localVector);

    /**
     * Returns local space vector from world space vector.
     */
    Vector<Float> worldToLocal(Vector<Float> worldVector);

}
