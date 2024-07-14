package org.dmarshaq.kubix.math.processor;

import org.dmarshaq.kubix.math.array.DoubleArray;
import org.dmarshaq.kubix.math.array.IntegerArray;
import org.dmarshaq.kubix.math.matrix.Matrix;
import org.dmarshaq.kubix.math.vector.Vector;

import static org.dmarshaq.kubix.math.MathCore.AXIS;

public class DoubleProcessor extends OperationProcessor {
    public DoubleProcessor(OperationProcessor nextProcessor) {
        super(nextProcessor);
    }

    /**
     * Processes VectorAddition operation with Double's.
     */
    @Override
    public <T extends Number> Vector<T> vectorAddition(Vector<T> first, Vector<T> second) {
        if (Double.class.isAssignableFrom(first.getValues().getElementClass())) {

            double[] arr1 = first.getValues().doubleArray();
            double[] arr2 = second.getValues().doubleArray();
            if (arr1.length < arr2.length) {
                arr1 = arr2;
                arr2 = first.getValues().doubleArray();
            }

            double[] result = new double[arr1.length];

            int i = 0;
            for (; i < arr2.length; i++) {
                result[i] = arr1[i] + arr2[i];
            }
            for (; i < arr1.length; i++) {
                result[i] = arr1[i];
            }

            return (Vector<T>) new Vector<>(new DoubleArray(result));
        }
        else if (getNextProcessor() != null) {
            return getNextProcessor().vectorAddition(first, second);
        }
        return null;
    }

    /**
     * Processes VectorNegation operation with Double's.
     */
    @Override
    public <T extends Number> Vector<T> vectorNegation(Vector<T> vector) {
        if (Double.class.isAssignableFrom(vector.getValues().getElementClass())) {

            double[] arr = vector.getValues().doubleArray();
            double[] result = new double[arr.length];

            for (int i = 0; i < arr.length; i++) {
                result[i] = -arr[i];
            }

            return (Vector<T>) new Vector<>(new DoubleArray(result));
        }
        else if (getNextProcessor() != null) {
            return getNextProcessor().vectorNegation(vector);
        }
        return null;
    }

    /**
     * Processes VectorDotProduct operation with Double's.
     */
    @Override
    public <T extends Number> T vectorDotProduct(Vector<T> first, Vector<T> second) {
        if (Double.class.isAssignableFrom(first.getValues().getElementClass())) {

            double[] arr1 = first.getValues().doubleArray();
            double[] arr2 = second.getValues().doubleArray();

            int minLength = Math.min(arr1.length, arr2.length);

            double product = 0.0D;

            for (int i = 0; i < minLength; i++) {
                product += arr1[i] * arr2[i];
            }

            return (T) (Double)product;
        }
        else if (getNextProcessor() != null) {
            return getNextProcessor().vectorDotProduct(first, second);
        }
        return null;
    }

    /**
     * Processes VectorMagnitude operation with Double's.
     */
    @Override
    public <T extends Number> T vectorMagnitude(Vector<T> vector) {
        if (Double.class.isAssignableFrom(vector.getValues().getElementClass())) {

            double[] arr = vector.getValues().doubleArray();

            double sumOfSquares = 0.0D;
            for (int i = 0; i < arr.length; i++) {
                sumOfSquares += arr[i] * arr[i];
            }

            Double magnitude = (double) Math.sqrt(sumOfSquares);
            return (T) magnitude;
        }
        else if (getNextProcessor() != null) {
            return getNextProcessor().vectorMagnitude(vector);
        }
        return null;
    }

    /**
     * Processes ScalarMultiplication operation with Double's.
     */
    @Override
    public <T extends Number> Vector<T> scalarMultiplication(Vector<T> vector, T scalar) {
        if (Double.class.isAssignableFrom(vector.getValues().getElementClass())) {

            double[] arr = vector.getValues().doubleArray();
            double[] result = new double[arr.length];

            for (int i = 0; i < arr.length; i++) {
                result[i] = arr[i] * scalar.doubleValue();
            }

            return (Vector<T>) new Vector<>(new DoubleArray(result));
        }
        else if (getNextProcessor() != null) {
            return getNextProcessor().scalarMultiplication(vector, scalar);
        }
        return null;
    }

    /**
     * Processes ScalarDivision operation with Double's.
     */
    @Override
    public <T extends Number> Vector<T> scalarDivision(Vector<T> vector, T scalar) {
        if (Double.class.isAssignableFrom(vector.getValues().getElementClass())) {

            double[] arr = vector.getValues().doubleArray();
            double[] result = new double[arr.length];

            for (int i = 0; i < arr.length; i++) {
                result[i] = arr[i] / scalar.doubleValue();
            }

            return (Vector<T>) new Vector<>(new DoubleArray(result));
        }
        else if (getNextProcessor() != null) {
            return getNextProcessor().scalarDivision(vector, scalar);
        }
        return null;
    }

