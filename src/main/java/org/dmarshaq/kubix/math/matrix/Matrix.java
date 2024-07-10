package org.dmarshaq.kubix.math.matrix;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Arrays;

@Getter
@EqualsAndHashCode
public class Matrix<T extends Number> {
    private final T[][] elements;

    /**
     * Builds matrix based on the specified elements.
     */
    public Matrix(T[][] elements) {
        this.elements = elements;
    }

    /**
     * Builds matrix based on the specified number of rows and columns.
     */
    public Matrix(int rows, int columns) {
        this.elements = (T[][]) new Number[rows][columns];
    }

    /**
     * Gets value of any element in the matrix by the row and column number.
     */
    public final T getElement(int row, int col) {
        return elements[row][col];
    }

    /**
     * Gets number of Rows in matrix.
     */
    public final int getRows() {
        return elements.length;
    }

    /**
     * Gets number of Columns in matrix.
     */
    public final int getColumns() {
        return elements[0].length;
    }


    @Override
    public String toString() {
        return "Matrix(" + "elements=\n" + Arrays.deepToString(elements).replace("], ", "]\n").replace("[[", "[").replace("]]", "]") + ')';
    }
}
