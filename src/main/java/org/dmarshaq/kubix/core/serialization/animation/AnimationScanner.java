package org.dmarshaq.kubix.core.serialization.animation;

import org.dmarshaq.kubix.core.app.Context;
import org.dmarshaq.kubix.core.graphic.base.texture.TextureAtlas;
import org.dmarshaq.kubix.core.graphic.base.animation.Animation;
import org.dmarshaq.kubix.core.graphic.base.GraphicCore;
import org.dmarshaq.kubix.core.serialization.texture.TextureManager;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;

import static org.dmarshaq.kubix.core.util.FileUtils.loadAsString;

public class AnimationScanner {

    private static final String ANIMATIONS_KEY = "animations";
    private static final String ATLAS_KEY = "atlas";
    private static final String ATLAS_TEXTURE_NAME_KEY = "texture";
    private static final String ATLAS_ROWS_KEY = "rows";
    private static final String ATLAS_COLUMNS_KEY = "columns";
    private static final String ANIMATION_FPS_KEY = "fps";
    private static final String ANIMATION_FIRST_FRAME_KEY = "firstFrame";
    private static final String ANIMATION_LAST_FRAME_KEY = "lastFrame";

    public static AnimationDto[] loadAnimationDtosFromFile(String path) {
        return animationDtosFromString(loadAsString(path));
    }

    private static AnimationDto[] animationDtosFromString(String jsonFile) {
        // Getting JSON animations file into object
        JSONObject jsonAnimations = new JSONObject(jsonFile);

        // Slicing texture for animation frames
        JSONObject jsonAtlas = jsonAnimations.getJSONObject(ATLAS_KEY);
        TextureAtlas textureAtlas = GraphicCore.sliceTexture(Context.getInstance().TEXTURE_MANAGER.TEXTURE_MAP.get(jsonAtlas.getString(ATLAS_TEXTURE_NAME_KEY)), jsonAtlas.getInt(ATLAS_ROWS_KEY), jsonAtlas.getInt(ATLAS_COLUMNS_KEY));

        // Loading animations
        JSONArray jsonAnimationKeys = jsonAnimations.getJSONArray(ANIMATIONS_KEY);
        int length = jsonAnimationKeys.length();

        AnimationDto[] animations = new AnimationDto[length];

        for (int i = 0; i < length; i++) {
            String name = jsonAnimationKeys.getString(i);
            JSONObject jsonAnimation = jsonAnimations.getJSONObject(name);

            AnimationDto animationDto = new AnimationDto();
            animationDto.setName(name);
            animationDto.setFps(jsonAnimation.getFloat(ANIMATION_FPS_KEY));
            animationDto.setFrames(getAllFramesInRange(jsonAnimation.getInt(ANIMATION_FIRST_FRAME_KEY), jsonAnimation.getInt(ANIMATION_LAST_FRAME_KEY)));
            animationDto.setTextureAtlas(textureAtlas);
            animations[i] = animationDto;
        }

        return animations;
    }

    private static int[] getAllFramesInRange(int firstFrame, int lastFrame) {
        int[] frames = new int[lastFrame - firstFrame + 1];
        for (int i = 0; i < frames.length; i++) {
            frames[i] = firstFrame + i;
        }
        return frames;
    }

    public static Animation toAnimation(AnimationDto animationDto) {
        return new Animation(animationDto.getFps(), animationDto.getFrames(), animationDto.getTextureAtlas());
    }
}
