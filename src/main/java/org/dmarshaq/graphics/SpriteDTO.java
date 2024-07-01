package org.dmarshaq.graphics;


import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.dmarshaq.mathj.Matrix4f;
import org.dmarshaq.mathj.Vector4f;

@Getter
@Builder
@EqualsAndHashCode
public class SpriteDTO {

    private final Matrix4f transform;
    private final float width;
    private final float height;
    private final Texture texture;
    private final SubTexture subTexture;
    private final Layer layer;
    private final Vector4f color;
    private final Shader shader;


}
