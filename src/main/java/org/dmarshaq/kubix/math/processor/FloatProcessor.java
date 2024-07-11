package org.dmarshaq.kubix.math.processor;

import org.dmarshaq.kubix.math.array.FloatArray;
import org.dmarshaq.kubix.math.matrix.Matrix;
import org.dmarshaq.kubix.math.vector.Vector;

import java.util.Arrays;

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


//
//    /**
//     * Builds new Float identity matrix.
//     */
//    @Override
//    public <T extends Number> Matrix<T> buildIdentityMatrix(Class<T> clas, int rows, int columns) {
//        if (Float.class.isAssignableFrom(clas)) {
//            Matrix<T> identity = new Matrix<>(rows, columns);
//            for (int row = 0; row < rows; row++) {
//                for (int col = 0; col < columns; col++) {
//                    Float element;
//                    if (row == col) {
//                        element = 1.0f;
//                    }
//                    else {
//                        element = 0.0f;
//                    }
//                    identity.getElements()[row][col] = (T) element;
//                }
//            }
//            return identity;
//        }
//        else if (getNextProcessor() != null) {
//            return getNextProcessor().buildIdentityMatrix(clas, rows, columns);
//        }
//        return null;
//    }
//
//    /**
//     * Processes MatrixMultiplication operation with Float's.
//     */
//    @Override
//    public <T extends Number> void processOperation(MatrixMultiplication<T> operation) {
//        if (Float.class.isAssignableFrom(operation.getClasType())) {
//            Matrix<T> first = operation.getFirst();
//            Matrix<T> second = operation.getSecond();
//
//            if (first.getColumns() == second.getRows()) {
//                Matrix<T> result = new Matrix<>(first.getRows(), second.getColumns());
//                for (int rCol = 0; rCol < result.getColumns(); rCol++) {
//                    for (int rRow = 0; rRow < result.getRows(); rRow++) {
//                        float res = 0.0f;
//                        for (int col = 0; col < first.getColumns(); col++) {
//                            res += first.getElement(rRow, col).floatValue() * second.getElement(col, rCol).floatValue();
//                        }
//                        Float element = res;
//                        result.getElements()[rRow][rCol] = (T) element;
//                    }
//                }
//                operation.setResult(result);
//            }
//        }
//        else if (getNextProcessor() != null) {
//            getNextProcessor().processOperation(operation);
//        }
//    }
//
//    /**
//     * Processes MatrixVectorMultiplication operation with Float's.
//     */
//    @Override
//    public <T extends Number> void processOperation(MatrixVectorMultiplication<T> operation) {
//        if (Float.class.isAssignableFrom(operation.getClasType())) {
//            Matrix<T> matrix = operation.getMatrix();
//            Vector<T> vector = operation.getVector();
//
//            if (matrix.getColumns() == vector.getValues().length) {
//                Vector<T> result = new Vector<>(vector.getValues().length);
//                for (int rRow = 0; rRow < result.getValues().length; rRow++) {
//                    float res = 0.0f;
//                    for (int col = 0; col < matrix.getColumns(); col++) {
//                        res += matrix.getElement(rRow, col).floatValue() * vector.getComponent(col).floatValue();
//                    }
//                    Float value = res;
//                    result.getValues()[rRow] = (T) value;
//                }
//                operation.setResultant(result);
//            }
//        }
//        else if (getNextProcessor() != null) {
//            getNextProcessor().processOperation(operation);
//        }
//    }
}
