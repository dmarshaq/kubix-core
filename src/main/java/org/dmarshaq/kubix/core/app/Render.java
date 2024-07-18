package org.dmarshaq.kubix.core.app;

import org.dmarshaq.kubix.core.graphic.*;
import org.dmarshaq.kubix.core.graphic.Window;
import org.dmarshaq.kubix.core.input.Input;
import org.dmarshaq.kubix.core.input.MouseInput;
import org.dmarshaq.kubix.core.util.BufferUtils;
import org.dmarshaq.kubix.core.graphic.render.QuadRender;
import org.dmarshaq.kubix.core.graphic.render.Snapshot;
import org.dmarshaq.kubix.math.MathCore;
import org.dmarshaq.kubix.math.matrix.Matrix4x4;
import org.dmarshaq.kubix.math.vector.Vector4;
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

import static org.dmarshaq.kubix.core.app.Context.*;
import static org.dmarshaq.kubix.core.app.Render.BatchRenderer.*;
import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.openal.AL10.*;
import static org.lwjgl.openal.ALC10.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.*;

public abstract class Render implements Runnable {

    private Window window;
    private Snapshot data;
    private long audioContext, audioDevice;
    private Matrix4x4 pr_matrix;
    private Update updateTask;
    private Context context;
    private static MouseInput mouseInput;
    private static int screenWidth, screenHeight;
    private static float aspectRatio;
    private static Color color = new Color(150, 150, 150, 255);

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
    }

    @Override
    public void run() {
        init();
        GL.createCapabilities();

        renderInit();

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
        window.freeCallbacks();
        window.destroyWindow();

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
        // TODO refactor screen data usage.
        // wtf is this
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

        window = new Window(screenWidth, screenHeight, Context.getTitle(), Context.isFullScreen());

        // Get the thread stack and push a new frame
        try ( MemoryStack stack = stackPush() ) {
            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*

            // Get the window size passed to glfwCreateWindow
            glfwGetWindowSize(window.getWindow(), pWidth, pHeight);


            // Get the resolution of the primary monitor
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            // Center the window
            if (!isFullScreen()) {
                glfwSetWindowPos(
                        window.getWindow(),
                        (vidmode.width() - screenWidth) / 2,
                        (vidmode.height() - screenHeight) / 2
                );
            }
        } // the stack frame is popped automatically

        window.setKeyCallback(new Input());
        glfwSetInputMode(window.getWindow(), GLFW_STICKY_KEYS, 1);

        // Make the OpenGL context current
        window.makeContextCurrent();

        // Enable v-sync
        glfwSwapInterval(1);

        // Make the window visible
        window.show();

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
        mouseInput = new MouseInput();
        mouseInput.init(window.getWindow());

        window.setSizeCallback(Window.windowDefaultSizeCallback(window));
    }

    private void render() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        renderSnapshot(); // Render each object in given snapshot from single update frame

        int error = glGetError();
        if (error != GL_NO_ERROR)
            System.out.println(error);

        glfwSwapBuffers(window.getWindow()); // swap the color buffers
        // Poll for window events. The key callback above will only be
        // invoked during this call.
        glfwPollEvents();
    }

    public Window getWindow() {
        return window;
    }

    private void renderInit() {
        // Blending
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        // Background
        Vector4 vector4 = MathCore.vector4(color);
        glClearColor(vector4.x(), vector4.y(), vector4.z(), vector4.w());

        // Setup audio
        alListener3f(AL_POSITION, 0.0f, 0.0f, 0.0f);
        alListener3f(AL_VELOCITY, 0.0f, 0.0f, 0.0f);

        // Load all resources
        context.loadResources();

        //  Texture Samplers
        int[] samplers = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11,
                12, 13, 14, 15, 16, 17, 18, 19, 20, 21,
                22, 23, 24, 25, 26, 27, 28, 29, 30, 31 };

        // Setup shader
        pr_matrix = MathCore.orthographic(-2f, 2f, -1.5f, 1.5f, -1f, 1f); // basically camera matrix


        Context.shaders().get("basic").setUniformMatrix4x4("pr_matrix", pr_matrix);
        Context.shaders().get("basic").setUniform1iv("u_Textures", samplers);
        Context.shaders().get("basic").setUniformMatrix4x4("ml_matrix", new Matrix4x4());
        Context.shaders().get("basic").disable();

        // Setup fonts
//        context.loadFonts(); TODO: Fonts?

        // Build indices
        for (int i = 0; i < getMaxQuadsPerBatch(); i++) {
            for (int j = 0; j < 6; j++) {
                int val = j;
                if (j > 2 && j < 5) {
                    val--;
                }
                else if (j == 5) {
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
        loadCameraMatrix();

        QuadRender.renderQuadsInBatch(data.getQuadRenderArray(), getMaxQuadsPerBatch());
    }


    private void loadCameraMatrix() {
        pr_matrix = data.getCamera().projectionMatrix();
        Context.shaders().get("basic").setUniformMatrix4x4("pr_matrix", pr_matrix);
        Context.shaders().get("basic").disable();
    }

    public static float getAspectRatio() {
        return aspectRatio;
    }

    public static int getScreenWidth() {
        return screenWidth;
    }

    public static int getScreenHeight() {
        return screenHeight;
    }

    public static MouseInput getMouseInput() {
        return mouseInput;
    }

    public static void setClearScreenColor(Color c) {
        color = c;
    }

}
