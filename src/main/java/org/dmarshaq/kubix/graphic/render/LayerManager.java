package org.dmarshaq.kubix.graphic.render;

import org.dmarshaq.kubix.core.util.IndexedHashMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class LayerManager {
    public static final HashMap<String, Layer> LAYER_MAP = new HashMap<>();

    public static void buildLayers() {
        LAYER_MAP.put("background", new Layer(0));
        LAYER_MAP.put("default", new Layer(1));
        LAYER_MAP.put("gizmos", new Layer(2));
    }

}
