package org.dmarshaq.kubix.core.graphic.resource;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.dmarshaq.kubix.core.app.Context;
import org.dmarshaq.kubix.core.util.Ordarable;
import org.dmarshaq.kubix.core.math.vector.Vector2;

import static org.dmarshaq.kubix.core.util.FileUtils.loadAsImage;

@Getter
@ToString
public class Texture implements Ordarable {
    private final int order;
    private final int width;
    private final int height;
    private final int textureId;
    @Setter
    private TextureCroppedRegion[] tileSet;

    public static final Texture NO_TEXTURE = new Texture(0, Context.getUnitSize(), Context.getUnitSize(), 0);

    public Texture(int id, int width, int height, int order) {
        this.order = order;
        this.width = width;
        this.height = height;
        textureId = id;
        tileSet = new TextureCroppedRegion[] {
                new TextureCroppedRegion(new Vector2(0, 0), width, height, this)
        };
    }

    @Override
    public int ordinal() {
        return order;
    }

}
