package org.dmarshaq.app;

import org.dmarshaq.graphics.Anim;
import org.dmarshaq.input.Input;
import org.dmarshaq.input.KeyCode;
import org.dmarshaq.mathj.*;
import org.dmarshaq.time.Time;

import java.util.ArrayList;

import static org.lwjgl.glfw.GLFW.*;
import static org.dmarshaq.mathj.MathJ.Math2D;
import static org.dmarshaq.app.GameContext.*;

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
        // entities to render
        ArrayList<Integer> entityToRenderID = new ArrayList<>();

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
                    ENTITY_TRANSFORM[i] = null;
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
                     *   ALL ENTITIES: Following if moves all entities down if they are above y = 0 line.
                     *
                     *   IMPORTANT:
                     *   * Position used throughout update is exact position from the end of the last update
                     *   * it only assigned once per update in the beginning, the only thing that is changes is
                     *   * ENTITY_TRANSFORM. This is made to "blend" all unrelated entity changes in position in
                     *   * single update. This means that unrelated changes will appear to be applied
                     *   * simultaneously at the end of update.
                     *   ------------------------------------------------------------------------------
                     * */
                    Vector3f initialPosition = Matrix4f.getPosition(ENTITY_TRANSFORM[i]);

                    if (initialPosition.y > 0) {
                        if (initialPosition.y < 2f * Time.DeltaTime.getSeconds())
                            ENTITY_TRANSFORM[i].setPosition(new Vector3f(initialPosition.x, 0f, initialPosition.z));
                        else
                            ENTITY_TRANSFORM[i].multiply(Matrix4f.translate( new Vector2f(0f, -2f * Time.DeltaTime.getSeconds() ) ));
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

                            ENTITY_TRANSFORM[i].multiply( Matrix4f.translate(velocity) );
//                            ENTITY_POSITION[i].copyValues(MathJ.sum2d(ENTITY_POSITION[i], velocity));

                            // Camera positioning based on player's position.
                            /*
                             *   ------------------------------------------------------------------------------
                             *   IMPORTANT:
                             *   * For example right here I don't access initialPosition, because camera center
                             *   * is related to result of transformation right before this next statement.
                             *   * So in this case I can just access entities most recent position via it's
                             *   * ENTITY_TRANSFORM.
                             *   ------------------------------------------------------------------------------
                             * */
                            context.setCameraCenter( new Vector2f( Matrix4f.getPosition(ENTITY_TRANSFORM[i]).x + 0.08f, 0.5f ) );
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
                    if (context.getCameraFov().touchesRect(ENTITY_BOUNDING_BOX[i]) && ENTITY_ID[i] > -1) {
                        entityToRenderID.add(i);
                    }
            }
        }

        /*
         *   ------------------------------------------------------------------------------
         *   ALL ENTITIES: Actually loads snapshot array by array.
         *   ------------------------------------------------------------------------------
         * */
        snapshot.initEntityData(entityToRenderID.size());
        int i = 0;
        for (int eID : entityToRenderID) {
            snapshot.ENTITY_ID[i] = eID;
            snapshot.ENTITY_TRANSFORM[i] = new Matrix4f();
            snapshot.ENTITY_TRANSFORM[i].copy(ENTITY_TRANSFORM[eID]);
            snapshot.ENTITY_TYPE[i] = ENTITY_TYPE[eID];
            snapshot.ENTITY_FLIP[i] = ENTITY_FLIP[eID];
            snapshot.ENTITY_CURRENT_ANIM[i] = ENTITY_CURRENT_ANIM[eID];
            i++;
        }

    }

    private void updateEnvironment() {
        ArrayList<Integer> chunksID = new ArrayList<>();

        for (int i = 0; i < CHUNKS_ID.length; i++) {
            if (context.getCameraFov().touchesRect(new Rect(Math2D.toVector2f(CHUNKS_POSITION[i]), CHUNKS_WIDTH, CHUNKS_HEIGHT))) {
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


