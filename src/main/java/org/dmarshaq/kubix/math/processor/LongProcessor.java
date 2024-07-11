package org.dmarshaq.kubix.math.processor;

import org.dmarshaq.kubix.math.array.LongArray;
import org.dmarshaq.kubix.math.matrix.Matrix;
import org.dmarshaq.kubix.math.vector.Vector;

public class LongProcessor extends OperationProcessor {
    public LongProcessor(OperationProcessor nextProcessor) {
        super(nextProcessor);
    }

    /**
     * Processes VectorAddition operation with Long's.
     */
    @Override
    public <T extends Number> Vector<T> vectorAddition(Vector<T> first, Vector<T> second) {
        if (Long.class.isAssignableFrom(first.getValues().getElementClass())) {

            long[] arr1 = first.getValues().longArray();
            long[] arr2 = second.getValues().longArray();
            if (arr1.length < arr2.length) {
                arr1 = arr2;
                arr2 = first.getValues().longArray();
            }

            long[] result = new long[arr1.length];

            int i = 0;
            for (; i < arr2.length; i++) {
                result[i] = arr1[i] + arr2[i];
            }
            for (; i < arr1.length; i++) {
                result[i] = arr1[i];
            }

            return (Vector<T>) new Vector<>(new LongArray(result));
        }
        else if (getNextProcessor() != null) {
            return getNextProcessor().vectorAddition(first, second);
        }
        return null;
    }

    /**
     * Processes VectorNegation operation with Long's.
     */
    @Override
    public <T extends Number> Vector<T> vectorNegation(Vector<T> vector) {
        if (Long.class.isAssignableFrom(vector.getValues().getElementClass())) {

            long[] arr = vector.getValues().longArray();
            long[] result = new long[arr.length];

            for (int i = 0; i < arr.length; i++) {
                result[i] = -arr[i];
            }

            return (Vector<T>) new Vector<>(new LongArray(result));
        }
        else if (getNextProcessor() != null) {
            return getNextProcessor().vectorNegation(vector);
        }
        return null;
    }

    /**
     * Processes VectorDotProduct operation with Long's.
     */
    @Override
    public <T extends Number> T vectorDotProduct(Vector<T> first, Vector<T> second) {
        if (Long.class.isAssignableFrom(first.getValues().getElementClass())) {

            long[] arr1 = first.getValues().longArray();
            long[] arr2 = second.getValues().longArray();

            int minLength = Math.min(arr1.length, arr2.length);

            long product = 0L;

            for (int i = 0; i < minLength; i++) {
                product += arr1[i] * arr2[i];
            }

            return (T) (Long)product;
        }
        else if (getNextProcessor() != null) {
            return getNextProcessor().vectorDotProduct(first, second);
        }
        return null;
    }

    /**
     * Processes VectorMagnitude operation with Long's.
     */
    @Override
    public <T extends Number> T vectorMagnitude(Vector<T> vector) {
        if (Long.class.isAssignableFrom(vector.getValues().getElementClass())) {

            long[] arr = vector.getValues().longArray();

            long sumOfSquares = 0L;
            for (int i = 0; i < arr.length; i++) {
                sumOfSquares += arr[i] * arr[i];
            }

            Long magnitude = (long) Math.sqrt(sumOfSquares);
            return (T) magnitude;
        }
        else if (getNextProcessor() != null) {
            return getNextProcessor().vectorMagnitude(vector);
        }
        return null;
    }

    /**
     * Processes ScalarMultiplication operation with Long's.
     */
    @Override
    public <T extends Number> Vector<T> scalarMultiplication(Vector<T> vector, T scalar) {
        if (Long.class.isAssignableFrom(vector.getValues().getElementClass())) {

            long[] arr = vector.getValues().longArray();
            long[] result = new long[arr.length];

            for (int i = 0; i < arr.length; i++) {
                result[i] = arr[i] * scalar.longValue();
            }

            return (Vector<T>) new Vector<>(new LongArray(result));
        }
        else if (getNextProcessor() != null) {
            return getNextProcessor().scalarMultiplication(vector, scalar);
        }
        return null;
    }

    /**
     * Processes ScalarDivision operation with Long's.
     */
    @Override
    public <T extends Number> Vector<T> scalarDivision(Vector<T> vector, T scalar) {
        if (Long.class.isAssignableFrom(vector.getValues().getElementClass())) {

            long[] arr = vector.getValues().longArray();
            long[] result = new long[arr.length];

            for (int i = 0; i < arr.length; i++) {
                result[i] = arr[i] / scalar.longValue();
            }

            return (Vector<T>) new Vector<>(new LongArray(result));
        }
        else if (getNextProcessor() != null) {
            return getNextProcessor().scalarDivision(vector, scalar);
        }
        return null;
    }

