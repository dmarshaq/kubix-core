package org.dmarshaq.kubix.core.math.function;

import org.dmarshaq.kubix.core.math.vector.Vector;

public interface AbstractParametric<T extends Number, E extends Vector<T>> {
    E parametric(T t);
}
