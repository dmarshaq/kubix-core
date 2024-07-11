package org.dmarshaq.kubix.math.vector;

import org.dmarshaq.kubix.math.array.FloatArray;


public class Vector4 extends Vector<Float> {

    /**
     * Builds 4D float vector based on the specified values.
     */
    public Vector4(float x, float y, float z, float w) {
        super(new FloatArray(new float[] {x, y, z, w}));
    }
}
