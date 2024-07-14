package org.dmarshaq.kubix.core.graphic;


import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.dmarshaq.kubix.core.mathj.Matrix4f;
import org.dmarshaq.kubix.core.mathj.Vector4f;

@Getter
@Builder
@EqualsAndHashCode
public class SpriteDto {

    private final Matrix4f transform;
    private final float width;
    private final float height;
    private final Texture texture;
    private final TextureCroppedRegion textureCroppedRegion;
    private final Layer layer;
    private final Vector4f color;
    private final Shader shader;


}
