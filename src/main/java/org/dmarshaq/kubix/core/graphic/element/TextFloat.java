package org.dmarshaq.kubix.core.graphic.element;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.dmarshaq.kubix.core.graphic.base.Shader;
import org.dmarshaq.kubix.core.graphic.base.layer.Layer;
import org.dmarshaq.kubix.core.graphic.base.text.Font;
import org.dmarshaq.kubix.core.math.vector.Vector2;

@Getter
@Setter
@ToString
public class TextFloat implements AbstractText<Float, Vector2> {
    private final Vector2 origin;
    private CharSequence charSequence;
    private Font font;
    private Shader shader;
    private Layer layer;

    public TextFloat(Vector2 origin, CharSequence charSequence, Font font, Shader shader, Layer layer) {
        this.origin = origin;
        this.charSequence = charSequence;
        this.font = font;
        this.shader = shader;
        this.layer = layer;
    }

    @Override
    public CharSequence getCharSequence() {
        return charSequence;
    }

    @Override
    public Vector2 getCursorOrigin() {
        return origin;
    }

//    public TextMetrics<Float, Vector2> getMetrics() {
//        char[] chars = text.toCharArray();
//        HashMap<Character, CharacterData> atlas = font.getAtlas();
//
//        Vector2 cursor = new Vector2(position.x(), position.y());
//
//        float maxWidth = 0;
//        float lineCount = 1;
//
//        for (int i = 0; i < chars.length; i++) {
//            // Regular loading
//            CharacterData data = atlas.get(chars[i]);
//
//            // Advancing
//            cursor.getArrayOfValues()[0] += (float) data.getXAdvance() / Context.getUnitSize();
//
//            // Checking if it ends the line with the word
//            if (chars[i] == ' ') {
//                int count = 1;
//                float length = 0;
//                while(i + count < chars.length && chars[i + count] != ' ') {
//                    length += (float) atlas.get(chars[i + count]).getXAdvance() / Context.getUnitSize();
//                    count++;
//                }
//                if (length + cursor.getArrayOfValues()[0] >= lineLimit) {
//                    if (maxWidth < cursor.getArrayOfValues()[0] - position.x() - (float) atlas.get(' ').getXAdvance() / Context.getUnitSize()) {
//                        maxWidth = cursor.getArrayOfValues()[0] - position.x() - (float) atlas.get(' ').getXAdvance() / Context.getUnitSize();
//                    }
//                    lineCount++;
//
//                    cursor.getArrayOfValues()[0] = position.x();
//                    cursor.getArrayOfValues()[1] -= (float) font.getLineHeight() / Context.getUnitSize();
//                }
//            }
//        }
//        float height = lineCount * ((float) font.getLineHeight() / Context.getUnitSize());
//        return new TextMetrics<>(new Vector2(0, -height).add(position), maxWidth, height);
//    }
}
