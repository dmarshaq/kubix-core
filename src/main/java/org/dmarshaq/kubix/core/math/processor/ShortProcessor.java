package org.dmarshaq.kubix.core.math.processor;

import org.dmarshaq.kubix.core.math.array.ByteArray;
import org.dmarshaq.kubix.core.math.array.ShortArray;
import org.dmarshaq.kubix.core.math.function.Domain;
import org.dmarshaq.kubix.core.math.matrix.Matrix;
import org.dmarshaq.kubix.core.math.vector.Vector;

import static org.dmarshaq.kubix.core.math.MathCore.AXIS;

public class ShortProcessor extends OperationProcessor {
    public ShortProcessor(OperationProcessor nextProcessor) {
        super(nextProcessor);
    }

    /**
     * Processes VectorAddition operation with Short's.
     */
    @Override
    public <T extends Number> Vector<T> vectorAddition(Vector<T> first, Vector<T> second) {
        if (Short.class.isAssignableFrom(first.getValues().getElementClass())) {

            short[] arr1 = first.getValues().shortArray();
            short[] arr2 = second.getValues().shortArray();
            if (arr1.length < arr2.length) {
                arr1 = arr2;
                arr2 = first.getValues().shortArray();
            }

            short[] result = new short[arr1.length];

            int i = 0;
            for (; i < arr2.length; i++) {
                result[i] = (short) (arr1[i] + arr2[i]);
            }
            for (; i < arr1.length; i++) {
                result[i] = arr1[i];
            }

            return (Vector<T>) new Vector<>(new ShortArray(result));
        }
        else if (getNextProcessor() != null) {
            return getNextProcessor().vectorAddition(first, second);
        }
        return null;
    }

    /**
     * Processes VectorNegation operation with Short's.
     */
    @Override
    public <T extends Number> Vector<T> vectorNegation(Vector<T> vector) {
        if (Short.class.isAssignableFrom(vector.getValues().getElementClass())) {

            short[] arr = vector.getValues().shortArray();
            short[] result = new short[arr.length];

            for (int i = 0; i < arr.length; i++) {
                result[i] = (short) -arr[i];
            }

            return (Vector<T>) new Vector<>(new ShortArray(result));
        }
        else if (getNextProcessor() != null) {
            return getNextProcessor().vectorNegation(vector);
        }
        return null;
    }

    /**
     * Processes VectorDotProduct operation with Short's.
     */
    @Override
    public <T extends Number> T vectorDotProduct(Vector<T> first, Vector<T> second) {
        if (Short.class.isAssignableFrom(first.getValues().getElementClass())) {

            short[] arr1 = first.getValues().shortArray();
            short[] arr2 = second.getValues().shortArray();

            int minLength = Math.min(arr1.length, arr2.length);

            short product = (short) 0;

            for (int i = 0; i < minLength; i++) {
                product += (short) (arr1[i] * arr2[i]);
            }

            return (T) (Short)product;
        }
        else if (getNextProcessor() != null) {
            return getNextProcessor().vectorDotProduct(first, second);
        }
        return null;
    }

    /**
     * Processes VectorMagnitude operation with Short's.
     */
    @Override
    public <T extends Number> T vectorMagnitude(Vector<T> vector) {
        if (Short.class.isAssignableFrom(vector.getValues().getElementClass())) {

            short[] arr = vector.getValues().shortArray();

            short sumOfSquares = (short) 0;
            for (int i = 0; i < arr.length; i++) {
                sumOfSquares += (short) (arr[i] * arr[i]);
            }

            Short magnitude = (short) Math.sqrt(sumOfSquares);
            return (T) magnitude;
        }
        else if (getNextProcessor() != null) {
            return getNextProcessor().vectorMagnitude(vector);
        }
        return null;
    }

    /**
     * Processes ScalarMultiplication operation with Short's.
     */
    @Override
    public <T extends Number> Vector<T> scalarMultiplication(Vector<T> vector, T scalar) {
        if (Short.class.isAssignableFrom(vector.getValues().getElementClass())) {

            short[] arr = vector.getValues().shortArray();
            short[] result = new short[arr.length];

            for (int i = 0; i < arr.length; i++) {
                result[i] = (short) (arr[i] * scalar.shortValue());
            }

            return (Vector<T>) new Vector<>(new ShortArray(result));
        }
        else if (getNextProcessor() != null) {
            return getNextProcessor().scalarMultiplication(vector, scalar);
        }
        return null;
    }

    /**
     * Processes ScalarDivision operation with Short's.
     */
    @Override
    public <T extends Number> Vector<T> scalarDivision(Vector<T> vector, T scalar) {
        if (Short.class.isAssignableFrom(vector.getValues().getElementClass())) {

            short[] arr = vector.getValues().shortArray();
            short[] result = new short[arr.length];

            for (int i = 0; i < arr.length; i++) {
                result[i] = (short) (arr[i] / scalar.shortValue());
            }

            return (Vector<T>) new Vector<>(new ShortArray(result));
        }
        else if (getNextProcessor() != null) {
            return getNextProcessor().scalarDivision(vector, scalar);
        }
        return null;
    }

    /**
     * Builds new Short vector of any length.
     */
    @Override
    public <T extends Number> Vector<T> buildVector(Class<T> clas, int length) {
        if (Short.class.isAssignableFrom(clas)) {
            return (Vector<T>) new Vector<>(new ShortArray(new short[length]));
        }
        else if (getNextProcessor() != null) {
            return getNextProcessor().buildVector(clas, length);
        }
        return null;
    }

