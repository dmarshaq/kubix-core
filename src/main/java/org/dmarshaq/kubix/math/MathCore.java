package org.dmarshaq.kubix.math;

import org.dmarshaq.kubix.math.matrix.Matrix;
import org.dmarshaq.kubix.math.processor.*;
import org.dmarshaq.kubix.math.vector.Vector;

import java.util.Arrays;

public class MathCore {

    /**
     * AXIS used to give each character a unique index based on it's position in the string.
     */
    public static final String AXIS = "xyzw";

    /**
     * OPERATION_PROCESSOR used to process generic operations.
     */
    private static final FloatProcessor OPERATION_PROCESSOR = new FloatProcessor(null);

    /**
     * Returns new Vector of desired type and any length.
     */
    public static <T extends Number> Vector<T> vector2(Class<T> clas, int length) {

        return OPERATION_PROCESSOR.buildVector(clas, length);
    }

    /**
     * Returns new 2D vector of the desired type.
     */
    public static <T extends Number> Vector<T> vector2(T x, T y) {
        return OPERATION_PROCESSOR.buildVector(x, y);
    }

    /**
     * Returns new 3D vector of the desired type.
     */
    public static <T extends Number> Vector<T> vector3(T x, T y, T z) {
        return OPERATION_PROCESSOR.buildVector(x, y, z);
    }

    /**
     * Returns new 4D vector of the desired type.
     */
    public static <T extends Number> Vector<T> vector4(T x, T y, T z, T w) {
        return OPERATION_PROCESSOR.buildVector(x, y, z, w);
    }

    /**
     * Solves for the new vector from addition of the two input vectors.
     */
    public static <T extends Number> Vector<T> addition(Vector<T> first, Vector<T> second) {
        return OPERATION_PROCESSOR.vectorAddition(first, second);
    }

    /**
     * Solves for the new vector from negation of the input vector.
     */
    public static <T extends Number> Vector<T> negation(Vector<T> vector) {
        return OPERATION_PROCESSOR.vectorNegation(vector);
    }

    /**
     * Solves for the new vector from subtraction of the two input vectors.
     */
    public static <T extends Number> Vector<T> subtraction(Vector<T> first, Vector<T> second) {
        return addition(first, negation(second));
    }

    /**
     * Solves for the dot product of the two input vectors.
     */
    public static <T extends Number> T dotProduct(Vector<T> first, Vector<T> second) {
        return OPERATION_PROCESSOR.vectorDotProduct(first, second);
    }

    /**
     * Solves for the magnitude of the input vector.
     */
    public static <T extends Number> T magnitude(Vector<T> vector) {
        return OPERATION_PROCESSOR.vectorMagnitude(vector);
    }

    /**
     * Solves for the new vector from scalar multiplication of the input vector.
     */
    public static <T extends Number> Vector<T> multiplication(Vector<T> vector, T scalar) {
        return OPERATION_PROCESSOR.scalarMultiplication(vector, scalar);
    }

    /**
     * Solves for the new vector from scalar division of the input vector.
     */
    public static <T extends Number> Vector<T> division(Vector<T> vector, T scalar) {
        return OPERATION_PROCESSOR.scalarDivision(vector, scalar);
    }

