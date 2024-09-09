package org.dmarshaq.kubix.core.graphic.base;

import lombok.Getter;
import org.lwjgl.BufferUtils;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.NULL;

@Getter
public class Cursor {
    private final long cursor;

    /**
     * Builds new Cursor based on specified shape.
     */
    public Cursor(int shape) {
        cursor = glfwCreateStandardCursor(shape);

        if ( cursor == NULL )
            throw new RuntimeException("Failed to create the GLFW cursor");


    }

    /**
     * Destroys cursor.
     */
    public void destroyCursor() {
        glfwDestroyCursor(cursor);
    }
}
