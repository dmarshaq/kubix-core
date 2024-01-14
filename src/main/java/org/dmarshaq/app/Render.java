package org.dmarshaq.app;

import org.dmarshaq.graphics.Shader;
import org.dmarshaq.graphics.Sprite;
import org.dmarshaq.graphics.Texture;
import org.dmarshaq.input.Input;
import org.dmarshaq.mathj.*;
import org.dmarshaq.utils.BufferUtils;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;

import static org.dmarshaq.app.GameContext.Render.*;
import static org.dmarshaq.app.GameContext.SCREEN_HEIGHT;
import static org.dmarshaq.app.GameContext.SCREEN_WIDTH;
import static org.dmarshaq.app.Render.Renderer.*;
import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.*;

public class Render implements Runnable {

    private Snapshot data;
    private long window;
    private Matrix4f pr_matrix;
    // Render data
    static class Renderer {
        public static int drawcalls = 0;
        public static final int FLOATS_PER_VERTEX = 10;
        public static final int VERTICES_PER_SPRITE = 4;
        public static final int SPRITE_STRIDE_IN_ARRAY = FLOATS_PER_VERTEX * VERTICES_PER_SPRITE; // floats allocated per each sprite (quad its storing)

        public static final float[] VERTICES = new float[MAX_QUADS_PER_BATCH * SPRITE_STRIDE_IN_ARRAY]; // 3D array compacted in single dimension array
        public static final int[] INDICES = new int[MAX_QUADS_PER_BATCH * 6];
        public static final int[] TEXTURES_USED = new int[32];
        public static int vao, vbo, ibo;
    }

    @Override
    public void run() {
        init();
        GL.createCapabilities();

        renderInit();

        GameContext gameContext = new GameContext();

        Update updateTask = new Update(gameContext, this);
        Thread update = new Thread(updateTask);
        update.start();


        while(GameContext.isRunning()) {
            // do some render
            synchronized (this) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            render();
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

    public void init() {

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
        glClearColor(0.3f, 0.3f, 0.3f, 1.0f);

        // Setup textures
        Texture.loadTextures();

        // Setup shader
        Shader.loadAll();

        pr_matrix = Matrix4f.orthographic(-2f, 2f, -1.5f, 1.5f, -1f, 1f); // basically camera matrix
        Shader.BASIC.setUniformMatrix4f("pr_matrix", pr_matrix);

        int[] samplers = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11,
                12, 13, 14, 15, 16, 17, 18, 19, 20, 21,
                22, 23, 24, 25, 26, 27, 28, 29, 30, 31 };
        Shader.BASIC.setUniform1iv("u_Textures", samplers);

        // Build indices
        for (int i = 0; i < MAX_QUADS_PER_BATCH; i++) {
            for (int j = 0; j < 6; j++) {
                int val = j;
                if (j > 2 && j < 5) {
                    val--;
                }
                if (j == 5) {
                    val = 1;
                }
                INDICES[i * 6 + j] = (i * 4 + val);
            }
        }

        // vertex buffer creation
        vao = glGenVertexArrays();
        glBindVertexArray(vao);

        vbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, VERTICES.length * 4, GL_DYNAMIC_DRAW);

        glVertexAttribPointer(Shader.VERTEX_ATTRIBUTE, 3, GL_FLOAT, false, 10 * 4, 0 * 4);
        glEnableVertexAttribArray(Shader.VERTEX_ATTRIBUTE);

        glVertexAttribPointer(Shader.COLOR_ATTRIBUTE, 4, GL_FLOAT, false, 10 * 4, 3 * 4);
        glEnableVertexAttribArray(Shader.COLOR_ATTRIBUTE);

        glVertexAttribPointer(Shader.TCOORDS_ATTRIBUTE, 2, GL_FLOAT, false, 10 * 4, 7 * 4);
        glEnableVertexAttribArray(Shader.TCOORDS_ATTRIBUTE);

        glVertexAttribPointer(Shader.TINDEX_ATTRIBUTE, 1, GL_FLOAT, false, 10 * 4, 9 * 4);
        glEnableVertexAttribArray(Shader.TINDEX_ATTRIBUTE);

        ibo = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, BufferUtils.createIntBuffer(INDICES), GL_STATIC_DRAW);

