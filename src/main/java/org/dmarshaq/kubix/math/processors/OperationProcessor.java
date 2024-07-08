package org.dmarshaq.kubix.math.processors;

import lombok.Getter;
import org.dmarshaq.kubix.math.Matrix;
import org.dmarshaq.kubix.math.operations.*;

@Getter
public abstract class OperationProcessor {
    private final OperationProcessor nextProcessor;

    public OperationProcessor(OperationProcessor nextProcessor) {
        this.nextProcessor = nextProcessor;
    }

    // Operations
    public abstract <T extends Number> void processOperation(VectorAddition<T> operation);
    public abstract <T extends Number> void processOperation(VectorNegation<T> operation);
    public abstract <T extends Number> void processOperation(VectorDotProduct<T> operation);
    public abstract <T extends Number> void processOperation(VectorMagnitude<T> operation);
    public abstract <T extends Number> void processOperation(ScalarMultiplication<T> operation);
    public abstract <T extends Number> void processOperation(ScalarDivision<T> operation);

    // Methods
    public abstract <T extends Number> Matrix<T> buildIdentityMatrix(Class<T> clas, int rows, int columns);
}
