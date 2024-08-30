package org.dmarshaq.kubix.core.graphic.render;

import lombok.Getter;
import org.dmarshaq.kubix.core.app.Context;
import org.dmarshaq.kubix.core.graphic.base.layer.Layer;
import org.dmarshaq.kubix.core.graphic.base.Shader;
import org.dmarshaq.kubix.core.graphic.data.QuadStructure;

import java.util.Arrays;

import static org.dmarshaq.kubix.core.graphic.render.Render.BatchRenderer.*;
import static org.lwjgl.opengl.GL11.GL_FRONT_AND_BACK;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL11.glPolygonMode;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;

public class QuadRender {
    private static Layer currentLayer;
    private static Shader currentShader;
    private static int currentTextureGroup;
    private static int currentTextureGroupIndex;
    private static int quadsInBatch;

    public static void renderInBatch(QuadStructure[] sortedQuads, int maxQuadsPerBatch) {
        int pointer = 0;
        while (pointer < sortedQuads.length) {
            // Vertices loading
            pointer = nextBatch(pointer, sortedQuads, maxQuadsPerBatch);
            // Enabling shader
            currentShader.enable();
            // Textures loading
            loadTexturesUsedIntoSlots();
            // Binding dynamic draw vertex buffer
            glBindBuffer(GL_ARRAY_BUFFER, vertexQuadBufferObject);
            // Actually loading vertices into graphics memory
            glBufferSubData(GL_ARRAY_BUFFER, 0, QUAD_RENDER_VERTICES);
            // Drawing mode
            glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
            // Drawing
//            System.out.println("Quads rendered in Batch: " + quadsRenderedInBatch);
            glDrawElements(GL_TRIANGLES, quadsInBatch * 6, GL_UNSIGNED_INT, 0);
            // Debug stats
            debug();
            // Cleaning up
            flush();
        }
    }

    private static int nextBatch(int pointer, QuadStructure[] sortedQuads, int threshold) {
        currentLayer = sortedQuads[pointer].getLayer();
        currentShader = sortedQuads[pointer].getShader();
        currentTextureGroup = sortedQuads[pointer].getTextureGroup();
        currentTextureGroupIndex = -1;

        while(
                quadsInBatch < threshold
                && pointer < sortedQuads.length
                && currentLayer == sortedQuads[pointer].getLayer()
                && currentShader == sortedQuads[pointer].getShader()
                && currentTextureGroup == sortedQuads[pointer].getTextureGroup()) {

            // Check for potential quad batch overflow if loading multiple quads at ones.
            if (quadsInBatch + sortedQuads[pointer].getQuadCount() > threshold) {
                break;
            }

            // If texture index in samplers is new, use its texture id in samplers by its index.
            if (currentTextureGroupIndex != sortedQuads[pointer].getTextureGroupIndex()) {
                currentTextureGroupIndex = sortedQuads[pointer].getTextureGroupIndex();
                QUAD_RENDER_TEXTURES_USED[currentTextureGroupIndex] = sortedQuads[pointer].getTexture().getTextureId();
            }

            // Copy quads
            copyQuadVertices(quadsInBatch * QuadStructure.QUAD_STRIDE, sortedQuads[pointer].getVertexData());

            quadsInBatch += sortedQuads[pointer].getQuadCount();
            pointer++;
        }

        return pointer;
    }

    private static void copyQuadVertices(int pointer, float[] src) {
        for (float f : src) {
            QUAD_RENDER_VERTICES[pointer++] = f;
        }
    }

    private static void loadTexturesUsedIntoSlots() {
        for (int i = 0; i < QUAD_RENDER_TEXTURES_USED.length; i++) {
            glActiveTexture(GL_TEXTURE0 + i);
            glBindTexture(GL_TEXTURE_2D, QUAD_RENDER_TEXTURES_USED[i]);
        }
    }

    private static void flush() {
        unbindUsedTextures();
        quadsInBatch = 0;
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        currentShader.disable();
    }

    private static void debug() {
        if (Context.isDebugRender()) {
            System.out.println("_____ Quad Renderer Batch report _____" );
            System.out.println("Quads rendered: " + quadsInBatch);
            System.out.println("Layer used: " + currentLayer);
            System.out.println("Shader used: " + currentShader);
            System.out.println("Textures used: " + Arrays.toString(QUAD_RENDER_TEXTURES_USED));
        }
    }

    private static void unbindUsedTextures() {
        for (int i = 0; i < QUAD_RENDER_TEXTURES_USED.length; i++) {
            glActiveTexture(GL_TEXTURE0 + i);
            glBindTexture(GL_TEXTURE_2D, 0);
            QUAD_RENDER_TEXTURES_USED[i] = 0;
        }
    }
}
