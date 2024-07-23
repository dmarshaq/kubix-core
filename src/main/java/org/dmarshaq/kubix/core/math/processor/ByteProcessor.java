package org.dmarshaq.kubix.core.math.processor;

import org.dmarshaq.kubix.core.math.array.ByteArray;
import org.dmarshaq.kubix.core.math.function.Domain;
import org.dmarshaq.kubix.core.math.matrix.Matrix;
import org.dmarshaq.kubix.core.math.vector.Vector;

import static org.dmarshaq.kubix.core.math.base.MathCore.AXIS;

public class ByteProcessor extends OperationProcessor {
    public ByteProcessor(OperationProcessor nextProcessor) {
        super(nextProcessor);
    }

    /**
     * Processes VectorAddition operation with Byte's.
     */
    @Override
    public <T extends Number> Vector<T> vectorAddition(Vector<T> first, Vector<T> second) {
        if (Byte.class.isAssignableFrom(first.getValues().getElementClass())) {

            byte[] arr1 = first.getValues().byteArray();
            byte[] arr2 = second.getValues().byteArray();
            if (arr1.length < arr2.length) {
                arr1 = arr2;
                arr2 = first.getValues().byteArray();
            }

            byte[] result = new byte[arr1.length];

            int i = 0;
            for (; i < arr2.length; i++) {
                result[i] = (byte) (arr1[i] + arr2[i]);
            }
            for (; i < arr1.length; i++) {
                result[i] = arr1[i];
            }

            return (Vector<T>) new Vector<>(new ByteArray(result));
        }
        else if (getNextProcessor() != null) {
            return getNextProcessor().vectorAddition(first, second);
        }
        return null;
    }

    /**
     * Processes VectorNegation operation with Byte's.
     */
    @Override
    public <T extends Number> Vector<T> vectorNegation(Vector<T> vector) {
        if (Byte.class.isAssignableFrom(vector.getValues().getElementClass())) {

            byte[] arr = vector.getValues().byteArray();
            byte[] result = new byte[arr.length];

            for (int i = 0; i < arr.length; i++) {
                result[i] = (byte) -arr[i];
            }

            return (Vector<T>) new Vector<>(new ByteArray(result));
        }
        else if (getNextProcessor() != null) {
            return getNextProcessor().vectorNegation(vector);
        }
        return null;
    }

    /**
     * Processes VectorDotProduct operation with Byte's.
     */
    @Override
    public <T extends Number> T vectorDotProduct(Vector<T> first, Vector<T> second) {
        if (Byte.class.isAssignableFrom(first.getValues().getElementClass())) {

            byte[] arr1 = first.getValues().byteArray();
            byte[] arr2 = second.getValues().byteArray();

            int minLength = Math.min(arr1.length, arr2.length);

            byte product = (byte) 0;

            for (int i = 0; i < minLength; i++) {
                product += (byte) (arr1[i] * arr2[i]);
            }

            return (T) (Byte)product;
        }
        else if (getNextProcessor() != null) {
            return getNextProcessor().vectorDotProduct(first, second);
        }
        return null;
    }

    /**
     * Processes VectorMagnitude operation with Byte's.
     */
    @Override
    public <T extends Number> T vectorMagnitude(Vector<T> vector) {
        if (Byte.class.isAssignableFrom(vector.getValues().getElementClass())) {

            byte[] arr = vector.getValues().byteArray();

            byte sumOfSquares = (byte) 0;
            for (int i = 0; i < arr.length; i++) {
                sumOfSquares += (byte) (arr[i] * arr[i]);
            }

            Byte magnitude = (byte) Math.sqrt(sumOfSquares);
            return (T) magnitude;
        }
        else if (getNextProcessor() != null) {
            return getNextProcessor().vectorMagnitude(vector);
        }
        return null;
    }

    /**
     * Processes ScalarMultiplication operation with Byte's.
     */
    @Override
    public <T extends Number> Vector<T> scalarMultiplication(Vector<T> vector, T scalar) {
        if (Byte.class.isAssignableFrom(vector.getValues().getElementClass())) {

            byte[] arr = vector.getValues().byteArray();
            byte[] result = new byte[arr.length];

            for (int i = 0; i < arr.length; i++) {
                result[i] = (byte) (arr[i] * scalar.byteValue());
            }

            return (Vector<T>) new Vector<>(new ByteArray(result));
        }
        else if (getNextProcessor() != null) {
            return getNextProcessor().scalarMultiplication(vector, scalar);
        }
        return null;
    }

    /**
     * Processes ScalarDivision operation with Byte's.
     */
    @Override
    public <T extends Number> Vector<T> scalarDivision(Vector<T> vector, T scalar) {
        if (Byte.class.isAssignableFrom(vector.getValues().getElementClass())) {

            byte[] arr = vector.getValues().byteArray();
            byte[] result = new byte[arr.length];

            for (int i = 0; i < arr.length; i++) {
                result[i] = (byte) (arr[i] / scalar.byteValue());
            }

            return (Vector<T>) new Vector<>(new ByteArray(result));
        }
        else if (getNextProcessor() != null) {
            return getNextProcessor().scalarDivision(vector, scalar);
        }
        return null;
    }

    /**
     * Builds new Byte vector of any length.
     */
    @Override
    public <T extends Number> Vector<T> buildVector(Class<T> clas, int length) {
        if (Byte.class.isAssignableFrom(clas)) {
            return (Vector<T>) new Vector<>(new ByteArray(new byte[length]));
        }
        else if (getNextProcessor() != null) {
            return getNextProcessor().buildVector(clas, length);
        }
        return null;
    }

