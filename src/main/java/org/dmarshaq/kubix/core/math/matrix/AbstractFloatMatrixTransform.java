package org.dmarshaq.kubix.core.math.matrix;

import org.dmarshaq.kubix.core.util.BufferUtils;
import org.dmarshaq.kubix.core.math.MathCore;
import org.dmarshaq.kubix.core.math.vector.Vector;

import java.nio.FloatBuffer;

public interface AbstractFloatMatrixTransform<T extends Vector<Float>, E extends Matrix<Float>> extends AbstractFloatMatrix<E> {

    /**
     * Returns new vector representing x-axis in transform matrix, if defined.
     */
    default T axisVectorX() {
        return null;
    }

    /**
     * Returns new vector representing y-axis in transform matrix, if defined.
     */
    default T axisVectorY() {
        return null;
    }

    /**
     * Returns new vector representing z-axis in transform matrix, if defined.
     */
    default T axisVectorZ() {
        return null;
    }

    /**
     * Returns new vector representing origin position in transform matrix.
     */
    T originVector();

    /**
     * Returns new vector direction of x-axis in transform matrix, if axisVectorX() is defined.
     */
    default T right() {
        if (axisVectorX() != null)
            return (T) MathCore.normalization(axisVectorX());
        return null;
    }

    /**
     * Returns new vector direction of y-axis in transform matrix, if axisVectorY() is defined.
     */
    default T up() {
        if (axisVectorY() != null)
            return (T) MathCore.normalization(axisVectorY());
        return null;
    }

    /**
     * Returns new vector direction of z-axis in transform matrix, if axisVectorZ() is defined.
     */
    default T forward() {
        if (axisVectorZ() != null)
            return (T) MathCore.normalization(axisVectorZ());
        return null;
    }

    /**
     * Returns new world space vector from local space vector.
     */
    T localToWorld(T localVector);

    /**
     * Returns new local space vector from world space vector.
     */
    T worldToLocal(T worldVector);

}
