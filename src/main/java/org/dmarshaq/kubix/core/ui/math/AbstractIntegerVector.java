package org.dmarshaq.kubix.core.ui.math;

import org.dmarshaq.kubix.core.math.vector.Vector;

/**
 * AbstractIntegerVector interface provides helper methods for int vectors.
 * As well as set of modification methods that return "this" vector.
 */
public interface AbstractIntegerVector<T extends Vector<Integer>> {

    /**
     * Returns direct access to int array of vector values.
     */
    int[] getArrayOfValues();

    /**
     * Returns int value of x-component of the vector.
     */
    default int x() {
        return 0;
    }

    /**
     * Returns int value of y-component of the vector.
     */
    default int y() {
        return 0;
    }

    /**
     * Returns int value of z-component of the vector.
     */
    default int z() {
        return 0;
    }

    /**
     * Returns int value of w-component of the vector.
     */
    default int w() {
        return 0;
    }

    /**
     * Adds input vector to this vector.
     * Note: doesn't return new int vector.
     */
    T add(Vector<Integer> vector);

    /**
     * Subtracts input vector from this vector.
     * Note: doesn't return new int vector.
     */
    T subtract(Vector<Integer> vector);

    /**
     * Multiplies this vector by scalar.
     * Note: doesn't return new int vector.
     */
    T multiply(int scalar);

    /**
     * Divides this vector by scalar.
     * Note: doesn't return new int vector.
     */
    T divide(int scalar);

    /**
     * Normalizes this vector.
     * Note: doesn't return new int vector.
     */
    T normalize();

    /**
     * Negates this vector.
     * Note: doesn't return new int vector.
     */
    T negate();
}
