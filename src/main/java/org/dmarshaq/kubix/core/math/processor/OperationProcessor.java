package org.dmarshaq.kubix.core.math.processor;

import lombok.Getter;
import org.dmarshaq.kubix.core.math.matrix.Matrix;
import org.dmarshaq.kubix.core.math.vector.Vector;

@Getter
public abstract class OperationProcessor {
    private final OperationProcessor nextProcessor;

    public OperationProcessor(OperationProcessor nextProcessor) {
        this.nextProcessor = nextProcessor;
    }

    // Math Operations
    public abstract <T extends Number> Vector<T> vectorAddition(Vector<T> first, Vector<T> second);
    public abstract <T extends Number> Vector<T>  vectorNegation(Vector<T> vector);
    public abstract <T extends Number> T vectorDotProduct(Vector<T> first, Vector<T> second);
    public abstract <T extends Number> T vectorMagnitude(Vector<T> vector);
    public abstract <T extends Number> Vector<T> scalarMultiplication(Vector<T> vector, T scalar);
    public abstract <T extends Number> Vector<T> scalarDivision(Vector<T> vector, T scalar);
    public abstract <T extends Number> Matrix<T> matrixMultiplication(Matrix<T> first, Matrix<T> second);
    public abstract <T extends Number> Vector<T> matrixVectorMultiplication(Matrix<T> matrix, Vector<T> vector);

    // Methods
    public abstract <T extends Number> Vector<T> buildVector(Class<T> clas, int length);
    public abstract <T extends Number> Vector<T> buildVector(T x, T y);
    public abstract <T extends Number> Vector<T> buildVector(T x, T y, T z);
    public abstract <T extends Number> Vector<T> buildVector(T x, T y, T z, T w);
    public abstract <T extends Number> Matrix<T> buildIdentityMatrix(Class<T> clas, int rows, int columns);
    public abstract <T extends Number> Vector<T> componentVector(Vector<T> vector, String axis);

}
