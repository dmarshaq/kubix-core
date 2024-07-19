package org.dmarshaq.kubix.core.graphic.render;

import org.dmarshaq.kubix.core.util.Ordarable;
import org.dmarshaq.kubix.core.math.matrix.Matrix4x4;
import org.dmarshaq.kubix.core.math.vector.Vector3;

import java.util.HashMap;

import static org.lwjgl.opengl.GL20.*;

public class Shader implements Ordarable {

    public static final int VERTEX_ATTRIBUTE = 0;
    public static final int COLOR_ATTRIBUTE = 1;
    public static final int TCOORDS_ATTRIBUTE = 2;
    public static final int TINDEX_ATTRIBUTE = 3;

    private boolean enabled = false;

    private final int id;
    private final int order;
    private final HashMap<String, Integer> locationCache = new HashMap<>();

    public Shader(int id, int order) {
        this.order = order;
        this.id = id;
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

    // variables
    public void setUniform1i(String name, int value) {
        if (!enabled) enable();
        glUniform1i(getUniform(name), value);
    }
    public void setUniform1f(String name, float value) {
        if (!enabled) enable();
        glUniform1f(getUniform(name), value);
    }
    public void setUniform2f(String name, float x, float y) {
        if (!enabled) enable();
        glUniform2f(getUniform(name), x, y);
    }
    public void setUniform3f(String name, Vector3 vector) {
        if (!enabled) enable();
        glUniform3f(getUniform(name), vector.x(), vector.y(), vector.z());
    }
    public void setUniformMatrix4x4(String name, Matrix4x4 matrix) {
        if (!enabled) enable();
        glUniformMatrix4fv(getUniform(name), false, matrix.toFloatBuffer());
    }

    // arrays
    public void setUniform1iv(String name, int[] array) {
        if (!enabled) enable();
        glUniform1iv(getUniform(name), array);
    }

    public void enable() {
        glUseProgram(id);
        enabled = true;
    }

    public void disable() {
        glUseProgram(0);
        enabled = false;
    }

    public int getId() {
        return id;
    }

    @Override
    public int ordinal() {
        return order;
    }
}