    /**
     * Builds new Byte 2D vector.
     */
    @Override
    public <T extends Number> Vector<T> buildVector(T x, T y) {
        if (Byte.class.isAssignableFrom(x.getClass())) {
            return (Vector<T>) new Vector<>(new ByteArray(new byte[] {x.byteValue(), y.byteValue()}));
        }
        else if (getNextProcessor() != null) {
            return getNextProcessor().buildVector(x, y);
        }
        return null;
    }

    /**
     * Builds new Byte 3D vector.
     */
    @Override
    public <T extends Number> Vector<T> buildVector(T x, T y, T z) {
        if (Byte.class.isAssignableFrom(x.getClass())) {
            return (Vector<T>) new Vector<>(new ByteArray(new byte[] {x.byteValue(), y.byteValue(), z.byteValue()}));
        }
        else if (getNextProcessor() != null) {
            return getNextProcessor().buildVector(x, y, z);
        }
        return null;
    }

    /**
     * Builds new Byte 4D vector.
     */
    @Override
    public <T extends Number> Vector<T> buildVector(T x, T y, T z, T w) {
        if (Byte.class.isAssignableFrom(x.getClass())) {
            return (Vector<T>) new Vector<>(new ByteArray(new byte[] {x.byteValue(), y.byteValue(), z.byteValue(), w.byteValue()}));
        }
        else if (getNextProcessor() != null) {
            return getNextProcessor().buildVector(x, y, z, w);
        }
        return null;
    }

    /**
     * Builds new Byte identity matrix.
     */
    @Override
    public <T extends Number> Matrix<T> buildIdentityMatrix(Class<T> clas, int rows, int columns) {
        if (Byte.class.isAssignableFrom(clas)) {
            byte[] identity = new byte[rows * columns];
            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < columns; col++) {
                    if (row == col) {
                        identity[col + row * columns] = (byte) 1;
                    }
                    else {
                        identity[col + row * columns] = (byte) 0;
                    }
                }
            }
            return (Matrix<T>) new Matrix<>(new ByteArray(identity), rows, columns);
        }
        else if (getNextProcessor() != null) {
            return getNextProcessor().buildIdentityMatrix(clas, rows, columns);
        }
        return null;
    }

    /**
     * Processes MatrixMultiplication operation with Byte's.
     */
    @Override
    public <T extends Number> Matrix<T> matrixMultiplication(Matrix<T> first, Matrix<T> second) {
        if (Byte.class.isAssignableFrom(first.getElements().getElementClass())) {

            if (first.getColumns() == second.getRows()) {
                byte[] arr1 = first.getElements().byteArray();
                byte[] arr2 = second.getElements().byteArray();
                byte[] result = new byte[first.getRows() * second.getColumns()];

                for (int rCol = 0; rCol < second.getColumns(); rCol++) {
                    for (int rRow = 0; rRow < first.getRows(); rRow++) {
                        byte res = (byte) 0;
                        for (int col = 0; col < first.getColumns(); col++) {
                            res += (byte) (arr1[col + rRow * first.getColumns()] * arr2[rCol + col * second.getColumns()]);
                        }
                        result[rCol + rRow * second.getColumns()] = res;
                    }
                }

                return (Matrix<T>) new Matrix<>(new ByteArray(result), first.getRows(), second.getColumns());
            }
        }
        else if (getNextProcessor() != null) {
            return getNextProcessor().matrixMultiplication(first, second);
        }
        return null;
    }

    /**
     * Processes MatrixVectorMultiplication operation with Byte's.
     */
    @Override
    public <T extends Number> Vector<T> matrixVectorMultiplication(Matrix<T> matrix, Vector<T> vector) {
        if (Byte.class.isAssignableFrom(matrix.getElements().getElementClass())) {

            if (matrix.getColumns() == vector.getValues().byteArray().length) {
                byte[] arr1 = matrix.getElements().byteArray();
                byte[] arr2 = vector.getValues().byteArray();
                byte[] result = new byte[matrix.getRows()];

                for (int rRow = 0; rRow < result.length; rRow++) {
                    byte res = (byte) 0;
                    for (int col = 0; col < matrix.getColumns(); col++) {
                        res += (byte) (arr1[col + rRow * matrix.getColumns()] * arr2[col]);
                    }
                    result[rRow] = res;
                }

                return (Vector<T>) new Vector<>(new ByteArray(result));
            }
        }
        else if (getNextProcessor() != null) {
            return getNextProcessor().matrixVectorMultiplication(matrix, vector);
        }
        return null;
    }

    /**
     * Used to get component vector based on axis specified, order matters, carefully "xy" doesn't equal "yx".
     * Vector returned always have same number of dimensions as axis.length().
     * Can only be specified up to "xyzw" in any order.
     * If specified axis.length() more than original Vector dimensions it will return component with added dimensions equal to 0.
     */
    @Override
    public <T extends Number> Vector<T> componentVector(Vector<T> vector, String axis) {
        int length = axis.length();

        byte[] arr = vector.getValues().byteArray();
        byte[] result = new byte[length];

        for(int i = 0; i < length; i++) {
            int index = AXIS.indexOf(axis.charAt(i));
            if (index < arr.length) {
                result[i] = arr[index];
            }
        }
        return (Vector<T>) new Vector<>(new ByteArray(result));
    }

    /**
     * Determines if value is outside of domain.
     */
    @Override
    public <T extends Number> boolean isOutsideDomain(Domain<T> domain, T value) {
        byte min = domain.getMin().byteValue();
        byte max = domain.getMax().byteValue();
        byte val = value.byteValue();

        return val < min || val > max;
    }

    /**
     * Determines if value is inside of domain.
     */
    @Override
    public <T extends Number> boolean isInsideDomain(Domain<T> domain, T value) {
        byte min = domain.getMin().byteValue();
        byte max = domain.getMax().byteValue();
        byte val = value.byteValue();

        return val > min && val < max;
    }
}