    /**
     * Builds new Double vector of any length.
     */
    @Override
    public <T extends Number> Vector<T> buildVector(Class<T> clas, int length) {
        if (Double.class.isAssignableFrom(clas)) {
            return (Vector<T>) new Vector<>(new DoubleArray(new double[length]));
        }
        else if (getNextProcessor() != null) {
            return getNextProcessor().buildVector(clas, length);
        }
        return null;
    }

    /**
     * Builds new Double 2D vector.
     */
    @Override
    public <T extends Number> Vector<T> buildVector(T x, T y) {
        if (Double.class.isAssignableFrom(x.getClass())) {
            return (Vector<T>) new Vector<>(new DoubleArray(new double[] {x.doubleValue(), y.doubleValue()}));
        }
        else if (getNextProcessor() != null) {
            return getNextProcessor().buildVector(x, y);
        }
        return null;
    }

    /**
     * Builds new Double 3D vector.
     */
    @Override
    public <T extends Number> Vector<T> buildVector(T x, T y, T z) {
        if (Double.class.isAssignableFrom(x.getClass())) {
            return (Vector<T>) new Vector<>(new DoubleArray(new double[] {x.doubleValue(), y.doubleValue(), z.doubleValue()}));
        }
        else if (getNextProcessor() != null) {
            return getNextProcessor().buildVector(x, y, z);
        }
        return null;
    }

    /**
     * Builds new Double 4D vector.
     */
    @Override
    public <T extends Number> Vector<T> buildVector(T x, T y, T z, T w) {
        if (Double.class.isAssignableFrom(x.getClass())) {
            return (Vector<T>) new Vector<>(new DoubleArray(new double[] {x.doubleValue(), y.doubleValue(), z.doubleValue(), w.doubleValue()}));
        }
        else if (getNextProcessor() != null) {
            return getNextProcessor().buildVector(x, y, z, w);
        }
        return null;
    }

    /**
     * Builds new Double identity matrix.
     */
    @Override
    public <T extends Number> Matrix<T> buildIdentityMatrix(Class<T> clas, int rows, int columns) {
        if (Double.class.isAssignableFrom(clas)) {
            double[] identity = new double[rows * columns];
            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < columns; col++) {
                    if (row == col) {
                        identity[col + row * columns] = 1.0D;
                    }
                    else {
                        identity[col + row * columns] = 0.0D;
                    }
                }
            }
            return (Matrix<T>) new Matrix<>(new DoubleArray(identity), rows, columns);
        }
        else if (getNextProcessor() != null) {
            return getNextProcessor().buildIdentityMatrix(clas, rows, columns);
        }
        return null;
    }

    /**
     * Processes MatrixMultiplication operation with Double's.
     */
    @Override
    public <T extends Number> Matrix<T> matrixMultiplication(Matrix<T> first, Matrix<T> second) {
        if (Double.class.isAssignableFrom(first.getElements().getElementClass())) {

            if (first.getColumns() == second.getRows()) {
                double[] arr1 = first.getElements().doubleArray();
                double[] arr2 = second.getElements().doubleArray();
                double[] result = new double[first.getRows() * second.getColumns()];

                for (int rCol = 0; rCol < second.getColumns(); rCol++) {
                    for (int rRow = 0; rRow < first.getRows(); rRow++) {
                        double res = 0.0D;
                        for (int col = 0; col < first.getColumns(); col++) {
                            res += arr1[col + rRow * first.getColumns()] * arr2[rCol + col * second.getColumns()];
                        }
                        result[rCol + rRow * second.getColumns()] = res;
                    }
                }

                return (Matrix<T>) new Matrix<>(new DoubleArray(result), first.getRows(), second.getColumns());
            }
        }
        else if (getNextProcessor() != null) {
            return getNextProcessor().matrixMultiplication(first, second);
        }
        return null;
    }

    /**
     * Processes MatrixVectorMultiplication operation with Double's.
     */
    @Override
    public <T extends Number> Vector<T> matrixVectorMultiplication(Matrix<T> matrix, Vector<T> vector) {
        if (Double.class.isAssignableFrom(matrix.getElements().getElementClass())) {

            if (matrix.getColumns() == vector.getValues().doubleArray().length) {
                double[] arr1 = matrix.getElements().doubleArray();
                double[] arr2 = vector.getValues().doubleArray();
                double[] result = new double[arr2.length];

                for (int rRow = 0; rRow < result.length; rRow++) {
                    double res = 0.0D;
                    for (int col = 0; col < matrix.getColumns(); col++) {
                        res += arr1[col + rRow * matrix.getColumns()] * arr2[col];
                    }
                    result[rRow] = res;
                }

                return (Vector<T>) new Vector<>(new DoubleArray(result));
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

        double[] arr = vector.getValues().doubleArray();
        double[] result = new double[length];

        for(int i = 0; i < length; i++) {
            int index = AXIS.indexOf(axis.charAt(i));
            if (index < arr.length) {
                result[i] = arr[index];
            }
        }
        return (Vector<T>) new Vector<>(new DoubleArray(result));
    }
}
