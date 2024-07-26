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
}
