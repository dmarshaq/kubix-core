package org.dmarshaq.kubix.core.graphic.render;

import org.dmarshaq.kubix.core.graphic.data.Line;
import org.dmarshaq.kubix.core.graphic.base.layer.Layer;
import org.dmarshaq.kubix.core.graphic.base.Shader;

import static org.dmarshaq.kubix.core.graphic.render.Render.BatchRenderer.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.GL_FILL;
import static org.lwjgl.opengl.GL15.*;

public class LineRender {
    private static Shader currentShader;
    private static float currentWidth;
    private static int linesInBatch;
    
    public static void renderInBatch(Line[] sortedLines, int maxLinesPerBatch) {
        int pointer = 0;
        while (pointer < sortedLines.length) {
            // Vertices loading
            pointer = nextBatch(pointer, sortedLines, maxLinesPerBatch);
            // Enabling shader
            currentShader.enable();
            // Binding dynamic draw vertex buffer
            glBindBuffer(GL_ARRAY_BUFFER, vertexLineBufferObject);
            // Actually loading vertices into graphics memory
            glBufferSubData(GL_ARRAY_BUFFER, 0, LINE_RENDER_VERTICES);
            // Drawing mode
            glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
            // Drawing
            glLineWidth(currentWidth);
            glDrawArrays(GL_LINES, 0, linesInBatch * Line.VERTICES);

            // Cleaning up
            flush();
        }
    }

    private static int nextBatch(int pointer, Line[] sortedLines, int threshold) {
        Layer currentLayer = sortedLines[pointer].getLayer();
        currentShader = sortedLines[pointer].getShader();
        currentWidth = sortedLines[pointer].getWidth();

        while(linesInBatch < threshold
                && pointer < sortedLines.length
                && currentLayer == sortedLines[pointer].getLayer()
                && currentShader == sortedLines[pointer].getShader()
                && currentWidth == sortedLines[pointer].getWidth()) {

            copyLineVertices(linesInBatch * Line.STRIDE, sortedLines[pointer].getVertexData());

            linesInBatch++;
            pointer++;
        }

        return pointer;
    }

    private static void copyLineVertices(int pointer, float[] src) {
        for (float f : src) {
            LINE_RENDER_VERTICES[pointer++] = f;
        }
    }

    private static void flush() {
        linesInBatch = 0;
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        currentShader.disable();
    }

}
