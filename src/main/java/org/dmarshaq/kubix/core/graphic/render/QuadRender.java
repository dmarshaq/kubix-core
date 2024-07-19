package org.dmarshaq.kubix.core.graphic.render;

import org.dmarshaq.kubix.core.app.Context;
import org.dmarshaq.kubix.core.graphic.Shader;
import org.dmarshaq.kubix.core.graphic.Texture;
import org.lwjgl.opengl.GL20;

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
    private static int quadsInBatch;


    public static void renderQuadsInBatch(Quad[] sortedQuads, int maxQuadsPerBatch) {
        int pointer = 0;
        while (pointer < sortedQuads.length) {
            // Vertices loading
            pointer = nextBatch(pointer, sortedQuads, maxQuadsPerBatch);
            // Enabling shader
            currentShader.enable();
            // Textures loading
            loadTextureGroup();
            loadTexturesUsedIntoSlots();
            // Binding dynamic draw vertex buffer
            glBindBuffer(GL_ARRAY_BUFFER, vbo);
            // Actually loading vertices into graphics memory
            glBufferSubData(GL_ARRAY_BUFFER, 0, VERTICES);
            // Drawing mode
            glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
            // Drawing
//            System.out.println("Quads rendered in Batch: " + quadsRenderedInBatch);
            glDrawElements(GL_TRIANGLES, quadsInBatch * 6, GL_UNSIGNED_INT, 0);
            int error = GL20.glGetError();
            if (error != 0) {
                System.out.println(error);
            }
            // Cleaning up
            flush();
        }
    }

    private static int nextBatch(int pointer, Quad[] sortedQuads, int threshold) {
        currentLayer = sortedQuads[pointer].getLayer();
        currentShader = sortedQuads[pointer].getShader();
        currentTextureGroup = sortedQuads[pointer].getTexture().ordinal() / 32;


        while(quadsInBatch < threshold
                && pointer < sortedQuads.length
                && currentLayer == sortedQuads[pointer].getLayer()
                && currentShader == sortedQuads[pointer].getShader()
                && currentTextureGroup == sortedQuads[pointer].getTexture().ordinal() / 32) {

            copyQuadVertices(quadsInBatch * 4 * Quad.VERTEX_STRIDE, sortedQuads[pointer].getVertexData());

            quadsInBatch++;
            pointer++;
        }

        return pointer;
    }

    private static void copyQuadVertices(int pointer, float[] src) {
        for (float f : src) {
            VERTICES[pointer++] = f;
        }
    }

    private static void loadTextureGroup() {
        for (int i = 0; i < 32; i++) {
            Texture texture = Context.textures().get(i + currentTextureGroup * 32);
            if (texture == null) {
                break;
            }
            else {
                TEXTURES_USED[i] = texture.getTextureId();
            }
        }
    }

    private static void loadTexturesUsedIntoSlots() {
        for (int i = 0; i < TEXTURES_USED.length; i++) {
            glActiveTexture(GL_TEXTURE0 + i);
            glBindTexture(GL_TEXTURE_2D, TEXTURES_USED[i]);
        }
    }

    private static void flush() {
        unbindUsedTextures();
        quadsInBatch = 0;
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        currentShader.disable();
    }

    private static void unbindUsedTextures() {
        for (int i = 0; i < TEXTURES_USED.length; i++) {
            glActiveTexture(GL_TEXTURE0 + i);
            glBindTexture(GL_TEXTURE_2D, 0);
            TEXTURES_USED[i] = 0;
        }
    }
}
