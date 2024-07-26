package org.dmarshaq.kubix.core.graphic.element;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.dmarshaq.kubix.core.graphic.base.Shader;
import org.dmarshaq.kubix.core.graphic.base.layer.Layer;
import org.dmarshaq.kubix.core.graphic.base.text.Font;
import org.dmarshaq.kubix.core.graphic.base.texture.TextureCroppedRegion;
import org.dmarshaq.kubix.core.math.vector.Vector2;

@Getter
@Setter
@ToString
public class Text {
    private final Vector2 position;
    private String text;
    private Font font;
    private Shader shader;
    private Layer layer;


    public Text(Vector2 position, String text, Font font, Shader shader, Layer layer) {
        this.position = position;
        this.text = text;
        this.font = font;
        this.shader = shader;
        this.layer = layer;
    }
}
