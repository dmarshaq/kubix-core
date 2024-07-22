package org.dmarshaq.kubix.core.graphic.base;

import org.dmarshaq.kubix.core.app.Context;
import org.dmarshaq.kubix.core.graphic.data.Line;
import org.dmarshaq.kubix.core.graphic.data.Quad;
import org.dmarshaq.kubix.core.graphic.element.Sprite;
import org.dmarshaq.kubix.core.graphic.base.Texture;
import org.dmarshaq.kubix.core.graphic.base.TextureCroppedRegion;
import org.dmarshaq.kubix.core.math.AbstractRectangle;
import org.dmarshaq.kubix.core.math.MathCore;
import org.dmarshaq.kubix.core.math.function.AbstractFunction;
import org.dmarshaq.kubix.core.math.function.Domain;
import org.dmarshaq.kubix.core.math.matrix.Matrix3x3;
import org.dmarshaq.kubix.core.math.matrix.Matrix4x4;
import org.dmarshaq.kubix.core.math.vector.*;

import java.awt.*;

public class GraphicCore {

    /**
     * Returns new quad from sprite without additional matrix transformations.
     */
    public static Quad quad(Sprite sprite) {
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
        Texture texture = null;
        int texIndex = 0;
        Vector2 percentPosition;
        float percentWidth;
        float percentHeight;

        // Calculating texture index.
        texture = sprite.getTexture().getHost();
        texIndex = texture.ordinal() % 32;

        width = sprite.getWidth();
        height = sprite.getHeight();
        percentPosition = sprite.getTexture().getPercentPosition();
        percentWidth = sprite.getTexture().getPercentWidth();
        percentHeight = sprite.getTexture().getPercentHeight();

        // Getting normal vector.
        Vector3 normal = MathCore.forward().negate();

        // Creating quad with shader defined in sprite.
        Quad quad = new Quad(sprite.getShader(), sprite.getLayer(), texture);

        // Setting vertices through 0 to 3.
        quad.setVertex(0, position,                                    color, percentPosition,                                               texIndex, normal);
        quad.setVertex(1, new Vector3(width, 0, 0).add(position),      color, new Vector2(percentWidth, 0).add(percentPosition),             texIndex, normal);
        quad.setVertex(2, new Vector3(0, height, 0).add(position),     color, new Vector2(0, percentHeight).add(percentPosition),            texIndex, normal);
        quad.setVertex(3, new Vector3(width, height, 0).add(position), color, new Vector2(percentWidth, percentHeight).add(percentPosition), texIndex, normal);

        return quad;
    }

    /**
     * Returns new quad from sprite, with additional Matrix4x4 transformation.
     */
    public static Quad quad(Sprite sprite, Matrix4x4 transformation) {
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
        Texture texture = null;
        Vector2 percentPosition;
        float percentWidth;
        float percentHeight;
        int texIndex = 0;

        // Calculating texture index.
        texture = sprite.getTexture().getHost();
        texIndex = texture.ordinal() % 32;

        width = sprite.getWidth();
        height = sprite.getHeight();
        percentPosition = sprite.getTexture().getPercentPosition();
        percentWidth = sprite.getTexture().getPercentWidth();
        percentHeight = sprite.getTexture().getPercentHeight();

        // Getting vertices positions by matrix vector multiplication.
        Vector4 position = new Vector4(sprite.getPosition().x(), sprite.getPosition().y(), 0.0f, 1.0f);

        Vector3 vertex0 = new Vector3(MathCore.multiplication(transformation, position));
        Vector3 vertex1 = new Vector3(MathCore.multiplication(transformation, new Vector4(width, 0, 0, 0).add(position)));
        Vector3 vertex2 = new Vector3(MathCore.multiplication(transformation, new Vector4(0, height, 0, 0).add(position)));
        Vector3 vertex3 = new Vector3(MathCore.multiplication(transformation, new Vector4(width, height, 0, 0).add(position)));

        // Getting normal vector.
        Vector3 normal = transformation.axisVectorZ().normalize().negate();

        // Creating quad with shader defined in sprite.
        Quad quad = new Quad(sprite.getShader(), sprite.getLayer(), texture);

        // Setting vertices through 0 to 3.
        quad.setVertex(0, vertex0, color, percentPosition,                                               texIndex, normal);
        quad.setVertex(1, vertex1, color, new Vector2(percentWidth, 0).add(percentPosition),             texIndex, normal);
        quad.setVertex(2, vertex2, color, new Vector2(0, percentHeight).add(percentPosition),            texIndex, normal);
        quad.setVertex(3, vertex3, color, new Vector2(percentWidth, percentHeight).add(percentPosition), texIndex, normal);

        return quad;
    }

