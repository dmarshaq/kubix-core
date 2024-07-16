package org.dmarshaq.kubix.graphic.render;

import lombok.Getter;

import java.util.Objects;

public class Layer {
    @Getter
    private final String name;

    public int quadCount;
    public int lineCount;

    Layer(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Layer layer)) return false;
        return Objects.equals(name, layer.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
