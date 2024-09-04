package org.dmarshaq.kubix.core.input;

import lombok.Getter;
import org.lwjgl.glfw.GLFWCharCallback;
import org.lwjgl.glfw.GLFWKeyCallback;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class CharacterInput extends GLFWCharCallback {
    public static boolean ENABLE_CHAR_INPUT = false;

    /**
     * If ENABLE_CHAR_INPUT is true, every char typed will be added to the INPUT_STRING_BUFFER.
     * Note: it doesn't get cleared no updated, if you want to clear buffer you should do it by yourself.
     * Since StringBuffer is thread safe it will synchronised and can manipulate safely.
     */
    public static final StringBuffer INPUT_STRING_BUFFER = new StringBuffer();

    @Override
    public void invoke(long window, int charCode) {
        if (ENABLE_CHAR_INPUT) {
            INPUT_STRING_BUFFER.append((char) charCode);
        }
    }

}
