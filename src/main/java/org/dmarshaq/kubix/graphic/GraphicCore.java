package org.dmarshaq.kubix.graphic;

import org.dmarshaq.kubix.core.graphic.Texture;
import org.dmarshaq.kubix.core.graphic.TextureCroppedRegion;
import org.dmarshaq.kubix.graphic.render.Quad;
import org.dmarshaq.kubix.math.MathCore;
import org.dmarshaq.kubix.math.matrix.Matrix2x3;
import org.dmarshaq.kubix.math.matrix.Matrix3x4;
import org.dmarshaq.kubix.math.vector.Vector2;
import org.dmarshaq.kubix.math.vector.Vector3;
import org.dmarshaq.kubix.math.vector.Vector4;

public class GraphicCore {

    /**
     * Returns new quad from sprite without additional matrix transformations.
     */
    public static Quad quad(Sprite sprite) {
        // Creating quad with shader defined in sprite.
        Quad quad = new Quad(sprite.getShader(), sprite.getLayer());

        // Getting origin position of the sprite.
        Vector3 position = new Vector3(MathCore.componentVector(sprite.getPosition(), "xyz"));

        // Getting color if it is not null, otherwise setting to pure white.
        Vector4 color;
        if (sprite.getColor() != null)
            color = MathCore.vector4(sprite.getColor());
        else
            color = new Vector4(1.0f, 1.0f, 1.0f, 1.0f);

        // Getting sprite width and height if texture is not null, otherwise leaving them to 1.0f.
        float width;
        float height;

        // Getting texture percent position, width and height to find texture coordinates.
        Vector2 percentPosition;
        float percentWidth;
        float percentHeight;

        if (sprite.getTexture() != null) {
            width = sprite.getWidth();
            height = sprite.getHeight();
            percentPosition = sprite.getTexture().getPercentPosition();
            percentWidth = sprite.getTexture().getPercentWidth();
            percentHeight = sprite.getTexture().getPercentHeight();
        }
        else {
            width = 1.0f;
            height = 1.0f;
            percentPosition = new Vector2(0, 0);
            percentWidth = 1.0f;
            percentHeight = 1.0f;
        }

        // Setting vertices through 0 to 3.
        quad.setVertex(0, position,                                    color, percentPosition,                                               0);
        quad.setVertex(1, new Vector3(width, 0, 0).add(position),      color, new Vector2(percentWidth, 0).add(percentPosition),             0);
        quad.setVertex(2, new Vector3(0, height, 0).add(position),     color, new Vector2(0, percentHeight).add(percentPosition),            0);
        quad.setVertex(3, new Vector3(width, height, 0).add(position), color, new Vector2(percentWidth, percentHeight).add(percentPosition), 0);

        return quad;
    }

    /**
     * Returns new quad from sprite, with additional Matrix3x4 transformation.
     */
    public static Quad quad(Sprite sprite, Matrix3x4 transformation) {
        // Creating quad with shader defined in sprite.
        Quad quad = new Quad(sprite.getShader(), sprite.getLayer());

        // Getting color if it is not null, otherwise setting to pure white.
        Vector4 color;
        if (sprite.getColor() != null)
            color = MathCore.vector4(sprite.getColor());
        else
            color = new Vector4(1.0f, 1.0f, 1.0f, 1.0f);

        // Getting sprite width and height if texture is not null, otherwise leaving them to 1.0f.
        float width;
        float height;

        // Getting texture percent position, width and height to find texture coordinates.
        Vector2 percentPosition;
        float percentWidth;
        float percentHeight;

        if (sprite.getTexture() != null) {
            width = sprite.getWidth();
            height = sprite.getHeight();
            percentPosition = sprite.getTexture().getPercentPosition();
            percentWidth = sprite.getTexture().getPercentWidth();
            percentHeight = sprite.getTexture().getPercentHeight();
        }
        else {
            width = 1.0f;
            height = 1.0f;
            percentPosition = new Vector2(0, 0);
            percentWidth = 1.0f;
            percentHeight = 1.0f;
        }

        // Getting vertices positions by matrix vector multiplication.
        Vector4 position = new Vector4(sprite.getPosition().x(), sprite.getPosition().y(), 0.0f, 1.0f);

        Vector3 vertex0 = new Vector3(MathCore.multiplication(transformation, position));
        Vector3 vertex1 = new Vector3(MathCore.multiplication(transformation, new Vector4(width, 0, 0, 0).add(position)));
        Vector3 vertex2 = new Vector3(MathCore.multiplication(transformation, new Vector4(0, height, 0, 0).add(position)));
        Vector3 vertex3 = new Vector3(MathCore.multiplication(transformation, new Vector4(width, height, 0, 0).add(position)));


        // Setting vertices through 0 to 3.
        quad.setVertex(0, vertex0, color, percentPosition,                                               0);
        quad.setVertex(1, vertex1, color, new Vector2(percentWidth, 0).add(percentPosition),             0);
        quad.setVertex(2, vertex2, color, new Vector2(0, percentHeight).add(percentPosition),            0);
        quad.setVertex(3, vertex3, color, new Vector2(percentWidth, percentHeight).add(percentPosition), 0);

        return quad;
    }

