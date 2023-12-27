package app;

import graphics.Shader;
import graphics.Sprite;
import input.Input;
import mathj.MathJ;
import mathj.Matrix4f;
import mathj.Rect;
import mathj.Vector3f;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.*;

public class Render implements Runnable {

    private Snapshot data;
    private long window;
    private Matrix4f pr_matrix;

    private Sprite ground1;
    private Sprite ground2;
    private Sprite player1;

    @Override
    public void run() {
        init();
        GL.createCapabilities();

        renderInit();

        while(GameContext.isRunning()) {
            // do some render
            render();
            synchronized (this) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        // Free the window callbacks and destroy the window
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        // Terminate GLFW and free the error callback
        glfwTerminate();
    }

    synchronized public void loadData(Snapshot data) {
        this.data = data;
    }

    private void init() {

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if ( !glfwInit() )
            throw new IllegalStateException("Unable to initialize GLFW");

        // Configure GLFW
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable

        // Create the window
        window = glfwCreateWindow(GameContext.SCREEN_WIDTH, GameContext.SCREEN_HEIGHT, GameContext.TITLE, NULL, NULL);
        if ( window == NULL )
            throw new RuntimeException("Failed to create the GLFW window");



        // Get the thread stack and push a new frame
        try ( MemoryStack stack = stackPush() ) {
            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*

            // Get the window size passed to glfwCreateWindow
            glfwGetWindowSize(window, pWidth, pHeight);

            // Get the resolution of the primary monitor
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            // Center the window
            glfwSetWindowPos(
                    window,
                    (vidmode.width() - GameContext.SCREEN_WIDTH) / 2,
                    (vidmode.height() - GameContext.SCREEN_HEIGHT) / 2
            );
        } // the stack frame is popped automatically


        glfwSetKeyCallback(window, new Input());

        // Make the OpenGL context current
        glfwMakeContextCurrent(window);

        // Enable v-sync
        glfwSwapInterval(1);



        // Make the window visible
        glfwShowWindow(window);
    }

    private void render() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        renderSnapshot(); // Render each object in given snapshot from single update frame

        int error = glGetError();
        if (error != GL_NO_ERROR)
            System.out.println(error);

        glfwSwapBuffers(window); // swap the color buffers
        // Poll for window events. The key callback above will only be
        // invoked during this call.
        glfwPollEvents();
    }

    public long getWindow() {
        return window;
    }

    private void renderInit() {
        // Blending
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        // Background
        glClearColor(0.3f, 0.4f, 0.8f, 1.0f);

        // Active textures, use 1, default
        glActiveTexture(GL_TEXTURE1);

        // Setup shaders
        Shader.loadAll();
        pr_matrix = Matrix4f.orthographic(-2f, 2f, -1.5f, 1.5f, -1f, 1f); // basically camera matrix
        Shader.BASIC.setUniformMatrix4f("pr_matrix", pr_matrix);
        Shader.BASIC.setUniform1i("tex", 1);

//        ground1 = new Sprite(new Rect(0f, -3f, 4f, 2f, 0f), Context.GROUND_TEXTURE_PATH, Shader.BASIC);
//        ground2 = new Sprite(new Rect(-4f, -3f, 4f, 2f, 0f), Context.GROUND_TEXTURE_PATH, Shader.BASIC);
//        player1 = new Sprite(new Rect(0f, -1f, 41f / 64f * 2, 31f / 64f * 2, 0f), Context.PLAYER_TEXTURE_PATH, Shader.BASIC);
    }

    private void renderSnapshot() {
        // data

        renderCamera();
        renderEntities();
        renderEnvironment();

//        ground1.render();
//        ground2.render();
//        player1.render();
    }

    private void renderEntities() {
        int[] id = data.ENTITY_ID;
        for (int i = 0; i < id.length; i++) {
            if (GameContext.ENTITY_SPRITE[id[i]] == null) {
                switch (GameContext.ENTITY_TYPE[id[i]]) {
                    case PLAYER:
                        GameContext.ENTITY_SPRITE[id[i]] = new Sprite(new Rect(GameContext.ENTITY_POSITION[id[i]], GameContext.PLAYER_WIDTH, GameContext.PLAYER_HEIGHT), GameContext.PLAYER_TEXTURE_PATH, Shader.BASIC);
                        break;
                    case SLIME:
                        GameContext.ENTITY_SPRITE[id[i]] = new Sprite(new Rect(GameContext.ENTITY_POSITION[id[i]], GameContext.SLIME_WIDTH, GameContext.SLIME_HEIGHT), GameContext.SLIME_TEXTURE_PATH, Shader.BASIC);
                        break;
                }
            }
            GameContext.ENTITY_SPRITE[id[i]].setPosition(GameContext.ENTITY_POSITION[id[i]]);
            switch (GameContext.ENTITY_TYPE[id[i]]) {
                case PLAYER:
                    GameContext.ENTITY_SPRITE[id[i]].flipY(GameContext.ENTITY_FLIP[id[i]], MathJ.pixelToWorld(-9));
                    break;
                case SLIME:
                    GameContext.ENTITY_SPRITE[id[i]].flipY(GameContext.ENTITY_FLIP[id[i]]);
                    break;
            }
            GameContext.ENTITY_SPRITE[id[i]].render();
        }
    }

    private void renderEnvironment() {
        int[] id = data.CHUNKS_ID;
        for (int i = 0; i < id.length; i++) {
            if (GameContext.CHUNKS_SPRITE[id[i]] == null) {
                GameContext.CHUNKS_SPRITE[id[i]] = new Sprite(new Rect(GameContext.CHUNKS_POSITION[id[i]], GameContext.CHUNKS_WIDTH, GameContext.CHUNKS_HEIGHT), GameContext.GROUND_TEXTURE_PATH, Shader.BASIC);
            }
            GameContext.CHUNKS_SPRITE[id[i]].render();
        }
    }

    private void renderCamera() {
        Rect fov = data.cameraFov;
        Vector3f camPos = fov.getCenter();
        pr_matrix = Matrix4f.orthographic((fov.width / -2f) + camPos.x, (fov.width / 2f) + camPos.x, (fov.height/ -2f) + camPos.y, (fov.height / 2f) + camPos.y, -1f + camPos.z, 1f + camPos.z);
        Shader.BASIC.setUniformMatrix4f("pr_matrix", pr_matrix);
    }
}
