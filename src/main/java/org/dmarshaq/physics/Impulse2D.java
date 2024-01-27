package org.dmarshaq.physics;


import org.dmarshaq.mathj.MathJ;
import org.dmarshaq.mathj.Vector2f;
import org.dmarshaq.time.Time;

public class Impulse2D {
    private final Vector2f force;
    private final RigidBody2D target;

    private float counter;
    private boolean isAlive;

    public Impulse2D(Vector2f force, float seconds, RigidBody2D target) {
        this.target = target;
        this.force = force;

        counter = seconds;
        isAlive = true;
    }

    public void update() {
        Vector2f appliedForce = getAppliedForce();
        target.addForce(appliedForce);
    }

    public boolean isAlive() {
        return isAlive;
    }

    private Vector2f getAppliedForce() {
        if (counter > 0) {
            float secondsPast = Time.DeltaTime.getSeconds();
            counter -= secondsPast;
            if (counter < 0) {
                secondsPast += counter;
                isAlive = false;
            }
            return MathJ.Math2D.multiply(force, secondsPast);
        }
        return null;
    }
}
