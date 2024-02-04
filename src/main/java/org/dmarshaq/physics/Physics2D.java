package org.dmarshaq.physics;

import org.dmarshaq.app.Context;
import org.dmarshaq.app.Layer;
import org.dmarshaq.app.Snapshot;
import org.dmarshaq.graphics.Shader;
import org.dmarshaq.graphics.Sprite;
import org.dmarshaq.mathj.Matrix4f;
import org.dmarshaq.mathj.Vector2f;
import org.dmarshaq.mathj.Vector4f;

import java.util.*;

import static org.dmarshaq.mathj.MathJ.Math2D;


public interface Physics2D {
    Vector2f ACCELERATION_GRAVITY = new Vector2f(0.0f, -9.81f); // m/s^2
    List<Impulse2D>  IMPULSES = new ArrayList<>();
    List<BoxCollider2D> BOX_COLLIDERS = new ArrayList<>();
    List<RayCast2D> RAY_CASTS= new ArrayList<>();


    static void applyForce(Vector2f force, RigidBody2D body2D) {
        body2D.addForce(force);
    }

    static void applyImpulse(Vector2f force, float seconds, RigidBody2D body2D) {
        IMPULSES.add( new Impulse2D(force, seconds, body2D) );
    }

    static RayCast2D rayCast2D(Vector2f position, Vector2f target) {
        target = Math2D.normalize(target);
        float angle = (float) Math.toDegrees( Math.atan2(target.y, target.x) );

        Matrix4f transform = Matrix4f.TRS(position, angle, 1.0f, 1.0f);
        Ray2D ray2D = new Ray2D(transform);

        Vector2f intersection = null;
        BoxCollider2D hitCollider = null;

        for (BoxCollider2D boxCollider2D : BOX_COLLIDERS) {
            if (boxCollider2D.isSolid) {
                Vector2f result = ray2D.castToLocally(boxCollider2D);
                if (result != null) {
                    if (intersection == null) {
                        intersection = result;
                        hitCollider = boxCollider2D;
                    }
                    else if (intersection.x > result.x) {
                        intersection = result;
                        hitCollider = boxCollider2D;
                    }

                }
            }
        }

        if (intersection != null) {
            Vector2f worldIntersection = transform.localToWorld( intersection );
            RayCast2D rayCast2D = new RayCast2D( worldIntersection, Math2D.diffrence(worldIntersection, position), hitCollider);
            RAY_CASTS.add(rayCast2D);
            return rayCast2D;
        }
        return null;
    }

    static BoxCollider2D createBoxCollider2D(Matrix4f parent, Vector2f offset, float width, float height, boolean isTrigger, boolean isSolid) {
        BoxCollider2D collider2D = new BoxCollider2D(parent, offset, width, height, isTrigger, isSolid);
        BOX_COLLIDERS.add(collider2D);
        return collider2D;
    }

    static void updatePhysics(Snapshot snapshot) {
        for (int i = 0; i < IMPULSES.size(); i++) {
            Impulse2D impulse = IMPULSES.get(i);
            impulse.update();
            if ( !impulse.isAlive() ) {
                IMPULSES.remove(i);
                i--;
            }


        }

        if (Context.isDrawGizmos()) {
            Sprite colliderGizmos = new Sprite(Matrix4f.identity(), Layer.GIZMOS, new Vector4f(1.0f, 0.5f, 1.0f, 1.0f), Shader.BASIC);
            for (BoxCollider2D boxCollider2D : BOX_COLLIDERS) {
                colliderGizmos.setTransform(boxCollider2D.gizmosTransform());
                snapshot.addSpriteToRender(colliderGizmos);
            }
        }
    }

}
