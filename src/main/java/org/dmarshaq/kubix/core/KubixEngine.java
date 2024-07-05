package org.dmarshaq.kubix.core;

import org.dmarshaq.kubix.core.app.Context;
import org.dmarshaq.kubix.core.app.Render;
import org.dmarshaq.kubix.core.app.Update;

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