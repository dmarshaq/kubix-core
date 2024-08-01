package org.dmarshaq.kubix.core.graphic.base.layer;

import java.util.HashMap;

public class LayerManager {
    public static final HashMap<String, Layer> LAYER_MAP = new HashMap<>();

    public static  void buildLayers() {
        LAYER_MAP.put("background", new StaticLayer(0));
        LAYER_MAP.put("default", new StaticLayer(1));
        LAYER_MAP.put("gizmos", new StaticLayer(2));
    }

}
