package org.dmarshaq.kubix.core.graphic.data;

import org.dmarshaq.kubix.core.graphic.base.texture.Texture;
import org.dmarshaq.kubix.core.math.vector.Vector2;
import org.dmarshaq.kubix.core.math.vector.Vector3;
import org.dmarshaq.kubix.core.math.vector.Vector4;

public interface QuadStructure extends Renderable {
    int VERTEX_STRIDE = 13;
    int VERTICES_PER_QUAD = 4;
    int QUAD_STRIDE = VERTEX_STRIDE * VERTICES_PER_QUAD;

    /**
     * Sets any quad vertex from 0 to 3 to specified properties.
     *
     * @param vertex integer from 0 to 3.
     */
    void setVertex(int quad, int vertex, Vector3 position, Vector4 color, Vector2 texCoordinates, Vector3 normal);
    int getQuadCount();

    Texture getTexture();
    int getTextureGroup();
    int getTextureGroupIndex();
    void setTextureGroup(int textureGroup, int textureGroupIndex);

    @Override
    default int compareTo(Renderable o) {
        if (o instanceof QuadStructure) {
            int layerCompare = getLayer().compareTo(o.getLayer());
            if (layerCompare != 0) {
                return layerCompare;
            }
            int shaderCompare = getShader().compareTo(o.getShader());
            if (shaderCompare != 0) {
                return shaderCompare;
            }
            return Integer.compare(getTextureGroup(), ((QuadStructure) o).getTextureGroup());
        }
        return 0;
    }
}
