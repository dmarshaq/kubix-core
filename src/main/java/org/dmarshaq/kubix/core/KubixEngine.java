package org.dmarshaq.kubix.core;

import org.dmarshaq.kubix.core.app.Context;
import org.dmarshaq.kubix.core.app.Graphic;
import org.dmarshaq.kubix.core.app.Update;
import org.dmarshaq.kubix.core.graphic.render.Render;

public class KubixEngine {
    public static void initialize() {
        if (Context.getInstance() == null || Update.getInstance() == null || Graphic.getInstance() == null) {
            throw new RuntimeException("Context, Update, Graphic instances must be created before initialization.");
        }
        Context.getInstance().initContextProperties();
        new Thread(Graphic.getInstance()).start();
    }
} 