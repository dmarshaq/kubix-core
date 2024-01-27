package org.dmarshaq.input;

import org.lwjgl.glfw.GLFW;

public enum KeyCode {
    W (GLFW.GLFW_KEY_W, 0),
    A (GLFW.GLFW_KEY_A, 1),
    S (GLFW.GLFW_KEY_S, 2),
    D (GLFW.GLFW_KEY_D, 3),
    SPACE (GLFW.GLFW_KEY_SPACE, 4);

    private final int old;
    private final int kubix;

    KeyCode(int old, int kubix) {
        this.old = old;
        this.kubix = kubix;
    }

    public static int getKeyCode(int key) {
        for (KeyCode e : KeyCode.values()) {
            if (e.old == key) return e.kubix;
        }
        return -1;
    }

    public static int getKeyCode(KeyCode e) {
        return e.kubix;
    }
}
