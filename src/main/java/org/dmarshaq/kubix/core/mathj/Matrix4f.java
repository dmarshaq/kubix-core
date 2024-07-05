package org.dmarshaq.kubix.core.mathj;

import org.dmarshaq.kubix.core.utils.BufferUtils;
import static org.dmarshaq.kubix.core.mathj.MathJ.Math2D;

import java.nio.FloatBuffer;

public class Matrix4f {
    private static final int SIZE = 4 * 4;
    public float[] elements = new float[4 * 4];

    public Matrix4f() {}

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

    public static Matrix4f translateXY(Vector2f vector) {
        Matrix4f result = identity();

        result.elements[3 + 0 * 4] = vector.x;
        result.elements[3 + 1 * 4] = vector.y;
        result.elements[3 + 2 * 4] = 0;
        result.elements[3 + 3 * 4] = 1f;

        return result;
    }

    public static Matrix4f rotateXY(float angle) {
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

    public static Matrix4f scaleXY(float xScale, float yScale) {
        Matrix4f result = identity();

        result.elements[0 + 0 * 4] = xScale;
        result.elements[1 + 1 * 4] = yScale;

        return result;
    }

    public static Matrix4f TRS(Vector2f position, float angle, float xScale, float yScale) {
        Matrix4f translate = Matrix4f.translateXY(position);
        Matrix4f rotation = Matrix4f.rotateXY(angle);
        Matrix4f scale = Matrix4f.scaleXY(xScale, yScale);
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
        float x = this.elements[0 + 0 * 4] * vector.x + this.elements[1 + 0 * 4] * vector.y + this.elements[3 + 0 * 4] * w;
        float y = this.elements[0 + 1 * 4] * vector.x + this.elements[1 + 1 * 4] * vector.y + this.elements[3 + 1 * 4] * w;
        vector.x = x;
        vector.y = y;
        return vector;
    }

    public Vector2f getPositionXY() {
        return new Vector2f(elements[3 + 0 * 4], elements[3 + 1 * 4]);
    }

    public Vector2f getXAxisVector2() {
        return new Vector2f(elements[0 + 0 * 4], elements[0 + 1 * 4]);
    }

    public Vector2f right() {
        Vector2f right = getXAxisVector2();
        right.normalize();
        return right;
    }

    public Vector2f getYAxisVector2() {
        return new Vector2f(elements[1 + 0 * 4], elements[1 + 1 * 4]);
    }

    public Vector2f up() {
        Vector2f up = getYAxisVector2();
        up.normalize();
        return up;
    }

    public Vector2f localToWorld(Vector2f localPoint) {
        Vector2f xVector = getXAxisVector2();
        Vector2f yVector = getYAxisVector2();

        xVector.multiply(localPoint.x);
        yVector.multiply(localPoint.y);

        xVector.add(yVector);
        xVector.add( getPositionXY() );

        return xVector;
    }

    public Vector2f worldToLocal(Vector2f worldPoint) {
        Vector2f relPoint = getPositionXY();
        relPoint.negate();
        relPoint.add( worldPoint );

        Vector2f right = right();
        Vector2f up = up();

        float x = Math2D.dot( relPoint, right ) / Math2D.magnitude(getXAxisVector2());
        float y = Math2D.dot( relPoint, up ) / Math2D.magnitude(getYAxisVector2());

        return new Vector2f(x, y);
    }

    public void setPositionXY(Vector2f vector) {
        this.elements[3 + 0 * 4] = vector.x;
        this.elements[3 + 1 * 4] = vector.y;
    }

    public void setPositionXY(Vector2i vector) {
        this.elements[3 + 0 * 4] = vector.x;
        this.elements[3 + 1 * 4] = vector.y;
    }

    /**
     * Use carefully it sets scale to already rotated matrix
     * if you want to scale and then rotate use TRS or
     * Matrix multiplication
     */
    public void setScaleX(float x) {
        this.elements[0 + 0 * 4] = x;
    }

    /**
     * Use carefully it sets scale to already rotated matrix
     * if you want to scale and then rotate use TRS or
     * Matrix multiplication
     */
    public void setScaleY(float y) {
        this.elements[1 + 1 * 4] = y;
    }

    public void copy(Matrix4f matrix) {
        this.elements = matrix.elements.clone();
    }

    public static Matrix4f duplicate(Matrix4f matrix) {
        if (matrix == null) {
            return null;
        }
        Matrix4f result = new Matrix4f();
        result.copy(matrix);
        return result;
    }

    public FloatBuffer toFloatBuffer() {
        return BufferUtils.createFloatBuffer(elements);
    }

}
