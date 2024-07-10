package org.dmarshaq.kubix.math.operation;

import lombok.Getter;
import lombok.Setter;
import org.dmarshaq.kubix.math.Vector;

@Getter
public class VectorMagnitude<T extends Number> extends Operation<T> {
    private final Vector<T> vector;
    @Setter
    private T magnitude;

    public VectorMagnitude(Vector<T> vector) {
        super((Class<T>) vector.getComponent(0).getClass());
        this.vector = vector;;
    }

}
