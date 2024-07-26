package org.dmarshaq.kubix.core.graphic.base.animation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.dmarshaq.kubix.core.graphic.base.texture.TextureAtlas;

@Getter
@AllArgsConstructor
public class Animation {
    private final float fps;
    private final int[] frames;
    private final TextureAtlas textureAtlas;

    public float totalTimeSeconds() {
        return (1 / fps) * frames.length;
    }
    public float totalTimeMilliseconds() {
        return (1 / (fps / 1000)) * frames.length;
    }
}

