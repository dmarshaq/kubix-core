package org.dmarshaq.kubix.core.graphic.render;

import java.util.HashMap;

public class LayerManager {
    public static final HashMap<String, Layer> LAYER_MAP = new HashMap<>();

    public static void buildLayers() {
        LAYER_MAP.put("background", new Layer(0));
        LAYER_MAP.put("default", new Layer(1));
        LAYER_MAP.put("gizmos", new Layer(2));
    }

}
