package graphics;

import mathj.Matrix4f;
import mathj.Vector3f;
import utils.ShaderUtils;

import java.util.HashMap;

import static org.lwjgl.opengl.GL20.*;

public class Shader {

    public static final int VERTEX_ATTRIBUTE = 0;
    public static final int TCORDS_ATTRIBUTE = 1;

    public static Shader BASIC;

    private boolean enabled = false;

    private final int ID;
    private HashMap<String, Integer> locationCache = new HashMap<String, Integer>();

    private Shader(String vertex, String fragment) {

        ID = ShaderUtils.load(vertex, fragment);
    }

    public static void loadAll() {
        BASIC = new Shader("res/shader/basic.vert", "res/shader/basic.frag");
    }

    public int getUniform(String name) {
        if (locationCache.containsKey(name)) {
            return locationCache.get(name);
        }
        int result =  glGetUniformLocation(ID, name);
        if (result == -1) {
            System.err.println("Could not find uniform variable '" + name + "'!");
        }
        else {
            locationCache.put(name, result);
        }
        return result;
    }
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
    public void setUniform3f(String name, Vector3f vector) {
        if (!enabled) enable();
        glUniform3f(getUniform(name), vector.x, vector.y, vector.z);
    }
    public void setUniformMatrix4f(String name, Matrix4f matrix) {
        if (!enabled) enable();
        glUniformMatrix4fv(getUniform(name), false, matrix.toFloatBuffer());
    }

    public void enable() {
        glUseProgram(ID);
        enabled = true;
    }

    public void disable() {
        glUseProgram(0);
        enabled = false;
    }

}
