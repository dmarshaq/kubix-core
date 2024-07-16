package org.dmarshaq.kubix.graphic.render;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.dmarshaq.kubix.core.util.Ordarable;

@ToString
@EqualsAndHashCode
public class Layer implements Ordarable {
    private final int order;

    public Layer(int order) {
        this.order = order;
    }

    @Override
    public int ordinal() {
        return order;
    }
}
