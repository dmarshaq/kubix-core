package org.dmarshaq.kubix.core.input;

import org.lwjgl.glfw.GLFWKeyCallback;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;

public class Input extends GLFWKeyCallback {
    public final static boolean[] KEY_PRESSED = new boolean[KeyCode.values().length];
    public final static boolean[] KEY_HOLD = new boolean[KeyCode.values().length];
    public final static boolean[] KEY_RELEASED = new boolean[KeyCode.values().length];

    private final static List<Integer> RELEASED_KEY_INDICES = new ArrayList<>();
    private final static List<Integer> PRESSED_KEY_INDICES = new ArrayList<>();

    @Override
    public void invoke(long window, int key, int scancode, int action, int mods) {
        if (!InputManager.KEY_CODE_MAP.containsKey(key)) {
            return;
        }
        if (action == GLFW_PRESS) {
            KEY_PRESSED[InputManager.KEY_CODE_MAP.get(key).ordinal()] = true;
            PRESSED_KEY_INDICES.add(InputManager.KEY_CODE_MAP.get(key).ordinal());
        }
        if (action == GLFW_PRESS) {
            KEY_HOLD[InputManager.KEY_CODE_MAP.get(key).ordinal()] = true;
        }
        else if (action == GLFW_RELEASE) {
            KEY_HOLD[InputManager.KEY_CODE_MAP.get(key).ordinal()] = false;
            KEY_RELEASED[InputManager.KEY_CODE_MAP.get(key).ordinal()] = true;
            RELEASED_KEY_INDICES.add(InputManager.KEY_CODE_MAP.get(key).ordinal());
        }
    }

    static void nullifyKeyIndices() {
        while (!PRESSED_KEY_INDICES.isEmpty()) {
            KEY_PRESSED[PRESSED_KEY_INDICES.get(0)] = false;
            PRESSED_KEY_INDICES.remove(0);
        }
        while (!RELEASED_KEY_INDICES.isEmpty()) {
            KEY_RELEASED[RELEASED_KEY_INDICES.get(0)] = false;
            RELEASED_KEY_INDICES.remove(0);
        }
    }
}
