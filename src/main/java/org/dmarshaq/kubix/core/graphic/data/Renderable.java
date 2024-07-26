package org.dmarshaq.kubix.core.graphic.data;

import org.dmarshaq.kubix.core.graphic.base.layer.Layer;
import org.dmarshaq.kubix.core.graphic.base.Shader;

public interface Renderable extends Comparable<Renderable> {

    /**
     * Returns vertex data of renderable object.
     */
    float[] getVertexData();

    /**
     * Returns shader key that points to the shader that is used in render to draw renderable object.
     */
    Shader getShader();

    /**
     * Returns layer key that points to the layer on which render will draw renderable object.
     */
    Layer getLayer();
}