        // Do not unbind other static buffers, buffers would likely to be erased by graphics
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        Shader.BASIC.setUniformMatrix4f("ml_matrix", Matrix4f.identity());
        Shader.BASIC.disable();


    }

    private void renderSnapshot() {
        loadCameraMatrix();

        Sprite[] spriteArray = data.getSpriteDataArray();

        // BATCHES LOOP
        int lastBatchIndexStopped = 0;
        while (lastBatchIndexStopped < spriteArray.length) {
            // Vertices loading
            lastBatchIndexStopped = nextBatch(lastBatchIndexStopped, spriteArray);
            // Textures loading
            loadTexturesUsedIntoSlots();
            // Binding dynamic draw vertex buffer
            glBindBuffer(GL_ARRAY_BUFFER, vbo);
            // Enabling shader
            Shader.BASIC.enable();
            // Actually loading vertices into graphics memory
            glBufferSubData(GL_ARRAY_BUFFER, 0, VERTICES);
            // Drawing
            // TODO select precise number of indices to render, not render every indices each time
            glDrawElements(GL_TRIANGLES, INDICES.length, GL_UNSIGNED_INT, 0);
            drawcalls++;
            // Cleaning up
            flush();
        }

        System.out.println("Drawcalls per render: "  + drawcalls);
        drawcalls = 0;
    }


    private int nextBatch(int startIndex, Sprite[] spriteArray) {
        int spriteGlobalIndex = 0;
        int nextBatchStartIndex = 0;
        int spritesLoaded = 0;
        int offsetToStartIndex = 0;
        int currentLayerIndex = -1;

        while (spritesLoaded < MAX_QUADS_PER_BATCH) {
            spriteGlobalIndex = startIndex + offsetToStartIndex;
            offsetToStartIndex++;

            if (spriteGlobalIndex >= spriteArray.length) {
                return spriteArray.length;
            }

            Sprite sprite = spriteArray[spriteGlobalIndex];

            // check if null, if true skip that element
            if (sprite == null) {
                continue;
            }

            // layer check logic
            else if (currentLayerIndex != -1 && currentLayerIndex != sprite.getLayer().getIndex() && nextBatchStartIndex != 0) {
                break;
            }
            currentLayerIndex = sprite.getLayer().getIndex();

            // manage texture
            if (!tryAllocateTextureSlot( sprite.getTexture().getID() )) {
                if (nextBatchStartIndex == 0) {
                    nextBatchStartIndex = spriteGlobalIndex;
                }
                continue;
            }

            // if every check before is good, load sprite data into actual vertices used
            loadSpriteVertices(spritesLoaded, spriteArray[spriteGlobalIndex]);
            spritesLoaded++;

            // little clean up, plus it will ensure our nextBatch doesn't go twice for any sprite
            spriteArray[spriteGlobalIndex] = null;

        }

        if (nextBatchStartIndex == 0) {
            nextBatchStartIndex = spriteGlobalIndex;
        }

        return nextBatchStartIndex;
    }

    private void loadSpriteVertices(int spriteIndex, Sprite sprite) {
        int vertexArraySpriteStart = spriteIndex * SPRITE_STRIDE_IN_ARRAY;
        int offset;
        // ORDER: BOTTOM LEFT -> BOTTOM RIGHT -> TOP LEFT -> TOP RIGHT

        Vector3f position = Matrix4f.getTransformPosition(sprite.getTransform());

        // BOTTOM LEFT
        offset = 0;
        VERTICES[vertexArraySpriteStart + 0 + offset] = position.x;
        VERTICES[vertexArraySpriteStart + 1 + offset] = position.y;
        VERTICES[vertexArraySpriteStart + 2 + offset] = position.z;

        VERTICES[vertexArraySpriteStart + 3 + offset] = 1.0f;
        VERTICES[vertexArraySpriteStart + 4 + offset] = 1.0f;
        VERTICES[vertexArraySpriteStart + 5 + offset] = 1.0f;
        VERTICES[vertexArraySpriteStart + 6 + offset] = 1.0f;

        VERTICES[vertexArraySpriteStart + 7 + offset] = 0.0f;
        VERTICES[vertexArraySpriteStart + 8 + offset] = 0.0f;

        VERTICES[vertexArraySpriteStart + 9 + offset] = 0.0f;

        // BOTTOM RIGHT
        offset += 10;
        VERTICES[vertexArraySpriteStart + 0 + offset] = position.x + MathJ.pixelToWorld(sprite.getTexture().getWidth());
        VERTICES[vertexArraySpriteStart + 1 + offset] = position.y;
        VERTICES[vertexArraySpriteStart + 2 + offset] = position.z;

        VERTICES[vertexArraySpriteStart + 3 + offset] = 1.0f;
        VERTICES[vertexArraySpriteStart + 4 + offset] = 1.0f;
        VERTICES[vertexArraySpriteStart + 5 + offset] = 1.0f;
        VERTICES[vertexArraySpriteStart + 6 + offset] = 1.0f;

        VERTICES[vertexArraySpriteStart + 7 + offset] = 1.0f;
        VERTICES[vertexArraySpriteStart + 8 + offset] = 0.0f;

        VERTICES[vertexArraySpriteStart + 9 + offset] = 0.0f;

        // TOP LEFT
        offset += 10;
        VERTICES[vertexArraySpriteStart + 0 + offset] = position.x;
        VERTICES[vertexArraySpriteStart + 1 + offset] = position.y + MathJ.pixelToWorld(sprite.getTexture().getHeight());
        VERTICES[vertexArraySpriteStart + 2 + offset] = position.z;

        VERTICES[vertexArraySpriteStart + 3 + offset] = 1.0f;
        VERTICES[vertexArraySpriteStart + 4 + offset] = 1.0f;
        VERTICES[vertexArraySpriteStart + 5 + offset] = 1.0f;
        VERTICES[vertexArraySpriteStart + 6 + offset] = 1.0f;

        VERTICES[vertexArraySpriteStart + 7 + offset] = 0.0f;
        VERTICES[vertexArraySpriteStart + 8 + offset] = 1.0f;

        VERTICES[vertexArraySpriteStart + 9 + offset] = 0.0f;

        // TOP RIGHT
        offset += 10;
        VERTICES[vertexArraySpriteStart + 0 + offset] = position.x + MathJ.pixelToWorld(sprite.getTexture().getWidth());
        VERTICES[vertexArraySpriteStart + 1 + offset] = position.y + MathJ.pixelToWorld(sprite.getTexture().getHeight());
        VERTICES[vertexArraySpriteStart + 2 + offset] = position.z;

        VERTICES[vertexArraySpriteStart + 3 + offset] = 1.0f;
        VERTICES[vertexArraySpriteStart + 4 + offset] = 1.0f;
        VERTICES[vertexArraySpriteStart + 5 + offset] = 1.0f;
        VERTICES[vertexArraySpriteStart + 6 + offset] = 1.0f;

        VERTICES[vertexArraySpriteStart + 7 + offset] = 1.0f;
        VERTICES[vertexArraySpriteStart + 8 + offset] = 1.0f;

        VERTICES[vertexArraySpriteStart + 9 + offset] = 0.0f;


    }

    private boolean tryAllocateTextureSlot(int textureID) {
        for (int i = 0; i < TEXTURES_USED.length; i++) {
            if (textureID == TEXTURES_USED[i]) {
                // Use existing one for sprite
                return true;
            }
            if (TEXTURES_USED[i] == 0) {
                // Allocate another texture slot for sprite
                TEXTURES_USED[i] = textureID;
                return true;
            }
            if (i == TEXTURES_USED.length - 1) {
                // Not enough space skip sprite
                return false;
            }
        }
        // Failed in allocation
        System.out.println("Failed in allocation of texture slot.");
        return false;
    }

    private void loadTexturesUsedIntoSlots() {
        for (int i = 0; i < TEXTURES_USED.length; i++) {
            glActiveTexture(GL_TEXTURE0 + i);
            glBindTexture(GL_TEXTURE_2D, TEXTURES_USED[i]);
        }
    }

    private void flush() {
        unbindUsedTextures();

        glBindBuffer(GL_ARRAY_BUFFER, 0);
        Shader.BASIC.disable();
    }

    private void unbindUsedTextures() {
        for (int i = 0; i < TEXTURES_USED.length; i++) {
            glActiveTexture(GL_TEXTURE0 + i);
            glBindTexture(GL_TEXTURE_2D, 0);
            TEXTURES_USED[i] = 0;
        }
    }

    private void loadCameraMatrix() {
        /*
         *   ------------------------------------------------------------------------------
         *   CAMERA: this function takes camera's property from last update snapshot and
         *   assigns it to pr_matrix which applies to Sprites with Basic shader.
         *   ------------------------------------------------------------------------------
         * */

        Rect fov = data.cameraFov;
        Vector2f camPos = fov.getCenter();
        pr_matrix = Matrix4f.orthographic((fov.width / -2f) + camPos.x, (fov.width / 2f) + camPos.x, (fov.height/ -2f) + camPos.y, (fov.height / 2f) + camPos.y, -1f, 1f);
        Shader.BASIC.setUniformMatrix4f("pr_matrix", pr_matrix);
        Shader.BASIC.disable();
    }
}
