package org.dmarshaq.graphics.ui;


import org.dmarshaq.graphics.SpriteDTO;
import org.dmarshaq.graphics.font.Font;
import org.dmarshaq.mathj.Vector3f;

public class Text {
    private SpriteDTO[] characters;
    private String text;
    private Vector3f position;


    public Text(String text, Font font, Vector3f position) {
        this.text = text;
        this.position = position;
        characters = font.buildSpriteText(text, position);
    }

//    public int getTextCenterY() {
//        int maxHeight = 0;
//        if (characters != null) {
//            for (SpriteDTO c : characters) {
//                if (c.getHeight() > maxHeight) {
//                    maxHeight = (int) c.getHeight();
//                }
//            }
//        }
//        return maxHeight / 2;
//    }
//
//    public int getTextCenterX() {
//        int length = 0;
//        if (characters != null) {
//            for (SpriteDTO c : characters) {
//                length += (int) c.getWidth();
//            }
//        }
//        return length / 2;
//    }

    public void render() {
        if (characters != null) {
            for (SpriteDTO c : characters) {
//                c.render();
            }
        }
    }

    public void dispose() {
        characters = null;
    }
}
