package org.dmarshaq.kubix.core.graphic.base.text;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.dmarshaq.kubix.core.graphic.base.texture.Texture;

import java.util.HashMap;

@Getter
@AllArgsConstructor
@ToString
public class Font {
    private final HashMap<Character, Glyph> atlas;
    private final int lineHeight;
    private final int base;
    private final Texture texture;

    /**
     * Calculates pixel length of the string in this font format.
     * @return length in pixels
     */
    public int lengthOf(String string) {
        int length = 0;

        for (int i = 0; i < string.length(); i++) {
            length += atlas.get(string.charAt(i)).getXAdvance();
        }

        return length;
    }
}
