package org.dmarshaq.graphics;

import org.dmarshaq.app.Layer;
import org.dmarshaq.app.Snapshot;
import org.dmarshaq.graphics.font.Character;
import org.dmarshaq.graphics.font.Font;
import org.dmarshaq.mathj.MathJ;
import org.dmarshaq.mathj.Matrix4f;
import org.dmarshaq.mathj.Vector2f;

public class Text {
    private String text;
    private Matrix4f transform;
    private Font font;

    // following are changing for each character to allow more dynamic text rendering
    private Matrix4f charTransform;
    private Sprite charSprite;

    public Text(String text, Matrix4f transform, Layer layer, Font font, Shader shader) {
        this.text = text;
        this.transform = transform;
        this.font = font;

        charTransform = new Matrix4f();
        charSprite = new Sprite(charTransform, layer, font.getFontTexture(), font.getFontTexture().getSubTextures()[0], shader);
    }

    public void update(Snapshot snapshot) {
        float cursor = 0;

        charTransform.copy(transform);
        for(int i = 0; i < text.length(); i++) {
            Character c = font.getCharacterData(text.charAt(i));

            float xOffset, yOffset, width, height;
            xOffset = MathJ.pixelToWorld(c.getXoffset());
            yOffset = MathJ.pixelToWorld(c.getYoffset());
            width = MathJ.pixelToWorld(c.getWidth());
            height = MathJ.pixelToWorld(c.getHeight());

            Vector2f characterPosition = new Vector2f(cursor + xOffset, -(yOffset + height));
            characterPosition = transform.multiply(characterPosition, 1);

            charTransform.setPositionXY(characterPosition);
            charSprite.setSubTexture( font.getSubTexture(text.charAt(i)) );
            snapshot.addSpriteToRender(charSprite);

            cursor += MathJ.pixelToWorld(c.getXadvance());
        }
    }
}
