package org.dmarshaq.kubix.core.graphic.data;

import lombok.Getter;
import lombok.ToString;
import org.dmarshaq.kubix.core.graphic.base.Layer;
import org.dmarshaq.kubix.core.graphic.base.Shader;
import org.dmarshaq.kubix.core.math.vector.Vector3;
import org.dmarshaq.kubix.core.math.vector.Vector4;

/**
 * Line is a wrapper class for simple vertex float array that describes line's geometry.
 * It contains float array for storing each of 2 vertices data, as well as shader that is used in render to draw the line.
 * It also stores layer it supposed to render on.
 */
@ToString
public class Line implements Renderable {
    public static final int VERTEX_STRIDE = 7;
    public static final int VERTICES = 2;
    public static final int STRIDE = VERTEX_STRIDE * VERTICES;

    private final float[] vertices;
    private final Shader shader;
    private final Layer layer;
    @Getter
    private final float width;


    /**
     * Builds the line with float array describing all 2 vertices each taking 7 floats in array.
     * Note: each vertex properties needs to also be specified, through setVertex().
     */
    public Line(Shader shader, Layer layer, float width) {
        this.shader = shader;
        this.layer = layer;
        this.width = width;
        vertices = new float[VERTICES * VERTEX_STRIDE];
    }

    /**
     * Sets any line vertex from 0 to 1 to specified properties.
     */
    public void setVertex(int vertex, Vector3 position, Vector4 color) {
        vertices[0 + vertex * VERTEX_STRIDE] = position.x();
        vertices[1 + vertex * VERTEX_STRIDE] = position.y();
        vertices[2 + vertex * VERTEX_STRIDE] = position.z();
        vertices[3 + vertex * VERTEX_STRIDE] = color.x();
        vertices[4 + vertex * VERTEX_STRIDE] = color.y();
        vertices[5 + vertex * VERTEX_STRIDE] = color.z();
        vertices[6 + vertex * VERTEX_STRIDE] = color.w();
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
    public int compareTo(Renderable o) {
        if (o instanceof Line) {
            int layerCompare = layer.compareTo(o.getLayer());
            if (layerCompare != 0) {
                return layerCompare;
            }
            int shaderCompare = shader.compareTo(o.getShader());
            if (shaderCompare != 0) {
                return shaderCompare;
            }
            return Float.compare(width, ((Line) o).getWidth());
        }
        return 0;
    }
}
