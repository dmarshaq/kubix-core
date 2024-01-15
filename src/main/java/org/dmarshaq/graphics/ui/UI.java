package org.dmarshaq.graphics.ui;

import org.dmarshaq.graphics.Shader;
import org.dmarshaq.graphics.font.Font;
import org.dmarshaq.mathj.Matrix4f;
import org.dmarshaq.mathj.Vector3f;

import java.util.ArrayList;

public class UI {

    private Matrix4f pr_matrix;
    private ArrayList<Text> textList;

    public UI(Matrix4f pr_matrix) {
        this.pr_matrix = pr_matrix;
        textList = new ArrayList<>();

    }

    public void addText(String text, Font font, Vector3f position) {
        textList.add(new Text(text, font, position));
    }

    public void render() {

        for (Text t : textList) {
            t.render();
        }
    }
}