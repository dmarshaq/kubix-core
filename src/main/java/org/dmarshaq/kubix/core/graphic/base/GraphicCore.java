package org.dmarshaq.kubix.core.graphic.base;

import org.dmarshaq.kubix.core.app.Context;
import org.dmarshaq.kubix.core.graphic.base.layer.Layer;
import org.dmarshaq.kubix.core.graphic.base.text.Glyph;
import org.dmarshaq.kubix.core.graphic.base.texture.Texture;
import org.dmarshaq.kubix.core.graphic.base.texture.TextureAtlas;
import org.dmarshaq.kubix.core.graphic.base.texture.TextureCroppedRegion;
import org.dmarshaq.kubix.core.graphic.data.Line;
import org.dmarshaq.kubix.core.graphic.data.Quad;
import org.dmarshaq.kubix.core.graphic.data.QuadBatch;
import org.dmarshaq.kubix.core.graphic.element.Sprite;
import org.dmarshaq.kubix.core.graphic.element.TextFloat;
import org.dmarshaq.kubix.core.graphic.element.TextInt;
import org.dmarshaq.kubix.core.math.base.AbstractRectangle;
import org.dmarshaq.kubix.core.math.base.MathCore;
import org.dmarshaq.kubix.core.math.function.AbstractFunction;
import org.dmarshaq.kubix.core.math.function.AbstractParametric;
import org.dmarshaq.kubix.core.math.function.Domain;
import org.dmarshaq.kubix.core.math.matrix.Matrix3x3;
import org.dmarshaq.kubix.core.math.matrix.Matrix4x4;
import org.dmarshaq.kubix.core.math.vector.*;

import java.awt.*;
import java.util.HashMap;
import java.util.regex.Pattern;

import static org.dmarshaq.kubix.core.app.Context.SHADER_BASIC_LINE;

public class GraphicCore {

    /**
     * Returns new quad from sprite without additional matrix transformations.
     */
    public static Quad quad(Sprite sprite) {
        return quad(
                new Vector3(MathCore.componentVector(sprite.getPosition(), "xyz")),
                sprite.getWidth(),
                sprite.getHeight(),
                MathCore.vector4(sprite.getColor()),
                sprite.getTexture(),
                sprite.getShader(),
                sprite.getLayer()
        );
    }

    /**
     * Returns new quad from sprite, with additional Matrix4x4 transformation.
     */
    public static Quad quad(Sprite sprite, Matrix4x4 transformation) {
        return quad(
                new Vector3(MathCore.componentVector(sprite.getPosition(), "xyz")),
                sprite.getWidth(),
                sprite.getHeight(),
                MathCore.vector4(sprite.getColor()),
                sprite.getTexture(),
                sprite.getShader(),
                sprite.getLayer(),
                transformation
        );
    }

    /**
     * Returns new quad from sprite, with additional Matrix3x3 transformation.
     */
    public static Quad quad(Sprite sprite, Matrix3x3 transformation) {
        return quad(
                new Vector3(MathCore.componentVector(sprite.getPosition(), "xyz")),
                sprite.getWidth(),
                sprite.getHeight(),
                MathCore.vector4(sprite.getColor()),
                sprite.getTexture(),
                sprite.getShader(),
                sprite.getLayer(),
                MathCore.transform4x4(transformation)
        );
    }

    
    /**
     * Slices texture by grid, by creating new TextureCroppedRegion[].
     */
    public static TextureAtlas sliceTexture(Texture texture, int rows, int columns) {
        float width = (float) texture.getWidth() / columns;
        float height = (float) texture.getHeight() / rows;

        TextureCroppedRegion[] tileSet = new TextureCroppedRegion[rows * columns];

        for(int row = 0; row < rows; row++) {
            for(int col = 0; col < columns; col++) {
                tileSet[col + row * columns] = new TextureCroppedRegion(new Vector2(col * width, row * height), width, height, texture);
            }
        }

        return TextureAtlas.build(tileSet);
    }

    /**
     * Returns new line from vector.
     */
    public static <T extends Vector<Float>> Line line(T vector, Color color) {
        Line line = new Line(Context.SHADERS.get(SHADER_BASIC_LINE), Context.LAYERS.get("gizmos"), 1.0f);

        Vector4 vector4 = MathCore.vector4(color);
        line.setVertex(0, new Vector3(0, 0, 0), vector4);
        line.setVertex(1, new Vector3(MathCore.componentVector(vector, "xyz")), vector4);

        return line;
    }

