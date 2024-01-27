package org.dmarshaq.app;

import org.dmarshaq.audio.Sound;
import org.dmarshaq.graphics.*;
import org.dmarshaq.graphics.font.Font;
import org.dmarshaq.graphics.particles.ParticleSystem;
import org.dmarshaq.input.Input;
import org.dmarshaq.input.KeyCode;
import org.dmarshaq.mathj.*;
import org.dmarshaq.mathj.MathJ.Math2D;
import org.dmarshaq.physics.Physics2D;
import org.dmarshaq.physics.RayCast2D;
import org.dmarshaq.physics.RigidBody2D;
import org.dmarshaq.time.Time;


import static org.lwjgl.glfw.GLFW.*;
import static org.dmarshaq.app.GameContext.*;
import static org.dmarshaq.app.GameContext.HelloWorld.*;

public class Update implements Runnable {

    private GameContext context;
    private Render render;
    private Snapshot snapshot;
    private static float fps;
    private boolean start = true;

    public Update(final GameContext context, final Render render) {
        this.context = context;
        this.render = render;
    }

    @Override

    public void run() {

        double updateInterval = (double) 1000000000 / FPS;
        double nextUpdateTime = System.nanoTime() + updateInterval;
        double lastFrameTime = 0;
        while (GameContext.isRunning()) {
            double time = System.nanoTime();
            if (lastFrameTime != 0) {
                Time.DeltaTime.setTime((float) (time - lastFrameTime)); // time diffrence between 2 frames, deltaTime
                fps = (1f / Time.DeltaTime.getSeconds());
            }
            lastFrameTime = time;

            if (render.getWindow() != 0 && glfwWindowShouldClose(render.getWindow())) {
                GameContext.stopRunning();
            }

            // New snapshot
            snapshot = new Snapshot();
            // Layers Clean Up
            Layer.clearRenderSpritesCount();
            // only in first update
            if (start) {
                start();
                start = false;
            }
            // update itself
            update();
            // snapshot packing up
            snapshot.closeSpriteInputStream();
            // loading snapshot into render
            render.loadData(snapshot);

            synchronized (render) {
                render.notify();
            }
            try {
                double remainingTime = nextUpdateTime - System.nanoTime();
                remainingTime /= 1000000;
//                System.out.println("Remaining time: " + remainingTime);
                if (remainingTime < 0) {
                    remainingTime = 0;
                }

                Thread.sleep((long) remainingTime);

                nextUpdateTime += updateInterval;

            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    private Animation animation1, animation2, animation3;
    private Sprite coloredSprite, origin, fire, explosion, pawn, particle, body;
    private RigidBody2D rigidBody2D;
    private Text text1, text2, text3;
    private ParticleSystem particleSystem;
    private Matrix4f transform = Matrix4f.identity();
    private Matrix4f transform1 = Matrix4f.identity();
    private Matrix4f transform2 = Matrix4f.identity();
    private Matrix4f transform3 = Matrix4f.identity();
    private Matrix4f localSpace = Matrix4f.identity();
    private int counter = 1;
    private Sound sound1;

    private void start() {
        sound1 = new Sound("sound/Innerbloom.ogg", true, 0.08f, 1.0f);

        Vector4f color = new Vector4f(0.5f, 0.6f, 0.4f, 0.2f);
        coloredSprite = new Sprite(transform, Layer.L1, color, Shader.S1);

        transform = Matrix4f.TRS(new Vector2f(), 45f, 0.25f, 0.25f);
        transform.multiply(Matrix4f.translateXY( new Vector2f(-0.5f, -0.5f) ));
        color = new Vector4f(0.7f, 0.7f, 0.7f, 0.7f);
        origin = new Sprite(transform, Layer.DEFAULT, color, Shader.BASIC);

//        animation1 = new Animation(500, 0, Texture.FIRE);
//        animation2 = new Animation(500, 0, Texture.EXPLOSION);
//        animation3 = new Animation(800, 0, Texture.PAWN_BLUE);
//
//        transform = Matrix4f.scaleXY(0.5f, 0.5f);
//        fire = new Sprite(transform, Layer.DEFAULT, animation1, Shader.BASIC);
//
//        transform = Matrix4f.scaleXY(0.5f, 0.5f);
//        explosion = new Sprite(transform, Layer.DEFAULT, animation2, Shader.BASIC);
//
//        transform = Matrix4f.scaleXY(1.0f, 1.0f);
//        pawn = new Sprite(transform, Layer.PLAYER, animation3, Shader.BASIC);
//
//        transform = Matrix4f.scaleXY(0.25f, 0.25f);
//        Sprite particleSprite = new Sprite(transform, Layer.DEFAULT, Texture.FIRE, Texture.FIRE.getSubTextures()[0], Shader.BASIC);
//
//        transform1 = Matrix4f.TRS(new Vector2f(0, 0), 0f, 3.0f, 3.0f);
//        text1 = new Text("white balls", transform1, Layer.UI, Font.BASIC_PUP_WHITE, Shader.BASIC);
//
//        transform2 = Matrix4f.TRS(new Vector2f(0, 0), 180f, 3.0f, 3.0f);
//        text2 = new Text("black balls", transform2, Layer.UI, Font.BASIC_PUP_BLACK, Shader.BASIC);
//
//        transform3 = Matrix4f.TRS(new Vector2f(0, -2), 0, 2.0f, 2.0f);
//        String paragraph = "In the bustling city of Eldoria, a grand procession was underway. Thousands of characters, from knights in shining armor to enchanting sorceresses, filled the cobblestone streets. As they marched in unison, the batch renderer seamlessly managed this diverse crowd, ensuring smooth and efficient rendering. The knights' armor gleamed in the sunlight, while the sorceresses' magical spells cast shimmering trails of light. Each character, unique in their appearance and animations, moved effortlessly through the cityscape, creating a vibrant and immersive world. Thanks to the powerful batch renderer, players could explore the rich tapestry of Eldoria without a hitch, experiencing the grandeur of this fantastical realm in all its glory.";
//        text3 = new Text(paragraph, transform3, Layer.UI, Font.BASIC_PUP_BLACK, Shader.BASIC);
//
//        transform = Matrix4f.scaleXY(0.10f, 0.10f);
//        color = new Vector4f(0.7f, 0.7f, 0.7f, 0.9f);
//        particle = new Sprite(transform, Layer.DEFAULT, color, Shader.BASIC);
//
//        particleSystem = new ParticleSystem(new Vector2f(-5, 0), 500, 100, particle, true);
//        particleSystem.setParticleLifetime(10f);
//        particleSystem.setParticleSpeed(1.0f);
//        particleSystem.stop();

        transform = Matrix4f.TRS(new Vector2f(0.0f, 0.0f), 0f, 1f, 1f);
        body = new Sprite(transform, Layer.DEFAULT, color, Shader.BASIC);
        rigidBody2D = new RigidBody2D(body.getCenter(), 1.00f);
//
//        localSpace = Matrix4f.TRS( new Vector2f(0.0f, 0.0f), 0f, 2.0f, 1.0f );
//        Vector2f worldPoint = localSpace.localToWorld( new Vector2f(1.0f, 5.0f) );
//        System.out.println(worldPoint);
//        Vector2f localPoint = localSpace.worldToLocal( worldPoint );
//        System.out.println(localPoint);

        Physics2D.createBoxCollider2D(localSpace, new Vector2f(1.0f, 1.0f), 1.0f, 4.0f, false, true);

        RayCast2D rayCast2D = Physics2D.rayCast2D(new Vector2f(1.0f, 0.0f), new Vector2f(0.5f, 0.6f));
        if (rayCast2D != null) {
            Vector2f intersection = rayCast2D.getIntersection();
            System.out.println("Ray hit: " + intersection);
        }
        else {
            System.out.println("No ray intersection.");
        }
    }

    private float angle = 0f;

    private void update() {

        // example sound
        sound1.play();

        // example sprites

        snapshot.addSpriteToRender(origin);

//        snapshot.addSpriteToRender(coloredSprite);

//        // example sprites & animations
//        fire.playAnimation(MathJ.Easing.LINEAR);
//        snapshot.addSpriteToRender(fire);
//
//        explosion.playAnimation(MathJ.Easing.EASE_IN_OUT_SINE);
//        snapshot.addSpriteToRender(explosion);
//
//        pawn.playAnimation(MathJ.Easing.LINEAR);
//        snapshot.addSpriteToRender(pawn);
//
//        if ( (timer_test = Time.Timer.countTimer(timer_test)) == 0 ) {
//            fire.setAnimation(animation2);
//
//            animation3.setAnimationRow(counter);
//
//            counter++;
//            if (counter > 5) {
//                counter = 0;
//            }
//            particleSystem.resume();
//            timer_test = 4000;
//        }
//
//        // example text's & their transformations
//        transform1.multiply(Matrix4f.rotateXY(30 * Time.DeltaTime.getSeconds()));
//        text1.update(snapshot);
//
//        transform2.multiply(Matrix4f.rotateXY(30 * Time.DeltaTime.getSeconds()));
//        text2.update(snapshot);
//
//        text3.update(snapshot);
//
//        // example simple particle system
//        particleSystem.update(snapshot);



        // Physics 2D demonstration
        if ( (timer_physics_test = Time.Timer.countTimer(timer_physics_test)) == 0 ) {
            float randomX = (float) Math.random();
            float randomY = (float) Math.random();
            Physics2D.applyImpulse(new Vector2f(randomX * 1000f, randomY * 1000f), 0.01f, rigidBody2D);

//            Physics2D.applyImpulse(new Vector2f(-randomX * 10f, -randomY * 10f), 1.00f, rigidBody2D);

            timer_physics_test = 4000;
        }

        Physics2D.updatePhysics(snapshot);
        rigidBody2D.update();
        body.getTransform().setPositionXY( Math2D.diffrence( rigidBody2D.getPosition(), new Vector2f(0.5f, 0.5f))  );
        snapshot.addSpriteToRender(body);

        // No clip primitive 2D movement
        Vector2f camCenter = camera.getCameraCenter();
        Vector2f velocity = new Vector2f();
        if (Input.keysHold[ KeyCode.getKeyCode(KeyCode.D) ]) {
            velocity.x += 18 * Time.DeltaTime.getSeconds();
        }
        if (Input.keysHold[ KeyCode.getKeyCode(KeyCode.A) ]) {
            velocity.x -= 18 * Time.DeltaTime.getSeconds();
        }
        if (Input.keysHold[ KeyCode.getKeyCode(KeyCode.W) ]) {
            velocity.y += 18 * Time.DeltaTime.getSeconds();
        }
        if (Input.keysHold[ KeyCode.getKeyCode(KeyCode.S) ]) {
            velocity.y -= 18 * Time.DeltaTime.getSeconds();
        }
        camera.setCameraCenter(Math2D.sum(camCenter, velocity));

//        System.out.println("FPS: " + fps);
        snapshot.setCameraToRender(camera);
    }

    public static float getCurrentFPS() {
        return fps;
    }
}


