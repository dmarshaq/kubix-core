package org.dmarshaq.graphics;

import org.dmarshaq.app.Layer;
import org.dmarshaq.app.Snapshot;
import org.dmarshaq.graphics.font.Character;
import org.dmarshaq.graphics.font.Font;
import org.dmarshaq.mathj.Matrix4f;
import org.dmarshaq.mathj.Vector2f;

public class Text {
    private float maxLength, maxHeight, maxLineLength;
    private int lines, lineSpacing;
    public boolean enabledDrawing;

    private String text;
    private Matrix4f transform;
    private Font font;


    // following are changing for each character to allow more dynamic text rendering
    private Matrix4f charTransform;
    private Sprite charSprite;

    public Text(String text, Matrix4f transform, Font font, float maxLineLength, int lineSpacing, Layer layer, Shader shader) {
        this.text = text;
        this.transform = transform;
        this.font = font;
        this.maxLineLength = maxLineLength;
        this.lineSpacing = lineSpacing;


        charTransform = new Matrix4f();
        charSprite = new Sprite(charTransform, layer, font.getFontTexture(), font.getFontTexture().getSubTextures()[0], shader);
        enabledDrawing = false;
    }

    public void update(Snapshot snapshot) {
        float cursor = 0;
        float line = 0;

        charTransform.copy(transform);
        lines = 1;
        for(int i = 0; i < text.length(); i++) {
            char cChar = text.charAt(i);
            Character c = font.getCharacterData(cChar);

            float xOffset, yOffset, height;
            xOffset = c.getXoffset();
            yOffset = c.getYoffset();
            height = c.getHeight();


            Vector2f characterPosition = new Vector2f(cursor + xOffset, line - (yOffset + height));
            characterPosition = transform.multiply(characterPosition, 1);


            charTransform.setPositionXY(characterPosition);

            if (enabledDrawing) {
                charSprite.setSubTexture(font.getSubTexture(text.charAt(i)));
                snapshot.addSpriteToRender(charSprite);
            }

            cursor += c.getXadvance();

            if (cChar == ' ' && i < text.length() - 1) {
                String word = text.substring(i + 1);
                int end = word.indexOf(' ');
                if (end != -1) {
                    word = word.substring(0, end);
                    int nextWordLength = font.getWordLength(word);
                    if (cursor + nextWordLength > maxLineLength) {
                        lines += 1;
                        float temp = transform.multiply(new Vector2f(cursor, 0), 0).magnitude();
                        if (temp > maxLength) {
                            maxLength = temp;
                        }
                        cursor = 0;
                        line -= font.getMaxCharacterHeight() + lineSpacing;
                    }
                }
            }

            if (i == text.length() - 1) {
                float temp = transform.multiply(new Vector2f(cursor, 0), 0).magnitude();
                if (temp > maxLength) {
                    maxLength = temp;
                }
                maxHeight = lines * font.getMaxCharacterHeight() + (lines - 1) * lineSpacing;
            }
        }
    }

    public void setLineSpacing(int spacing) {
        lineSpacing = spacing;
    }

    public void setMaxLineLength(float length) {
        maxLineLength = length;
    }

    public void setText(String text) {
        this.text = text;
    }

    public float getMaxLength() {
        return maxLength;
    }

    public float getMaxHeight() {
        return maxHeight;
    }

    public Matrix4f getTransform() {
        return transform;
    }


}
