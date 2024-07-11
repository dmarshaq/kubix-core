package org.dmarshaq.kubix.math.vector;

import org.dmarshaq.kubix.math.array.FloatArray;

public class Vector3 extends Vector<Float> {

    /**
     * Builds 3D float vector based on the specified values.
     */
    public Vector3(float x, float y, float z) {
        super(new FloatArray(new float[] {x, y, z}));
    }
}