    /**
     * Returns new quad from sprite, with additional Matrix3x3 transformation.
     */
    public static Quad quad(Sprite sprite, Matrix3x3 transformation) {
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
        Texture texture = null;
        int texIndex = 0;
        Vector2 percentPosition;
        float percentWidth;
        float percentHeight;

        // Calculating texture index.
        texture = sprite.getTexture().getHost();
        texIndex = texture.ordinal() % 32;

        width = sprite.getWidth();
        height = sprite.getHeight();
        percentPosition = sprite.getTexture().getPercentPosition();
        percentWidth = sprite.getTexture().getPercentWidth();
        percentHeight = sprite.getTexture().getPercentHeight();

        // Getting vertices positions by matrix vector multiplication.
        Vector3 position = new Vector3(sprite.getPosition().x(), sprite.getPosition().y(), 1.0f);

        Vector3 vertex0 = new Vector3(MathCore.componentVector(MathCore.multiplication(transformation, position), "xyz"));
        Vector3 vertex1 = new Vector3(MathCore.componentVector(MathCore.multiplication(transformation, new Vector3(width, 0, 0).add(position)), "xyz"));
        Vector3 vertex2 = new Vector3(MathCore.componentVector(MathCore.multiplication(transformation, new Vector3(0, height, 0).add(position)), "xyz"));
        Vector3 vertex3 = new Vector3(MathCore.componentVector(MathCore.multiplication(transformation, new Vector3(width, height, 0).add(position)), "xyz"));

        // Getting normal vector.
        Vector3 normal = MathCore.forward().negate();

        // Creating quad with shader defined in sprite.
        Quad quad = new Quad(sprite.getShader(), sprite.getLayer(), texture);

        // Setting vertices through 0 to 3.
        quad.setVertex(0, vertex0, color, percentPosition,                                               texIndex, normal);
        quad.setVertex(1, vertex1, color, new Vector2(percentWidth, 0).add(percentPosition),             texIndex, normal);
        quad.setVertex(2, vertex2, color, new Vector2(0, percentHeight).add(percentPosition),            texIndex, normal);
        quad.setVertex(3, vertex3, color, new Vector2(percentWidth, percentHeight).add(percentPosition), texIndex, normal);

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

    /**
     * Returns new line from vector.
     */
    public static <T extends Vector<Float>> Line line(T vector, Color color) {
        Line line = new Line(Context.shaders().get("basic_line"), Context.layers().get("gizmos"), 1.0f);

        Vector4 vector4 = MathCore.vector4(color);
        line.setVertex(0, new Vector3(0, 0, 0), vector4);
        line.setVertex(1, new Vector3(MathCore.componentVector(vector, "xyz")), vector4);

        return line;
    }

    /**
     * Returns new line from two points.
     */
    public static <T extends Vector<Float>> Line line(T start, T end, Color color) {
        Line line = new Line(Context.shaders().get("basic_line"), Context.layers().get("gizmos"), 1.0f);

        Vector4 vector4 = MathCore.vector4(color);
        line.setVertex(0, new Vector3(MathCore.componentVector(start, "xyz")), vector4);
        line.setVertex(1, new Vector3(MathCore.componentVector(end, "xyz")), vector4);

        return line;
    }

    /**
     * Returns new line array from rectangle.
     */
    public static <T extends AbstractRectangle<Float, ?>> Line[] outline(T rectangle, Color color) {
        Line[] outline = new Line[4];
        Vector4 vector4 = MathCore.vector4(color);

        for (int i = 0; i < outline.length; i++) {
            outline[i] = new Line(Context.shaders().get("basic_line"), Context.layers().get("gizmos"), 1.0f);
        }

        Vector3 start = new Vector3(MathCore.componentVector(rectangle.getPosition(), "xyz"));
        Vector3 end = new Vector3(rectangle.getWidth(), 0, 0).add(start);

        outline[0].setVertex(0, start, vector4);
        outline[0].setVertex(1, end, vector4);

        start = end;
        end = new Vector3(0, rectangle.getHeight(), 0).add(start);

        outline[1].setVertex(0, start, vector4);
        outline[1].setVertex(1, end, vector4);

        start = end;
        end = new Vector3(-rectangle.getWidth(), 0, 0).add(start);

        outline[2].setVertex(0, start, vector4);
        outline[2].setVertex(1, end, vector4);

        start = end;
        end = new Vector3(0, -rectangle.getHeight(), 0).add(start);

        outline[3].setVertex(0, start, vector4);
        outline[3].setVertex(1, end, vector4);

        return outline;
    }

    /**
     * Returns new line array from function.
     */
    public static <T extends AbstractFunction<Float>, E extends Domain<Float>> Line[] outline(T function, E domain, Color color, int detail) {
        Line[] graph = new Line[detail];
        Vector4 vector4 = MathCore.vector4(color);

        float start = domain.getMin();
        float step = (domain.getMax() - domain.getMin()) / detail;
        float end;

        for (int i = 0; i < detail; i++) {
            end = start + step;

            graph[i] = new Line(Context.shaders().get("basic_line"), Context.layers().get("gizmos"), 1.0f);
            graph[i].setVertex(0, new Vector3(start, function.function(start), 0), vector4);
            graph[i].setVertex(1, new Vector3(end, function.function(end), 0), vector4);

            start = end;
        }
        return graph;
    }


}
