package org.dmarshaq.physics;

import org.dmarshaq.mathj.Vector2f;

public class RayCast2D {
    private final Vector2f endPoint;
    private final Vector2f intersection;
    private final BoxCollider2D collidedObject;

    RayCast2D(Vector2f intersection, Vector2f endPoint, BoxCollider2D collidedObject) {
        this.intersection = intersection;
        this.endPoint = endPoint;
        this.collidedObject = collidedObject;
    }

    public Vector2f getEndPoint() {
        return endPoint;
    }

    public Vector2f getIntersection() {
        return intersection;
    }

    public BoxCollider2D getCollidedObject() {
        return collidedObject;
    }
}
