package org.dmarshaq.kubix.core.serialization.animation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.dmarshaq.kubix.core.graphic.base.texture.TextureAtlas;

@Getter
@Setter
@NoArgsConstructor
public class AnimationDto {
    private String name;
    private float fps;
    private int[] frames;
    private TextureAtlas textureAtlas;
}