    /**
     * Returns new quad from sprite, with additional Matrix2x3 transformation.
     */
    public static Quad quad(Sprite sprite, Matrix2x3 transformation) {
        // Creating quad with shader defined in sprite.
        Quad quad = new Quad(sprite.getShader(), sprite.getLayer());

        // Getting color if it is not null, otherwise setting to pure white.
        Vector4 color;
        if (sprite.getColor() != null)
            color = MathCore.vector4(sprite.getColor());
        else
            color = new Vector4(1.0f, 1.0f, 1.0f, 1.0f);

        // Getting sprite width and height if texture is not null, otherwise leaving them to 1.0f.
        float width;
        float height;

        // Getting texture percent position, width and height to find texture coordinates.
        Vector2 percentPosition;
        float percentWidth;
        float percentHeight;

        if (sprite.getTexture() != null) {
            width = sprite.getWidth();
            height = sprite.getHeight();
            percentPosition = sprite.getTexture().getPercentPosition();
            percentWidth = sprite.getTexture().getPercentWidth();
            percentHeight = sprite.getTexture().getPercentHeight();
        }
        else {
            width = 1.0f;
            height = 1.0f;
            percentPosition = new Vector2(0, 0);
            percentWidth = 1.0f;
            percentHeight = 1.0f;
        }

        // Getting vertices positions by matrix vector multiplication.
        Vector3 position = new Vector3(sprite.getPosition().x(), sprite.getPosition().y(), 1.0f);

        Vector3 vertex0 = new Vector3(MathCore.componentVector(MathCore.multiplication(transformation, position), "xyz"));
        Vector3 vertex1 = new Vector3(MathCore.componentVector(MathCore.multiplication(transformation, new Vector3(width, 0, 0).add(position)), "xyz"));
        Vector3 vertex2 = new Vector3(MathCore.componentVector(MathCore.multiplication(transformation, new Vector3(0, height, 0).add(position)), "xyz"));
        Vector3 vertex3 = new Vector3(MathCore.componentVector(MathCore.multiplication(transformation, new Vector3(width, height, 0).add(position)), "xyz"));


        // Setting vertices through 0 to 3.
        quad.setVertex(0, vertex0, color, percentPosition,                                               0);
        quad.setVertex(1, vertex1, color, new Vector2(percentWidth, 0).add(percentPosition),             0);
        quad.setVertex(2, vertex2, color, new Vector2(0, percentHeight).add(percentPosition),            0);
        quad.setVertex(3, vertex3, color, new Vector2(percentWidth, percentHeight).add(percentPosition), 0);

        return quad;
    }

    /**
     * Slices texture by grid, by creating new TextureCroppedRegion[].
     */
    public static void sliceTexture(Texture texture, int rows, int columns) {
        float width = (float) texture.getWidth() / columns;
        float height = (float) texture.getHeight() / rows;

        TextureCroppedRegion[] tileSet = new TextureCroppedRegion[rows * columns];

        for(int row = 0; row < rows; row++) {
            for(int col = 0; col < columns; col++) {
                tileSet[col + row * columns] = new TextureCroppedRegion(new Vector2(col * width, row * height), width, height, texture);
            }
        }

        texture.setTileSet(tileSet);
    }
}
