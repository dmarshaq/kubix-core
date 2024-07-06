package org.dmarshaq.kubix.math.processors;

import lombok.Getter;
import org.dmarshaq.kubix.math.operations.VectorDotProduct;
import org.dmarshaq.kubix.math.operations.VectorNegation;
import org.dmarshaq.kubix.math.operations.VectorSummation;

@Getter
public abstract class OperationProcessor {
    private final OperationProcessor nextProcessor;

    public OperationProcessor(OperationProcessor nextProcessor) {
        this.nextProcessor = nextProcessor;
    }

    public abstract <T extends Number> void processOperation(VectorSummation<T> operation);
    public abstract <T extends Number> void processOperation(VectorNegation<T> operation);
    public abstract <T extends Number> void processOperation(VectorDotProduct<T> operation);
}
