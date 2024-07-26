package org.dmarshaq.kubix.core.app;

import org.dmarshaq.kubix.core.serialization.animation.AnimationManager;
import org.dmarshaq.kubix.core.graphic.base.Window;
import org.dmarshaq.kubix.core.input.InputManager;
import org.dmarshaq.kubix.core.time.Time;
import org.dmarshaq.kubix.core.graphic.base.layer.LayerManager;
import org.dmarshaq.kubix.core.graphic.data.Snapshot;


import static org.lwjgl.glfw.GLFW.*;

public abstract class Update implements Runnable {
    private Graphic graphic;
    private boolean start = true;

    double lastTime = System.currentTimeMillis();
    double accumulatorTime;

    double deltaTime;

    protected Snapshot snapshot;

    public void setRenderTask(Graphic graphicTask) {
        this.graphic = graphicTask;
    }

    @Override
    public void run() {
        while (Context.isRunning()) {
            // delta time is collected
            double current = System.currentTimeMillis();
            deltaTime = current - lastTime;
            lastTime = current;
            accumulatorTime += deltaTime;
            double sliceTime = Context.getTickTime();

            while (accumulatorTime >= sliceTime) {
                Time.DeltaTime.setTickTime(sliceTime);

                // check if user closed the window
                Window window = Graphic.getWindow();
                if (window.getWindow() != 0 && glfwWindowShouldClose(window.getWindow())) {
                    Context.setRunning(false);
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
                Time.updateTimers();
                AnimationManager.updateAnimators();
                update();
                // snapshot packing up, as well as resetting key states
                snapshot.releaseQuadRenderBuffer();
                snapshot.releaseLineRenderBuffer();
                InputManager.resetReleasedKeyStates();
                // loading snapshot into graphic
                graphic.loadData(snapshot);

                accumulatorTime -= sliceTime;
            }


            synchronized (graphic) {
                graphic.notify();
            }


        }
    }

    protected abstract void start();

    protected abstract void update();
}


