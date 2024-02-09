package org.dmarshaq.graphics.particles;

import org.dmarshaq.graphics.Sprite;
import org.dmarshaq.mathj.MathJ;
import org.dmarshaq.mathj.Vector2f;
import org.dmarshaq.time.Time;

public class Particle {
    private final float lifetime; // Time in seconds
    private float age;
    private float scale;
    private float speed; // units per second
    private final Vector2f position = new Vector2f();
    private final Vector2f velocity = new Vector2f();
    private final Vector2f moveDirection = new Vector2f();


    public Particle(float lifetime, float scale, float speed, Vector2f position, Sprite sprite) {
        this.lifetime = lifetime;
        this.scale = scale;
        this.speed = speed;
        this.position.copyValues(position);
        this.position.x -= sprite.getScaledWidth() / 2;
        this.position.y -= sprite.getScaledHeight() / 2;
    }

    public boolean move(Vector2f direction) {
        moveDirection.copyValues(direction);
        velocity.x = moveDirection.x * speed * Time.DeltaTime.getSeconds();
        velocity.y = moveDirection.y * speed * Time.DeltaTime.getSeconds();
        position.copyValues(MathJ.Math2D.sum(position, velocity));
        age += Time.DeltaTime.getSeconds(); // time past this movement
        return !(age >= lifetime);
    }

    public float getLifetime() {
        return lifetime;
    }

    public float getAge() {
        return age;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public Vector2f getPosition() {
        return position;
    }

    public Vector2f getMoveDirection() {
        return moveDirection;
    }
}
