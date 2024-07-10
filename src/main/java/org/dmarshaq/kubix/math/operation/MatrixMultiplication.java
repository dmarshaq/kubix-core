package org.dmarshaq.kubix.math.operation;

import lombok.Getter;
import lombok.Setter;
import org.dmarshaq.kubix.math.matrix.Matrix;

@Getter
public class MatrixMultiplication<T extends Number> extends Operation<T>{
    private final Matrix<T> first;
    private final Matrix<T> second;
    @Setter
    private Matrix<T> result;

    public MatrixMultiplication(Matrix<T> first, Matrix<T> second) {
        super((Class<T>) first.getElement(0, 0).getClass());
        this.first = first;
        this.second = second;
    }
}
