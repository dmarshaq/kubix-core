package org.dmarshaq.kubix.math.operations;

import lombok.Getter;
import lombok.Setter;
import org.dmarshaq.kubix.math.Vector;

@Getter
public class VectorNegation<T extends Number> extends Operation<T> {
    private final Vector<T> vector1;
    @Setter
    private Vector<T> resultant;

    public VectorNegation(Vector<T> vector1) {
        super((Class<T>) vector1.getComponent(0).getClass());
        this.vector1 = vector1;;
    }

}
