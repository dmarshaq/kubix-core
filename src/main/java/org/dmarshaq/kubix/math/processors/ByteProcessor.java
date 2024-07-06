package org.dmarshaq.kubix.math.processors;

import org.dmarshaq.kubix.math.Vector;
import org.dmarshaq.kubix.math.operations.VectorDotProduct;
import org.dmarshaq.kubix.math.operations.VectorNegation;
import org.dmarshaq.kubix.math.operations.VectorSummation;

public class ByteProcessor extends OperationProcessor {
    public ByteProcessor(OperationProcessor nextProcessor) {
        super(nextProcessor);
    }

    /**
     * Processes VectorSummation operation with Byte's.
     */
    @Override
    public <T extends Number> void processOperation(VectorSummation<T> operation) {
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
            Vector<T> vector = operation.getVector1();
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
}
