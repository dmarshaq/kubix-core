package org.dmarshaq.kubix.math.processor;

import org.dmarshaq.kubix.math.matrix.Matrix;
import org.dmarshaq.kubix.math.Vector;
import org.dmarshaq.kubix.math.operation.*;

public class ByteProcessor extends OperationProcessor {
    public ByteProcessor(OperationProcessor nextProcessor) {
        super(nextProcessor);
    }

    /**
     * Processes VectorAddition operation with Byte's.
     */
    @Override
    public <T extends Number> void processOperation(VectorAddition<T> operation) {
        if (Byte.class.isAssignableFrom(operation.getClasType())) {
            Vector<T> large = operation.getVector1();
            Vector<T> small = operation.getVector2();
            if (small.getValues().length > large.getValues().length) {
                large = operation.getVector2();
                small = operation.getVector1();
            }

            Vector<T> resultant = new Vector<>(large.getValues().length);

            for (int i = 0; i < small.getValues().length; i++) {
                Byte sum = (byte) (small.getValues()[i].byteValue() + large.getValues()[i].byteValue());
                resultant.getValues()[i] = (T) sum;
            }
            for (int i = small.getValues().length; i < large.getValues().length; i++) {
                Byte sum = large.getValues()[i].byteValue();
                resultant.getValues()[i] = (T) sum;
            }

            operation.setResultant(resultant);
        }
        else if (getNextProcessor() != null) {
            getNextProcessor().processOperation(operation);
        }
    }

    /**
     * Processes VectorNegation operation with Byte's.
     */
    @Override
    public <T extends Number> void processOperation(VectorNegation<T> operation) {
        if (Byte.class.isAssignableFrom(operation.getClasType())) {
            Vector<T> vector = operation.getVector();
            Vector<T> resultant = new Vector<>(vector.getValues().length);

            for (int i = 0; i < resultant.getValues().length; i++) {
                Byte negativeNum = (byte) -vector.getValues()[i].byteValue();
                resultant.getValues()[i] = (T) negativeNum;
            }

            operation.setResultant(resultant);
        }
        else if (getNextProcessor() != null) {
            getNextProcessor().processOperation(operation);
        }
    }

    /**
     * Processes VectorDotProduct operation with Byte's.
     */
    @Override
    public <T extends Number> void processOperation(VectorDotProduct<T> operation) {
        if (Byte.class.isAssignableFrom(operation.getClasType())) {
            Vector<T> vector1 = operation.getVector1();
            Vector<T> vector2 = operation.getVector2();

            int minLength = Math.min(vector1.getValues().length, vector2.getValues().length);

            Byte product = 0;

            for (int i = 0; i < minLength; i++) {
                product = (byte) ((vector1.getValues()[i].byteValue() * vector2.getValues()[i].byteValue()) + product.byteValue());
            }

            operation.setProduct((T) product);
        }
        else if (getNextProcessor() != null) {
            getNextProcessor().processOperation(operation);
        }
    }

    /**
     * Processes VectorMagnitude operation with Byte's.
     */
    @Override
    public <T extends Number> void processOperation(VectorMagnitude<T> operation) {
        if (Byte.class.isAssignableFrom(operation.getClasType())) {
            Vector<T> vector1 = operation.getVector();

            byte sumOfSquares = 0;
            for (int i = 0; i < vector1.getValues().length; i++) {
                sumOfSquares += (byte) (vector1.getValues()[i].byteValue() * vector1.getValues()[i].byteValue());
            }

            Byte magnitude = (byte) Math.sqrt(sumOfSquares);
            operation.setMagnitude((T) magnitude);
        }
        else if (getNextProcessor() != null) {
            getNextProcessor().processOperation(operation);
        }
    }

    /**
     * Processes ScalarMultiplication operation with Byte's.
     */
    @Override
    public <T extends Number> void processOperation(ScalarMultiplication<T> operation) {
        if (Byte.class.isAssignableFrom(operation.getClasType())) {
            Vector<T> vector = operation.getVector();
            T scalar = operation.getScalar();

            Vector<T> resultant = new Vector<>(vector.getValues().length);

            for (int i = 0; i < resultant.getValues().length; i++) {
                Byte mul = (byte) (vector.getValues()[i].byteValue() * scalar.byteValue());
                resultant.getValues()[i] = (T) mul;
            }

            operation.setResultant(resultant);
        }
        else if (getNextProcessor() != null) {
            getNextProcessor().processOperation(operation);
        }
    }

    /**
     * Processes ScalarDivision operation with Byte's.
     */
    @Override
    public <T extends Number> void processOperation(ScalarDivision<T> operation) {
        if (Byte.class.isAssignableFrom(operation.getClasType())) {
            Vector<T> vector = operation.getVector();
            T scalar = operation.getScalar();

            Vector<T> resultant = new Vector<>(vector.getValues().length);

            for (int i = 0; i < resultant.getValues().length; i++) {
                Byte div = (byte) (vector.getValues()[i].byteValue() / scalar.byteValue());
                resultant.getValues()[i] = (T) div;
            }

            operation.setResultant(resultant);
        }
        else if (getNextProcessor() != null) {
            getNextProcessor().processOperation(operation);
        }
    }

    /**
     * Builds new Byte identity matrix.
     */
    @Override
    public <T extends Number> Matrix<T> buildIdentityMatrix(Class<T> clas, int rows, int columns) {
        if (Byte.class.isAssignableFrom(clas)) {
            Matrix<T> identity = new Matrix<>(rows, columns);
            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < columns; col++) {
                    Byte element;
                    if (row == col) {
                        element = (byte) 1;
                    }
                    else {
                        element = (byte) 0;
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
     * Processes MatrixMultiplication operation with Byte's.
     */
    @Override
    public <T extends Number> void processOperation(MatrixMultiplication<T> operation) {
        if (Byte.class.isAssignableFrom(operation.getClasType())) {
            Matrix<T> first = operation.getFirst();
            Matrix<T> second = operation.getSecond();

            if (first.getColumns() == second.getRows()) {
                Matrix<T> result = new Matrix<>(first.getRows(), second.getColumns());
                for (int rCol = 0; rCol < result.getColumns(); rCol++) {
                    for (int rRow = 0; rRow < result.getRows(); rRow++) {
                        byte res = (byte) 0;
                        for (int col = 0; col < first.getColumns(); col++) {
                            res += (byte) (first.getElement(rRow, col).byteValue() * second.getElement(col, rCol).byteValue());
                        }
                        Byte element = res;
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
     * Processes MatrixVectorMultiplication operation with Byte's.
     */
    @Override
    public <T extends Number> void processOperation(MatrixVectorMultiplication<T> operation) {
        if (Byte.class.isAssignableFrom(operation.getClasType())) {
            Matrix<T> matrix = operation.getMatrix();
            Vector<T> vector = operation.getVector();

            if (matrix.getColumns() == vector.getValues().length) {
                Vector<T> result = new Vector<>(vector.getValues().length);
                for (int rRow = 0; rRow < result.getValues().length; rRow++) {
                    byte res = (byte) 0;
                    for (int col = 0; col < matrix.getColumns(); col++) {
                        res += (byte) (matrix.getElement(rRow, col).floatValue() * vector.getComponent(col).floatValue());
                    }
                    Byte value = res;
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
