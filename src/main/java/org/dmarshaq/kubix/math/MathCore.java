package org.dmarshaq.kubix.math;

import org.dmarshaq.kubix.math.matrix.Matrix;
import org.dmarshaq.kubix.math.matrix.Matrix4x4;
import org.dmarshaq.kubix.math.operation.*;
import org.dmarshaq.kubix.math.processor.*;

public class MathCore {

    /**
     * AXIS used to give each character a unique index based on it's position in the string.
     */
    public static final String AXIS = "xyzw";

    /**
     * OPERATION_PROCESSOR used to process generic operations.
     */
    private static final FloatProcessor OPERATION_PROCESSOR = new FloatProcessor(new IntegerProcessor(new DoubleProcessor(new LongProcessor(new ShortProcessor(new ByteProcessor(null))))));

    /**
     * Constructs new 2D vector of the desired type.
     */
    public static <T extends Number> Vector<T> vector2(T x, T y) {
        return new Vector<T>((T[])new Number[] {x, y});
    }

    /**
     * Constructs new 3D vector of the desired type.
     */
    public static <T extends Number> Vector<T> vector3(T x, T y, T z) {
        return new Vector<T>((T[])new Number[] {x, y, z});
    }

    /**
     * Constructs new 4D vector of the desired type.
     */
    public static <T extends Number> Vector<T> vector4(T x, T y, T z, T w) {
        return new Vector<T>((T[])new Number[] {x, y, z, w});
    }

    /**
     * Solves for the new vector from addition of the two input vectors.
     */
    public static <T extends Number> Vector<T> addition(Vector<T> vector1, Vector<T> vector2) {
        VectorAddition<T> summation = new VectorAddition<>(vector1, vector2);
        OPERATION_PROCESSOR.processOperation(summation);
        return summation.getResultant();
    }

    /**
     * Solves for the new vector from negation of the input vector.
     */
    public static <T extends Number> Vector<T> negation(Vector<T> vector) {
        VectorNegation<T> negation = new VectorNegation<>(vector);
        OPERATION_PROCESSOR.processOperation(negation);
        return negation.getResultant();
    }

    /**
     * Solves for the new vector from subtraction of the two input vectors.
     */
    public static <T extends Number> Vector<T> subtraction(Vector<T> vector1, Vector<T> vector2) {
        return addition(vector1, negation(vector2));
    }

    /**
     * Solves for the dot product of the two input vectors.
     */
    public static <T extends Number> T dotProduct(Vector<T> vector1, Vector<T> vector2) {
        VectorDotProduct<T> dotProduct = new VectorDotProduct<>(vector1, vector2);
        OPERATION_PROCESSOR.processOperation(dotProduct);
        return dotProduct.getProduct();
    }

    /**
     * Solves for the magnitude of the input vector.
     */
    public static <T extends Number> T magnitude(Vector<T> vector) {
        VectorMagnitude<T> magnitude = new VectorMagnitude<>(vector);
        OPERATION_PROCESSOR.processOperation(magnitude);
        return magnitude.getMagnitude();
    }

    /**
     * Solves for the new vector from scalar multiplication of the input vector.
     */
    public static <T extends Number> Vector<T> multiplication(Vector<T> vector, T scalar) {
        ScalarMultiplication<T> multiplication = new ScalarMultiplication<>(vector, scalar);
        OPERATION_PROCESSOR.processOperation(multiplication);
        return multiplication.getResultant();
    }

    /**
     * Solves for the new vector from scalar division of the input vector.
     */
    public static <T extends Number> Vector<T> division(Vector<T> vector, T scalar) {
        ScalarDivision<T> division = new ScalarDivision<>(vector, scalar);
        OPERATION_PROCESSOR.processOperation(division);
        return division.getResultant();
    }

    /**
     * Solves for the normalized new vector from the input vector.
     */
    public static <T extends Number> Vector<T> normalization(Vector<T> vector) {
        return division(vector, magnitude(vector));
    }

    /**
     * Returns new identity matrix of the specified class, rows and columns.
     */
    public static <T extends Number> Matrix<T> identityMatrix(Class<T> clas, int rows, int columns) {
        return OPERATION_PROCESSOR.buildIdentityMatrix(clas, rows, columns);
    }

    /**
     * Returns new matrix based on the specified number of rows, columns and fills it with specified value.
     */
     public static <T extends Number> Matrix<T> uniformMatrix(T value, int rows, int columns) {
        Matrix<T> matrix = new Matrix<>(rows, columns);
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                matrix.getElements()[row][col] = value;
            }
        }
        return matrix;
    }

    /**
     * Solves for the new matrix by multiplying the first matrix by the second matrix.
     */
    public static <T extends Number> Matrix<T> multiplication(Matrix<T> first, Matrix<T> second) {
        MatrixMultiplication<T> multiplication = new MatrixMultiplication<>(first, second);
        OPERATION_PROCESSOR.processOperation(multiplication);
        return multiplication.getResult();
    }

    /**
     * Solves for the new vector by multiplying the matrix by the input vector.
     */
    public static <T extends Number> Vector<T> multiplication(Matrix<T> matrix, Vector<T> vector) {
        MatrixVectorMultiplication<T> multiplication = new MatrixVectorMultiplication<>(matrix, vector);
        OPERATION_PROCESSOR.processOperation(multiplication);
        return multiplication.getResultant();
    }

    /**
     * Returns new transform based on the specified vector3, rotation and scale values.
     */
    public static Matrix4x4 TRS(Vector<Float> vector3, float angle, float scaleX, float scaleY) {
        Matrix4x4 matrix4x4 = new Matrix4x4();

        matrix4x4.getElements()[0][3] = vector3.getComponent(0);
        matrix4x4.getElements()[1][3] = vector3.getComponent(1);
        matrix4x4.getElements()[2][3] = vector3.getComponent(2);

        float r = (float) Math.toRadians(angle);
        float cos = (float) Math.cos(r);
        float sin = (float) Math.sin(r);

        matrix4x4.getElements()[0][0] = cos * scaleX;
        matrix4x4.getElements()[1][1] = cos * scaleY;
        matrix4x4.getElements()[0][1] = -sin * scaleY;
        matrix4x4.getElements()[1][0] = sin * scaleX;

        return matrix4x4;
    }

    /**
     * Returns new translate transform based on the specified vector3 value.
     */
    public static Matrix4x4 translate(Vector<Float> vector3) {
        Matrix4x4 matrix4x4 = new Matrix4x4();

        matrix4x4.getElements()[0][3] = vector3.getComponent(0);
        matrix4x4.getElements()[1][3] = vector3.getComponent(1);
        matrix4x4.getElements()[2][3] = vector3.getComponent(2);

        return matrix4x4;
    }

    /**
     * Returns new rotation transform based on the specified rotation value.
     */
    public static Matrix4x4 rotation(float angle) {
        Matrix4x4 matrix4x4 = new Matrix4x4();

        float r = (float) Math.toRadians(angle);
        float cos = (float) Math.cos(r);
        float sin = (float) Math.sin(r);

        matrix4x4.getElements()[0][0] = cos;
        matrix4x4.getElements()[1][1] = cos;
        matrix4x4.getElements()[0][1] = -sin;
        matrix4x4.getElements()[1][0] = sin;

        return matrix4x4;
    }

    /**
     * Returns new scale transform based on the specified scale values.
     */
    public static Matrix4x4 scale(float scaleX, float scaleY) {
        Matrix4x4 matrix4x4 = new Matrix4x4();

        matrix4x4.getElements()[0][0] = scaleX;
        matrix4x4.getElements()[1][1] = scaleY;

        return matrix4x4;
    }
}
