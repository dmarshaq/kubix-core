package org.dmarshaq.kubix.core.graphic.base;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Animation {
    private float fps;
    private int[] frames;
    private Texture texture;

    public float totalTimeSeconds() {
        return (1 / fps) * frames.length;
    }
    public float totalTimeMilliseconds() {
        return (1 / (fps / 1000)) * frames.length;
    }
}

