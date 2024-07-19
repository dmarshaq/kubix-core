package org.dmarshaq.kubix.core.input;

import lombok.Getter;
import org.dmarshaq.kubix.core.app.Graphic;
import org.dmarshaq.kubix.core.math.MathCore;
import org.dmarshaq.kubix.core.math.vector.Vector;
import org.lwjgl.glfw.GLFW;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class MouseInput  {
    public static final Vector<Integer> POSITION = MathCore.vector2(0, 0);
    @Getter
    private static boolean inWindow;

    public void init(long windowHandle) {
        GLFW.glfwSetCursorPosCallback(windowHandle, (window, xpos, ypos) -> {
            POSITION.getValues().intArray()[0] = (int) xpos;
            POSITION.getValues().intArray()[1] = (int) (Graphic.getWindow().height() - ypos);
        });

        GLFW.glfwSetCursorEnterCallback(windowHandle, (window, entered) -> {
            inWindow = entered;
        });

        GLFW.glfwSetMouseButtonCallback(windowHandle, (window, button, action, mode) -> {
            if (!InputManager.KEY_CODE_MAP.containsKey(button)) {
                return;
            }
            if (action == GLFW_PRESS) {
                Input.KEY_PRESSED[InputManager.KEY_CODE_MAP.get(button).ordinal()] = true;
                Input.PRESSED_KEY_INDICES.add(InputManager.KEY_CODE_MAP.get(button).ordinal());
            }
            if (action == GLFW_PRESS) {
                Input.KEY_HOLD[InputManager.KEY_CODE_MAP.get(button).ordinal()] = true;
            }
            else if (action == GLFW_RELEASE) {
                Input.KEY_HOLD[InputManager.KEY_CODE_MAP.get(button).ordinal()] = false;
                Input.KEY_RELEASED[InputManager.KEY_CODE_MAP.get(button).ordinal()] = true;
                Input.RELEASED_KEY_INDICES.add(InputManager.KEY_CODE_MAP.get(button).ordinal());
            }
        });
    }
}
