package org.dmarshaq.kubix.core.math.processor;

import org.dmarshaq.kubix.core.math.array.FloatArray;
import org.dmarshaq.kubix.core.math.function.Domain;
import org.dmarshaq.kubix.core.math.matrix.Matrix;
import org.dmarshaq.kubix.core.math.vector.Vector;

import static org.dmarshaq.kubix.core.math.base.MathCore.AXIS;

public class FloatProcessor extends OperationProcessor {
    public FloatProcessor(OperationProcessor nextProcessor) {
        super(nextProcessor);
    }

    /**
     * Processes VectorAddition operation with Float's.
     */
    @Override
    public <T extends Number> Vector<T> vectorAddition(Vector<T> first, Vector<T> second) {
        if (Float.class.isAssignableFrom(first.getValues().getElementClass())) {

            float[] arr1 = first.getValues().floatArray();
            float[] arr2 = second.getValues().floatArray();
            if (arr1.length < arr2.length) {
                arr1 = arr2;
                arr2 = first.getValues().floatArray();
            }

            float[] result = new float[arr1.length];

            int i = 0;
            for (; i < arr2.length; i++) {
                result[i] = arr1[i] + arr2[i];
            }
            for (; i < arr1.length; i++) {
                result[i] = arr1[i];
            }

            return (Vector<T>) new Vector<>(new FloatArray(result));
        }
        else if (getNextProcessor() != null) {
            return getNextProcessor().vectorAddition(first, second);
        }
        return null;
    }

    /**
     * Processes VectorNegation operation with Float's.
     */
    @Override
    public <T extends Number> Vector<T> vectorNegation(Vector<T> vector) {
        if (Float.class.isAssignableFrom(vector.getValues().getElementClass())) {

            float[] arr = vector.getValues().floatArray();
            float[] result = new float[arr.length];

            for (int i = 0; i < arr.length; i++) {
                result[i] = -arr[i];
            }

            return (Vector<T>) new Vector<>(new FloatArray(result));
        }
        else if (getNextProcessor() != null) {
            return getNextProcessor().vectorNegation(vector);
        }
        return null;
    }

    /**
     * Processes VectorDotProduct operation with Float's.
     */
    @Override
    public <T extends Number> T vectorDotProduct(Vector<T> first, Vector<T> second) {
        if (Float.class.isAssignableFrom(first.getValues().getElementClass())) {

            float[] arr1 = first.getValues().floatArray();
            float[] arr2 = second.getValues().floatArray();

            int minLength = Math.min(arr1.length, arr2.length);

            float product = 0.0f;

            for (int i = 0; i < minLength; i++) {
                product += arr1[i] * arr2[i];
            }

            return (T) (Float)product;
        }
        else if (getNextProcessor() != null) {
            return getNextProcessor().vectorDotProduct(first, second);
        }
        return null;
    }

    /**
     * Processes VectorMagnitude operation with Float's.
     */
    @Override
    public <T extends Number> T vectorMagnitude(Vector<T> vector) {
        if (Float.class.isAssignableFrom(vector.getValues().getElementClass())) {

            float[] arr = vector.getValues().floatArray();

            float sumOfSquares = 0.0f;
            for (int i = 0; i < arr.length; i++) {
                sumOfSquares += arr[i] * arr[i];
            }

            Float magnitude = (float) Math.sqrt(sumOfSquares);
            return (T) magnitude;
        }
        else if (getNextProcessor() != null) {
            return getNextProcessor().vectorMagnitude(vector);
        }
        return null;
    }

    /**
     * Processes ScalarMultiplication operation with Float's.
     */
    @Override
    public <T extends Number> Vector<T> scalarMultiplication(Vector<T> vector, T scalar) {
        if (Float.class.isAssignableFrom(vector.getValues().getElementClass())) {

            float[] arr = vector.getValues().floatArray();
            float[] result = new float[arr.length];

            for (int i = 0; i < arr.length; i++) {
                result[i] = arr[i] * scalar.floatValue();
            }

            return (Vector<T>) new Vector<>(new FloatArray(result));
        }
        else if (getNextProcessor() != null) {
            return getNextProcessor().scalarMultiplication(vector, scalar);
        }
        return null;
    }

    /**
     * Processes ScalarDivision operation with Float's.
     */
    @Override
    public <T extends Number> Vector<T> scalarDivision(Vector<T> vector, T scalar) {
        if (Float.class.isAssignableFrom(vector.getValues().getElementClass())) {

            float[] arr = vector.getValues().floatArray();
            float[] result = new float[arr.length];

            for (int i = 0; i < arr.length; i++) {
                result[i] = arr[i] / scalar.floatValue();
            }

            return (Vector<T>) new Vector<>(new FloatArray(result));
        }
        else if (getNextProcessor() != null) {
            return getNextProcessor().scalarDivision(vector, scalar);
        }
        return null;
    }

    /**
     * Builds new Float vector of any length.
     */
    @Override
    public <T extends Number> Vector<T> buildVector(Class<T> clas, int length) {
        if (Float.class.isAssignableFrom(clas)) {
            return (Vector<T>) new Vector<>(new FloatArray(new float[length]));
        }
        else if (getNextProcessor() != null) {
            return getNextProcessor().buildVector(clas, length);
        }
        return null;
    }

