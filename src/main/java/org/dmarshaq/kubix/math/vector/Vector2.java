package org.dmarshaq.kubix.math.vector;

import org.dmarshaq.kubix.math.array.FloatArray;


public class Vector2 extends Vector<Float> {

    /**
     * Builds 2D float vector based on the specified values.
     */
    public Vector2(float x, float y) {
        super(new FloatArray(new float[] {x, y}));
    }
}
