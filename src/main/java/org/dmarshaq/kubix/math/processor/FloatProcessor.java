package org.dmarshaq.kubix.math.processor;

import org.dmarshaq.kubix.math.matrix.Matrix;
import org.dmarshaq.kubix.math.Vector;
import org.dmarshaq.kubix.math.operation.*;

public class FloatProcessor extends OperationProcessor {
    public FloatProcessor(OperationProcessor nextProcessor) {
        super(nextProcessor);
    }

    /**
     * Processes VectorAddition operation with Float's.
     */
    @Override
    public <T extends Number> void processOperation(VectorAddition<T> operation) {
        if (Float.class.isAssignableFrom(operation.getClasType())) {
            Vector<T> large = operation.getVector1();
            Vector<T> small = operation.getVector2();
            if (small.getValues().length > large.getValues().length) {
                large = operation.getVector2();
                small = operation.getVector1();
            }

            Vector<T> resultant = new Vector<>(large.getValues().length);

            for (int i = 0; i < small.getValues().length; i++) {
                Float sum = small.getValues()[i].floatValue() + large.getValues()[i].floatValue();
                resultant.getValues()[i] = (T) sum;
            }
            for (int i = small.getValues().length; i < large.getValues().length; i++) {
                Float sum = large.getValues()[i].floatValue();
                resultant.getValues()[i] = (T) sum;
            }

            operation.setResultant(resultant);
        }
        else if (getNextProcessor() != null) {
            getNextProcessor().processOperation(operation);
        }
    }

    /**
     * Processes VectorNegation operation with Float's.
     */
    @Override
    public <T extends Number> void processOperation(VectorNegation<T> operation) {
        if (Float.class.isAssignableFrom(operation.getClasType())) {
            Vector<T> vector = operation.getVector();
            Vector<T> resultant = new Vector<>(vector.getValues().length);

            for (int i = 0; i < resultant.getValues().length; i++) {
                Float negativeNum = -vector.getValues()[i].floatValue();
                resultant.getValues()[i] = (T) negativeNum;
            }

            operation.setResultant(resultant);
        }
        else if (getNextProcessor() != null) {
            getNextProcessor().processOperation(operation);
        }
    }

    /**
     * Processes VectorDotProduct operation with Float's.
     */
    @Override
    public <T extends Number> void processOperation(VectorDotProduct<T> operation) {
        if (Float.class.isAssignableFrom(operation.getClasType())) {
            Vector<T> vector1 = operation.getVector1();
            Vector<T> vector2 = operation.getVector2();

            int minLength = Math.min(vector1.getValues().length, vector2.getValues().length);

            Float product = 0.0f;

            for (int i = 0; i < minLength; i++) {
                product += vector1.getValues()[i].floatValue() * vector2.getValues()[i].floatValue();
            }

            operation.setProduct((T) product);
        }
        else if (getNextProcessor() != null) {
            getNextProcessor().processOperation(operation);
        }
    }

    /**
     * Processes VectorMagnitude operation with Float's.
     */
    @Override
    public <T extends Number> void processOperation(VectorMagnitude<T> operation) {
        if (Float.class.isAssignableFrom(operation.getClasType())) {
            Vector<T> vector1 = operation.getVector();

            float sumOfSquares = 0.0f;
            for (int i = 0; i < vector1.getValues().length; i++) {
                sumOfSquares += vector1.getValues()[i].floatValue() * vector1.getValues()[i].floatValue();
            }

            Float magnitude = (float) Math.sqrt(sumOfSquares);
            operation.setMagnitude((T) magnitude);
        }
        else if (getNextProcessor() != null) {
            getNextProcessor().processOperation(operation);
        }
    }

    /**
     * Processes ScalarMultiplication operation with Float's.
     */
    @Override
    public <T extends Number> void processOperation(ScalarMultiplication<T> operation) {
        if (Float.class.isAssignableFrom(operation.getClasType())) {
            Vector<T> vector = operation.getVector();
            T scalar = operation.getScalar();

            Vector<T> resultant = new Vector<>(vector.getValues().length);

            for (int i = 0; i < resultant.getValues().length; i++) {
                Float mul = vector.getValues()[i].floatValue() * scalar.floatValue();
                resultant.getValues()[i] = (T) mul;
            }

            operation.setResultant(resultant);
        }
        else if (getNextProcessor() != null) {
            getNextProcessor().processOperation(operation);
        }
    }

    /**
     * Processes ScalarDivision operation with Float's.
     */
    @Override
    public <T extends Number> void processOperation(ScalarDivision<T> operation) {
        if (Float.class.isAssignableFrom(operation.getClasType())) {
            Vector<T> vector = operation.getVector();
            T scalar = operation.getScalar();

            Vector<T> resultant = new Vector<>(vector.getValues().length);

            for (int i = 0; i < resultant.getValues().length; i++) {
                Float div = vector.getValues()[i].floatValue() / scalar.floatValue();
                resultant.getValues()[i] = (T) div;
            }

            operation.setResultant(resultant);
        }
        else if (getNextProcessor() != null) {
            getNextProcessor().processOperation(operation);
        }
    }

    /**
     * Builds new Float identity matrix.
     */
    @Override
    public <T extends Number> Matrix<T> buildIdentityMatrix(Class<T> clas, int rows, int columns) {
        if (Float.class.isAssignableFrom(clas)) {
            Matrix<T> identity = new Matrix<>(rows, columns);
            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < columns; col++) {
                    Float element;
                    if (row == col) {
                        element = 1.0f;
                    }
                    else {
                        element = 0.0f;
                    }
                    identity.getElements()[row][col] = (T) element;
                }
            }
            return identity;
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
    public <T extends Number> void processOperation(MatrixMultiplication<T> operation) {
        if (Float.class.isAssignableFrom(operation.getClasType())) {
            Matrix<T> first = operation.getFirst();
            Matrix<T> second = operation.getSecond();

            if (first.getColumns() == second.getRows()) {
                Matrix<T> result = new Matrix<>(first.getRows(), second.getColumns());
                for (int rCol = 0; rCol < result.getColumns(); rCol++) {
                    for (int rRow = 0; rRow < result.getRows(); rRow++) {
                        float res = 0.0f;
                        for (int col = 0; col < first.getColumns(); col++) {
                            res += first.getElement(rRow, col).floatValue() * second.getElement(col, rCol).floatValue();
                        }
                        Float element = res;
                        result.getElements()[rRow][rCol] = (T) element;
                    }
                }
                operation.setResult(result);
            }
        }
        else if (getNextProcessor() != null) {
            getNextProcessor().processOperation(operation);
        }
    }

    /**
     * Processes MatrixVectorMultiplication operation with Float's.
     */
    @Override
    public <T extends Number> void processOperation(MatrixVectorMultiplication<T> operation) {
        if (Float.class.isAssignableFrom(operation.getClasType())) {
            Matrix<T> matrix = operation.getMatrix();
            Vector<T> vector = operation.getVector();

            if (matrix.getColumns() == vector.getValues().length) {
                Vector<T> result = new Vector<>(vector.getValues().length);
                for (int rRow = 0; rRow < result.getValues().length; rRow++) {
                    float res = 0.0f;
                    for (int col = 0; col < matrix.getColumns(); col++) {
                        res += matrix.getElement(rRow, col).floatValue() * vector.getComponent(col).floatValue();
                    }
                    Float value = res;
                    result.getValues()[rRow] = (T) value;
                }
                operation.setResultant(result);
            }
        }
        else if (getNextProcessor() != null) {
            getNextProcessor().processOperation(operation);
        }
    }
}
