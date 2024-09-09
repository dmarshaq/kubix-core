package org.dmarshaq.kubix.core.graphic.element;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.dmarshaq.kubix.core.graphic.base.Shader;
import org.dmarshaq.kubix.core.graphic.base.layer.Layer;
import org.dmarshaq.kubix.core.graphic.base.text.Font;
import org.dmarshaq.kubix.core.graphic.base.text.Glyph;
import org.dmarshaq.kubix.core.graphic.data.CachedGlyph;
import org.dmarshaq.kubix.core.graphic.data.TextMetrics;
import org.dmarshaq.kubix.core.math.vector.Vector2;
import org.dmarshaq.kubix.core.ui.math.Vector2Int;

import java.util.ArrayList;
import java.util.HashMap;

@Getter
@ToString
public class TextFloat implements AbstractText<Float, Vector2> {
    private TextMetrics textMetrics;
    private final Vector2 origin;
    private CharSequence charSequence;
    private Font font;
    @Setter
    private Shader shader;
    @Setter
    private Layer layer;

    public TextFloat(Vector2 origin, Font font, Shader shader, Layer layer) {
        this.origin = origin;
        this.charSequence = "";
        this.font = font;
        this.shader = shader;
        this.layer = layer;
    }

    public void setCharSequence(CharSequence charSequence) {
        this.charSequence = charSequence;
        reCacheText();
    }

    public void setFont(Font font) {
        this.font = font;
        reCacheText();
    }

    private void reCacheText() {
        HashMap<Character, Glyph> atlas = font.getAtlas();
        Vector2 cursor = new Vector2(0, 0);

        float width = 0;
        float height = font.getLineHeight();
        int length = charSequence.length() + 1;
        CachedGlyph[] cachedGlyphs = new CachedGlyph[length];
        ArrayList<Integer> endLineIndices = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            // Auto place termination at the end of the text;
            char c = i + 1 >= length ? '\0' : charSequence.charAt(i);
            // Regular data loading
            Glyph data = atlas.get(c);
            switch (c) {
                case ('\n'):
                    // New line operator, moving cursor down by one line
                    cachedGlyphs[i] = new CachedGlyph(cursor.getArrayOfValues()[0], cursor.getArrayOfValues()[1], c, null);
                    width = Math.max(width, cursor.getArrayOfValues()[0]);
                    height += font.getLineHeight();
                    cursor.getArrayOfValues()[0] = 0;
                    cursor.getArrayOfValues()[1] -= font.getLineHeight();
                    endLineIndices.add(i);
                    break;
                case ('\0'):
                    // End of the string, termination character
                    cachedGlyphs[i] = new CachedGlyph(cursor.getArrayOfValues()[0], cursor.getArrayOfValues()[1], c, null);
                    width = Math.max(width, cursor.getArrayOfValues()[0]);
                    endLineIndices.add(i);
                    break;
                default:
                    cachedGlyphs[i] = new CachedGlyph(cursor.getArrayOfValues()[0], cursor.getArrayOfValues()[1], c, data);
                    // Advancing
                    cursor.getArrayOfValues()[0] += data.getXAdvance();
                    break;
            }
        }

        textMetrics = new TextMetrics(cachedGlyphs, height, width, endLineIndices.toArray(new Integer[0]));
    }
}
