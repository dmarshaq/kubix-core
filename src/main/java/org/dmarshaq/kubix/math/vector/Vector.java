package org.dmarshaq.kubix.math.vector;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.dmarshaq.kubix.math.MathCore;
import org.dmarshaq.kubix.math.array.NumberArray;

import static org.dmarshaq.kubix.math.MathCore.AXIS;


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


// TODO: make methods below work through processor solution.


}