    /**
     * Builds new Short 2D vector.
     */
    @Override
    public <T extends Number> Vector<T> buildVector(T x, T y) {
        if (Short.class.isAssignableFrom(x.getClass())) {
            return (Vector<T>) new Vector<>(new ShortArray(new short[] {x.shortValue(), y.shortValue()}));
        }
        else if (getNextProcessor() != null) {
            return getNextProcessor().buildVector(x, y);
        }
        return null;
    }

    /**
     * Builds new Short 3D vector.
     */
    @Override
    public <T extends Number> Vector<T> buildVector(T x, T y, T z) {
        if (Short.class.isAssignableFrom(x.getClass())) {
            return (Vector<T>) new Vector<>(new ShortArray(new short[] {x.shortValue(), y.shortValue(), z.shortValue()}));
        }
        else if (getNextProcessor() != null) {
            return getNextProcessor().buildVector(x, y, z);
        }
        return null;
    }

    /**
     * Builds new Short 4D vector.
     */
    @Override
    public <T extends Number> Vector<T> buildVector(T x, T y, T z, T w) {
        if (Short.class.isAssignableFrom(x.getClass())) {
            return (Vector<T>) new Vector<>(new ShortArray(new short[] {x.shortValue(), y.shortValue(), z.shortValue(), w.shortValue()}));
        }
        else if (getNextProcessor() != null) {
            return getNextProcessor().buildVector(x, y, z, w);
        }
        return null;
    }

    /**
     * Builds new Short identity matrix.
     */
    @Override
    public <T extends Number> Matrix<T> buildIdentityMatrix(Class<T> clas, int rows, int columns) {
        if (Short.class.isAssignableFrom(clas)) {
            short[] identity = new short[rows * columns];
            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < columns; col++) {
                    if (row == col) {
                        identity[col + row * columns] = (short) 1;
                    }
                    else {
                        identity[col + row * columns] = (short) 0;
                    }
                }
            }
            return (Matrix<T>) new Matrix<>(new ShortArray(identity), rows, columns);
        }
        else if (getNextProcessor() != null) {
            return getNextProcessor().buildIdentityMatrix(clas, rows, columns);
        }
        return null;
    }

    /**
     * Processes MatrixMultiplication operation with Short's.
     */
    @Override
    public <T extends Number> Matrix<T> matrixMultiplication(Matrix<T> first, Matrix<T> second) {
        if (Short.class.isAssignableFrom(first.getElements().getElementClass())) {

            if (first.getColumns() == second.getRows()) {
                short[] arr1 = first.getElements().shortArray();
                short[] arr2 = second.getElements().shortArray();
                short[] result = new short[first.getRows() * second.getColumns()];

                for (int rCol = 0; rCol < second.getColumns(); rCol++) {
                    for (int rRow = 0; rRow < first.getRows(); rRow++) {
                        short res = (short) 0;
                        for (int col = 0; col < first.getColumns(); col++) {
                            res += (short) (arr1[col + rRow * first.getColumns()] * arr2[rCol + col * second.getColumns()]);
                        }
                        result[rCol + rRow * second.getColumns()] = res;
                    }
                }

                return (Matrix<T>) new Matrix<>(new ShortArray(result), first.getRows(), second.getColumns());
            }
        }
        else if (getNextProcessor() != null) {
            return getNextProcessor().matrixMultiplication(first, second);
        }
        return null;
    }

    /**
     * Processes MatrixVectorMultiplication operation with Short's.
     */
    @Override
    public <T extends Number> Vector<T> matrixVectorMultiplication(Matrix<T> matrix, Vector<T> vector) {
        if (Short.class.isAssignableFrom(matrix.getElements().getElementClass())) {

            if (matrix.getColumns() == vector.getValues().shortArray().length) {
                short[] arr1 = matrix.getElements().shortArray();
                short[] arr2 = vector.getValues().shortArray();
                short[] result = new short[matrix.getRows()];

                for (int rRow = 0; rRow < result.length; rRow++) {
                    short res = (short) 0;
                    for (int col = 0; col < matrix.getColumns(); col++) {
                        res += (short) (arr1[col + rRow * matrix.getColumns()] * arr2[col]);
                    }
                    result[rRow] = res;
                }

                return (Vector<T>) new Vector<>(new ShortArray(result));
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

        short[] arr = vector.getValues().shortArray();
        short[] result = new short[length];

        for(int i = 0; i < length; i++) {
            int index = AXIS.indexOf(axis.charAt(i));
            if (index < arr.length) {
                result[i] = arr[index];
            }
        }
        return (Vector<T>) new Vector<>(new ShortArray(result));
    }

    /**
     * Determines if value is outside of domain.
     */
    @Override
    public <T extends Number> boolean isOutsideDomain(Domain<T> domain, T value) {
        short min = domain.getMin().shortValue();
        short max = domain.getMax().shortValue();
        short val = value.shortValue();

        return val < min || val > max;
    }

    /**
     * Determines if value is inside of domain.
     */
    @Override
    public <T extends Number> boolean isInsideDomain(Domain<T> domain, T value) {
        short min = domain.getMin().shortValue();
        short max = domain.getMax().shortValue();
        short val = value.shortValue();

        return val > min && val < max;
    }
}
