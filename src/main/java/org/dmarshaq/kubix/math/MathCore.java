package org.dmarshaq.kubix.math;

import org.dmarshaq.kubix.math.operations.*;
import org.dmarshaq.kubix.math.processors.*;

public class MathCore {

    public static final String AXIS = "xyzw";
    public static final FloatProcessor OPERATION_PROCESSOR = new FloatProcessor(new IntegerProcessor(new DoubleProcessor(new LongProcessor(new ShortProcessor(new ByteProcessor(null))))));

    /**
     * Constructs new 2D Vector of the desired type.
     */
    public static <T extends Number> Vector<T> vector2(T x, T y) {
        return new Vector<T>((T[])new Number[] {x, y});
    }

    /**
     * Constructs new 3D Vector of the desired type.
     */
    public static <T extends Number> Vector<T> vector3(T x, T y, T z) {
        return new Vector<T>((T[])new Number[] {x, y, z});
    }

    /**
     * Constructs new 4D Vector of the desired type.
     */
    public static <T extends Number> Vector<T> vector4(T x, T y, T z, T w) {
        return new Vector<T>((T[])new Number[] {x, y, z, w});
    }

    /**
     * Solves for the new Vector from addition of the two input Vectors.
     */
    public static <T extends Number> Vector<T> addition(Vector<T> vector1, Vector<T> vector2) {
        VectorAddition<T> summation = new VectorAddition<>(vector1, vector2);
        OPERATION_PROCESSOR.processOperation(summation);
        return summation.getResultant();
    }

    /**
     * Solves for the new Vector from negation of the input Vector.
     */
    public static <T extends Number> Vector<T> negation(Vector<T> vector) {
        VectorNegation<T> negation = new VectorNegation<>(vector);
        OPERATION_PROCESSOR.processOperation(negation);
        return negation.getResultant();
    }

    /**
     * Solves for the new Vector from subtraction of the two input Vectors.
     */
    public static <T extends Number> Vector<T> subtraction(Vector<T> vector1, Vector<T> vector2) {
        return addition(vector1, negation(vector2));
    }

    /**
     * Solves for the Dot Product of the two input Vectors.
     */
    public static <T extends Number> T dotProduct(Vector<T> vector1, Vector<T> vector2) {
        VectorDotProduct<T> dotProduct = new VectorDotProduct<>(vector1, vector2);
        OPERATION_PROCESSOR.processOperation(dotProduct);
        return dotProduct.getProduct();
    }

    /**
     * Solves for the Magnitude of the input Vector.
     */
    public static <T extends Number> T magnitude(Vector<T> vector) {
        VectorMagnitude<T> magnitude = new VectorMagnitude<>(vector);
        OPERATION_PROCESSOR.processOperation(magnitude);
        return magnitude.getMagnitude();
    }

    /**
     * Solves for the new Vector from scalar multiplication of the input Vector.
     */
    public static <T extends Number> Vector<T> multiplication(Vector<T> vector, T scalar) {
        ScalarMultiplication<T> multiplication = new ScalarMultiplication<>(vector, scalar);
        OPERATION_PROCESSOR.processOperation(multiplication);
        return multiplication.getResultant();
    }

    /**
     * Solves for the new Vector from scalar division of the input Vector.
     */
    public static <T extends Number> Vector<T> division(Vector<T> vector, T scalar) {
        ScalarDivision<T> division = new ScalarDivision<>(vector, scalar);
        OPERATION_PROCESSOR.processOperation(division);
        return division.getResultant();
    }

    /**
     * Solves for the normalized new Vector from the input Vector.
     */
    public static <T extends Number> Vector<T> normalization(Vector<T> vector) {
        return division(vector, magnitude(vector));
    }

}
