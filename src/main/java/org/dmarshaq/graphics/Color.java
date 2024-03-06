package org.dmarshaq.graphics;

import org.dmarshaq.mathj.Vector4f;

public class Color {
    public int r, g, b;
    public float a;

    public Color() {
        this.r = 0;
        this.g = 0;
        this.b = 0;
        this.a = 0;
    }

    public Color(int r, int g, int b, float a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    public void copyValues(Color color) {
        this.r = color.r;
        this.g = color.g;
        this.b = color.b;
        this.a = color.a;
    }

    public static Color duplicate(Color color) {
        if (color != null) {
            return new Color(color.r, color.g, color.b, color.a);
        }
        return null;
    }

    public static Vector4f toVector4f(Color color) {
        if (color != null) {
            return new Vector4f(color.r, color.g, color.b, color.a);
        }
        return null;
    }

    public String toString() {
        return "( " + r + " , " + g + ", " + b + ", " + a + " )";
    }
}
