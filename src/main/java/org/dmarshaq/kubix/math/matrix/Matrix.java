package org.dmarshaq.kubix.math.matrix;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.dmarshaq.kubix.math.MathCore;
import org.dmarshaq.kubix.math.array.NumberArray;

@Getter
@EqualsAndHashCode
public class Matrix<T extends Number> {
    private final NumberArray<T> elements;
    private final int rows;
    private final int columns;

    /**
     * Builds matrix based on the specified elements, and matrix respective dimensions.
     */
    public Matrix(NumberArray<T> elements, int rows, int columns) {
        this.elements = elements;
        this.rows = rows;
        this.columns = columns;
    }

    @Override
    public String toString() {
        return "Matrix(" + "elements=" + elements.arrayToString(rows, columns) + ')';
    }

// TODO: make methods below work, hint: through processor solution.
//    /**
//     * Builds matrix based on the specified number of rows and columns.
//     */
//    public Matrix(int rows, int columns) {
//        this.elements = (T[][]) new Number[rows][columns];
//    }
//
//    /**
//     * Gets value of any element in the matrix by the row and column number.
//     */
//    public final T getElement(int row, int col) {
//        return elements[row][col];
//    }


}
