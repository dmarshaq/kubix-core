package org.dmarshaq.kubix.core.serialization.animation;

import org.dmarshaq.kubix.core.graphic.base.animation.Animation;
import org.dmarshaq.kubix.core.graphic.element.Animator;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.dmarshaq.kubix.core.util.FileUtils.findAllFilesInResources;
import static org.dmarshaq.kubix.core.util.FileUtils.findAllFilesInResourcesJar;

public class AnimationManager {
    public static final HashMap<String, Animation> ANIMATION_MAP = new HashMap<>();

    public static void updateAnimators() {
        Animator.ANIMATORS.forEach(Animator::update);
    }

    public static void loadAnimationsFromFiles()  {
        List<String> paths = findAllFilesInResourcesJar("animation", ".json");
        for (String path : paths) {
            putAnimationDtosIntoAnimationMap(AnimationScanner.loadAnimationDtosFromFile(path));
        }

        List<File> files = findAllFilesInResources("animation", ".json");
        for (File file : files) {
            putAnimationDtosIntoAnimationMap(AnimationScanner.loadAnimationDtosFromFile(file));
        }
    }

    private static void putAnimationDtosIntoAnimationMap(AnimationDto[] animationDtos) {
        Arrays.stream(animationDtos).forEach(animationDto -> ANIMATION_MAP.put(animationDto.getName(), AnimationScanner.toAnimation(animationDto)));
    }
}
