package org.dmarshaq.physics;

import org.dmarshaq.mathj.LineSegment;
import org.dmarshaq.mathj.Matrix4f;
import org.dmarshaq.mathj.Vector2f;

public class Ray2D {
    private final Matrix4f transform;

    Ray2D(Matrix4f transform) {
        this.transform = transform;
    }

    Vector2f castToLocally(BoxCollider2D boxCollider2D) {
        Vector2f localIntersection = null;
        for (int i = 0; i < 4; i++) {
            Vector2f start = transform.worldToLocal( boxCollider2D.corner(i) );
            Vector2f end;
            if (i + 1 == 4) {
                end = transform.worldToLocal( boxCollider2D.corner(0) );
            }
            else {
                end = transform.worldToLocal( boxCollider2D.corner(i + 1) );
            }
            LineSegment lineSegment = new LineSegment( start, end );
            Vector2f zero = checkZeroForNegative( lineSegment.zero() );

            if (localIntersection != null && zero != null && localIntersection.x < zero.x) {
                return localIntersection;
            }
            else if (localIntersection != null && zero != null) {
                return zero;
            }
            else if (localIntersection == null) {
                localIntersection = zero;
            }
        }
        return null;
    }

    private Vector2f checkZeroForNegative(Vector2f zero) {
        if (zero != null && zero.x >= 0) {
            return zero;
        }
        return null;
    }
}
