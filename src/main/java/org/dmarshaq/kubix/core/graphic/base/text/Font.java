package org.dmarshaq.kubix.core.graphic.base.text;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.HashMap;

@Getter
@AllArgsConstructor
@ToString
public class Font {
    private final HashMap<Character, CharacterData> atlas;
    private final int lineHeight;
    private final int base;

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
