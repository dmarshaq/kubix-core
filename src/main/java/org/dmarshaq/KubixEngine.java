package org.dmarshaq;

import org.dmarshaq.app.GameContext;
import org.dmarshaq.app.Render;
import org.dmarshaq.app.Update;

public class KubixEngine {
    public static void main(String[] args){

        Render renderTask = new Render();
        Thread render = new Thread(renderTask);
        render.start();

        GameContext gameContext = new GameContext();

        Update updateTask = new Update(gameContext, renderTask);
        Thread update = new Thread(updateTask);
        update.start();
    }
}