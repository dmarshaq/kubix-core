package org.dmarshaq.kubix.graphic.render;

import lombok.Setter;
import lombok.ToString;
import org.dmarshaq.kubix.core.graphic.Shader;
import org.dmarshaq.kubix.math.vector.Vector2;
import org.dmarshaq.kubix.math.vector.Vector3;
import org.dmarshaq.kubix.math.vector.Vector4;

import java.util.List;

/**
 * Quad is a wrapper class for simple vertex float array that describes quad's figure.
 * It contains float array for storing each of 4 vertices data, as well as shader that is used in render to draw the quad.
 * It also stores layer it supposed to render on.
 */
@ToString
public class Quad implements Renderable {
    public static final int VERTEX_STRIDE = 10;

    private final float[] vertices;
    private final Shader shader;
    private final Layer layer;

    /**
     * Builds the quad with float array describing all 4 vertices each taking 10 floats in array.
     * ShaderType needs to be specified.
     * Note: each vertex properties needs to also specified.
     */
    public Quad(Shader shader, Layer layer) {
        this.shader = shader;
        this.layer = layer;
        vertices = new float[4 * VERTEX_STRIDE];
    }

    /**
     * Sets any quad vertex from 0 to 3 to specified properties.
     */
    public void setVertex(int vertex, Vector3 position, Vector4 color, Vector2 texCoordinates, int texSlotIndex) {
        vertices[0 + vertex * VERTEX_STRIDE] = position.x();
        vertices[1 + vertex * VERTEX_STRIDE] = position.y();
        vertices[2 + vertex * VERTEX_STRIDE] = position.z();
        vertices[3 + vertex * VERTEX_STRIDE] = color.x();
        vertices[4 + vertex * VERTEX_STRIDE] = color.y();
        vertices[5 + vertex * VERTEX_STRIDE] = color.z();
        vertices[6 + vertex * VERTEX_STRIDE] = color.w();
        vertices[7 + vertex * VERTEX_STRIDE] = texCoordinates.x();
        vertices[8 + vertex * VERTEX_STRIDE] = texCoordinates.y();
        vertices[9 + vertex * VERTEX_STRIDE] = texSlotIndex;
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
}
