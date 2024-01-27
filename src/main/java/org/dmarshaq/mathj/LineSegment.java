package org.dmarshaq.mathj;

import static java.lang.Float.NaN;

public class LineSegment {
    private final Vector2f start;
    private final Vector2f end;


    public LineSegment(Vector2f start, Vector2f end) {
        this.start = start;
        this.end = end;
    }

    public Domain getDomain() {
        return new Domain(start.x, end.x);
    }

    public Domain getRange() {
        return new Domain(start.y, end.y);
    }

    public float slope() { // m
        if (end.x - start.x == 0) {
            return NaN;
        }
        return (end.y - start.y) / (end.x - start.x);
    }

    public float verticalOffset() { // b
        if (Float.isNaN( slope() )) {
            return NaN;
        }
        return start.y - slope() * start.x;
    }

    public Vector2f zero() { // zero
        if (getRange().isInDomain(0.0f) != 0) {
            return null;
        }
        if (Float.isNaN(slope()) ) {
            return new Vector2f(start.x, 0.0f);
        }
        return new Vector2f( -verticalOffset() / slope(), 0.0f );
    }
}
