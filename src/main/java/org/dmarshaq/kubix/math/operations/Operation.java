package org.dmarshaq.kubix.math.operations;

import lombok.Getter;

@Getter
public class Operation<T extends Number> {
    private final Class<T> clasType;

    public Operation(Class<T> clas) {
        clasType = clas;
    }
}
