package org.dmarshaq.kubix.core.math.array;

public interface AbstractNumberArray {
    default float[] floatArray() {
        return null;
    }
    default int[] intArray() {
        return null;
    }
    default long[] longArray() {
        return null;
    }
    default double[] doubleArray() {
        return null;
    }
    default short[] shortArray() {
        return null;
    }
    default byte[] byteArray() {
        return null;
    }
}
