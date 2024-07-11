package org.dmarshaq.kubix.math.array;

import lombok.ToString;

import java.util.Arrays;

@ToString
public class ShortArray extends NumberArray<Short>{
    private final short[] array;

    public ShortArray(short[] array) {
        super(Short.class);
        this.array = array;
    }

    @Override
    public short[] shortArray() {
        return array;
    }

    @Override
    public String arrayToString() {
        return Arrays.toString(array);
    }

    @Override
    public String arrayToString(int rows, int columns) {
        StringBuilder stringBuilder = new StringBuilder();
        for(int row = 0; row < rows; row++) {
            stringBuilder.append("\n[");
            for(int col = 0; col < columns - 1; col++) {
                stringBuilder.append(array[col + row * rows]).append(", ");
            }
            stringBuilder.append(array[columns - 1 + row * rows]).append("]");
        }

        return stringBuilder.toString();
    }
}
