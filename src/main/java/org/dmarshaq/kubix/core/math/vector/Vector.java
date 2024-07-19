package org.dmarshaq.kubix.core.math.vector;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.dmarshaq.kubix.core.math.array.NumberArray;

@Getter
@EqualsAndHashCode
public class Vector<T extends Number> {
    private final NumberArray<T> values;

    /**
     * Builds vector based on the specified values.
     */
    public Vector(NumberArray<T> values) {
        this.values = values;
    }

    @Override
    public String toString() {
        return "Vector(" + "values=" + values.arrayToString() + ')';
    }

}
