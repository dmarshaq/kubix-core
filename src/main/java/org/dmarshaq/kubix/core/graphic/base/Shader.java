package org.dmarshaq.kubix.core.graphic.base;

import lombok.Getter;
import org.dmarshaq.kubix.core.math.vector.Vector2;
import org.dmarshaq.kubix.core.util.Ordarable;
import org.dmarshaq.kubix.core.math.matrix.Matrix4x4;
import org.dmarshaq.kubix.core.math.vector.Vector3;

import java.util.HashMap;

import static org.lwjgl.opengl.GL20.*;

/**
 * Shader is a representation of actual shader program loaded into graphic card.
 * Due to its nature shader can only be modified in OpenGL context current.
 * Meaning you can safely call methods on shader only inside Graphic Thread.
 * Graphic class provides special method named modifyShaders().
 * It is called after every update loop, before render loop happens.
 * Note: at this time in Kubix Engine, Shader class supports vertices with attributes: position, color, texture-coordinate, texture-index and normal vector.
 */
public class Shader implements Ordarable {

    public static final int VERTEX_ATTRIBUTE = 0;
    public static final int COLOR_ATTRIBUTE = 1;
    public static final int TCOORDS_ATTRIBUTE = 2;
    public static final int TINDEX_ATTRIBUTE = 3;
    public static final int NORMAL_ATTRIBUTE = 4;

    @Getter
    private final int id;
    private final int order;
    private final HashMap<String, Integer> locationCache = new HashMap<>();

    public Shader(int id, int order) {
        this.id = id;
        this.order = order;
    }

    private int getUniform(String name) {
        if (locationCache.containsKey(name)) {
            return locationCache.get(name);
        }
        int result =  glGetUniformLocation(id, name);
        if (result == -1) {
            System.err.println("Could not find uniform variable '" + name + "'!");
        }
        else {
            locationCache.put(name, result);
        }
        return result;
    }

    /**
     * Sets int to specified uniform in shader program.
     */
    public void setUniform1i(String name, int value) {
        glUniform1i(getUniform(name), value);
    }

    /**
     * Sets float to specified uniform in shader program.
     */
    public void setUniform1f(String name, float value) {
        glUniform1f(getUniform(name), value);
    }

    /**
     * Sets Vector2 to specified uniform in shader program.
     */
    public void setUniform2f(String name, Vector2 vector2) {
        glUniform2f(getUniform(name), vector2.x(), vector2.y());
    }

    /**
     * Sets Vector3 to specified uniform in shader program.
     */
    public void setUniform3f(String name, Vector3 vector) {
        glUniform3f(getUniform(name), vector.x(), vector.y(), vector.z());
    }

    /**
     * Sets Matrix4x4 to specified uniform in shader program.
     */
    public void setUniformMatrix4x4(String name, Matrix4x4 matrix) {
        glUniformMatrix4fv(getUniform(name), false, matrix.toFloatBuffer());
    }

    /**
     * Sets int array to specified uniform in shader program.
     */
    public void setUniform1iv(String name, int[] array) {
        glUniform1iv(getUniform(name), array);
    }

    /**
     * Method enables OpenGL to use this shader program.
     * Because OpenGL is a huge state machine, it can work with only one shader program at a time.
     * Thus, every shader must be enabled and disabled manually after use.
     * Be careful not to call any other methods before enabling shader, it will result in error.
     */
    public void enable() {
        glUseProgram(id);
    }

    /**
     * Method disables OpenGL usage of this shader program. B
     * Because OpenGL is a huge state machine, it can work with only one shader program at a time.
     * Thus, every shader must be enabled and disabled manually after use.
     * Be careful not to leave shader enabled after use, it might cause render issues.
     */
    public void disable() {
        glUseProgram(0);
    }

    @Override
    public int ordinal() {
        return order;
    }
}
