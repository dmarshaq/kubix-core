package org.dmarshaq.kubix.graphic.render;

import org.dmarshaq.kubix.core.graphic.Shader;

public interface Renderable {

    /**
     * Returns vertex data of renderable object.
     */
    float[] getVertexData();

    /**
     * Returns shader that is used in render to draw renderable object.
     */
    Shader getShader();
}