    /**
     * Builds new Float 2D vector.
     */
    @Override
    public <T extends Number> Vector<T> buildVector(T x, T y) {
        if (Float.class.isAssignableFrom(x.getClass())) {
            return (Vector<T>) new Vector<>(new FloatArray(new float[] {x.floatValue(), y.floatValue()}));
        }
        else if (getNextProcessor() != null) {
            return getNextProcessor().buildVector(x, y);
        }
        return null;
    }

    /**
     * Builds new Float 3D vector.
     */
    @Override
    public <T extends Number> Vector<T> buildVector(T x, T y, T z) {
        if (Float.class.isAssignableFrom(x.getClass())) {
            return (Vector<T>) new Vector<>(new FloatArray(new float[] {x.floatValue(), y.floatValue(), z.floatValue()}));
        }
        else if (getNextProcessor() != null) {
            return getNextProcessor().buildVector(x, y, z);
        }
        return null;
    }

    /**
     * Builds new Float 4D vector.
     */
    @Override
    public <T extends Number> Vector<T> buildVector(T x, T y, T z, T w) {
        if (Float.class.isAssignableFrom(x.getClass())) {
            return (Vector<T>) new Vector<>(new FloatArray(new float[] {x.floatValue(), y.floatValue(), z.floatValue(), w.floatValue()}));
        }
        else if (getNextProcessor() != null) {
            return getNextProcessor().buildVector(x, y, z, w);
        }
        return null;
    }

    /**
     * Builds new Float identity matrix.
     */
    @Override
    public <T extends Number> Matrix<T> buildIdentityMatrix(Class<T> clas, int rows, int columns) {
        if (Float.class.isAssignableFrom(clas)) {
            float[] identity = new float[rows * columns];
            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < columns; col++) {
                    if (row == col) {
                        identity[col + row * columns] = 1.0f;
                    }
                    else {
                        identity[col + row * columns] = 0.0f;
                    }
                }
            }
            return (Matrix<T>) new Matrix<>(new FloatArray(identity), rows, columns);
        }
        else if (getNextProcessor() != null) {
            return getNextProcessor().buildIdentityMatrix(clas, rows, columns);
        }
        return null;
    }

    /**
     * Processes MatrixMultiplication operation with Float's.
     */
    @Override
    public <T extends Number> Matrix<T> matrixMultiplication(Matrix<T> first, Matrix<T> second) {
        if (Float.class.isAssignableFrom(first.getElements().getElementClass())) {

            if (first.getColumns() == second.getRows()) {
                float[] arr1 = first.getElements().floatArray();
                float[] arr2 = second.getElements().floatArray();
                float[] result = new float[first.getRows() * second.getColumns()];

                for (int rCol = 0; rCol < second.getColumns(); rCol++) {
                    for (int rRow = 0; rRow < first.getRows(); rRow++) {
                        float res = 0.0f;
                        for (int col = 0; col < first.getColumns(); col++) {
                            res += arr1[col + rRow * first.getColumns()] * arr2[rCol + col * second.getColumns()];
                        }
                        result[rCol + rRow * second.getColumns()] = res;
                    }
                }

                return (Matrix<T>) new Matrix<>(new FloatArray(result), first.getRows(), second.getColumns());
            }
        }
        else if (getNextProcessor() != null) {
            return getNextProcessor().matrixMultiplication(first, second);
        }
        return null;
    }

    /**
     * Processes MatrixVectorMultiplication operation with Float's.
     */
    @Override
    public <T extends Number> Vector<T> matrixVectorMultiplication(Matrix<T> matrix, Vector<T> vector) {
        if (Float.class.isAssignableFrom(matrix.getElements().getElementClass())) {

            if (matrix.getColumns() == vector.getValues().floatArray().length) {
                float[] arr1 = matrix.getElements().floatArray();
                float[] arr2 = vector.getValues().floatArray();
                float[] result = new float[matrix.getRows()];

                for (int rRow = 0; rRow < result.length; rRow++) {
                    float res = 0.0f;
                    for (int col = 0; col < matrix.getColumns(); col++) {
                        res += arr1[col + rRow * matrix.getColumns()] * arr2[col];
                    }
                    result[rRow] = res;
                }

                return (Vector<T>) new Vector<>(new FloatArray(result));
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

        float[] arr = vector.getValues().floatArray();
        float[] result = new float[length];

        for(int i = 0; i < length; i++) {
            int index = AXIS.indexOf(axis.charAt(i));
            if (index < arr.length) {
                result[i] = arr[index];
            }
        }
        return (Vector<T>) new Vector<>(new FloatArray(result));
    }

    /**
     * Determines if value is outside of domain.
     */
    @Override
    public <T extends Number> boolean isOutsideDomain(Domain<T> domain, T value) {
        float min = domain.getMin().floatValue();
        float max = domain.getMax().floatValue();
        float val = value.floatValue();

        return val < min || val > max;
    }

    /**
     * Determines if value is inside of domain.
     */
    @Override
    public <T extends Number> boolean isInsideDomain(Domain<T> domain, T value) {
        float min = domain.getMin().floatValue();
        float max = domain.getMax().floatValue();
        float val = value.floatValue();

        return val > min && val < max;
    }

    /**
     * Determines if first number equals, less or greater than the second number.
     */
    @Override
    public <T extends Number> int compareNumbers(T first, T second) {
        return Float.compare(first.floatValue(), second.floatValue());
    }

}
