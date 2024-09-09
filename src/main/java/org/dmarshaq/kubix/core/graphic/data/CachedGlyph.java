package org.dmarshaq.kubix.core.graphic.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.dmarshaq.kubix.core.graphic.base.text.Glyph;
import org.dmarshaq.kubix.core.graphic.base.texture.TextureCroppedRegion;

@Getter
@AllArgsConstructor
@ToString
public class CachedGlyph {
    private final Number xOffsetFromOrigin;
    private final Number yOffsetFromOrigin;
    private final char aChar;
    private final Glyph glyph;
}
