package org.dmarshaq.kubix.core.graphic;

import lombok.Getter;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWWindowSizeCallback;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.glfwSetWindowSizeCallback;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.system.MemoryUtil.NULL;

@Getter
public class Window {
    private final long window;
    private final IntBuffer ptrWidth;
    private final IntBuffer ptrHeight;

    /**
     * Builds new Window based on specified width, height and title.
     * isFullScreen primarily states whether window should be sized to primary monitor or not.
     */
    public Window(int width, int height, String title, boolean isFullScreen) {
        window = glfwCreateWindow(width, height, title, isFullScreen ? glfwGetPrimaryMonitor() : 0, 0);

        if ( window == NULL )
            throw new RuntimeException("Failed to create the GLFW window");

        ptrWidth = BufferUtils.createIntBuffer(1);
        ptrHeight = BufferUtils.createIntBuffer(1);

        glfwGetWindowSize(window, ptrWidth, ptrHeight);
    }

    /**
     * Make the window visible.
     */
    public void show() {
        glfwShowWindow(window);
    }

    /**
     * All drawing happens in the current context.
     * If you make a different context current, then all drawing will now happen in that context.
     * Context are bound to window you are drawing on.
     */
    public void makeContextCurrent() {
        glfwMakeContextCurrent(window);
    }

    /**
     * Sets key callback object that will be called whenever action happens on this window.
     */
    public void setKeyCallback(GLFWKeyCallback keyCallback) {
        glfwSetKeyCallback(window, keyCallback);
    }

    /**
     * Sets size callback object that will be called whenever resize happens on this window.
     */
    public void setSizeCallback(GLFWWindowSizeCallback sizeCallback) {
        glfwSetWindowSizeCallback(window, sizeCallback);
    }

    /**
     * Returns calculated value that represents aspect ratio in window.
     */
    public float aspect() {
        return (float) ptrWidth.get(0) / ptrHeight.get(0);
    }

    /**
     * Returns value of window width
     */
    public int width() {
        return ptrWidth.get(0);
    }

    /**
     * Returns value of window height.
     */
    public int height() {
        return ptrHeight.get(0);
    }

    /**
     * Frees window callbacks, usually is used before window is destroyed.
     */
    public void freeCallbacks() {
        glfwFreeCallbacks(window);
    }

    /**
     * Destroys window.
     */
    public void destroyWindow() {
        glfwDestroyWindow(window);
    }

    /**
     * Returns new default window size callback object.
     */
    public static GLFWWindowSizeCallback windowDefaultSizeCallback(Window windowObj) {
        return new GLFWWindowSizeCallback() {
            @Override
            public void invoke(long window, int width, int height){
                windowObj.getPtrWidth().put(0, width);
                windowObj.getPtrHeight().put(0, height);
                glViewport(0, 0, width, height);
            }
        };
    }

}
