package org.dmarshaq.kubix.core.input;

import org.dmarshaq.kubix.core.app.Render;
import org.dmarshaq.kubix.core.mathj.Vector2i;
import org.lwjgl.glfw.GLFW;

public class MouseInput  {
    private final Vector2i currentPos = new Vector2i();
    private boolean inWindow, leftButtonPressed, rightButtonPressed, leftToggle, leftPress, leftUnpress, rightToggle, rightPress, rightUnpress;


    public MouseInput() {
        leftToggle = true;
        rightToggle = true;
    }

    public void init(long windowHandle) {
        GLFW.glfwSetCursorPosCallback(windowHandle, (window, xpos, ypos) -> {
            currentPos.x = (int) xpos;
            currentPos.y = (int) (Render.getScreenHeight() - ypos);
        });

        GLFW.glfwSetCursorEnterCallback(windowHandle, (window, entered) -> {
            inWindow = entered;
        });

        GLFW.glfwSetMouseButtonCallback(windowHandle, (window, button, action, mode) -> {
            leftButtonPressed = button == GLFW.GLFW_MOUSE_BUTTON_1 && action == GLFW.GLFW_PRESS;
            rightButtonPressed = button == GLFW.GLFW_MOUSE_BUTTON_2 && action == GLFW.GLFW_PRESS;
        });
    }


    public Vector2i mousePos() {
        return currentPos;
    }


    public boolean isLeftButtonPressed() {
        return leftButtonPressed;
    }

    public boolean leftButtonPressed() {
        return leftPress;
    }

    public boolean leftButtonUnpressed() {
        return leftUnpress;
    }


    public boolean isRightButtonPressed() {
        return rightButtonPressed;
    }

    public boolean rightButtonPressed() {
        return rightPress;
    }

    public boolean rightButtonUnpressed() {
        return rightUnpress;
    }


    public void input() {
        leftPress = false;
        leftUnpress = false;
        if (leftToggle == leftButtonPressed && leftToggle) {
            leftPress = true;
            leftToggle = false;
        }
        else if (leftToggle == leftButtonPressed) {
            leftUnpress = true;
            leftToggle = true;
        }

        rightPress = false;
        rightUnpress = false;
        if (rightToggle == rightButtonPressed && rightToggle) {
            rightPress = true;
            rightToggle = false;
        }
        else if (rightToggle == rightButtonPressed) {
            rightUnpress = true;
            rightToggle = true;
        }
    }


}
