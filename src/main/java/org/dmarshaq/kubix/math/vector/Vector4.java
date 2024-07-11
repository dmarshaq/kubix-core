package org.dmarshaq.kubix.math.vector;

import org.dmarshaq.kubix.math.array.FloatArray;

public class Vector4 extends Vector<Float> {

    public Vector4(float x, float y, float z, float w) {
        super(new FloatArray(new float[] {x, y, z, w}));
    }
}
