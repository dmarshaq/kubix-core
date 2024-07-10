package org.dmarshaq.kubix.math.operation;

import lombok.Getter;
import lombok.Setter;
import org.dmarshaq.kubix.math.matrix.Matrix;
import org.dmarshaq.kubix.math.Vector;

@Getter
public class MatrixVectorMultiplication<T extends Number> extends Operation<T>{
    private final Matrix<T> matrix;
    private final Vector<T> vector;
    @Setter
    private Vector<T> resultant;

    public MatrixVectorMultiplication(Matrix<T> matrix, Vector<T> vector) {
        super((Class<T>) matrix.getElement(0, 0).getClass());
        this.matrix = matrix;
        this.vector = vector;
    }
}
