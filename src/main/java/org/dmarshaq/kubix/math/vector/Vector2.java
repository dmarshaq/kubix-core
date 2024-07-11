package org.dmarshaq.kubix.math.vector;

import org.dmarshaq.kubix.math.array.FloatArray;

public class Vector2 extends Vector<Float> {

    public Vector2(float x, float y) {
        super(new FloatArray(new float[] {x, y}));
    }
}
