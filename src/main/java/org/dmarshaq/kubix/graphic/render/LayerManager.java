package org.dmarshaq.kubix.graphic.render;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LayerManager {
    public static final List<Layer> LAYER_LIST = new ArrayList<>();

    public static void buildLayers() {
        LAYER_LIST.add(new Layer("background"));
        LAYER_LIST.add(new Layer("default"));
        LAYER_LIST.add(new Layer("gizmos"));
    }

    public static void clearLayerCount() {
        for (Layer layer : LAYER_LIST) {
            layer.quadCount = 0;
            layer.lineCount = 0;
        }
    }

    public static int countQuadsTillIncludingLayer(Layer layer) {
        int count = 0;

        Iterator<Layer> iterator = LAYER_LIST.iterator();
        Layer currentLayer;

        do {
            currentLayer = iterator.next();
            count += currentLayer.quadCount;
        } while (!currentLayer.equals(layer));

        return count;
    }
}
