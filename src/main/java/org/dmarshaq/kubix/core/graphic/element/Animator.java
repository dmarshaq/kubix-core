package org.dmarshaq.kubix.core.graphic.element;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public abstract class Animator {
    public static final List<Animator> ANIMATORS = new ArrayList<>();
    private Sprite target;

    public Animator(Sprite target) {
        ANIMATORS.add(this);
        this.target = target;
        start();
    }

    public abstract void start();
    public abstract void update();
}
