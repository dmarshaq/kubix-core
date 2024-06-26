package org.dmarshaq.mathj;

public final class Vector4f {
    public float x, y, z, w;

    public Vector4f() {
        this.x = 0f;
        this.y = 0f;
        this.z = 0f;
        this.w = 0f;
    }

    public Vector4f(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public void copyValues(Vector4f v) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
        this.w = v.w;
    }

    public static Vector4f duplicate(Vector4f v) {
        if (v == null) {
            return null;
        }
        return new Vector4f(v.x, v.y, v.z, v.w);
    }

    public String toString() {
        return "( " + x + " , " + y + ", " + z + ", " + w + " )";
    }
}
