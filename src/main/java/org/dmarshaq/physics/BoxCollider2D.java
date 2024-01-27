package org.dmarshaq.physics;

import org.dmarshaq.app.Layer;
import org.dmarshaq.graphics.Sprite;
import org.dmarshaq.mathj.MathJ;
import org.dmarshaq.mathj.Matrix4f;
import org.dmarshaq.mathj.Vector2f;

public class BoxCollider2D {
    public final boolean isTrigger;
    public final boolean isSolid;
    private final Vector2f offset;
    private final Matrix4f parent;
    private float width, height;

    public BoxCollider2D(Matrix4f parent, Vector2f offset, float width, float height, boolean isTrigger, boolean isSolid) {
        this.parent = parent;
        this.offset = offset;
        this.width = width;
        this.height = height;

        this.isTrigger = isTrigger;
        this.isSolid = isSolid;
    }

    public Vector2f corner(int cornerId) {
        return switch (cornerId) {
            case (0) -> parent.localToWorld(offset);
            case (1) -> parent.localToWorld( MathJ.Math2D.sum(offset, new Vector2f(0, height)) );
            case (2) -> parent.localToWorld( MathJ.Math2D.sum(offset, new Vector2f(width, 0)) );
            case (3) -> parent.localToWorld( MathJ.Math2D.sum(offset, new Vector2f(width, height)) );
            default -> null;
        };
    }

    public Matrix4f gizmosTransform() {
        Matrix4f transform = Matrix4f.TRS(offset, 0.0f, width, height);
        transform = MathJ.Math2D.multiply(parent, transform);
        return transform;
    }


}
