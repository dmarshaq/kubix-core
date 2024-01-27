package org.dmarshaq.mathj;

public final class Vector3f {
    public float x, y, z;

    public Vector3f() {
        this.x = 0f;
        this.y = 0f;
        this.z = 0f;
    }

    public Vector3f(float x, float y) {
        this.x = x;
        this.y = y;
        this.z = 0f;
    }

    public Vector3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void copyValues(Vector3f v) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
    }

    public static Vector3f duplicate(Vector3f v) {
        if (v == null) {
            return null;
        }
        return new Vector3f(v.x, v.y, v.z);
    }

    public String toString() {
        return "( " + x + " , " + y + ", " + z + " )";
    }
}
