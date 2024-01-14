package org.dmarshaq.app;

import org.dmarshaq.graphics.Anim;
import org.dmarshaq.graphics.SpriteDTO;
import org.dmarshaq.graphics.Texture;
import org.dmarshaq.input.Input;
import org.dmarshaq.input.KeyCode;
import org.dmarshaq.mathj.*;
import org.dmarshaq.time.Time;


import static org.lwjgl.glfw.GLFW.*;
import static org.dmarshaq.app.GameContext.*;
import static org.dmarshaq.app.GameContext.HelloWorld.*;

public class Update implements Runnable {

    private GameContext context;
    private Render render;
    private Snapshot snapshot;
    private float fps;


    public Update(final GameContext context, final Render render) {
        this.context = context;
        this.render = render;
    }

    @Override

    public void run() {

        double updateInterval = (double) 1000000000 / FPS;
        double nextUpdateTime = System.nanoTime() + updateInterval;
        double lastFrameTime = 0;
        System.out.println("Here, Update");
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
            // do some job

            snapshot = new Snapshot();
            update();
            snapshot.closeSpriteInputStream();

            render.loadData(snapshot);

            synchronized (render) {
                render.notify();
            }
            try {
                double remainingTime = nextUpdateTime - System.nanoTime();
                remainingTime /= 1000000;
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

    private void update() {

        // Layers Clean Up
        Layer.clearRenderSpritesCount();

        // Entities
        updateEntities();

        // Camera
        updateCamera();

    }

    private int c;

    private void updateEntities() {
        // entities to render
        for (int x = 0; x < 128; x++) {
            for (int y = 0; y < 128; y++) {
                snapshot.addSpriteToRender( new SpriteDTO(Matrix4f.translate(new Vector2f((float) x / 4, (float) y / 4)), Texture.SLIME_TEXTURE, Layer.DEFAULT) );
            }
        }

        for (int i = 0; i < ENTITY_ID.length; i++) {

            switch (ENTITY_ID[i]) {
                case -1:
                    ENTITY_ID[i] = -2;
                    ENTITY_TRANSFORM[i] = null;
                    ENTITY_BOUNDING_BOX[i] = null;
                    ENTITY_TYPE[i] = null;
                    ENTITY_SPRITE_DTO[i].getLayer().decrementSpriteCount();
                    ENTITY_SPRITE_DTO[i] = null;
                    ENTITY_CURRENT_ANIM[i] = null;
                    break;
                case -2:
                    break;

                default:

                    Vector3f initialPosition = Matrix4f.getTransformPosition(ENTITY_TRANSFORM[i]);

                    if (initialPosition.y > 0) {
                        if (initialPosition.y < 2f * Time.DeltaTime.getSeconds())
                            ENTITY_TRANSFORM[i].setTransformPosition(new Vector2f(initialPosition.x, 0f), initialPosition.z);
                        else
                            ENTITY_TRANSFORM[i].multiply(Matrix4f.translate( new Vector2f(0f, -2f * Time.DeltaTime.getSeconds() ) ));
                    }

                    switch (ENTITY_TYPE[i]) {

                        /*
                         *   ------------------------------------------------------------------------------
                         *   PLAYER CASE
                         *   ------------------------------------------------------------------------------
                         * */
                        case PLAYER:
                            // Timer demonstration.
                            if ((timer_player = Time.Timer.countTimer(timer_player)) == 0) {
                                System.out.println("5 second past");
                                System.out.println("Slime is destroyed");
                                HelloWorld.Destroy(1);
                                timer_player = -1;
                            }

                            // Simple movement and animation switcher based on key press.
                            Vector2f velocity = new Vector2f();

                            if (Input.keysHold[ KeyCode.getKeyCode(KeyCode.D) ]) {
//                                if (ENTITY_FLIP[i])
//                                    ENTITY_FLIP[i] = false;
                                ENTITY_CURRENT_ANIM[i] = Anim.PLAYER_RUN;
                                velocity.x = Player.PLAYER_SPEED * Time.DeltaTime.getSeconds();
                            }
                            else if (Input.keysHold[ KeyCode.getKeyCode(KeyCode.A) ]) {
//                                if (!ENTITY_FLIP[i])
//                                    ENTITY_FLIP[i] = true;
                                ENTITY_CURRENT_ANIM[i] = Anim.PLAYER_RUN;
                                velocity.x = -Player.PLAYER_SPEED * Time.DeltaTime.getSeconds();
                            }
                            else {
                                ENTITY_CURRENT_ANIM[i] = Anim.PLAYER_IDLE;
                            }

                            ENTITY_TRANSFORM[i].multiply( Matrix4f.translate(velocity) );

                            HelloWorld.setCameraCenter( new Vector2f( Matrix4f.getTransformPosition(ENTITY_TRANSFORM[i]).x + 0.08f, 0.5f ) );
                            break;

                        /*
                         *   ------------------------------------------------------------------------------
                         *   SLIME CASE
                         *   ------------------------------------------------------------------------------
                         * */
                        case SLIME:

                            break;

                        /*
                         *   ------------------------------------------------------------------------------
                         *   DEFAULT ENTITY CASE
                         *   ------------------------------------------------------------------------------
                         * */
                        default:

                            break;
                    }

                    /*
                     *   ------------------------------------------------------------------------------
                     *   ALL ENTITIES: Following if statement checks, if entity is in camera fov, if it is,
                     *   sends it to this update snapshot.
                     *   ------------------------------------------------------------------------------
                     * */
                    if (ENTITY_BOUNDING_BOX[i] != null && HelloWorld.getCameraFov().touchesRect(ENTITY_BOUNDING_BOX[i]) && ENTITY_ID[i] > -1) {
                        snapshot.addSpriteToRender(ENTITY_SPRITE_DTO[i]);
                    }
            }
        }
    }

    private void updateCamera() {
        // Camera logic
        System.out.println("FPS: " + fps);

        snapshot.cameraFov = new Rect(HelloWorld.getCameraFov());
    }
}


