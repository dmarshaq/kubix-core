package org.dmarshaq.input;

import org.lwjgl.glfw.GLFWCursorPosCallback;

public class CursorInputPos extends GLFWCursorPosCallback {
    private int xPos, yPos;
    @Override
    public void invoke(long window, double x, double y) {
        xPos = (int) x;
        yPos = (int) y;
    }

    public int x() {
        return xPos;
    }

    public int y() {
        return yPos;
    }
}
