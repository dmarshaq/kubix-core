package org.dmarshaq.kubix.core.graphic.base.layer;

import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.ToString;

import java.util.function.Supplier;

@ToString
@EqualsAndHashCode(callSuper = false)
@Setter
public class DynamicLayer extends Layer {
    private Supplier<Integer> ordinal;

    public DynamicLayer(Supplier<Integer> ordinal) {
        super(0);
        this.ordinal = ordinal;
    }

    @Override
    public int ordinal() {
        return ordinal.get();
    }
}
