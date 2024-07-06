package org.dmarshaq.kubix.math.processors;

import org.dmarshaq.kubix.math.Vector;
import org.dmarshaq.kubix.math.operations.VectorDotProduct;
import org.dmarshaq.kubix.math.operations.VectorNegation;
import org.dmarshaq.kubix.math.operations.VectorSummation;

public class IntegerProcessor extends OperationProcessor {
    public IntegerProcessor(OperationProcessor nextProcessor) {
        super(nextProcessor);
    }

    /**
     * Processes VectorSummation operation with Integer's.
     */
    @Override
    public <T extends Number> void processOperation(VectorSummation<T> operation) {
        if (Integer.class.isAssignableFrom(operation.getClasType())) {
            Vector<T> large = operation.getVector1();
            Vector<T> small = operation.getVector2();
            if (small.getValues().length > large.getValues().length) {
                large = operation.getVector2();
                small = operation.getVector1();
            }

            Vector<T> resultant = new Vector<>(large.getValues().length);

            for (int i = 0; i < small.getValues().length; i++) {
                Integer sum = small.getValues()[i].intValue() + large.getValues()[i].intValue();
                resultant.getValues()[i] = (T) sum;
            }
            for (int i = small.getValues().length; i < large.getValues().length; i++) {
                Integer sum = large.getValues()[i].intValue();
                resultant.getValues()[i] = (T) sum;
            }

            operation.setResultant(resultant);
        }
        else if (getNextProcessor() != null) {
            getNextProcessor().processOperation(operation);
        }
    }

    /**
     * Processes VectorNegation operation with Integer's.
     */
    @Override
    public <T extends Number> void processOperation(VectorNegation<T> operation) {
        if (Integer.class.isAssignableFrom(operation.getClasType())) {
            Vector<T> vector = operation.getVector1();
            Vector<T> resultant = new Vector<>(vector.getValues().length);

            for (int i = 0; i < resultant.getValues().length; i++) {
                Integer negativeNum = -vector.getValues()[i].intValue();
                resultant.getValues()[i] = (T) negativeNum;
            }

            operation.setResultant(resultant);
        }
        else if (getNextProcessor() != null) {
            getNextProcessor().processOperation(operation);
        }
    }

    /**
     * Processes VectorDotProduct operation with Integer's.
     */
    @Override
    public <T extends Number> void processOperation(VectorDotProduct<T> operation) {
        if (Integer.class.isAssignableFrom(operation.getClasType())) {
            Vector<T> vector1 = operation.getVector1();
            Vector<T> vector2 = operation.getVector2();

            int minLength = Math.min(vector1.getValues().length, vector2.getValues().length);

            Integer product = 0;

            for (int i = 0; i < minLength; i++) {
                product += vector1.getValues()[i].intValue() * vector2.getValues()[i].intValue();
            }

            operation.setProduct((T) product);
        }
        else if (getNextProcessor() != null) {
            getNextProcessor().processOperation(operation);
        }
    }
}
