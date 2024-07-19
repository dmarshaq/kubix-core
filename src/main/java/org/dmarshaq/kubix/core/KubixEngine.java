package org.dmarshaq.kubix.core;

import org.dmarshaq.kubix.core.app.Context;
import org.dmarshaq.kubix.core.app.Graphic;
import org.dmarshaq.kubix.core.app.Update;

public class KubixEngine {
    public static void initialize(Context context, Update updateTask, Graphic graphicTask) {
        context.initContextProperties();
        graphicTask.setUpdateTask(updateTask);
        graphicTask.setContext(context);
        updateTask.setRenderTask(graphicTask);
        Thread graphic = new Thread(graphicTask);
        graphic.start();
    }
} 