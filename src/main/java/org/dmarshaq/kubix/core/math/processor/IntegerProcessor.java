package org.dmarshaq.kubix.core.math.processor;

import org.dmarshaq.kubix.core.math.array.FloatArray;
import org.dmarshaq.kubix.core.math.array.IntegerArray;
import org.dmarshaq.kubix.core.math.function.Domain;
import org.dmarshaq.kubix.core.math.matrix.Matrix;
import org.dmarshaq.kubix.core.math.vector.Vector;

import static org.dmarshaq.kubix.core.math.MathCore.AXIS;

public class IntegerProcessor extends OperationProcessor {
    public IntegerProcessor(OperationProcessor nextProcessor) {
        super(nextProcessor);
    }

    /**
     * Processes VectorAddition operation with Integer's.
     */
    @Override
    public <T extends Number> Vector<T> vectorAddition(Vector<T> first, Vector<T> second) {
        if (Integer.class.isAssignableFrom(first.getValues().getElementClass())) {

            int[] arr1 = first.getValues().intArray();
            int[] arr2 = second.getValues().intArray();
            if (arr1.length < arr2.length) {
                arr1 = arr2;
                arr2 = first.getValues().intArray();
            }

            int[] result = new int[arr1.length];

            int i = 0;
            for (; i < arr2.length; i++) {
                result[i] = arr1[i] + arr2[i];
            }
            for (; i < arr1.length; i++) {
                result[i] = arr1[i];
            }

            return (Vector<T>) new Vector<>(new IntegerArray(result));
        }
        else if (getNextProcessor() != null) {
            return getNextProcessor().vectorAddition(first, second);
        }
        return null;
    }

    /**
     * Processes VectorNegation operation with Integer's.
     */
    @Override
    public <T extends Number> Vector<T> vectorNegation(Vector<T> vector) {
        if (Integer.class.isAssignableFrom(vector.getValues().getElementClass())) {

            int[] arr = vector.getValues().intArray();
            int[] result = new int[arr.length];

            for (int i = 0; i < arr.length; i++) {
                result[i] = -arr[i];
            }

            return (Vector<T>) new Vector<>(new IntegerArray(result));
        }
        else if (getNextProcessor() != null) {
            return getNextProcessor().vectorNegation(vector);
        }
        return null;
    }

    /**
     * Processes VectorDotProduct operation with Integer's.
     */
    @Override
    public <T extends Number> T vectorDotProduct(Vector<T> first, Vector<T> second) {
        if (Integer.class.isAssignableFrom(first.getValues().getElementClass())) {

            int[] arr1 = first.getValues().intArray();
            int[] arr2 = second.getValues().intArray();

            int minLength = Math.min(arr1.length, arr2.length);

            int product = 0;

            for (int i = 0; i < minLength; i++) {
                product += arr1[i] * arr2[i];
            }

            return (T) (Integer)product;
        }
        else if (getNextProcessor() != null) {
            return getNextProcessor().vectorDotProduct(first, second);
        }
        return null;
    }

    /**
     * Processes VectorMagnitude operation with Integer's.
     */
    @Override
    public <T extends Number> T vectorMagnitude(Vector<T> vector) {
        if (Integer.class.isAssignableFrom(vector.getValues().getElementClass())) {

            int[] arr = vector.getValues().intArray();

            int sumOfSquares = 0;
            for (int i = 0; i < arr.length; i++) {
                sumOfSquares += arr[i] * arr[i];
            }

            Integer magnitude = (int) Math.sqrt(sumOfSquares);
            return (T) magnitude;
        }
        else if (getNextProcessor() != null) {
            return getNextProcessor().vectorMagnitude(vector);
        }
        return null;
    }

    /**
     * Processes ScalarMultiplication operation with Integer's.
     */
    @Override
    public <T extends Number> Vector<T> scalarMultiplication(Vector<T> vector, T scalar) {
        if (Integer.class.isAssignableFrom(vector.getValues().getElementClass())) {

            int[] arr = vector.getValues().intArray();
            int[] result = new int[arr.length];

            for (int i = 0; i < arr.length; i++) {
                result[i] = arr[i] * scalar.intValue();
            }

            return (Vector<T>) new Vector<>(new IntegerArray(result));
        }
        else if (getNextProcessor() != null) {
            return getNextProcessor().scalarMultiplication(vector, scalar);
        }
        return null;
    }

    /**
     * Processes ScalarDivision operation with Integer's.
     */
    @Override
    public <T extends Number> Vector<T> scalarDivision(Vector<T> vector, T scalar) {
        if (Integer.class.isAssignableFrom(vector.getValues().getElementClass())) {

            int[] arr = vector.getValues().intArray();
            int[] result = new int[arr.length];

            for (int i = 0; i < arr.length; i++) {
                result[i] = arr[i] / scalar.intValue();
            }

            return (Vector<T>) new Vector<>(new IntegerArray(result));
        }
        else if (getNextProcessor() != null) {
            return getNextProcessor().scalarDivision(vector, scalar);
        }
        return null;
    }

    /**
     * Builds new Integer vector of any length.
     */
    @Override
    public <T extends Number> Vector<T> buildVector(Class<T> clas, int length) {
        if (Integer.class.isAssignableFrom(clas)) {
            return (Vector<T>) new Vector<>(new IntegerArray(new int[length]));
        }
        else if (getNextProcessor() != null) {
            return getNextProcessor().buildVector(clas, length);
        }
        return null;
    }

