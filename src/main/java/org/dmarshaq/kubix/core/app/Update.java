package org.dmarshaq.kubix.core.app;

import org.dmarshaq.kubix.core.input.InputManager;
import org.dmarshaq.kubix.core.time.Time;
import org.dmarshaq.kubix.graphic.render.LayerManager;
import org.dmarshaq.kubix.graphic.render.Snapshot;


import static org.lwjgl.glfw.GLFW.*;
import static org.dmarshaq.kubix.core.app.Context.*;

public abstract class Update implements Runnable {
    private Render render;
    private boolean start = true;

    protected Snapshot snapshot;

    public void setRenderTask(Render renderTask) {
        this.render = renderTask;
    }

    @Override
    public void run() {
        double updateInterval = (double) 1000000000 / getFPSCap();
        double nextUpdateTime = System.nanoTime() + updateInterval;
        double lastFrameTime = 0;
        while (Context.isRunning()) {
            // delta time is collected
            double time = System.nanoTime();
            if (lastFrameTime != 0) {
                Time.DeltaTime.setTime((float) (time - lastFrameTime)); // time diffrence between 2 frames, deltaTime
            }
            lastFrameTime = time;

            // check if user closed the window
            if (render.getWindow() != 0 && glfwWindowShouldClose(render.getWindow())) {
                Context.stopRunning();
            }

            // new snapshot
            snapshot = new Snapshot();
            // layers Clean Up
            // only in before first update (start method is called)
            if (start) {
                LayerManager.buildLayers();
                start();
                start = false;
            }
            // update method called, as well as inputs and time
            Render.getMouseInput().input();
            Time.updateTimers();
            update();
            // snapshot packing up, as well as resetting key states
            snapshot.releaseQuadRenderBuffer();
            InputManager.resetReleasedKeyStates();
            // loading snapshot into render
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

    protected abstract void start();

    protected abstract void update();
}


