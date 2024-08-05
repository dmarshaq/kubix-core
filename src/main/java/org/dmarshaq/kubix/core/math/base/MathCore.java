package org.dmarshaq.kubix.core.math.base;

import org.dmarshaq.kubix.core.math.function.Domain;
import org.dmarshaq.kubix.core.math.matrix.*;
import org.dmarshaq.kubix.core.math.processor.*;
import org.dmarshaq.kubix.core.math.vector.*;

import java.awt.*;

public class MathCore {

    /**
     * AXIS used to give each character a unique index based on it's position in the string.
     */
    public static final String AXIS = "xyzw";

    /**
     * OPERATION_PROCESSOR used to process generic operations.
     */
    private static final FloatProcessor OPERATION_PROCESSOR = new FloatProcessor(new IntegerProcessor(new DoubleProcessor(new LongProcessor(new ByteProcessor(new ShortProcessor(null))))));

    /**
     * Returns new vector of desired type and any length.
     */
    public static <T extends Number> Vector<T> vector(Class<T> clas, int length) {
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
     * Returns new Vector4 from Color specified.
     */
    public static Vector4 vector4(Color color) {
        return new Vector4((float) color.getRed() / 255, (float) color.getGreen() / 255, (float) color.getBlue() / 255, (float) color.getAlpha() / 255);
    }

    /**
     * Used to get component vector based on axis specified, order matters, carefully "xy" doesn't equal "yx".
     * Vector returned always have same number of dimensions as axis.length().
     * Can only be specified up to "xyzw" in any order.
     * If specified axis.length() more than original Vector dimensions it will return component with added dimensions equal to 0.
     */
    public static <T extends Number> Vector<T> componentVector(Vector<T> vector, String axis) {
        return OPERATION_PROCESSOR.componentVector(vector, axis);
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

    /**
     * Returns new identity matrix of th e specified class, rows and columns.
     */
    public static <T extends Number> Matrix<T> identityMatrix(Class<T> clas, int rows, int columns) {
        return OPERATION_PROCESSOR.buildIdentityMatrix(clas, rows, columns);
    }

    /**
     * Solves for the new matrix by multiplying the first matrix by the second matrix.
     */
    public static <T extends Number> Matrix<T> multiplication(Matrix<T> first, Matrix<T> second) {
        return OPERATION_PROCESSOR.matrixMultiplication(first, second);
    }

    /**
     * Solves for the new vector by multiplying the matrix by the input vector.
     */
    public static <T extends Number> Vector<T> multiplication(Matrix<T> matrix, Vector<T> vector) {
        return OPERATION_PROCESSOR.matrixVectorMultiplication(matrix, vector);
    }

    /**
     * Returns new orthographic projection based on the specified prism plane position values.
     */
    public static Matrix4x4 orthographic(float left, float right, float bottom, float top, float near, float far) {
        Matrix4x4 matrix4x4 = new Matrix4x4();
        float[] elements = matrix4x4.getElements().floatArray();

        elements[0 + 0 * 4] = 2f / (right - left);
        elements[1 + 1 * 4] = 2f / (top - bottom);
        elements[2 + 2 * 4] = 2f / (near - far);

        elements[0 + 3 * 4] = (left + right) / (left - right);
        elements[1 + 3 * 4] = (bottom + top) / (bottom - top);
        elements[2 + 3 * 4] = (near + far) / (near - far);

        return matrix4x4;
    }

    /**
     * Returns new 2D transform based on the specified vector, rotation and scale values.
     */
    public static Matrix3x3 TRS(Vector2 vector, float angle, float scaleX, float scaleY) {
        Matrix3x3 matrix3x3 = new Matrix3x3();
        float[] elements = matrix3x3.getElements().floatArray();
        float[] arr = vector.getValues().floatArray();

        elements[2 + 0 * 3] = arr[0];
        elements[2 + 1 * 3] = arr[1];

        float r = (float) Math.toRadians(angle);
        float cos = (float) Math.cos(r);
        float sin = (float) Math.sin(r);


        elements[0 + 0 * 3] = cos * scaleX;
        elements[1 + 0 * 3] = -sin * scaleY;
        elements[0 + 1 * 3] = sin * scaleX;
        elements[1 + 1 * 3] = cos * scaleY;

        return matrix3x3;
    }

    /**
     * Returns new 2D translate transform based on the specified vector2 value.
     */
    public static Matrix3x3 translate(Vector2 vector) {
        Matrix3x3 matrix3x3 = new Matrix3x3();
        float[] elements = matrix3x3.getElements().floatArray();
        float[] arr = vector.getValues().floatArray();

        elements[2 + 0 * 3] = arr[0];
        elements[2 + 1 * 3] = arr[1];

        return matrix3x3;
    }

    /**
     * Returns new 2D rotation transform based on the specified rotation value.
     */
    public static Matrix3x3 rotation(float angle) {
        Matrix3x3 matrix3x3 = new Matrix3x3();
        float[] elements = matrix3x3.getElements().floatArray();

        float r = (float) Math.toRadians(angle);
        float cos = (float) Math.cos(r);
        float sin = (float) Math.sin(r);

        elements[0 + 0 * 3] = cos;
        elements[1 + 0 * 3] = -sin;
        elements[0 + 1 * 3] = sin;
        elements[1 + 1 * 3] = cos;

        return matrix3x3;
    }

    /**
     * Returns new 2D scale transform based on the specified scale values.
     */
    public static Matrix3x3 scale(float scaleX, float scaleY) {

        Matrix3x3 matrix3x3 = new Matrix3x3();

        float[] elements = matrix3x3.getElements().floatArray();

        elements[0 + 0 * 3] = scaleX;
        elements[1 + 1 * 3] = scaleY;


        return matrix3x3;
    }

    /**
     * Returns new Vector3 that represents world right direction vector.
     */
    public static Vector3 right() {
        return new Vector3(1.0f, 0.0f, 0.0f);
    }

    /**
     * Returns new Vector3 that represents world up direction vector.
     */
    public static Vector3 up() {
        return new Vector3(0.0f, 1.0f, 0.0f);
    }

    /**
     * Returns new Vector3 that represents world forward direction vector.
     */
    public static Vector3 forward() {
        return new Vector3(0.0f, 0.0f, 1.0f);
    }

    /**
     * Determines if value is outside of domain.
     */
    public static <T extends Number> boolean isOutsideDomain(Domain<T> domain, T value) {
        return OPERATION_PROCESSOR.isOutsideDomain(domain, value);
    }

    /**
     * Determines if value is inside of domain.
     */
    public static <T extends Number> boolean isInsideDomain(Domain<T> domain, T value) {
        return OPERATION_PROCESSOR.isInsideDomain(domain, value);
    }

    /**
     * Determines if value is on domain's boundary.
     */
    public static <T extends Number> boolean isOnDomainBoundary(Domain<T> domain, T value) {
        return domain.isOnBoundary(value);
    }

    /**
     * Returns new Matrix4x4 from Matrix3x3 specified.
     * Note: This function only copies transform properties.
     */
    public static Matrix4x4 transform4x4(Matrix3x3 transform3x3) {
        float[] src = transform3x3.getArrayOfElements();
        return new Matrix4x4(new float[] {
                src[0], src[1], 0, src[2],
                src[3], src[4], 0, src[5],
                0,      0,      1,      0,
                0,      0,      0,      1,
        });
    }

    /**
     * Determines if first number equals, less or greater than the second number.
     */
    public static <T extends Number> int compareNumbers(T first, T second) {
        return OPERATION_PROCESSOR.compareNumbers(first, second);
    }


}
