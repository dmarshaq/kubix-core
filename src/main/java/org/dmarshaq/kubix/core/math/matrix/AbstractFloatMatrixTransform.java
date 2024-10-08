package org.dmarshaq.kubix.core.math.matrix;

import org.dmarshaq.kubix.core.math.base.MathCore;
import org.dmarshaq.kubix.core.math.vector.Vector;

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
        return null;
    }

    /**
     * Returns new vector direction of y-axis in transform matrix, if axisVectorY() is defined.
     */
    default T up() {
        return null;
    }

    /**
     * Returns new vector direction of z-axis in transform matrix, if axisVectorZ() is defined.
     */
    default T forward() {
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
