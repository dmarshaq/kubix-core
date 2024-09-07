package org.dmarshaq.kubix.core.graphic.element;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.dmarshaq.kubix.core.graphic.base.Shader;
import org.dmarshaq.kubix.core.graphic.base.layer.Layer;
import org.dmarshaq.kubix.core.graphic.base.text.Font;
import org.dmarshaq.kubix.core.graphic.base.text.Glyph;
import org.dmarshaq.kubix.core.graphic.data.CachedGlyph;
import org.dmarshaq.kubix.core.math.vector.Vector2;
import org.dmarshaq.kubix.core.ui.math.Vector2Int;

import java.util.HashMap;

@Getter
@ToString
public class TextFloat implements AbstractText<Float, Vector2> {
    private CachedGlyph[] cachedGlyphs;
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

        int length = charSequence.length();
        cachedGlyphs = new CachedGlyph[length];
        for (int i = 0; i < length; i++) {
            char c = charSequence.charAt(i);
            // Regular data loading
            Glyph data = atlas.get(c);
            switch (c) {
                case ('\r'):
                    // Return line operator, moving cursor back to the beginning of the line
                    cursor.getArrayOfValues()[0] = origin.x();
                    break;
                case ('\n'):
                    // New line operator, moving cursor down by one line
                    cursor.getArrayOfValues()[1] -= font.getLineHeight();
                    break;
                default:
                    cachedGlyphs[i] = new CachedGlyph(cursor.getArrayOfValues()[0], cursor.getArrayOfValues()[1]);
                    // Advancing
                    cursor.getArrayOfValues()[0] += data.getXAdvance();
                    break;
            }
        }
    }

}
