package org.dmarshaq.kubix.core.graphic.base.texture;

import lombok.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

@ToString
public class TextureAtlas {
    private static final HashMap<AtlasCoder, TextureAtlas> TEXTURE_ATLASES = new HashMap<>();
    private final TextureCroppedRegion[] atlas;

    private TextureAtlas(TextureCroppedRegion[] atlas) {
        this.atlas = atlas;
    }

    public static TextureAtlas build(TextureCroppedRegion[] atlas) {
        TextureAtlas textureAtlas = new TextureAtlas(atlas);
        AtlasCoder key = new AtlasCoder(textureAtlas);
        if (TEXTURE_ATLASES.containsKey(key)) {
            return TEXTURE_ATLASES.get(key);
        }
        TEXTURE_ATLASES.put(key, textureAtlas);
        return textureAtlas;
    }

    public TextureCroppedRegion getTextureCroppedRegion(int index) {
        return atlas[index];
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TextureAtlas that)) return false;
        return Arrays.equals(atlas, that.atlas);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(atlas);
    }
}
