package org.dmarshaq.kubix.math.operations;

import lombok.Getter;
import lombok.Setter;
import org.dmarshaq.kubix.math.Vector;

@Getter
public class VectorDotProduct<T extends Number> extends Operation<T> {
    private final Vector<T> vector1;
    private final Vector<T> vector2;
    @Setter
    private T product;

    public VectorDotProduct(Vector<T> vector1, Vector<T> vector2) {
        super((Class<T>) vector1.getComponent(0).getClass());
        this.vector1 = vector1;
        this.vector2 = vector2;
    }

}
