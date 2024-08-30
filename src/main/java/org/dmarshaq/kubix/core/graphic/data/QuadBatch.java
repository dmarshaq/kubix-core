package org.dmarshaq.kubix.core.graphic.data;

import lombok.Getter;
import lombok.ToString;
import org.dmarshaq.kubix.core.graphic.base.Shader;
import org.dmarshaq.kubix.core.graphic.base.layer.Layer;
import org.dmarshaq.kubix.core.graphic.base.texture.Texture;
import org.dmarshaq.kubix.core.math.vector.Vector2;
import org.dmarshaq.kubix.core.math.vector.Vector3;
import org.dmarshaq.kubix.core.math.vector.Vector4;

@ToString
public class QuadBatch implements QuadStructure {
    private final float[] vertices;
    private final Shader shader;
    private final Layer layer;
    private final Texture texture;
    private int textureGroup;
    private int textureGroupIndex;
    private final int quadCount;

    public QuadBatch(int quadCount, Shader shader, Layer layer, Texture texture) {
        this.quadCount = quadCount;
        this.shader = shader;
        this.layer = layer;
        this.texture= texture;
        vertices = new float[quadCount * QuadStructure.QUAD_STRIDE];
    }

    @Override
    public float[] getVertexData() {
        return vertices;
    }

    @Override
    public Shader getShader() {
        return shader;
    }

    @Override
    public Layer getLayer() {
        return layer;
    }

    @Override
    public void setVertex(int quad, int vertex, Vector3 position, Vector4 color, Vector2 texCoordinates, Vector3 normal) {
        vertices[quad * QUAD_STRIDE + 0 + vertex * VERTEX_STRIDE] = position.x();
        vertices[quad * QUAD_STRIDE + 1 + vertex * VERTEX_STRIDE] = position.y();
        vertices[quad * QUAD_STRIDE + 2 + vertex * VERTEX_STRIDE] = position.z();
        vertices[quad * QUAD_STRIDE + 3 + vertex * VERTEX_STRIDE] = color.x();
        vertices[quad * QUAD_STRIDE + 4 + vertex * VERTEX_STRIDE] = color.y();
        vertices[quad * QUAD_STRIDE + 5 + vertex * VERTEX_STRIDE] = color.z();
        vertices[quad * QUAD_STRIDE + 6 + vertex * VERTEX_STRIDE] = color.w();
        vertices[quad * QUAD_STRIDE + 7 + vertex * VERTEX_STRIDE] = texCoordinates.x();
        vertices[quad * QUAD_STRIDE + 8 + vertex * VERTEX_STRIDE] = texCoordinates.y();
        vertices[quad * QUAD_STRIDE + 9 + vertex * VERTEX_STRIDE] = 0;
        vertices[quad * QUAD_STRIDE + 10 + vertex * VERTEX_STRIDE] = normal.x();
        vertices[quad * QUAD_STRIDE + 11 + vertex * VERTEX_STRIDE] = normal.y();
        vertices[quad * QUAD_STRIDE + 12 + vertex * VERTEX_STRIDE] = normal.z();
    }

    @Override
    public int getQuadCount() {
        return quadCount;
    }

    @Override
    public Texture getTexture() {
        return texture;
    }

    @Override
    public int getTextureGroup() {
        return textureGroup;
    }

    @Override
    public int getTextureGroupIndex() {
        return textureGroupIndex;
    }

    @Override
    public void setTextureGroup(int textureGroup, int textureGroupIndex) {
        this.textureGroup = textureGroup;
        this.textureGroupIndex = textureGroupIndex;
        for (int quad = 0; quad < quadCount; quad++) {
            vertices[quad * QUAD_STRIDE + 9 + 0 * VERTEX_STRIDE] = textureGroupIndex;
            vertices[quad * QUAD_STRIDE + 9 + 1 * VERTEX_STRIDE] = textureGroupIndex;
            vertices[quad * QUAD_STRIDE + 9 + 2 * VERTEX_STRIDE] = textureGroupIndex;
            vertices[quad * QUAD_STRIDE + 9 + 3 * VERTEX_STRIDE] = textureGroupIndex;
        }
    }
}
