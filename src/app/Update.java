package app;

import graphics.Anim;
import input.Input;
import input.KeyCode;
import mathj.MathJ;
import mathj.Rect;
import mathj.Vector3f;
import time.Time;

import java.util.ArrayList;

import static org.lwjgl.glfw.GLFW.*;
import static app.GameContext.*;

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

        // Environment
        updateEnvironment();

        // Entities
        updateEntities();

        // Camera
        updateCamera();
    }

    private void updateEntities() {
        ArrayList<Integer> entityID = new ArrayList<>();

        for (int i = 0; i < ENTITY_ID.length; i++) {
            /*
             *   ------------------------------------------------------------------------------
             *   ALL ENTITIES: Switch checks if entity should be nullified, only if it was
             *   deleted (if it's id == -1).
             *   ------------------------------------------------------------------------------
             * */
            switch (ENTITY_ID[i]) {
                case -1:
                    ENTITY_ID[i] = -2;
                    ENTITY_POSITION[i] = null;
                    ENTITY_BOUNDING_BOX[i] = null;
                    ENTITY_TYPE[i] = null;
                    ENTITY_SPRITE[i] = null;
                    ENTITY_FLIP[i] = false;
                    break;
                case -2:
                    break;
                /*
                 *   ------------------------------------------------------------------------------
                 *   ALL ENTITIES: Every other entity will continue to update and possibly
                 *   be rendered.
                 *   ------------------------------------------------------------------------------
                 * */
                default:
                    /*
                     *   ------------------------------------------------------------------------------
                     *   ALL ENTITIES: Moves all entities down if they are above y = 0 line.
                     *   ------------------------------------------------------------------------------
                     * */
                    if (ENTITY_POSITION[i].y > 0) {
                        if (ENTITY_POSITION[i].y < 2f * Time.DeltaTime.getSeconds())
                            ENTITY_POSITION[i].y = 0;
                        else
                            ENTITY_POSITION[i].y -= 2f * Time.DeltaTime.getSeconds();
                    }

                    /*
                     *   ------------------------------------------------------------------------------
                     *   ALL ENTITIES: Following switch identifies entity and changes its properties
                     *   or trigger other events based on it's type.
                     *   ------------------------------------------------------------------------------
                     * */
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
                                Destroy(1);
                                timer_player = -1;
                            }

                            // Simple movement and animation switcher based on key press.
                            Vector3f velocity = new Vector3f();

                            if (Input.keysHold[ KeyCode.getKeyCode(KeyCode.D) ]) {
                                if (ENTITY_FLIP[i])
                                    ENTITY_FLIP[i] = false;
                                ENTITY_CURRENT_ANIM[i] = Anim.PLAYER_RUN;
                                velocity.x = Player.PLAYER_SPEED * Time.DeltaTime.getSeconds();
                            }
                            else if (Input.keysHold[ KeyCode.getKeyCode(KeyCode.A) ]) {
                                if (!ENTITY_FLIP[i])
                                    ENTITY_FLIP[i] = true;
                                ENTITY_CURRENT_ANIM[i] = Anim.PLAYER_RUN;
                                velocity.x = -Player.PLAYER_SPEED * Time.DeltaTime.getSeconds();
                            }
                            else {
                                ENTITY_CURRENT_ANIM[i] = Anim.PLAYER_IDLE;
                            }

                            ENTITY_POSITION[i].copyValues(MathJ.sum2d(ENTITY_POSITION[i], velocity));

                            // Camera positioning based on player's position.
                            context.setCameraCenter(new Vector3f(ENTITY_POSITION[i].x + 0.08f, 0.5f, 0f));
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
                         *   UNIDENTIFIED ENTITY CASE
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
                    if (MathJ.isInRect(ENTITY_BOUNDING_BOX[i], context.getCameraFov()) && ENTITY_ID[i] > -1) {
                        entityID.add(i);
                    }
            }
        }

        snapshot.ENTITY_ID = entityID.stream().mapToInt(i -> i).toArray();
    }

    private void updateEnvironment() {
        ArrayList<Integer> chunksID = new ArrayList<>();

        for (int i = 0; i < CHUNKS_ID.length; i++) {
            if (MathJ.isInRect(new Rect(CHUNKS_POSITION[i], CHUNKS_WIDTH, CHUNKS_HEIGHT), context.getCameraFov())) {
                chunksID.add(i);
            }
        }

        snapshot.CHUNKS_ID = chunksID.stream().mapToInt(i -> i).toArray();
    }

    private void updateCamera() {
        // Camera logic
//        System.out.println("FPS: " + fps);

        snapshot.cameraFov = new Rect(context.getCameraFov());
    }
}


