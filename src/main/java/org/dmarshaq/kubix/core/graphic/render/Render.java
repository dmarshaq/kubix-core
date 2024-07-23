package org.dmarshaq.kubix.core.graphic.render;

import org.dmarshaq.kubix.core.app.Context;
import org.dmarshaq.kubix.core.graphic.data.Line;
import org.dmarshaq.kubix.core.graphic.data.Quad;
import org.dmarshaq.kubix.core.graphic.data.Snapshot;
import org.dmarshaq.kubix.core.graphic.base.Window;
import org.dmarshaq.kubix.core.graphic.base.Shader;
import org.dmarshaq.kubix.core.util.BufferUtils;
import org.dmarshaq.kubix.core.math.base.MathCore;
import org.dmarshaq.kubix.core.math.matrix.Matrix4x4;
import org.dmarshaq.kubix.core.math.vector.Vector4;

import java.awt.*;

import static org.dmarshaq.kubix.core.app.Context.getMaxLinesPerBatch;
import static org.dmarshaq.kubix.core.app.Context.getMaxQuadsPerBatch;
import static org.dmarshaq.kubix.core.graphic.render.Render.BatchRenderer.*;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;


public class Render {
    private Matrix4x4 pr_matrix;
    private final Window window;

    public Render(Window window, Color clearColor) {
        this.window = window;

        // Blending
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        // Background
        Vector4 vector4 = MathCore.vector4(clearColor);
        glClearColor(vector4.x(), vector4.y(), vector4.z(), vector4.w());

        // Render Setup
        //  Texture Samplers
        int[] samplers = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11,
                12, 13, 14, 15, 16, 17, 18, 19, 20, 21,
                22, 23, 24, 25, 26, 27, 28, 29, 30, 31 };

        // Setup shaders
        pr_matrix = MathCore.orthographic(-2f, 2f, -1.5f, 1.5f, -1f, 1f); // basically camera matrix

        Shader basic = Context.shaders().get("basic_quad");

        basic.enable();
        basic.setUniformMatrix4x4("pr_matrix", pr_matrix);
        basic.setUniform1iv("u_Textures", samplers);
        basic.setUniformMatrix4x4("ml_matrix", new Matrix4x4());
        basic.disable();

        basic = Context.shaders().get("basic_line");

        basic.enable();
        basic.setUniformMatrix4x4("pr_matrix", pr_matrix);
        basic.setUniformMatrix4x4("ml_matrix", new Matrix4x4());
        basic.disable();


        // Quad Array Object
        vertexQuadArrayObject = glGenVertexArrays();
        glBindVertexArray(vertexQuadArrayObject);

        // Quad Buffer Object
        vertexQuadBufferObject = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vertexQuadBufferObject);
        glBufferData(GL_ARRAY_BUFFER, QUAD_RENDER_VERTICES.length * 4, GL_DYNAMIC_DRAW);

        glVertexAttribPointer(Shader.VERTEX_ATTRIBUTE, 3, GL_FLOAT, false, Quad.VERTEX_STRIDE * 4, 0 * 4);
        glEnableVertexAttribArray(Shader.VERTEX_ATTRIBUTE);

        glVertexAttribPointer(Shader.COLOR_ATTRIBUTE, 4, GL_FLOAT, false, Quad.VERTEX_STRIDE * 4, 3 * 4);
        glEnableVertexAttribArray(Shader.COLOR_ATTRIBUTE);

        glVertexAttribPointer(Shader.TCOORDS_ATTRIBUTE, 2, GL_FLOAT, false, Quad.VERTEX_STRIDE * 4, 7 * 4);
        glEnableVertexAttribArray(Shader.TCOORDS_ATTRIBUTE);

        glVertexAttribPointer(Shader.TINDEX_ATTRIBUTE, 1, GL_FLOAT, false, Quad.VERTEX_STRIDE * 4, 9 * 4);
        glEnableVertexAttribArray(Shader.TINDEX_ATTRIBUTE);

        glVertexAttribPointer(Shader.NORMAL_ATTRIBUTE, 3, GL_FLOAT, false, Quad.VERTEX_STRIDE * 4, 10 * 4);
        glEnableVertexAttribArray(Shader.NORMAL_ATTRIBUTE);

        glBindBuffer(GL_ARRAY_BUFFER, 0);

        // Indices
        // Build indices
        for (int i = 0; i < getMaxQuadsPerBatch(); i++) {
            for (int j = 0; j < 6; j++) {
                int val = j;
                if (j > 2 && j < 5) {
                    val--;
                }
                else if (j == 5) {
                    val = 1;
                }
                QUAD_RENDER_INDICES[i * 6 + j] = (i * 4 + val);
            }
        }
        indexBufferObject = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexBufferObject);

        glBufferData(GL_ELEMENT_ARRAY_BUFFER, BufferUtils.createIntBuffer(QUAD_RENDER_INDICES), GL_STATIC_DRAW);

        // Line Array Object
        vertexLineArrayObject = glGenVertexArrays();
        glBindVertexArray(vertexLineArrayObject);

        // Line Buffer Object
        vertexLineBufferObject = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vertexLineBufferObject);
        glBufferData(GL_ARRAY_BUFFER, LINE_RENDER_VERTICES.length * 4, GL_DYNAMIC_DRAW);

        glVertexAttribPointer(Shader.VERTEX_ATTRIBUTE, 3, GL_FLOAT, false, Line.VERTEX_STRIDE * 4, 0 * 4);
        glEnableVertexAttribArray(Shader.VERTEX_ATTRIBUTE);

        glVertexAttribPointer(Shader.COLOR_ATTRIBUTE, 4, GL_FLOAT, false, Line.VERTEX_STRIDE * 4, 3 * 4);
        glEnableVertexAttribArray(Shader.COLOR_ATTRIBUTE);

        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    public void render(Snapshot snapshot) {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        loadCameraMatrix(snapshot);

        glBindVertexArray(vertexQuadArrayObject);
        QuadRender.renderInBatch(snapshot.getQuadRenderArray(), getMaxQuadsPerBatch());
        glBindVertexArray(vertexLineArrayObject);
        LineRender.renderInBatch(snapshot.getLineRenderArray(), getMaxLinesPerBatch());

        glfwSwapBuffers(window.getWindow()); // swap the color buffers
        // Poll for window events. The key callback above will only be
        // invoked during this call.
        glfwPollEvents();
    }

    private void loadCameraMatrix(Snapshot snapshot) {
        pr_matrix = snapshot.getCamera().getProjection();

        // Quad shader
        Shader basic = Context.shaders().get("basic_quad");
        basic.enable();
        basic.setUniformMatrix4x4("pr_matrix", pr_matrix);
        basic.disable();

        // Line shader
        basic = Context.shaders().get("basic_line");
        basic.enable();
        basic.setUniformMatrix4x4("pr_matrix", pr_matrix);
        basic.disable();
    }

    public static class BatchRenderer {
        public static final float[] QUAD_RENDER_VERTICES = new float[getMaxQuadsPerBatch() * Quad.STRIDE]; // quad array simplified into float array of stacked vertices
        public static final int[] QUAD_RENDER_INDICES = new int[getMaxQuadsPerBatch() * 6];
        public static final int[] QUAD_RENDER_TEXTURES_USED = new int[32]; // 32 textures where texture at index 0 is a reserved space for color only
        public static int vertexQuadArrayObject, vertexLineArrayObject;
        public static int vertexQuadBufferObject, vertexLineBufferObject;
        public static int indexBufferObject;

        public static final float[] LINE_RENDER_VERTICES = new float[getMaxLinesPerBatch() * Line.STRIDE];

    }
}
