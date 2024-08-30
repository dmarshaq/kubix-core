package org.dmarshaq.kubix.core.graphic.base.text;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.dmarshaq.kubix.core.graphic.base.texture.TextureCroppedRegion;

@Getter
@AllArgsConstructor
@ToString
public class Glyph {
    private final TextureCroppedRegion textureCroppedRegion;
    private final int xOffset;
    private final int yOffset;
    private final int xAdvance;
}
