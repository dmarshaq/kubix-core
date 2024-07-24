package org.dmarshaq.kubix.core.graphic.base;

import org.dmarshaq.kubix.core.graphic.element.Animator;

import java.util.HashMap;

public class AnimationManager {
    public static final HashMap<String, Animation> ANIMATION_MAP = new HashMap<>();

    public static void updateAnimators() {
        Animator.animators.forEach(Animator::update);
    }
}
