package org.dmarshaq.input;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;

import static org.dmarshaq.app.Context.*;

public class Input extends GLFWKeyCallback {


    public static boolean[] keysPress = new boolean[getMaxInputKeys()];
    public static boolean[] keysHold = new boolean[getMaxInputKeys()];

    @Override
    public void invoke(long window, int key, int scancode, int action, int mods) {
        if (KeyCode.getKeyCode(key) != -1) {
            keysPress[KeyCode.getKeyCode(key)] = action == GLFW.GLFW_PRESS;
            keysHold[KeyCode.getKeyCode(key)] = action != GLFW.GLFW_RELEASE;
        }
    }
}
