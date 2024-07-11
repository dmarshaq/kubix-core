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


// TODO: make methods below work, hint: through processor solution.

//    /**
//     * Builds vector based on the specified length.
//     */
//    public Vector(int length) {
//        this.values = (T[]) new Number[length];
//    }
//
//    /**
//     * Gets value of any component in the vector by the index.
//     */
//    public final T getComponent(int index) {
//        return values[index];
//    }
//
//    /**
//     * Used to get component vector based on axis specified, order matters, carefully "xy" doesn't equal "yx".
//     * Vector returned always have same number of dimensions as axis.length().
//     * Can only be specified up to "xyzw" in any order.
//     * If specified axis.length() more than original Vector dimensions it will return component with added dimensions equal to null.
//     */
//    public final Vector<T> getComponent(String axis) {
//        int length = axis.length();
//        Vector<T> component = new Vector<>( (T[]) new Number[length] );
//        for(int i = 0; i < length; i++) {
//            int index = AXIS.indexOf(axis.charAt(i));
//            if (index < values.length) {
//                component.values[i] = values[index];
//            }
//        }
//        return component;
//    }


}
