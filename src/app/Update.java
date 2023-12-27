package app;

import graphics.Sprite;
import input.Input;
import input.KeyCode;
import mathj.MathJ;
import mathj.Rect;
import mathj.Vector3f;

import java.util.ArrayList;

import static org.lwjgl.glfw.GLFW.*;
import static app.Context.*;

public class Update implements Runnable {

    private Context context;
    private Render render;
    private Snapshot snapshot;
    private float fps;

    public Update(final Context context, final Render render) {
        this.context = context;
        this.render = render;
    }

    @Override

    public void run() {

        double updateInterval = 1000000000/FPS;
        double nextUpdateTime = System.nanoTime() + updateInterval;
        double lastFrameTime = 0;

        while (Context.isRunning()) {
            double time = System.nanoTime();
            if (lastFrameTime != 0) {
                DeltaTime.setTime((float) (time - lastFrameTime)); // time diffrence between 2 frames, deltaTime
                fps = (1f / DeltaTime.getSeconds());
            }
            lastFrameTime = time;

            if (render.getWindow() != 0 && glfwWindowShouldClose(render.getWindow())) {
                Context.stopRunning();
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
        // flags(booleans, isOnFire, ), propeties(health, velocity), positions
        ArrayList<Integer> entityID = new ArrayList<Integer>();

        for (int i = 0; i < ENTITY_ID.length; i++) {
            // check if entity should be nullified, only if it was deleted (a.k.a its id == -1)
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
                default:
                    // simple gravity pull
                    if (ENTITY_POSITION[i].y > 0) {
                        if (ENTITY_POSITION[i].y < 2f * DeltaTime.getSeconds())
                            ENTITY_POSITION[i].y = 0;
                        else
                            ENTITY_POSITION[i].y -= 2f * DeltaTime.getSeconds();
                    }
                    switch (ENTITY_TYPE[i]) {

                        case PLAYER:
                            Vector3f velocity = new Vector3f();
                            if (Input.keysHold[ KeyCode.getKeyCode(KeyCode.D) ]) {
                                if (ENTITY_FLIP[i])
                                    ENTITY_FLIP[i] = false;

                                velocity.x = 1f * DeltaTime.getSeconds();
                            }
                            else if (Input.keysHold[ KeyCode.getKeyCode(KeyCode.A) ]) {
                                if (!ENTITY_FLIP[i])
                                    ENTITY_FLIP[i] = true;

                                velocity.x = -1f * DeltaTime.getSeconds();
                            }

                            ENTITY_POSITION[i].copyValues(MathJ.sum2d(ENTITY_POSITION[i], velocity));
                            context.setCameraCenter(new Vector3f(ENTITY_POSITION[i].x + 0.25f, 0.5f, 0f));
                            break;

                        case SLIME:

                            break;
                        default:
                            break;
                    }
                    if (MathJ.isInRect(ENTITY_BOUNDING_BOX[i], context.getCameraFov()) && ENTITY_ID[i] > -1)
                        entityID.add(i);
            }
        }

        snapshot.ENTITY_ID = entityID.stream().mapToInt(i -> i).toArray();
    }

    private void updateEnvironment() {
        ArrayList<Integer> chunksID = new ArrayList<Integer>();

        for (int i = 0; i < CHUNKS_ID.length; i++) {
            if (MathJ.isInRect(new Rect(CHUNKS_POSITION[i], CHUNKS_WIDTH, CHUNKS_HEIGHT), context.getCameraFov())) {
                chunksID.add(i);
            }
        }

        snapshot.CHUNKS_ID = chunksID.stream().mapToInt(i -> i).toArray();
    }

    private void updateCamera() {
        // Camera logic
//        Vector3f velocity = new Vector3f(0f * DeltaTime.getSeconds(), 0f);
//        if (Input.keysHold[ KeyCode.getKeyCode(KeyCode.D) ]) {
//            velocity.x = 1f * DeltaTime.getSeconds();
//        }
//        else if (Input.keysHold[ KeyCode.getKeyCode(KeyCode.A) ]) {
//            velocity.x = -1f * DeltaTime.getSeconds();
//        }
//
//        Vector3f resultant = MathJ.sum2d(context.getCameraFov().getCenter(), velocity);
//        context.setCameraCenter(resultant);

//        System.out.println("FPS: " + fps);

        snapshot.cameraFov = new Rect(context.getCameraFov());
    }
}


