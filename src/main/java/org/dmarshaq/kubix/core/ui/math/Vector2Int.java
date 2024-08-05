package org.dmarshaq.kubix.core.ui.math;

import org.dmarshaq.kubix.core.math.array.IntegerArray;
import org.dmarshaq.kubix.core.math.base.MathCore;
import org.dmarshaq.kubix.core.math.vector.Vector;

public class Vector2Int extends Vector<Integer> implements AbstractIntegerVector<Vector2Int> {

    /**
     * Builds 2D int vector based on the specified values.
     */
    public Vector2Int(int x, int y) {
        super(new IntegerArray(new int[] {x, y}));
    }

    /**
     * Casts Vector<Integer> to Vector2Int.
     * Note: new Vector2Int will have reference to the same IntegerArray object.
     */
    public Vector2Int(Vector<Integer> vector2) {
        super(vector2.getValues());
    }

    @Override
    public int[] getArrayOfValues() {
        return getValues().intArray();
    }

    @Override
    public int x() {
        return getArrayOfValues()[0];
    }

    @Override
    public int y() {
        return getArrayOfValues()[1];
    }

    @Override
    public Vector2Int add(Vector<Integer> vector) {
        getArrayOfValues()[0] += vector.getValues().intArray()[0];
        getArrayOfValues()[1] += vector.getValues().intArray()[1];
        return this;
    }

    @Override
    public Vector2Int subtract(Vector<Integer> vector) {
        getArrayOfValues()[0] -= vector.getValues().intArray()[0];
        getArrayOfValues()[1] -= vector.getValues().intArray()[1];
        return this;
    }

    @Override
    public Vector2Int multiply(int scalar) {
        getArrayOfValues()[0] *= scalar;
        getArrayOfValues()[1] *= scalar;
        return this;
    }

    @Override
    public Vector2Int divide(int scalar) {
        getArrayOfValues()[0] /= scalar;
        getArrayOfValues()[1] /= scalar;
        return this;
    }

    @Override
    public Vector2Int normalize() {
        divide(MathCore.magnitude(this));
        return this;
    }

    @Override
    public Vector2Int negate() {
        multiply(-1);
        return this;
    }
}
