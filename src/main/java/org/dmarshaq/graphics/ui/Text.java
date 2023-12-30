package org.dmarshaq.graphics.ui;


import org.dmarshaq.graphics.Sprite;
import org.dmarshaq.graphics.font.Font;
import org.dmarshaq.mathj.Vector3int;

public class Text {
    private Sprite[] characters;
    private String text;
    private Vector3int position;


    public Text(String text, Font font, Vector3int position) {
        this.text = text;
        this.position = position;
        characters = font.buildSpriteText(text, position);
    }

//    public int getTextCenterY() {
//        int maxHeight = 0;
//        if (characters != null) {
//            for (Sprite c : characters) {
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
//            for (Sprite c : characters) {
//                length += (int) c.getWidth();
//            }
//        }
//        return length / 2;
//    }

    public void render() {
        if (characters != null) {
            for (Sprite c : characters) {
                c.render();
            }
        }
    }

    public void dispose() {
        characters = null;
    }
}
