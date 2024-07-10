package org.dmarshaq.kubix.math.operation;

import lombok.Getter;
import lombok.Setter;
import org.dmarshaq.kubix.math.Vector;

@Getter
public class VectorNegation<T extends Number> extends Operation<T> {
    private final Vector<T> vector;
    @Setter
    private Vector<T> resultant;

    public VectorNegation(Vector<T> vector) {
        super((Class<T>) vector.getComponent(0).getClass());
        this.vector = vector;;
    }

}
