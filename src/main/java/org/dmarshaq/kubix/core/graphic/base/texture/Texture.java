package org.dmarshaq.kubix.core.graphic.base.texture;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.dmarshaq.kubix.core.app.Context;
import org.dmarshaq.kubix.core.util.Ordarable;
import org.dmarshaq.kubix.core.math.vector.Vector2;

import java.util.Objects;

import static org.dmarshaq.kubix.core.util.FileUtils.loadAsImage;

@Getter
@ToString
public class Texture implements Ordarable {
    private final int order;
    private final int width;
    private final int height;
    private final int textureId;

    public static final Texture NO_TEXTURE = new Texture(0, Context.getUnitSize(), Context.getUnitSize(), 0);
    public static final TextureCroppedRegion NO_TEXTURE_REGION = new TextureCroppedRegion(new Vector2(0, 0), Texture.NO_TEXTURE.getWidth(), Texture.NO_TEXTURE.getHeight(), Texture.NO_TEXTURE);


    public Texture(int id, int width, int height, int order) {
        this.order = order;
        this.width = width;
        this.height = height;
        textureId = id;
    }

    @Override
    public int ordinal() {
        return order;
    }

    public TextureCroppedRegion toTextureCroppedRegion() {
        return new TextureCroppedRegion(new Vector2(0, 0), width, height, this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Texture texture)) return false;
        return textureId == texture.textureId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(textureId);
    }
}
