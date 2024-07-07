package org.dmarshaq.kubix.math.operations;

import lombok.Getter;
import lombok.Setter;
import org.dmarshaq.kubix.math.Vector;

@Getter
public class ScalarMultiplication<T extends Number> extends Operation<T> {
    private final Vector<T> vector;
    private final T scalar;
    @Setter
    private Vector<T> resultant;

    public ScalarMultiplication(Vector<T> vector, T scalar) {
        super((Class<T>) vector.getComponent(0).getClass());
        this.vector = vector;
        this.scalar = scalar;
    }

}
