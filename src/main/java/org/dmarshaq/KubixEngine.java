package org.dmarshaq;

import org.dmarshaq.app.Context;
import org.dmarshaq.app.Render;
import org.dmarshaq.app.Update;

public class KubixEngine {
    public static void initialize(Context context, Update updateTask, Render renderTask) {
        context.initContextProperties();
        renderTask.setUpdateTask(updateTask);
        renderTask.setContext(context);
        updateTask.setRenderTask(renderTask);
        Thread render = new Thread(renderTask);
        render.start();
    }
}