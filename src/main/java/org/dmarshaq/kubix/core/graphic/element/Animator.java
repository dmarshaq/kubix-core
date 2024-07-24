package org.dmarshaq.kubix.core.graphic.element;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public abstract class Animator {
    public static final List<Animator> animators = new ArrayList<>();
    @Setter
    @Getter
    private Sprite target;

    public Animator(Sprite target) {
        animators.add(this);
        this.target = target;
        start();
    }

    public abstract void start();
    public abstract void update();
}
