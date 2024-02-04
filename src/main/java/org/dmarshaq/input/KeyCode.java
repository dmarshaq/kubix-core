package org.dmarshaq.input;

import org.lwjgl.glfw.GLFW;

public enum KeyCode {
    W (GLFW.GLFW_KEY_W),
    A (GLFW.GLFW_KEY_A),
    S (GLFW.GLFW_KEY_S),
    D (GLFW.GLFW_KEY_D),
    SPACE (GLFW.GLFW_KEY_SPACE);

    private final int glfw;
    private int code;

    KeyCode(int glfw) {
        this.glfw = glfw;
    }

    public static void initKeyCodes() {
        for (int i = 0; i < KeyCode.values().length; i++) {
            KeyCode.values()[i].code = i;
        }
    }

    public static int getKeyCode(int key) {
        for (KeyCode e : KeyCode.values()) {
            if (e.glfw == key) return e.code;
        }
        return -1;
    }

    public static int getKeyCode(KeyCode e) {
        return e.code;
    }
}
