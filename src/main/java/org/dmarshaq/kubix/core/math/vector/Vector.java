package org.dmarshaq.kubix.core.math.vector;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.dmarshaq.kubix.core.math.array.NumberArray;

import java.util.Objects;

@Setter
@Getter
@EqualsAndHashCode
public class Vector<T extends Number> {
    private NumberArray<T> values;

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
