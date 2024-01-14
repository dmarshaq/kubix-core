package org.dmarshaq;

import org.dmarshaq.app.GameContext;
import org.dmarshaq.app.Render;
import org.dmarshaq.app.Update;
import org.lwjgl.opengl.GL;

public class KubixEngine {
    public static void main(String[] args){

        Render renderTask = new Render();
        Thread render = new Thread(renderTask);
        render.start();

    }
}