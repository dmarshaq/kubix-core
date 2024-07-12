package org.dmarshaq.kubix.math.vector;

public interface AbstractFloatVector {

    /**
     * Returns direct access to float array of vector values.
     */
    float[] getArrayOfValues();

    /**
     * Adds input vector to this vector.
     * Note: doesn't return new float vector.
     */
    void add(Vector<Float> vector);

    /**
     * Subtracts input vector from this vector.
     * Note: doesn't return new float vector.
     */
    void subtract(Vector<Float> vector);

    /**
     * Multiplies this vector by scalar.
     * Note: doesn't return new float vector.
     */
    void multiply(float scalar);

    /**
     * Divides this vector by scalar.
     * Note: doesn't return new float vector.
     */
    void divide(float scalar);

    /**
     * Normalizes this vector.
     * Note: doesn't return new float vector.
     */
    void normalize();

    /**
     * Negates this vector.
     * Note: doesn't return new float vector.
     */
    void negate();
}
