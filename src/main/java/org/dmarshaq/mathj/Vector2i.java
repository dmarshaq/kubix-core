package org.dmarshaq.mathj;

public class Vector2i {
    public int x, y;

    public Vector2i() {
        this.x = 0;
        this.y = 0;
    }

    public Vector2i(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void multiply(int value) {
        x *= value;
        y *= value;
    }

    public void divide(float value) {
        x /= (int) value;
        y /= (int) value;
    }

    public void add(int value) {
        x += value;
        y += value;
    }

    public void add(Vector2i vector) {
        x += vector.x;
        y += vector.y;
    }

    public void negate() {
        multiply(-1);
    }

    public void subtract(int value) {
        x -= value;
        y -= value;
    }

    public void subtract(Vector2i vector) {
        x -= vector.x;
        y -= vector.y;
    }

    public float magnitude() {
        return (float) Math.sqrt(x * x + y * y);
    }

    public void normalize() {
        divide( magnitude() );
    }

    public void copyValues(Vector2i v) {
        this.x = v.x;
        this.y = v.y;
    }

    public static Vector2i duplicate(Vector2i v) {
        if (v == null) {
            return null;
        }
        return new Vector2i(v.x, v.y);
    }
}