    /**
     * Builds new Long vector of any length.
     */
    @Override
    public <T extends Number> Vector<T> buildVector(Class<T> clas, int length) {
        if (Long.class.isAssignableFrom(clas)) {
            return (Vector<T>) new Vector<>(new LongArray(new long[length]));
        }
        else if (getNextProcessor() != null) {
            return getNextProcessor().buildVector(clas, length);
        }
        return null;
    }

    /**
     * Builds new Long 2D vector.
     */
    @Override
    public <T extends Number> Vector<T> buildVector(T x, T y) {
        if (Long.class.isAssignableFrom(x.getClass())) {
            return (Vector<T>) new Vector<>(new LongArray(new long[] {x.longValue(), y.longValue()}));
        }
        else if (getNextProcessor() != null) {
            return getNextProcessor().buildVector(x, y);
        }
        return null;
    }

    /**
     * Builds new Long 3D vector.
     */
    @Override
    public <T extends Number> Vector<T> buildVector(T x, T y, T z) {
        if (Long.class.isAssignableFrom(x.getClass())) {
            return (Vector<T>) new Vector<>(new LongArray(new long[] {x.longValue(), y.longValue(), z.longValue()}));
        }
        else if (getNextProcessor() != null) {
            return getNextProcessor().buildVector(x, y, z);
        }
        return null;
    }

    /**
     * Builds new Long 4D vector.
     */
    @Override
    public <T extends Number> Vector<T> buildVector(T x, T y, T z, T w) {
        if (Long.class.isAssignableFrom(x.getClass())) {
            return (Vector<T>) new Vector<>(new LongArray(new long[] {x.longValue(), y.longValue(), z.longValue(), w.longValue()}));
        }
        else if (getNextProcessor() != null) {
            return getNextProcessor().buildVector(x, y, z, w);
        }
        return null;
    }

    /**
     * Builds new Long identity matrix.
     */
    @Override
    public <T extends Number> Matrix<T> buildIdentityMatrix(Class<T> clas, int rows, int columns) {
        if (Long.class.isAssignableFrom(clas)) {
            long[] identity = new long[rows * columns];
            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < columns; col++) {
                    if (row == col) {
                        identity[col + row * columns] = 1L;
                    }
                    else {
                        identity[col + row * columns] = 0L;
                    }
                }
            }
            return (Matrix<T>) new Matrix<>(new LongArray(identity), rows, columns);
        }
        else if (getNextProcessor() != null) {
            return getNextProcessor().buildIdentityMatrix(clas, rows, columns);
        }
        return null;
    }

    /**
     * Processes MatrixMultiplication operation with Long's.
     */
    @Override
    public <T extends Number> Matrix<T> matrixMultiplication(Matrix<T> first, Matrix<T> second) {
        if (Long.class.isAssignableFrom(first.getElements().getElementClass())) {

            if (first.getColumns() == second.getRows()) {
                long[] arr1 = first.getElements().longArray();
                long[] arr2 = second.getElements().longArray();
                long[] result = new long[first.getRows() * second.getColumns()];

                for (int rCol = 0; rCol < second.getColumns(); rCol++) {
                    for (int rRow = 0; rRow < first.getRows(); rRow++) {
                        long res = 0L;
                        for (int col = 0; col < first.getColumns(); col++) {
                            res += arr1[col + rRow * first.getColumns()] * arr2[rCol + col * second.getColumns()];
                        }
                        result[rCol + rRow * second.getColumns()] = res;
                    }
                }

                return (Matrix<T>) new Matrix<>(new LongArray(result), first.getRows(), second.getColumns());
            }
        }
        else if (getNextProcessor() != null) {
            return getNextProcessor().matrixMultiplication(first, second);
        }
        return null;
    }

    /**
     * Processes MatrixVectorMultiplication operation with Long's.
     */
    @Override
    public <T extends Number> Vector<T> matrixVectorMultiplication(Matrix<T> matrix, Vector<T> vector) {
        if (Long.class.isAssignableFrom(matrix.getElements().getElementClass())) {

            if (matrix.getColumns() == vector.getValues().longArray().length) {
                long[] arr1 = matrix.getElements().longArray();
                long[] arr2 = vector.getValues().longArray();
                long[] result = new long[arr2.length];

                for (int rRow = 0; rRow < result.length; rRow++) {
                    long res = 0L;
                    for (int col = 0; col < matrix.getColumns(); col++) {
                        res += arr1[col + rRow * matrix.getColumns()] * arr2[col];
                    }
                    result[rRow] = res;
                }

                return (Vector<T>) new Vector<>(new LongArray(result));
            }
        }
        else if (getNextProcessor() != null) {
            return getNextProcessor().matrixVectorMultiplication(matrix, vector);
        }
        return null;
    }
}
