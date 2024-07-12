package org.dmarshaq.kubix.math.vector;

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
