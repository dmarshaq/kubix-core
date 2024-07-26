package org.dmarshaq.kubix.core.math.array;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
public abstract class NumberArray<T extends Number> implements AbstractNumberArray {
    private final Class<T> elementClass;

    public NumberArray(Class<T> clas) {
        this.elementClass = clas;
    }

    public abstract String arrayToString();
    public abstract String arrayToString(int rows, int columns);
}
