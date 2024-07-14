package org.dmarshaq.kubix.graphic;

import org.dmarshaq.kubix.graphic.render.Quad;
import org.dmarshaq.kubix.math.MathCore;
import org.dmarshaq.kubix.math.vector.Vector;
import org.dmarshaq.kubix.math.vector.Vector2;
import org.dmarshaq.kubix.math.vector.Vector3;
import org.dmarshaq.kubix.math.vector.Vector4;

public class GraphicCore {

    /**
     * Returns new textured quad from sprite without additional matrix transformations.
     */
    public static Quad quad(Sprite sprite) {
        Quad quad = new Quad(sprite.getShader());

        Vector3 position = new Vector3(MathCore.componentVector(sprite.getPosition(), "xyz"));

        float width = 1.0f;
        float height = 1.0f;
        if (sprite.getTexture() != null) {
            width = sprite.getWidth();
            height = sprite.getHeight();
        }

        Vector4 color;
        if (sprite.getColor() != null)
            color = MathCore.vector4(sprite.getColor());
        else
            color = new Vector4(1f, 1f, 1f, 1f);

        quad.setVertex(0, position,                                                             color, new Vector2(0, 0), 0);
        quad.setVertex(1, position.add(new Vector3(width, 0, 0)),                   color, new Vector2(1, 0), 0);
        quad.setVertex(2, position.add(new Vector3(-width, height, 0)), color, new Vector2(0, 1), 0);
        quad.setVertex(3, position.add(new Vector3(width, 0, 0)),                   color, new Vector2(1, 1), 0);

        return quad;
    }
}
