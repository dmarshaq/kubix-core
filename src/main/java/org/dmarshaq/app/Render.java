package org.dmarshaq.app;

import org.dmarshaq.graphics.Shader;
import org.dmarshaq.graphics.Sprite;
import org.dmarshaq.graphics.Texture;
import org.dmarshaq.graphics.font.Font;
import org.dmarshaq.input.Input;
import org.dmarshaq.mathj.*;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;

import static org.dmarshaq.app.GameContext.SCREEN_HEIGHT;
import static org.dmarshaq.app.GameContext.SCREEN_WIDTH;
import static org.dmarshaq.app.GameContext.Layer;
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
        window = glfwCreateWindow(SCREEN_WIDTH, SCREEN_HEIGHT, GameContext.TITLE, NULL, NULL);
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
                    (vidmode.width() - SCREEN_WIDTH) / 2,
                    (vidmode.height() - SCREEN_HEIGHT) / 2
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
        Shader.BASIC.disable();
        Shader.BASIC_UI.setUniformMatrix4f("pr_matrix", Matrix4f.orthographic(0f, SCREEN_WIDTH, 0f, SCREEN_HEIGHT, -1f, 1f));
        Shader.BASIC_UI.setUniform1i("tex", 1);
        Shader.BASIC_UI.disable();
        // Fonts
        Font.loadFonts();

        // UI
//        GameContext.GAME_UI.addText("Kubix Engine v0.1.5", Font.BASIC_PUP_WHITE, new Vector3f(0f, SCREEN_HEIGHT, Layer.UI));
//        GameContext.GAME_UI.addText("\"Balls\"", Font.BASIC_PUP_WHITE, new Vector3f(SCREEN_WIDTH / 2f, SCREEN_HEIGHT / 2f, Layer.UI));
    }

    private void renderSnapshot() {
        renderCamera();
        renderEntities();
        renderEnvironment();
        renderUI();
    }

    private void renderEntities() {
        for (int i = 0; i < data.ENTITY_ID.length; i++) {

            /*
             *   ------------------------------------------------------------------------------
             *   ALL ENTITIES: If sprite for the entity wasn't yet created,
             *   creates it and assigns for the entity.
             *
             *   IMPORTANT:
             *   * Render doesn't change actual values on any entity, the only values it is allowed
             *   * to change are ENTITY_SPRITE values because sprite is only used to render and store
             *   * render related data. Plus sprite is never updated in actual update, because
             *   * update needs to be fully ended in order sent actual snapshot data with all
             *   * entities to be rendered so only specific sprites will be rendered or created.
             *   ------------------------------------------------------------------------------
             * */
            int id = data.ENTITY_ID[i];

            if (GameContext.ENTITY_SPRITE[id] == null) {
                switch (data.ENTITY_TYPE[i]) {
                    case PLAYER:
                        GameContext.ENTITY_SPRITE[id] = new Sprite(new RectComponent(0f, 0f, MathJ.pixelToWorld(GameContext.Player.PLAYER_PIX_WIDTH), MathJ.pixelToWorld(GameContext.Player.PLAYER_PIX_HEIGHT), data.ENTITY_TRANSFORM[i]), GameContext.Player.PLAYER_ANIMATIONS, Shader.BASIC);
                        break;
                    case SLIME:
                        GameContext.ENTITY_SPRITE[id] = new Sprite(new RectComponent(0f, 0f, GameContext.Slime.SLIME_WIDTH, GameContext.Slime.SLIME_HEIGHT, data.ENTITY_TRANSFORM[i]), Texture.SLIME_TEXTURE, Shader.BASIC);
                        break;
                }
            }
            else {
                GameContext.ENTITY_SPRITE[id].setTransform(data.ENTITY_TRANSFORM[i]);
            }

            /*
             *   ------------------------------------------------------------------------------
             *   ALL ENTITIES: Manages animation if sprite has one.
             *   ------------------------------------------------------------------------------
             * */
            if (GameContext.ENTITY_SPRITE[id].hasAnim()) {

                // Checks if new animation was ordered to play, if true, sets this animation to entity's sprite.
                if (GameContext.ENTITY_SPRITE[id].getCurrentAnim() != data.ENTITY_CURRENT_ANIM[i]) {
                    GameContext.ENTITY_SPRITE[id].setCurrentAnim(data.ENTITY_CURRENT_ANIM[i]);
                }


                // Easing will dictate at what time each frame occurs based on easing function.
                MathJ.Easing easing = MathJ.Easing.LINEAR;
                switch (GameContext.ENTITY_SPRITE[id].getCurrentAnim()) {
                    case PLAYER_IDLE -> easing = MathJ.Easing.LINEAR;
                    case PLAYER_RUN -> easing = MathJ.Easing.LINEAR;
                }
                GameContext.ENTITY_SPRITE[id].cycleCurrentAnim(easing);
            }
            /*
             *   ------------------------------------------------------------------------------
             *   ALL ENTITIES: Following switch identifies entity and prepares its render
             *   properties based on it's type.
             *   ------------------------------------------------------------------------------
             * */
            switch (data.ENTITY_TYPE[i]) {

                /*
                 *   ------------------------------------------------------------------------------
                 *   PLAYER CASE
                 *   ------------------------------------------------------------------------------
                 * */
                case PLAYER:
                    GameContext.ENTITY_SPRITE[id].flipY(data.ENTITY_FLIP[i], -MathJ.pixelToWorld(8));

                    break;

                /*
                 *   ------------------------------------------------------------------------------
                 *   SLIME CASE
                 *   ------------------------------------------------------------------------------
                 * */
                case SLIME:
                    GameContext.ENTITY_SPRITE[id].flipY(data.ENTITY_FLIP[i]);
                    break;

            }
            GameContext.ENTITY_SPRITE[id].render();
        }
    }
    /*
     *   ------------------------------------------------------------------------------
     *   UI: Render of each ui element, accomplished by simply calling ui object's
     *   update method.
     *   ------------------------------------------------------------------------------
     * */
    private void renderUI() {
        GameContext.GAME_UI.render();
    }
    /*
     *   ------------------------------------------------------------------------------
     *   ENVIRONMENT: Simple render of each Chunk sprites, also makes sure that newly
     *   loaded chunks get their new Sprites.
     *   ------------------------------------------------------------------------------
     * */
    private void renderEnvironment() {
        int[] id = data.CHUNKS_ID;
        for (int i : id) {
            if (GameContext.CHUNKS_SPRITE[i] == null) {
                GameContext.CHUNKS_SPRITE[i] = new Sprite(new RectComponent(0f, 0f, GameContext.CHUNKS_WIDTH, GameContext.CHUNKS_HEIGHT, Matrix4f.translate(GameContext.CHUNKS_POSITION[i])), Texture.GROUND_TEXTURE, Shader.BASIC);
            }
            GameContext.CHUNKS_SPRITE[i].render();
        }
    }
    /*
     *   ------------------------------------------------------------------------------
     *   CAMERA: this function takes camera's property from last update snapshot and
     *   assigns it to pr_matrix which applies to Sprites with Basic shader.
     *   ------------------------------------------------------------------------------
     * */
    private void renderCamera() {
        Rect fov = data.cameraFov;
        Vector2f camPos = fov.getCenter();
        pr_matrix = Matrix4f.orthographic((fov.width / -2f) + camPos.x, (fov.width / 2f) + camPos.x, (fov.height/ -2f) + camPos.y, (fov.height / 2f) + camPos.y, -1f, 1f);
        Shader.BASIC.setUniformMatrix4f("pr_matrix", pr_matrix);
        Shader.BASIC.disable();
    }
}