    /**
     * Builds new Integer 2D vector.
     */
    @Override
    public <T extends Number> Vector<T> buildVector(T x, T y) {
        if (Integer.class.isAssignableFrom(x.getClass())) {
            return (Vector<T>) new Vector<>(new IntegerArray(new int[] {x.intValue(), y.intValue()}));
        }
        else if (getNextProcessor() != null) {
            return getNextProcessor().buildVector(x, y);
        }
        return null;
    }

    /**
     * Builds new Integer 3D vector.
     */
    @Override
    public <T extends Number> Vector<T> buildVector(T x, T y, T z) {
        if (Integer.class.isAssignableFrom(x.getClass())) {
            return (Vector<T>) new Vector<>(new IntegerArray(new int[] {x.intValue(), y.intValue(), z.intValue()}));
        }
        else if (getNextProcessor() != null) {
            return getNextProcessor().buildVector(x, y, z);
        }
        return null;
    }

    /**
     * Builds new Integer 4D vector.
     */
    @Override
    public <T extends Number> Vector<T> buildVector(T x, T y, T z, T w) {
        if (Integer.class.isAssignableFrom(x.getClass())) {
            return (Vector<T>) new Vector<>(new IntegerArray(new int[] {x.intValue(), y.intValue(), z.intValue(), w.intValue()}));
        }
        else if (getNextProcessor() != null) {
            return getNextProcessor().buildVector(x, y, z, w);
        }
        return null;
    }

    /**
     * Builds new Integer identity matrix.
     */
    @Override
    public <T extends Number> Matrix<T> buildIdentityMatrix(Class<T> clas, int rows, int columns) {
        if (Integer.class.isAssignableFrom(clas)) {
            int[] identity = new int[rows * columns];
            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < columns; col++) {
                    if (row == col) {
                        identity[col + row * columns] = 1;
                    }
                    else {
                        identity[col + row * columns] = 0;
                    }
                }
            }
            return (Matrix<T>) new Matrix<>(new IntegerArray(identity), rows, columns);
        }
        else if (getNextProcessor() != null) {
            return getNextProcessor().buildIdentityMatrix(clas, rows, columns);
        }
        return null;
    }

    /**
     * Processes MatrixMultiplication operation with Integer's.
     */
    @Override
    public <T extends Number> Matrix<T> matrixMultiplication(Matrix<T> first, Matrix<T> second) {
        if (Integer.class.isAssignableFrom(first.getElements().getElementClass())) {

            if (first.getColumns() == second.getRows()) {
                int[] arr1 = first.getElements().intArray();
                int[] arr2 = second.getElements().intArray();
                int[] result = new int[first.getRows() * second.getColumns()];

                for (int rCol = 0; rCol < second.getColumns(); rCol++) {
                    for (int rRow = 0; rRow < first.getRows(); rRow++) {
                        int res = 0;
                        for (int col = 0; col < first.getColumns(); col++) {
                            res += arr1[col + rRow * first.getColumns()] * arr2[rCol + col * second.getColumns()];
                        }
                        result[rCol + rRow * second.getColumns()] = res;
                    }
                }

                return (Matrix<T>) new Matrix<>(new IntegerArray(result), first.getRows(), second.getColumns());
            }
        }
        else if (getNextProcessor() != null) {
            return getNextProcessor().matrixMultiplication(first, second);
        }
        return null;
    }

    /**
     * Processes MatrixVectorMultiplication operation with Integer's.
     */
    @Override
    public <T extends Number> Vector<T> matrixVectorMultiplication(Matrix<T> matrix, Vector<T> vector) {
        if (Integer.class.isAssignableFrom(matrix.getElements().getElementClass())) {

            if (matrix.getColumns() == vector.getValues().intArray().length) {
                int[] arr1 = matrix.getElements().intArray();
                int[] arr2 = vector.getValues().intArray();
                int[] result = new int[matrix.getRows()];

                for (int rRow = 0; rRow < result.length; rRow++) {
                    int res = 0;
                    for (int col = 0; col < matrix.getColumns(); col++) {
                        res += arr1[col + rRow * matrix.getColumns()] * arr2[col];
                    }
                    result[rRow] = res;
                }

                return (Vector<T>) new Vector<>(new IntegerArray(result));
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

        int[] arr = vector.getValues().intArray();
        int[] result = new int[length];

        for(int i = 0; i < length; i++) {
            int index = AXIS.indexOf(axis.charAt(i));
            if (index < arr.length) {
                result[i] = arr[index];
            }
        }
        return (Vector<T>) new Vector<>(new IntegerArray(result));
    }

    /**
     * Determines if value is outside of domain.
     */
    @Override
    public <T extends Number> boolean isOutsideDomain(Domain<T> domain, T value) {
        int min = domain.getMin().intValue();
        int max = domain.getMax().intValue();
        int val = value.intValue();

        return val < min || val > max;
    }

    /**
     * Determines if value is inside of domain.
     */
    @Override
    public <T extends Number> boolean isInsideDomain(Domain<T> domain, T value) {
        int min = domain.getMin().intValue();
        int max = domain.getMax().intValue();
        int val = value.intValue();

        return val > min && val < max;
    }
}