    /**
     * Solves for the normalized new vector from the input vector.
     */
    public static <T extends Number> Vector<T> normalization(Vector<T> vector) {
        return division(vector, magnitude(vector));
    }

//
//    /**
//     * Returns new identity matrix of th e specified class, rows and columns.
//     */
//    public static <T extends Number> Matrix<T> identityMatrix(Class<T> clas, int rows, int columns) {
//        return OPERATION_PROCESSOR.buildIdentityMatrix(clas, rows, columns);
//    }
//
//    /**
//     * Returns new matrix based on the specified number of rows, columns and fills it with specified value.
//     */
//     public static <T extends Number> Matrix<T> uniformMatrix(T value, int rows, int columns) {
//        Matrix<T> matrix = new Matrix<>(rows, columns);
//        for (int row = 0; row < rows; row++) {
//            for (int col = 0; col < columns; col++) {
//                matrix.getElements()[row][col] = value;
//            }
//        }
//        return matrix;
//    }
//
//    /**
//     * Solves for the new matrix by multiplying the first matrix by the second matrix.
//     */
//    public static <T extends Number> Matrix<T> multiplication(Matrix<T> first, Matrix<T> second) {
//        MatrixMultiplication<T> multiplication = new MatrixMultiplication<>(first, second);
//        OPERATION_PROCESSOR.processOperation(multiplication);
//        return multiplication.getResult();
//    }
//
//    /**
//     * Solves for the new vector by multiplying the matrix by the input vector.
//     */
//    public static <T extends Number> Vector<T> multiplication(Matrix<T> matrix, Vector<T> vector) {
//        MatrixVectorMultiplication<T> multiplication = new MatrixVectorMultiplication<>(matrix, vector);
//        OPERATION_PROCESSOR.processOperation(multiplication);
//        return multiplication.getResultant();
//    }
//
//    /**
//     * Returns new orthographic projection based on the specified prism plane position values.
//     */
//    public static Matrix4x4 orthographic(float left, float right, float bottom, float top, float near, float far) {
//        Matrix4x4 result = new Matrix4x4();
//
//        result.getElements()[0][0] = 2f / (right - left);
//        result.getElements()[1][1] = 2f / (top - bottom);
//        result.getElements()[2][2] = 2f / (near - far);
//
//        result.getElements()[3][0] = (left + right) / (left - right);
//        result.getElements()[3][1] = (bottom + top) / (bottom - top);
//        result.getElements()[3][2] = (near + far) / (near - far);
//
//        return result;
//    }
//
//    /**
//     * Returns new 2D transform based on the specified vector, rotation and scale values.
//     */
//    public static Matrix2x3 TRS(Vector2 vector, float angle, float scaleX, float scaleY) {
//        Matrix2x3 matrix2x3 = new Matrix2x3();
//
////        matrix2x3.getElements()[0][2] = vector.getComponent(0);
////        matrix2x3.getElements()[1][2] = vector.getComponent(1);
//
//        float r = (float) Math.toRadians(angle);
//        float cos = (float) Math.cos(r);
//        float sin = (float) Math.sin(r);
//
//        matrix2x3.getElements()[0][0] = cos * scaleX;
//        matrix2x3.getElements()[1][1] = cos * scaleY;
//        matrix2x3.getElements()[0][1] = -sin * scaleY;
//        matrix2x3.getElements()[1][0] = sin * scaleX;
//
//        return matrix2x3;
//    }
//
//    /**
//     * Returns new 2D translate transform based on the specified vector2 value.
//     */
//    public static Matrix2x3 translate(Vector2 vector) {
//        Matrix2x3 matrix2x3 = new Matrix2x3();
//
////        matrix2x3.getElements()[0][2] = vector.getComponent(0);
////        matrix2x3.getElements()[1][2] = vector.getComponent(1);
//
//        return matrix2x3;
//    }
//
//    /**
//     * Returns new 2D rotation transform based on the specified rotation value.
//     */
//    public static Matrix2x3 rotation(float angle) {
//        Matrix2x3 matrix2x3 = new Matrix2x3();
//
//        float r = (float) Math.toRadians(angle);
//        float cos = (float) Math.cos(r);
//        float sin = (float) Math.sin(r);
//
//        matrix2x3.getElements()[0][0] = cos;
//        matrix2x3.getElements()[1][1] = cos;
//        matrix2x3.getElements()[0][1] = -sin;
//        matrix2x3.getElements()[1][0] = sin;
//
//        return matrix2x3;
//    }
//
//    /**
//     * Returns new 2D scale transform based on the specified scale values.
//     */
//    public static Matrix2x3 scale(float scaleX, float scaleY) {
//        Matrix2x3 matrix2x3 = new Matrix2x3();
//
//        matrix2x3.getElements()[0][0] = scaleX;
//        matrix2x3.getElements()[1][1] = scaleY;
//
//        return matrix2x3;
//    }


}