    /**
     * Returns new line from two points.
     */
    public static <T extends Vector<Float>> Line line(T start, T end, Color color) {
        Line line = new Line(Context.SHADERS.get(SHADER_BASIC_LINE), Context.LAYERS.get("gizmos"), 1.0f);

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
            outline[i] = new Line(Context.SHADERS.get(SHADER_BASIC_LINE), Context.LAYERS.get("gizmos"), 1.0f);
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

            graph[i] = new Line(Context.SHADERS.get(SHADER_BASIC_LINE), Context.LAYERS.get("gizmos"), 1.0f);
            graph[i].setVertex(0, new Vector3(start, function.function(start), 0), vector4);
            graph[i].setVertex(1, new Vector3(end, function.function(end), 0), vector4);

            start = end;
        }
        return graph;
    }

    /**
     * Returns new line array from function.
     */
    public static <T extends AbstractParametric<Float, Vector2>, E extends Domain<Float>> Line[] outline(T parametric, E domain, Color color, int detail) {
        Line[] graph = new Line[detail];
        Vector4 vector4 = MathCore.vector4(color);

        float start = domain.getMin();
        float step = (domain.getMax() - domain.getMin()) / detail;
        float end;
        Vector2 v0;
        Vector2 v1;

        for (int i = 0; i < detail; i++) {
            end = start + step;

            graph[i] = new Line(Context.SHADERS.get(SHADER_BASIC_LINE), Context.LAYERS.get("gizmos"), 1.0f);

            v0 = parametric.parametric(start);
            v1 = parametric.parametric(end);
            graph[i].setVertex(0, new Vector3(v0.x(), v0.y(), 0), vector4);
            graph[i].setVertex(1, new Vector3(v1.x(), v1.y(), 0), vector4);

            start = end;
        }
        return graph;
    }


    /**
     * Returns batch of quad from float text object.
     * Usually used to render text in the world.
     */
    public static QuadBatch quadBatch(TextFloat text, float pixelsPerUnit, Color color) {
        // Getting color
        Vector4 vcolor = MathCore.vector4(color);


        HashMap<Character, Glyph> atlas = text.getFont().getAtlas();
        QuadBatch result = new QuadBatch((int) Pattern.compile("\\S").matcher(text.getCharSequence()).results().count(), text.getShader(), text.getLayer(), text.getFont().getTexture());

        Vector3 cursor = new Vector3(MathCore.componentVector(text.getCursorOrigin(), "xyz"));

        int pointer = 0;

        for (int i = pointer; i < text.getCharSequence().length(); i++) {
            char c = text.getCharSequence().charAt(i);
            switch (c) {
                case ('\r'):
                    // Return line operator, moving cursor back to the beginning of the line
                    cursor.getArrayOfValues()[0] = text.getCursorOrigin().x();
                    break;
                case ('\n'):
                    // New line operator, moving cursor down by one line
                    cursor.getArrayOfValues()[1] -= text.getFont().getLineHeight() / pixelsPerUnit;
                    break;
                case (' '):
                    // Advancing, skipping loading because it is just space
                    cursor.getArrayOfValues()[0] += (float) atlas.get(c).getXAdvance() / pixelsPerUnit;
                    break;
                default:
                    // Regular loading
                    Glyph data = atlas.get(c);
                    TextureCroppedRegion textureCroppedRegion = data.getTextureCroppedRegion();

                    // Creating quad rect for character
                    Vector3 offset = new Vector3((float) data.getXOffset() / pixelsPerUnit, -(data.getYOffset() + textureCroppedRegion.getHeight()) / pixelsPerUnit, 0).add(cursor);
                    float width = textureCroppedRegion.getWidth() / pixelsPerUnit;
                    float height = textureCroppedRegion.getHeight() / pixelsPerUnit;

                    // Texture region percent position, width, height
                    Vector2 percentPosition = textureCroppedRegion.getPercentPosition();
                    float percentWidth = textureCroppedRegion.getPercentWidth();
                    float percentHeight = textureCroppedRegion.getPercentHeight();

                    // Default normal from 2D quad
                    Vector3 normal = MathCore.forward().negate();

                    // Setting vertices through 0 to 3.
                    result.setVertex(pointer, 0, offset,                                             vcolor, percentPosition,                                                  normal);
                    result.setVertex(pointer, 1, new Vector3(width, 0, 0).add(offset),         vcolor, new Vector2(percentWidth, 0).add(percentPosition),           normal);
                    result.setVertex(pointer, 2, new Vector3(0, height, 0).add(offset),        vcolor, new Vector2(0, percentHeight).add(percentPosition),          normal);
                    result.setVertex(pointer, 3, new Vector3(width, height, 0).add(offset),       vcolor, new Vector2(percentWidth, percentHeight).add(percentPosition),   normal);

                    // Advancing
                    cursor.getArrayOfValues()[0] += (float) data.getXAdvance() / pixelsPerUnit;
                    pointer++;
                    break;
            }
        }

        return result;
    }

    /**
     * Returns batch of quad from int text object.
     * Usually used to render text on the screen.
     */
    public static QuadBatch quadBatch(TextInt text, float scale, Color color) {
        // Getting color
        Vector4 vcolor = MathCore.vector4(color);

        HashMap<Character, Glyph> atlas = text.getFont().getAtlas();
        QuadBatch result = new QuadBatch((int) Pattern.compile("\\S").matcher(text.getCharSequence()).results().count(), text.getShader(), text.getLayer(), text.getFont().getTexture());

        Vector3 cursor = new Vector3(text.getCursorOrigin().x(), text.getCursorOrigin().y(), 0);

        int pointer = 0;

        for (int i = pointer; i < text.getCharSequence().length(); i++) {
            char c = text.getCharSequence().charAt(i);
            switch (c) {
                case ('\r'):
                    // Return line operator, moving cursor back to the beginning of the line
                    cursor.getArrayOfValues()[0] = text.getCursorOrigin().x();
                    break;
                case ('\n'):
                    // New line operator, moving cursor down by one line
                    cursor.getArrayOfValues()[1] -= text.getFont().getLineHeight() * scale;
                    break;
                case (' '):
                    // Advancing, skipping loading because it is just empty space
                    cursor.getArrayOfValues()[0] += atlas.get(c).getXAdvance() * scale;
                    break;
                default:
                    // Regular data loading
                    Glyph data = atlas.get(c);
                    TextureCroppedRegion textureCroppedRegion = data.getTextureCroppedRegion();

                    // Creating quad rect for character
                    Vector3 offset = new Vector3(data.getXOffset() * scale, -(data.getYOffset() + textureCroppedRegion.getHeight()) * scale, 0).add(cursor);
                    float width = textureCroppedRegion.getWidth() * scale;
                    float height = textureCroppedRegion.getHeight() * scale;

                    // Texture region percent position, width, height
                    Vector2 percentPosition = textureCroppedRegion.getPercentPosition();
                    float percentWidth = textureCroppedRegion.getPercentWidth();
                    float percentHeight = textureCroppedRegion.getPercentHeight();

                    // Default normal from 2D quad
                    Vector3 normal = MathCore.forward().negate();

                    // Setting vertices through 0 to 3.
                    result.setVertex(pointer, 0, offset,                                             vcolor, percentPosition,                                                  normal);
                    result.setVertex(pointer, 1, new Vector3(width, 0, 0).add(offset),         vcolor, new Vector2(percentWidth, 0).add(percentPosition),           normal);
                    result.setVertex(pointer, 2, new Vector3(0, height, 0).add(offset),        vcolor, new Vector2(0, percentHeight).add(percentPosition),          normal);
                    result.setVertex(pointer, 3, new Vector3(width, height, 0).add(offset),       vcolor, new Vector2(percentWidth, percentHeight).add(percentPosition),   normal);

                    // Advancing
                    cursor.getArrayOfValues()[0] += data.getXAdvance() * scale;
                    pointer++;
                    break;
            }
        }

        return result;
    }

    public static Quad quad(Vector3 offset, float width, float height, Vector4 color, TextureCroppedRegion texture, Shader shader, Layer layer) {
        // Creating quad with shader defined in sprite.
        Quad quad = new Quad(shader, layer, texture.getHost());

        // Texture region percent position, width, height
        Vector2 percentPosition = texture.getPercentPosition();
        float percentWidth = texture.getPercentWidth();
        float percentHeight = texture.getPercentHeight();

        // Default 2D normal
        Vector3 normal = MathCore.forward().negate();

        // Setting vertices through 0 to 3.
        quad.setVertex(0, 0, offset,                                       color, percentPosition,                                                  normal);
        quad.setVertex(0, 1, new Vector3(width, 0, 0).add(offset),         color, new Vector2(percentWidth, 0).add(percentPosition),      normal);
        quad.setVertex(0, 2, new Vector3(0, height, 0).add(offset),        color, new Vector2(0, percentHeight).add(percentPosition),     normal);
        quad.setVertex(0, 3, new Vector3(width, height, 0).add(offset),    color, new Vector2(percentWidth, percentHeight).add(percentPosition), normal);

        return quad;
    }

    public static Quad quad(Vector3 offset, float width, float height, Vector4 color, TextureCroppedRegion texture, Shader shader, Layer layer, Matrix4x4 matrix4x4) {
        // Creating quad with shader defined in sprite.
        Quad quad = new Quad(shader, layer, texture.getHost());

        // Calculating vertex positions
        Vector3 vertex0 = matrix4x4.localToWorld(offset);
        Vector3 vertex1 = matrix4x4.localToWorld(new Vector3(width, 0, 0).add(offset));
        Vector3 vertex2 = matrix4x4.localToWorld(new Vector3(0, height, 0).add(offset));
        Vector3 vertex3 = matrix4x4.localToWorld(new Vector3(width, height, 0).add(offset));

        // Texture region percent position, width, height
        Vector2 percentPosition = texture.getPercentPosition();
        float percentWidth = texture.getPercentWidth();
        float percentHeight = texture.getPercentHeight();

        // Default 2D normal
        Vector3 normal = matrix4x4.forward().negate();

        // Setting vertices through 0 to 3.
        quad.setVertex(0, 0, vertex0,  color, percentPosition, normal);
        quad.setVertex(0, 1, vertex1,  color, new Vector2(percentWidth, 0).add(percentPosition), normal);
        quad.setVertex(0, 2, vertex2,  color, new Vector2(0, percentHeight).add(percentPosition), normal);
        quad.setVertex(0, 3, vertex3,  color, new Vector2(percentWidth, percentHeight).add(percentPosition), normal);

        return quad;
    }


}
