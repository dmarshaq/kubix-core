package org.dmarshaq.kubix.core.graphic.base.layer;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.dmarshaq.kubix.core.util.Ordarable;

@ToString
@EqualsAndHashCode(callSuper = false)
public class StaticLayer extends Layer implements Ordarable {
    private final int order;

    public StaticLayer(int order) {
        super(order);
        this.order = order;
    }

    @Override
    public int ordinal() {
        return order;
    }
}
