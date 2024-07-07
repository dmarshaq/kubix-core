package org.dmarshaq.kubix.math.processors;

import org.dmarshaq.kubix.math.Vector;
import org.dmarshaq.kubix.math.operations.*;

public class DoubleProcessor extends OperationProcessor {
    public DoubleProcessor(OperationProcessor nextProcessor) {
        super(nextProcessor);
    }

    /**
     * Processes VectorAddition operation with Double's.
     */
    @Override
    public <T extends Number> void processOperation(VectorAddition<T> operation) {
        if (Double.class.isAssignableFrom(operation.getClasType())) {
            Vector<T> large = operation.getVector1();
            Vector<T> small = operation.getVector2();
            if (small.getValues().length > large.getValues().length) {
                large = operation.getVector2();
                small = operation.getVector1();
            }

            Vector<T> resultant = new Vector<>(large.getValues().length);

            for (int i = 0; i < small.getValues().length; i++) {
                Double sum = small.getValues()[i].doubleValue() + large.getValues()[i].doubleValue();
                resultant.getValues()[i] = (T) sum;
            }
            for (int i = small.getValues().length; i < large.getValues().length; i++) {
                Double sum = large.getValues()[i].doubleValue();
                resultant.getValues()[i] = (T) sum;
            }

            operation.setResultant(resultant);
        }
        else if (getNextProcessor() != null) {
            getNextProcessor().processOperation(operation);
        }
    }

    /**
     * Processes VectorNegation operation with Double's.
     */
    @Override
    public <T extends Number> void processOperation(VectorNegation<T> operation) {
        if (Double.class.isAssignableFrom(operation.getClasType())) {
            Vector<T> vector = operation.getVector();
            Vector<T> resultant = new Vector<>(vector.getValues().length);

            for (int i = 0; i < resultant.getValues().length; i++) {
                Double negativeNum = -vector.getValues()[i].doubleValue();
                resultant.getValues()[i] = (T) negativeNum;
            }

            operation.setResultant(resultant);
        }
        else if (getNextProcessor() != null) {
            getNextProcessor().processOperation(operation);
        }
    }

    /**
     * Processes VectorDotProduct operation with Double's.
     */
    @Override
    public <T extends Number> void processOperation(VectorDotProduct<T> operation) {
        if (Double.class.isAssignableFrom(operation.getClasType())) {
            Vector<T> vector1 = operation.getVector1();
            Vector<T> vector2 = operation.getVector2();

            int minLength = Math.min(vector1.getValues().length, vector2.getValues().length);

            Double product = 0D;

            for (int i = 0; i < minLength; i++) {
                product += vector1.getValues()[i].doubleValue() * vector2.getValues()[i].doubleValue();
            }

            operation.setProduct((T) product);
        }
        else if (getNextProcessor() != null) {
            getNextProcessor().processOperation(operation);
        }
    }

    /**
     * Processes VectorMagnitude operation with Double's.
     */
    @Override
    public <T extends Number> void processOperation(VectorMagnitude<T> operation) {
        if (Double.class.isAssignableFrom(operation.getClasType())) {
            Vector<T> vector1 = operation.getVector();

            double sumOfSquares = 0;
            for (int i = 0; i < vector1.getValues().length; i++) {
                sumOfSquares += vector1.getValues()[i].doubleValue() * vector1.getValues()[i].doubleValue();
            }

            Double magnitude = Math.sqrt(sumOfSquares);
            operation.setMagnitude((T) magnitude);
        }
        else if (getNextProcessor() != null) {
            getNextProcessor().processOperation(operation);
        }
    }

    /**
     * Processes ScalarMultiplication operation with Double's.
     */
    @Override
    public <T extends Number> void processOperation(ScalarMultiplication<T> operation) {
        if (Double.class.isAssignableFrom(operation.getClasType())) {
            Vector<T> vector = operation.getVector();
            T scalar = operation.getScalar();

            Vector<T> resultant = new Vector<>(vector.getValues().length);

            for (int i = 0; i < resultant.getValues().length; i++) {
                Double mul = vector.getValues()[i].doubleValue() * scalar.doubleValue();
                resultant.getValues()[i] = (T) mul;
            }

            operation.setResultant(resultant);
        }
        else if (getNextProcessor() != null) {
            getNextProcessor().processOperation(operation);
        }
    }

    /**
     * Processes ScalarDivision operation with Double's.
     */
    @Override
    public <T extends Number> void processOperation(ScalarDivision<T> operation) {
        if (Double.class.isAssignableFrom(operation.getClasType())) {
            Vector<T> vector = operation.getVector();
            T scalar = operation.getScalar();

            Vector<T> resultant = new Vector<>(vector.getValues().length);

            for (int i = 0; i < resultant.getValues().length; i++) {
                Double div = vector.getValues()[i].doubleValue() / scalar.doubleValue();
                resultant.getValues()[i] = (T) div;
            }

            operation.setResultant(resultant);
        }
        else if (getNextProcessor() != null) {
            getNextProcessor().processOperation(operation);
        }
    }
}
