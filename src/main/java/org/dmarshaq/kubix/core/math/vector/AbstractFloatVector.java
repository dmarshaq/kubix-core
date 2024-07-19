package org.dmarshaq.kubix.core.math.vector;

/**
 * AbstractFloatVector interface provides helper methods for float vectors.
 * As well as set of modification methods that return "this" vector.
 */
public interface AbstractFloatVector<T extends Vector<Float>> {

    /**
     * Returns direct access to float array of vector values.
     */
    float[] getArrayOfValues();

    /**
     * Returns float value of x-component of the vector.
     */
    default float x() {
        return 0;
    }

    /**
     * Returns float value of y-component of the vector.
     */
    default float y() {
        return 0;
    }

    /**
     * Returns float value of z-component of the vector.
     */
    default float z() {
        return 0;
    }

    /**
     * Returns float value of w-component of the vector.
     */
    default float w() {
        return 0;
    }

    /**
     * Adds input vector to this vector.
     * Note: doesn't return new float vector.
     */
    T add(Vector<Float> vector);

    /**
     * Subtracts input vector from this vector.
     * Note: doesn't return new float vector.
     */
    T subtract(Vector<Float> vector);

    /**
     * Multiplies this vector by scalar.
     * Note: doesn't return new float vector.
     */
    T multiply(float scalar);

    /**
     * Divides this vector by scalar.
     * Note: doesn't return new float vector.
     */
    T divide(float scalar);

    /**
     * Normalizes this vector.
     * Note: doesn't return new float vector.
     */
    T normalize();

    /**
     * Negates this vector.
     * Note: doesn't return new float vector.
     */
    T negate();
}
