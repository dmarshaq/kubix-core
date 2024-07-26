package org.dmarshaq.kubix.core.math.array;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Arrays;

@ToString
@EqualsAndHashCode(callSuper = false)
public class LongArray extends NumberArray<Long>{
    private final long[] array;

    public LongArray(long[] array) {
        super(Long.class);
        this.array = array;
    }

    @Override
    public long[] longArray() {
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
                stringBuilder.append(array[col + row * columns]).append(", ");
            }
            stringBuilder.append(array[columns - 1 + row * columns]).append("]");
        }

        return stringBuilder.toString();
    }
}
