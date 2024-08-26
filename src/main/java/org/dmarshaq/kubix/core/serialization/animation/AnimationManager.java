package org.dmarshaq.kubix.core.serialization.animation;

import org.dmarshaq.kubix.core.graphic.base.animation.Animation;
import org.dmarshaq.kubix.core.graphic.element.Animator;
import org.dmarshaq.kubix.core.serialization.ResourceManager;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.dmarshaq.kubix.core.util.FileUtils.JSON_TYPE;
import static org.dmarshaq.kubix.core.util.FileUtils.findAllFilesInResourcesJar;

public class AnimationManager implements ResourceManager {
    public final HashMap<String, Animation> ANIMATION_MAP = new HashMap<>();

    public void updateAnimators() {
        Animator.ANIMATORS.forEach(Animator::update);
    }

    private void putAnimationDtosIntoAnimationMap(AnimationDto[] animationDtos, String path) {
        Arrays.stream(animationDtos).forEach(animationDto -> ANIMATION_MAP.put(new StringBuilder(path).append('/').append(animationDto.getName()).append(JSON_TYPE).toString(), AnimationScanner.toAnimation(animationDto)));
    }

    @Override
    public void loadResources(List<String> resources) {
        for (String name : ResourceManager.extractResourcesOfFiletype(resources, JSON_TYPE)) {
            putAnimationDtosIntoAnimationMap(AnimationScanner.loadAnimationDtosFromFile(name), name.substring(0, name.lastIndexOf('.')));
        }
    }

    @Override
    public void loadResourcesJar() {
        List<String> paths = findAllFilesInResourcesJar("animation", JSON_TYPE);
        for (String path : paths) {
            putAnimationDtosIntoAnimationMap(AnimationScanner.loadAnimationDtosFromFile(path), path.substring(0, (path.lastIndexOf('.'))));
        }
    }
}
