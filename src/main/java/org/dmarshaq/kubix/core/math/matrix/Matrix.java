package org.dmarshaq.kubix.core.math.matrix;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.dmarshaq.kubix.core.math.MathCore;
import org.dmarshaq.kubix.core.math.array.NumberArray;

@Getter
@EqualsAndHashCode
public class Matrix<T extends Number> {
    private NumberArray<T> elements;
    private int rows;
    private int columns;

    /**
     * Builds matrix based on the specified elements, and matrix respective dimensions.
     */
    public Matrix(NumberArray<T> elements, int rows, int columns) {
        this.elements = elements;
        this.rows = rows;
        this.columns = columns;
    }

    public void setElements(NumberArray<T> elements, int rows, int columns) {
        this.elements = elements;
        this.rows = rows;
        this.columns = columns;
    }

    @Override
    public String toString() {
        return "Matrix(" + "elements=" + elements.arrayToString(rows, columns) + ')';
    }
}
