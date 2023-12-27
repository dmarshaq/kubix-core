package app;

public class Main {
    public static void main(String[] args){

        Render renderTask = new Render();
        Thread render = new Thread(renderTask);
        render.start();

        Context gameContext = new Context();

        Update updateTask = new Update(gameContext, renderTask);
        Thread update = new Thread(updateTask);

        update.start();
    }
}