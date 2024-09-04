package org.dmarshaq.kubix.core.input;

import java.util.HashMap;

import static org.lwjgl.glfw.GLFW.*;


public class InputManager {
    static final HashMap<Integer, KeyCode> KEY_CODE_MAP = new HashMap<>();

    public static void mapGLFWKeyCodes()  {
        KEY_CODE_MAP.put(GLFW_KEY_A, KeyCode.A);
        KEY_CODE_MAP.put(GLFW_KEY_B, KeyCode.B);
        KEY_CODE_MAP.put(GLFW_KEY_C, KeyCode.C);
        KEY_CODE_MAP.put(GLFW_KEY_D, KeyCode.D);
        KEY_CODE_MAP.put(GLFW_KEY_E, KeyCode.E);
        KEY_CODE_MAP.put(GLFW_KEY_F, KeyCode.F);
        KEY_CODE_MAP.put(GLFW_KEY_G, KeyCode.G);
        KEY_CODE_MAP.put(GLFW_KEY_H, KeyCode.H);
        KEY_CODE_MAP.put(GLFW_KEY_I, KeyCode.I);
        KEY_CODE_MAP.put(GLFW_KEY_J, KeyCode.J);
        KEY_CODE_MAP.put(GLFW_KEY_K, KeyCode.K);
        KEY_CODE_MAP.put(GLFW_KEY_L, KeyCode.L);
        KEY_CODE_MAP.put(GLFW_KEY_M, KeyCode.M);
        KEY_CODE_MAP.put(GLFW_KEY_N, KeyCode.N);
        KEY_CODE_MAP.put(GLFW_KEY_O, KeyCode.O);
        KEY_CODE_MAP.put(GLFW_KEY_P, KeyCode.P);
        KEY_CODE_MAP.put(GLFW_KEY_Q, KeyCode.Q);
        KEY_CODE_MAP.put(GLFW_KEY_R, KeyCode.R);
        KEY_CODE_MAP.put(GLFW_KEY_S, KeyCode.S);
        KEY_CODE_MAP.put(GLFW_KEY_T, KeyCode.T);
        KEY_CODE_MAP.put(GLFW_KEY_U, KeyCode.U);
        KEY_CODE_MAP.put(GLFW_KEY_V, KeyCode.V);
        KEY_CODE_MAP.put(GLFW_KEY_W, KeyCode.W);
        KEY_CODE_MAP.put(GLFW_KEY_X, KeyCode.X);
        KEY_CODE_MAP.put(GLFW_KEY_Y, KeyCode.Y);
        KEY_CODE_MAP.put(GLFW_KEY_Z, KeyCode.Z);
        KEY_CODE_MAP.put(GLFW_KEY_SPACE, KeyCode.SPACE);
        KEY_CODE_MAP.put(GLFW_KEY_LEFT_SHIFT, KeyCode.LEFT_SHIFT);
        KEY_CODE_MAP.put(GLFW_KEY_LEFT_CONTROL, KeyCode.LEFT_CONTROL);
        KEY_CODE_MAP.put(GLFW_KEY_RIGHT_CONTROL, KeyCode.RIGHT_CONTROL);
        KEY_CODE_MAP.put(GLFW_MOUSE_BUTTON_1, KeyCode.LEFT_MOUSE_BUTTON);
        KEY_CODE_MAP.put(GLFW_MOUSE_BUTTON_2, KeyCode.RIGHT_MOUSE_BUTTON);
        KEY_CODE_MAP.put(GLFW_KEY_BACKSPACE, KeyCode.BACKSPACE);
    }

    public static void resetReleasedKeyStates() {
        KeyInput.nullifyKeyIndices();
    }


}
