package org.dmarshaq.graphics.particles;

import org.dmarshaq.app.Snapshot;
import org.dmarshaq.app.Update;
import org.dmarshaq.graphics.Sprite;
import org.dmarshaq.mathj.MathJ;
import org.dmarshaq.mathj.Vector2f;

import java.util.ArrayList;
import java.util.List;

import static org.dmarshaq.graphics.particles.ParticleSystem.ParticleMovement.*;

public class ParticleSystem {
    private final List<Particle> particles = new ArrayList<>();
    private final int maxParticles;
    private final int rate; // Particles per second
    private final Sprite sprite;
    private final Vector2f origin;

    private boolean isOn;
    private float counter = 0.0f;

    private float particleLifetime = 1.0f;
    private float particleScale = 1.0f;
    private float particleSpeed = 1.0f;


    public void setParticleLifetime(float particleLifetime) {
        this.particleLifetime = particleLifetime;
    }
    public void setParticleScale(float particleScale) {
        this.particleScale = particleScale;
    }
    public void setParticleSpeed(float particleSpeed) {
        this.particleSpeed = particleSpeed;
    }

    public ParticleSystem(Vector2f origin, int maxParticles, int rate, Sprite sprite, boolean isOn) {
        this.maxParticles = maxParticles;
        this.rate = rate;
        this.sprite = sprite;
        this.origin = origin;
        this.isOn = isOn;
    }


    public void stop() {
        isOn = false;
    }

    public void toggle() {
        isOn = !isOn;
    }

    public void resume() {
        isOn = true;
    }

    public void update(Snapshot snapshot) {
        if (isOn && Update.getCurrentFPS() > 0 && particles.size() < maxParticles) {
            counter += rate / Update.getCurrentFPS();

            if (counter >= 1.0f) {
                spawnParticle(origin);
                counter -= 1.0f;
            }
        }
        for (int i = 0; i < particles.size(); i++) {
            Particle p = particles.get(i);
            if ( !moveLinearToTarget(p, new Vector2f(2.0f, 2.0f), 3.0f) ) {
                particles.remove(i);
            }
            else {
                sprite.getTransform().setPositionXY(p.getPosition());
                snapshot.addSpriteToRender(sprite);
            }
        }

    }

    private void spawnParticle(Vector2f position) {
        Particle particle = new Particle(particleLifetime, particleScale, particleSpeed, position, sprite);
        particles.add(particle);
    }

    // Different movements described by assigning direction to each particle individually, magnitude of direction
    // will affect velocity of the particle, normalize it if you don't want any effect on particle's velocity.
    public static class ParticleMovement {
        public static boolean moveRandom(Particle particle) {
            Vector2f direction = randomDirection();
            return particle.move(direction);
        }

        public static boolean moveLinear(Particle particle) {
            Vector2f direction = particle.getMoveDirection();
            if (direction.x == 0 && direction.y == 0) {
                direction = randomDirection();
            }
            return particle.move( direction );
        }

        public static boolean moveLinearToTarget(Particle particle, Vector2f target, float spread) {
            Vector2f direction = particle.getMoveDirection();
            if (direction.x == 0 && direction.y == 0) {
                boolean sign = Math.random() <= 0.5f;
                direction.copyValues(target);
                if (sign) {
                    direction.x = (float) (target.x + ( spread * Math.random() ));
                    direction.y = (float) (target.y + ( spread * Math.random() ));
                }
                else {
                    direction.x = (float) (target.x - ( spread * Math.random() ));
                    direction.y = (float) (target.y - ( spread * Math.random() ));
                }

            }
            return particle.move( MathJ.Math2D.normalize(direction) );
        }

        private static Vector2f randomDirection() {
            float angle = (float) (Math.random() * 360);
            return new Vector2f((float) Math.cos(angle), (float) Math.sin(angle));
        }
    }
}
