package org.dmarshaq.kubix.math.processor;

import org.dmarshaq.kubix.math.matrix.Matrix;
import org.dmarshaq.kubix.math.Vector;
import org.dmarshaq.kubix.math.operation.*;

public class ShortProcessor extends OperationProcessor {
    public ShortProcessor(OperationProcessor nextProcessor) {
        super(nextProcessor);
    }

    /**
     * Processes VectorAddition operation with Short's.
     */
    @Override
    public <T extends Number> void processOperation(VectorAddition<T> operation) {
        if (Short.class.isAssignableFrom(operation.getClasType())) {
            Vector<T> large = operation.getVector1();
            Vector<T> small = operation.getVector2();
            if (small.getValues().length > large.getValues().length) {
                large = operation.getVector2();
                small = operation.getVector1();
            }

            Vector<T> resultant = new Vector<>(large.getValues().length);

            for (int i = 0; i < small.getValues().length; i++) {
                Short sum = (short) (small.getValues()[i].shortValue() + large.getValues()[i].shortValue());
                resultant.getValues()[i] = (T) sum;
            }
            for (int i = small.getValues().length; i < large.getValues().length; i++) {
                Short sum = large.getValues()[i].shortValue();
                resultant.getValues()[i] = (T) sum;
            }

            operation.setResultant(resultant);
        }
        else if (getNextProcessor() != null) {
            getNextProcessor().processOperation(operation);
        }
    }

    /**
     * Processes VectorNegation operation with Short's.
     */
    @Override
    public <T extends Number> void processOperation(VectorNegation<T> operation) {
        if (Short.class.isAssignableFrom(operation.getClasType())) {
            Vector<T> vector = operation.getVector();
            Vector<T> resultant = new Vector<>(vector.getValues().length);

            for (int i = 0; i < resultant.getValues().length; i++) {
                Short negativeNum = (short) -vector.getValues()[i].shortValue();
                resultant.getValues()[i] = (T) negativeNum;
            }

            operation.setResultant(resultant);
        }
        else if (getNextProcessor() != null) {
            getNextProcessor().processOperation(operation);
        }
    }

    /**
     * Processes VectorDotProduct operation with Short's.
     */
    @Override
    public <T extends Number> void processOperation(VectorDotProduct<T> operation) {
        if (Short.class.isAssignableFrom(operation.getClasType())) {
            Vector<T> vector1 = operation.getVector1();
            Vector<T> vector2 = operation.getVector2();

            int minLength = Math.min(vector1.getValues().length, vector2.getValues().length);

            Short product = 0;

            for (int i = 0; i < minLength; i++) {
                product = (short) ((vector1.getValues()[i].shortValue() * vector2.getValues()[i].shortValue()) + product);
            }

            operation.setProduct((T) product);
        }
        else if (getNextProcessor() != null) {
            getNextProcessor().processOperation(operation);
        }
    }

    /**
     * Processes VectorMagnitude operation with Short's.
     */
    @Override
    public <T extends Number> void processOperation(VectorMagnitude<T> operation) {
        if (Short.class.isAssignableFrom(operation.getClasType())) {
            Vector<T> vector1 = operation.getVector();

            short sumOfSquares = 0;
            for (int i = 0; i < vector1.getValues().length; i++) {
                sumOfSquares += (short) (vector1.getValues()[i].shortValue() * vector1.getValues()[i].shortValue());
            }

            Short magnitude = (short) Math.sqrt(sumOfSquares);
            operation.setMagnitude((T) magnitude);
        }
        else if (getNextProcessor() != null) {
            getNextProcessor().processOperation(operation);
        }
    }

    /**
     * Processes ScalarMultiplication operation with Short's.
     */
    @Override
    public <T extends Number> void processOperation(ScalarMultiplication<T> operation) {
        if (Short.class.isAssignableFrom(operation.getClasType())) {
            Vector<T> vector = operation.getVector();
            T scalar = operation.getScalar();

            Vector<T> resultant = new Vector<>(vector.getValues().length);

            for (int i = 0; i < resultant.getValues().length; i++) {
                Short mul = (short) (vector.getValues()[i].shortValue() * scalar.shortValue());
                resultant.getValues()[i] = (T) mul;
            }

            operation.setResultant(resultant);
        }
        else if (getNextProcessor() != null) {
            getNextProcessor().processOperation(operation);
        }
    }

    /**
     * Processes ScalarDivision operation with Short's.
     */
    @Override
    public <T extends Number> void processOperation(ScalarDivision<T> operation) {
        if (Short.class.isAssignableFrom(operation.getClasType())) {
            Vector<T> vector = operation.getVector();
            T scalar = operation.getScalar();

            Vector<T> resultant = new Vector<>(vector.getValues().length);

            for (int i = 0; i < resultant.getValues().length; i++) {
                Short div = (short) (vector.getValues()[i].shortValue() / scalar.shortValue());
                resultant.getValues()[i] = (T) div;
            }

            operation.setResultant(resultant);
        }
        else if (getNextProcessor() != null) {
            getNextProcessor().processOperation(operation);
        }
    }

    /**
     * Builds new Short identity matrix.
     */
    @Override
    public <T extends Number> Matrix<T> buildIdentityMatrix(Class<T> clas, int rows, int columns) {
        if (Short.class.isAssignableFrom(clas)) {
            Matrix<T> identity = new Matrix<>(rows, columns);
            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < columns; col++) {
                    Short element;
                    if (row == col) {
                        element = (short) 1;
                    }
                    else {
                        element = (short) 0;
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
     * Processes MatrixMultiplication operation with Short's.
     */
    @Override
    public <T extends Number> void processOperation(MatrixMultiplication<T> operation) {
        if (Short.class.isAssignableFrom(operation.getClasType())) {
            Matrix<T> first = operation.getFirst();
            Matrix<T> second = operation.getSecond();

            if (first.getColumns() == second.getRows()) {
                Matrix<T> result = new Matrix<>(first.getRows(), second.getColumns());
                for (int rCol = 0; rCol < result.getColumns(); rCol++) {
                    for (int rRow = 0; rRow < result.getRows(); rRow++) {
                        short res = (short) 0;
                        for (int col = 0; col < first.getColumns(); col++) {
                            res += (short) (first.getElement(rRow, col).shortValue() * second.getElement(col, rCol).shortValue());
                        }
                        Short element = res;
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
     * Processes MatrixVectorMultiplication operation with Short's.
     */
    @Override
    public <T extends Number> void processOperation(MatrixVectorMultiplication<T> operation) {
        if (Short.class.isAssignableFrom(operation.getClasType())) {
            Matrix<T> matrix = operation.getMatrix();
            Vector<T> vector = operation.getVector();

            if (matrix.getColumns() == vector.getValues().length) {
                Vector<T> result = new Vector<>(vector.getValues().length);
                for (int rRow = 0; rRow < result.getValues().length; rRow++) {
                    short res = (short) 0;
                    for (int col = 0; col < matrix.getColumns(); col++) {
                        res += (short) (matrix.getElement(rRow, col).shortValue() * vector.getComponent(col).shortValue());
                    }
                    Short value = res;
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
