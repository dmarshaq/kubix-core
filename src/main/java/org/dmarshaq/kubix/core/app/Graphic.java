package org.dmarshaq.kubix.core.app;

import lombok.Getter;
import lombok.Setter;
import org.dmarshaq.kubix.core.audio.Audio;
import org.dmarshaq.kubix.core.graphic.Window;
import org.dmarshaq.kubix.core.graphic.render.Render;
import org.dmarshaq.kubix.core.input.Input;
import org.dmarshaq.kubix.core.input.MouseInput;
import org.dmarshaq.kubix.core.graphic.data.Snapshot;
import org.dmarshaq.kubix.core.math.vector.Vector3;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import java.awt.*;

import static org.dmarshaq.kubix.core.app.Context.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.openal.ALC10.*;

public abstract class Graphic implements Runnable {

    public static final Dimension SCREEN_DIMENSION = Toolkit.getDefaultToolkit().getScreenSize();

    @Getter
    private static Window window;
    @Getter
    private static Audio audio;
    @Getter
    private static Render render;

    private Snapshot snapshot;

    @Setter
    private Update updateTask;
    @Setter
    private Context context;

    @Override
    public void run() {
        init();
        GL.createCapabilities();

        context.loadResources();
        initShaders();
        render = new Render(window, Context.getClearColor());

        Thread update = new Thread(updateTask);
        update.start();


        while(Context.isRunning()) {
            // do some graphic
            synchronized (this) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            modifyShaders();
            render.render(snapshot);
        }

        // Destroy the audio Context
        audio.destroyAudio();

        // Free the window callbacks and destroy the window
        window.freeCallbacks();
        window.destroyWindow();

        // Terminate GLFW and free the error callback
        glfwTerminate();
    }

    synchronized public void loadData(Snapshot data) {
        this.snapshot = data;
    }

    public void init() {
        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if ( !glfwInit() )
            throw new IllegalStateException("Unable to initialize GLFW");

        // Configure GLFW
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, isFullScreen() ? GLFW_FALSE : GLFW_TRUE); // the window will be resizable


        // Create the window
        if (Context.isFullScreen()) {
            window = new Window((int) SCREEN_DIMENSION.getWidth(), (int) SCREEN_DIMENSION.getHeight(), Context.getTitle(), true);
        }
        else {
            window = new Window(1280, 720, Context.getTitle(), false);
        }

        // Get the resolution of the primary monitor
        GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        // Center the window
        if (!isFullScreen() && vidmode != null) {
            glfwSetWindowPos(
                    window.getWindow(),
                    (vidmode.width() - window.width()) / 2,
                    (vidmode.height() - window.height()) / 2
            );
        }

        // Make the OpenGL context current
        window.makeContextCurrent();
        // Enable v-sync
        glfwSwapInterval(1);

        // Input call back
        window.setKeyCallback(new Input());
        glfwSetInputMode(window.getWindow(), GLFW_STICKY_KEYS, 1);
        // Mouse Input call back
        new MouseInput().init(window.getWindow());
        // Size call back
        window.setSizeCallback(Window.windowDefaultSizeCallback(window));

        // Make the window visible
        window.show();

        // Initialize the audio device
        audio = new Audio(alcGetString(0, ALC_DEFAULT_DEVICE_SPECIFIER));

        audio.makeContextCurrent();

        audio.createCapabilities();

        audio.setListener(new Vector3(0.0f, 0.0f, 0.0f), new Vector3(0.0f, 0.0f, 0.0f));
    }

    abstract protected void initShaders();
    abstract protected void modifyShaders();


}
