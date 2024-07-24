package org.dmarshaq.kubix.core.graphic.base;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Animation {
    private float speed;
    private int[] frames;
    private Texture texture;

    public float totalTime() {
        return (1 / speed) * frames.length;
    }
}

