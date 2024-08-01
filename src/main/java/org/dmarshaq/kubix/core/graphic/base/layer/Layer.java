package org.dmarshaq.kubix.core.graphic.base.layer;

import lombok.Getter;
import org.dmarshaq.kubix.core.util.Ordarable;

public abstract class Layer implements Ordarable {
    @Getter
    private static int maxOrder;
    @Getter
    private static int minOrder;

    public Layer(int order) {
        maxOrder = Math.max(maxOrder, order);
        minOrder = Math.min(minOrder, order);
    }

}
