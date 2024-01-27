package org.dmarshaq.physics;

import org.dmarshaq.app.Update;
import org.dmarshaq.mathj.MathJ;
import org.dmarshaq.mathj.MathJ.Math2D;
import org.dmarshaq.mathj.Vector2f;
import org.dmarshaq.time.Time;

import static org.dmarshaq.physics.Physics2D.ACCELERATION_GRAVITY;

public class RigidBody2D {
    private float mass;
    private final Vector2f position;
    private final Vector2f velocity; // m/s
    private final Vector2f netForce;

    public RigidBody2D(Vector2f position, float mass) {
        this.position = position;
        this.mass = mass;
        this.velocity = new Vector2f();
        this.netForce = new Vector2f();
    }

    public Vector2f getWeight() {
        return Math2D.multiply(ACCELERATION_GRAVITY, mass); // returns acceleration per second!
    }

    public void addForce(Vector2f force) {
        netForce.add(force);
    }

    public void update() {
        Vector2f acceleration = Math2D.divide(netForce, mass); // m/s added in current update
        velocity.add(acceleration);

        position.add( Math2D.multiply(velocity, Time.DeltaTime.getSeconds()) );

        // Clear forces
        netForce.x = 0.0f;
        netForce.y = 0.0f;
    }

    public Vector2f getPosition() {
        return position;
    }
}
