package org.dmarshaq.mathj;

import org.dmarshaq.utils.BufferUtils;

import java.nio.FloatBuffer;

public class Matrix4f {
    private static final int SIZE = 4 * 4;
    public float[] elements = new float[4 * 4];

    public Matrix4f() {

    }

    public static Matrix4f identity() {
        Matrix4f result = new Matrix4f();
        for (int i = 0; i < SIZE; i++) {
            result.elements[i] = 0f;
        }
        result.elements[0 + 0 * 4] = 1f;
        result.elements[1 + 1 * 4] = 1f;
        result.elements[2 + 2 * 4] = 1f;
        result.elements[3 + 3 * 4] = 1f;

        return result;
    }

    public static Matrix4f orthographic(float left, float right, float bottom, float top, float near, float far) {
        Matrix4f result = identity();

        result.elements[0 + 0 * 4] = 2f / (right - left);
        result.elements[1 + 1 * 4] = 2f / (top - bottom);
        result.elements[2 + 2 * 4] = 2f / (near - far);

        result.elements[0 + 3 * 4] = (left + right) / (left - right);
        result.elements[1 + 3 * 4] = (bottom + top) / (bottom - top);
        result.elements[2 + 3 * 4] = (near + far) / (near - far);

        return result;
    }

    public static Matrix4f translate(Vector2f vector) {
        Matrix4f result = identity();

        result.elements[3 + 0 * 4] = vector.x;
        result.elements[3 + 1 * 4] = vector.y;
        result.elements[3 + 2 * 4] = 0;
        result.elements[3 + 3 * 4] = 1f;

        return result;
    }

    public static Matrix4f rotate(float angle) {
        Matrix4f result = identity();

        float r = (float) Math.toRadians(angle);
        float cos = (float) Math.cos(r);
        float sin = (float) Math.sin(r);

        result.elements[0 + 0 * 4] = cos;
        result.elements[1 + 0 * 4] = -sin;
        result.elements[0 + 1 * 4] = sin;
        result.elements[1 + 1 * 4] = cos;

        return result;
    }

    public static Matrix4f scale(float xScale, float yScale) {
        Matrix4f result = identity();

        result.elements[0 + 0 * 4] = xScale;
        result.elements[1 + 1 * 4] = yScale;

        return result;
    }

    public static Matrix4f TRS(Vector2f position, float layer, float angle, float xScale, float yScale) {
        Matrix4f translate = Matrix4f.translate(position);
        Matrix4f rotation = Matrix4f.rotate(angle);
        Matrix4f scale = Matrix4f.scale(xScale, yScale);
        // operation: T * R * S = ?
        // T * R -> T
        // T * S -> T
        translate.multiply(rotation);
        translate.multiply(scale);

        return translate;
    }

    public void multiply(Matrix4f matrix) {
        Matrix4f product = new Matrix4f();

        for (int i = 0; i < 16; i += 4) {
            for (int j = 0; j < 4; j++) {
                product.elements[i + j] = 0.0f;
                for (int k = 0; k < 4; k++)
                    product.elements[i + j] += elements[i + k] * matrix.elements[k * 4 + j];
            }
        }

        copy(product);
    }

    public Vector2f multiply(Vector2f vector, float w) {
        Vector2f result = new Vector2f();
        result.x = this.elements[0 + 0 * 4] * vector.x + this.elements[1 + 0 * 4] * vector.y + this.elements[3 + 0 * 4] * w;
        result.y = this.elements[0 + 1 * 4] * vector.x + this.elements[1 + 1 * 4] * vector.y + this.elements[3 + 1 * 4] * w;
        return result;
    }

    public Vector3f getTransformPosition() {
        return new Vector3f(elements[0 + 3 * 4], elements[1 + 3 * 4], elements[2 + 3 * 4]);
    }

    public void setTransformPosition(Vector2f vector, float layer) {
        this.elements[3 + 0 * 4] = vector.x;
        this.elements[3 + 1 * 4] = vector.y;
        this.elements[3 + 3 * 4] = 1f;
        setTransformLayer(layer);
    }

    public void setTransformLayer(float layer) {
        this.elements[3 + 2 * 4] = layer;
    }

    public void copy(Matrix4f matrix) {
        this.elements = matrix.elements.clone();
    }

    public FloatBuffer toFloatBuffer() {
        return BufferUtils.createFloatBuffer(elements);
    }

}
