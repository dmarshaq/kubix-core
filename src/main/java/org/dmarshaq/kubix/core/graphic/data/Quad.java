package org.dmarshaq.kubix.core.graphic.data;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.dmarshaq.kubix.core.graphic.base.layer.Layer;
import org.dmarshaq.kubix.core.graphic.base.Shader;
import org.dmarshaq.kubix.core.graphic.base.texture.Texture;
import org.dmarshaq.kubix.core.math.vector.Vector2;
import org.dmarshaq.kubix.core.math.vector.Vector3;
import org.dmarshaq.kubix.core.math.vector.Vector4;

/**
 * Quad is a wrapper class for simple vertex float array that describes quad's figure.
 * It contains float array for storing each of 4 vertices data, as well as shader that is used in render to draw the quad.
 * It also stores layer it supposed to render on.
 * It is not data heavy class, since vertices are stored in fixed array.
 * All heavy computational procedures are processed in graphic processors.
 * Quad can be saved and reused from one update to another but cannot be modified. Yet...
 */
@ToString
public class Quad implements Renderable {
    public static final int VERTEX_STRIDE = 13;
    public static final int VERTICES = 4;
    public static final int STRIDE = VERTEX_STRIDE * VERTICES;

    private final float[] vertices;
    private final Shader shader;     
    private final Layer layer;
    @Getter
    private final Texture texture;
    @Getter
    private int textureGroup;
    @Getter
    private int textureGroupRenderOrder;

    /**
     * Builds the quad with float array describing all 4 vertices each taking 13 floats in array.
     * Note: each vertex properties needs to also be specified, through setVertex().
     */
    public Quad(Shader shader, Layer layer, Texture texture) {
        this.shader = shader;
        this.layer = layer;
        this.texture = texture;
        vertices = new float[VERTICES * VERTEX_STRIDE];
    }

    /**
     * Sets any quad vertex from 0 to 3 to specified properties.
     */
    public void setVertex(int vertex, Vector3 position, Vector4 color, Vector2 texCoordinates, Vector3 normal) {
        vertices[0 + vertex * VERTEX_STRIDE] = position.x();
        vertices[1 + vertex * VERTEX_STRIDE] = position.y();
        vertices[2 + vertex * VERTEX_STRIDE] = position.z();
        vertices[3 + vertex * VERTEX_STRIDE] = color.x();
        vertices[4 + vertex * VERTEX_STRIDE] = color.y();
        vertices[5 + vertex * VERTEX_STRIDE] = color.z();
        vertices[6 + vertex * VERTEX_STRIDE] = color.w();
        vertices[7 + vertex * VERTEX_STRIDE] = texCoordinates.x();
        vertices[8 + vertex * VERTEX_STRIDE] = texCoordinates.y();
        vertices[9 + vertex * VERTEX_STRIDE] = 0;
        vertices[10 + vertex * VERTEX_STRIDE] = normal.x();
        vertices[11 + vertex * VERTEX_STRIDE] = normal.y();
        vertices[12 + vertex * VERTEX_STRIDE] = normal.z();
    }

    @Override
    public float[] getVertexData() {
        return vertices;
    }

    @Override
    public Shader getShader() {
        return shader;
    }

    public Layer getLayer() {
        return layer;
    }

    @Override
    public int compareTo(Renderable o) {
        if (o instanceof Quad) {
            int layerCompare = layer.compareTo(o.getLayer());
            if (layerCompare != 0) {
                return layerCompare;
            }
            int shaderCompare = shader.compareTo(o.getShader());
            if (shaderCompare != 0) {
                return shaderCompare;
            }
//            int textureGroupCompare = Integer.compare(textureGroup, ((Quad) o).getTextureGroup());
//            if (textureGroupCompare != 0) {
//                return textureGroupCompare;
//            }
            return Integer.compare(textureGroup, ((Quad) o).getTextureGroup());
        }
        return 0;
    }

    public void setTextureGroup(int textureGroup, int textureRenderOrder) {
        this.textureGroup = textureGroup;
        this.textureGroupRenderOrder = textureRenderOrder;
        vertices[9 + 0 * VERTEX_STRIDE] = textureRenderOrder;
        vertices[9 + 1 * VERTEX_STRIDE] = textureRenderOrder;
        vertices[9 + 2 * VERTEX_STRIDE] = textureRenderOrder;
        vertices[9 + 3 * VERTEX_STRIDE] = textureRenderOrder;

    }
}
