package org.dmarshaq.mathj;

import static org.dmarshaq.mathj.MathJ.Math2D;

public class RectComponent extends Rect {
    private Matrix4f reference;

    public RectComponent() {
        super();
        this.reference = new Matrix4f();
    }

    public RectComponent(float xOffset, float yOffset, float width, float height, Matrix4f reference) {
        super(new Rect(xOffset, yOffset, width, height));
        this.reference = reference;
    }

    public RectComponent(Rect rect, Matrix4f reference) {
        super(new Rect(rect));
        this.reference = reference;
    }

    public RectComponent(RectComponent rectC) {
        super.setPosition(rectC.getPositionObject());
        this.width = rectC.width;
        this.height = rectC.height;
        this.reference = rectC.getReferenceTransform();
    }

    @Override
    public Vector2f getPosition() {
        return new Vector2f(x(), y());
    }

    @Override
    public float x() {
        return super.x() + Math2D.toVector2f( Matrix4f.getPosition(reference) ).x;
    }

    @Override
    public float y() {
        return super.y() + Math2D.toVector2f( Matrix4f.getPosition(reference) ).y;
    }

    @Override
    public Vector2f getCenter() {
        return Math2D.sum(super.getCenter(), Math2D.toVector2f( Matrix4f.getPosition(reference) ) );
    }

    public Vector2f getOffsetObject() {
        return super.getPositionObject();
    }

    public void setReferenceTransform(Matrix4f transform) {
        reference = transform;
    }

    public Matrix4f getReferenceTransform() {
        return reference;
    }

    @Override
    public String toString() {
        return "at: " + super.getPositionObject() + " width: " + width + " height: " + height + " reference: " + reference;
    }


}
