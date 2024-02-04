package org.dmarshaq.app;

import org.dmarshaq.graphics.*;
import org.dmarshaq.input.CursorInputPos;
import org.dmarshaq.input.Input;
import org.dmarshaq.input.KeyCode;
import org.dmarshaq.mathj.*;
import org.dmarshaq.utils.BufferUtils;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALCCapabilities;
import org.lwjgl.openal.ALCapabilities;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.awt.*;
import java.nio.IntBuffer;
import java.util.Arrays;

import static org.dmarshaq.app.Context.*;
import static org.dmarshaq.app.Render.BatchRenderer.*;
import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.openal.AL10.*;
import static org.lwjgl.openal.ALC10.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.*;

public abstract class Render implements Runnable {

    private Snapshot data;
    private long window, audioContext, audioDevice;
    private Matrix4f pr_matrix;
    private Update updateTask;
    private Context context;
    private static CursorInputPos cursorInputPos;
    private static int screenWidth, screenHeight;
    private static float aspectRatio;

    public void setUpdateTask(Update updateTask) {
        this.updateTask = updateTask;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    // Render data
    public static class BatchRenderer {
        public static int drawcalls = 0;
        public static int quadsRenderedInBatch = 0;
        public static final int FLOATS_PER_VERTEX = 10;
        public static final int VERTICES_PER_SPRITE = 4;
        public static final int SPRITE_STRIDE_IN_ARRAY = FLOATS_PER_VERTEX * VERTICES_PER_SPRITE; // floats allocated per each sprite (quad its storing)

        public static final float[] VERTICES = new float[getMaxQuadsPerBatch() * SPRITE_STRIDE_IN_ARRAY]; // 3D array compacted in single dimension array
        public static final int[] INDICES = new int[getMaxQuadsPerBatch() * 6];
        public static final int[] TEXTURES_USED = new int[32]; // 32 textures where texture at index 0 is a reserved space for color only
        public static int vao, vbo, ibo;
        public static Shader currentShader;
    }

    @Override
    public void run() {
        init();
        GL.createCapabilities();

        renderInit();

        KeyCode.initKeyCodes();

        Thread update = new Thread(updateTask);
        update.start();


        while(Context.isRunning()) {
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

        // Destroy the audio Context
        alcDestroyContext(audioContext);
        alcCloseDevice(audioDevice);

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
        glfwWindowHint(GLFW_RESIZABLE, isFullScreen() ? GLFW_FALSE : GLFW_TRUE); // the window will be resizable

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        // Create the window
        if (isFullScreen()) {
            screenWidth = (int) screenSize.getWidth();
            screenHeight = (int) screenSize.getHeight();
        }
        else {
            screenWidth = 1280;
            screenHeight = 720;

        }
        aspectRatio = (float) screenWidth / screenHeight;
        window = glfwCreateWindow(screenWidth, screenHeight, getTitle(), isFullScreen() ? glfwGetPrimaryMonitor() : 0, 0);

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

//            // Center the window
            if (!isFullScreen()) {
                glfwSetWindowPos(
                        window,
                        (vidmode.width() - screenWidth) / 2,
                        (vidmode.height() - screenHeight) / 2
                );
            }
        } // the stack frame is popped automatically

        glfwSetKeyCallback(window, new Input());

        // Make the OpenGL context current
        glfwMakeContextCurrent(window);

        // Enable v-sync
        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(window);

        // Initialize the audio device
        String defaultDeviceName = alcGetString(0, ALC_DEFAULT_DEVICE_SPECIFIER);
        audioDevice = alcOpenDevice(defaultDeviceName);

        int[] attributes = {0};
        audioContext = alcCreateContext(audioDevice, attributes);
        alcMakeContextCurrent(audioContext);

        ALCCapabilities alcCapabilities = ALC.createCapabilities(audioDevice);
        ALCapabilities alCapabilities = AL.createCapabilities(alcCapabilities);

        if (!alCapabilities.OpenAL10) {
            System.out.println("Audio library not supported.");
        }

        // Set call backs
        cursorInputPos = new CursorInputPos();
        glfwSetCursorPosCallback(window, cursorInputPos);

        glfwSetWindowSizeCallback(window, new GLFWWindowSizeCallback(){
            @Override
            public void invoke(long window, int width, int height){
//                int newWidth = 0;
//                int newHeight = 0;
//                int diffrenceW = 0;
//                int diffrenceH = 0;
//
//
//                if (height > width || width - (int) (height * aspectRatio) < 0) {
//                    newHeight = (int) (width / aspectRatio);
//                    diffrenceH = height - newHeight;
//                    newWidth = width;
//                }
//                else if (height < width) {
//                    newWidth = (int) (height * aspectRatio);
//                    diffrenceW = width - newWidth;
//                    newHeight = height;
//                }

//                glViewport(diffrenceW / 2, diffrenceH / 2, newWidth, newHeight);

                screenWidth = width;
                screenHeight = height;
                aspectRatio = (float) screenWidth / screenHeight;
                glViewport(0, 0, screenWidth, screenHeight);
                Camera cam = getMainCamera();
                if (cam != null) {
                    float unitWidth = getMinScreenUnitWidth();
                    float unitHeight = getMinScreenUnitHeight();
                    cam.setCameraFov(Math.max((float) width / getUnitSize(), unitWidth), Math.max((float) height / getUnitSize(), unitHeight));
                }
                else {
                    System.out.println("No Main Camera found.");
                }
            }
        });
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

        // Setup audio
        alListener3f(AL_POSITION, 0.0f, 0.0f, 0.0f);
        alListener3f(AL_VELOCITY, 0.0f, 0.0f, 0.0f);

        // Setup textures
        context.loadTextures();

        int[] samplers = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11,
                12, 13, 14, 15, 16, 17, 18, 19, 20, 21,
                22, 23, 24, 25, 26, 27, 28, 29, 30, 31 };

        // Setup shader
        context.loadShaders();

        Shader.loadEngineShaders();
        pr_matrix = Matrix4f.orthographic(-2f, 2f, -1.5f, 1.5f, -1f, 1f); // basically camera matrix

        Shader.BASIC.setUniformMatrix4f("pr_matrix", pr_matrix);
        Shader.BASIC.setUniform1iv("u_Textures", samplers);
        Shader.BASIC.setUniformMatrix4f("ml_matrix", Matrix4f.identity());
        Shader.BASIC.disable();

        // Setup fonts
        context.loadFonts();

        // Build indices
        for (int i = 0; i < getMaxQuadsPerBatch(); i++) {
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




    }

    private void renderSnapshot() {
        modifyShaders();
        loadCameraMatrix();

        SpriteDTO[] spriteDTOArray = data.getSpriteDataArray();

        // BATCHES LOOP
        int lastBatchIndexStopped = 0;
        while (lastBatchIndexStopped < spriteDTOArray.length) {
            // Vertices loading
            lastBatchIndexStopped = nextBatch(lastBatchIndexStopped, spriteDTOArray);
            // Enabling shader
            currentShader.enable();
            // Textures loading
            loadTexturesUsedIntoSlots();
            // Binding dynamic draw vertex buffer
            glBindBuffer(GL_ARRAY_BUFFER, vbo);
            // Actually loading vertices into graphics memory
            glBufferSubData(GL_ARRAY_BUFFER, 0, VERTICES);
            // Drawing
//            System.out.println("Quads rendered in Batch: " + quadsRenderedInBatch);
            glDrawElements(GL_TRIANGLES, quadsRenderedInBatch * 6, GL_UNSIGNED_INT, 0);
            drawcalls++;
            // Cleaning up
            flush();
        }

//        System.out.println("Draw calls per render: "  + drawcalls);
        drawcalls = 0;
    }

    private int nextBatch(int startIndex, SpriteDTO[] spriteDTOArray) {
        int spriteGlobalIndex = 0;
        int nextBatchStartIndex = 0;
        int offsetToStartIndex = 0;
        int currentLayerIndex = -1;

        while (quadsRenderedInBatch < getMaxQuadsPerBatch()) {
            spriteGlobalIndex = startIndex + offsetToStartIndex;
            offsetToStartIndex++;

            if (spriteGlobalIndex >= spriteDTOArray.length) {
                return spriteDTOArray.length;
            }

            SpriteDTO spriteDTO = spriteDTOArray[spriteGlobalIndex];

            // check if null, if true skip that element
            if (spriteDTO == null) {
                continue;
            }
            // layer check logic
            else if (currentLayerIndex != -1 && currentLayerIndex != spriteDTO.getLayer().getIndex() && nextBatchStartIndex != 0) {
                break;
            }
            // shader check logic
            else if (quadsRenderedInBatch != 0 && currentShader != spriteDTO.getShader()) {
                nextBatchStartIndex = spriteGlobalIndex;
                break;
            }
            currentShader = spriteDTO.getShader();
            currentLayerIndex = spriteDTO.getLayer().getIndex();

            // manage texture
            int slot = 0;
            Texture texture = spriteDTO.getTexture();
            if (texture != null) {
                slot = allocateTextureSlot( texture.getID() );
                if (slot == -1) {
                    if (nextBatchStartIndex == 0) {
                        nextBatchStartIndex = spriteGlobalIndex;
                    }
                    continue;
                }
            }

            // if every check before is good, load spriteDTO data into actual vertices used
            loadSpriteVertices(quadsRenderedInBatch, spriteDTOArray[spriteGlobalIndex], slot);
            quadsRenderedInBatch++;

            // little clean up, plus it will ensure our nextBatch doesn't go twice for any spriteDTO
            spriteDTOArray[spriteGlobalIndex] = null;

        }

        if (nextBatchStartIndex == 0) {
            nextBatchStartIndex = spriteGlobalIndex + 1;
        }

        return nextBatchStartIndex;
    }

    private void loadSpriteVertices(int spriteIndex, SpriteDTO spriteDTO, int textureSlot) {
        int vertexArraySpriteStart = spriteIndex * SPRITE_STRIDE_IN_ARRAY;
        int offset;
        // ORDER: BOTTOM LEFT -> BOTTOM RIGHT -> TOP LEFT -> TOP RIGHT

        Vector4f color = spriteDTO.getColor();
        if (color == null) {
            color = new Vector4f(1.0f, 1.0f, 1.0f, 1.0f);
        }

        SubTexture subTexture = spriteDTO.getSubTexture();
        if (subTexture == null) {
            subTexture = new SubTexture(0, 0, 1, 1);
        }

        Vector2f position = spriteDTO.getTransform().multiply(new Vector2f(), 1f);
        float order = spriteDTO.getLayer().zOrder();

        Vector2f localXVector;
        Vector2f localYVector;

        if (spriteDTO.getTexture() != null) {
            localXVector = new Vector2f( MathJ.pixelToWorld( (int) (spriteDTO.getTexture().getWidth() * subTexture.width()) ), 0);
            localYVector = new Vector2f( 0, MathJ.pixelToWorld( (int) (spriteDTO.getTexture().getHeight() * subTexture.height()) ) );
        }
        else {
            localXVector = new Vector2f(1, 0);
            localYVector = new Vector2f(0, 1);
        }

        localXVector = spriteDTO.getTransform().multiply(localXVector, 0);
        localYVector = spriteDTO.getTransform().multiply(localYVector, 0);

        // BOTTOM LEFT
        offset = 0;
        VERTICES[vertexArraySpriteStart + 0 + offset] = position.x;
        VERTICES[vertexArraySpriteStart + 1 + offset] = position.y;
        VERTICES[vertexArraySpriteStart + 2 + offset] = order;

        VERTICES[vertexArraySpriteStart + 3 + offset] = color.x;
        VERTICES[vertexArraySpriteStart + 4 + offset] = color.y;
        VERTICES[vertexArraySpriteStart + 5 + offset] = color.z;
        VERTICES[vertexArraySpriteStart + 6 + offset] = color.w;

        VERTICES[vertexArraySpriteStart + 7 + offset] = subTexture.x();
        VERTICES[vertexArraySpriteStart + 8 + offset] = subTexture.y();

        VERTICES[vertexArraySpriteStart + 9 + offset] = textureSlot;

        // BOTTOM RIGHT
        offset += 10;
        VERTICES[vertexArraySpriteStart + 0 + offset] = position.x + localXVector.x;
        VERTICES[vertexArraySpriteStart + 1 + offset] = position.y + localXVector.y;
        VERTICES[vertexArraySpriteStart + 2 + offset] = order;

        VERTICES[vertexArraySpriteStart + 3 + offset] = color.x;
        VERTICES[vertexArraySpriteStart + 4 + offset] = color.y;
        VERTICES[vertexArraySpriteStart + 5 + offset] = color.z;
        VERTICES[vertexArraySpriteStart + 6 + offset] = color.w;

        VERTICES[vertexArraySpriteStart + 7 + offset] = subTexture.x() + subTexture.width();
        VERTICES[vertexArraySpriteStart + 8 + offset] = subTexture.y();

        VERTICES[vertexArraySpriteStart + 9 + offset] = textureSlot;

        // TOP LEFT
        offset += 10;
        VERTICES[vertexArraySpriteStart + 0 + offset] = position.x + localYVector.x;
        VERTICES[vertexArraySpriteStart + 1 + offset] = position.y + localYVector.y;
        VERTICES[vertexArraySpriteStart + 2 + offset] = order;

        VERTICES[vertexArraySpriteStart + 3 + offset] = color.x;
        VERTICES[vertexArraySpriteStart + 4 + offset] = color.y;
        VERTICES[vertexArraySpriteStart + 5 + offset] = color.z;
        VERTICES[vertexArraySpriteStart + 6 + offset] = color.w;

        VERTICES[vertexArraySpriteStart + 7 + offset] = subTexture.x();
        VERTICES[vertexArraySpriteStart + 8 + offset] = subTexture.y() + subTexture.height();

        VERTICES[vertexArraySpriteStart + 9 + offset] = textureSlot;

        // TOP RIGHT
        offset += 10;
        VERTICES[vertexArraySpriteStart + 0 + offset] = position.x + localXVector.x + localYVector.x;
        VERTICES[vertexArraySpriteStart + 1 + offset] = position.y + localXVector.y + localYVector.y;
        VERTICES[vertexArraySpriteStart + 2 + offset] = order;

        VERTICES[vertexArraySpriteStart + 3 + offset] = color.x;
        VERTICES[vertexArraySpriteStart + 4 + offset] = color.y;
        VERTICES[vertexArraySpriteStart + 5 + offset] = color.z;
        VERTICES[vertexArraySpriteStart + 6 + offset] = color.w;

        VERTICES[vertexArraySpriteStart + 7 + offset] = subTexture.x() + subTexture.width();
        VERTICES[vertexArraySpriteStart + 8 + offset] = subTexture.y() + subTexture.height();

        VERTICES[vertexArraySpriteStart + 9 + offset] = textureSlot;

    }

    private int allocateTextureSlot(int textureID) {
        for (int i = 1; i < TEXTURES_USED.length; i++) {
            if (textureID == TEXTURES_USED[i]) {
                // Use existing one for sprite
                return i;
            }
            if (TEXTURES_USED[i] == 0) {
                // Allocate another texture slot for sprite
                TEXTURES_USED[i] = textureID;
                return i;
            }
            if (i == TEXTURES_USED.length - 1) {
                // Not enough space skip sprite
                return -1;
            }
        }
        // Failed in allocation
        System.out.println("Failed in allocation of texture slot.");
        return -1;
    }

    private void loadTexturesUsedIntoSlots() {
        for (int i = 0; i < TEXTURES_USED.length; i++) {
            glActiveTexture(GL_TEXTURE0 + i);
            glBindTexture(GL_TEXTURE_2D, TEXTURES_USED[i]);
        }
    }

    private void flush() {
        unbindUsedTextures();
        quadsRenderedInBatch = 0;
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        currentShader.disable();
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
        pr_matrix = data.getCamera().projectionMatrix();
        Shader.BASIC.setUniformMatrix4f("pr_matrix", pr_matrix);
        Shader.BASIC.disable();
    }

    protected abstract void modifyShaders();

    public static float getAspectRatio() {
        return aspectRatio;
    }

    public static float getScreenWidth() {
        return screenWidth;
    }

    public static float getScreenHeight() {
        return screenHeight;
    }

    public static Vector2i getCursorPos() {
        return new Vector2i(cursorInputPos.x(), cursorInputPos.y());
    }
}
