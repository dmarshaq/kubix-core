package org.dmarshaq.kubix.core.serialization.animation;

import org.dmarshaq.kubix.core.app.Context;
import org.dmarshaq.kubix.core.graphic.base.Animation;
import org.dmarshaq.kubix.core.graphic.base.GraphicCore;
import org.dmarshaq.kubix.core.graphic.base.Shader;
import org.dmarshaq.kubix.core.graphic.base.Texture;
import org.dmarshaq.kubix.core.serialization.shader.ShaderManager;
import org.dmarshaq.kubix.core.serialization.texture.TextureManager;
import org.dmarshaq.kubix.core.serialization.texture.TextureScanner;
import org.dmarshaq.kubix.core.util.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.Arrays;
import java.util.Scanner;

import static org.dmarshaq.kubix.core.util.FileUtils.loadAsString;

public class AnimationScanner {

    private static final String ANIMATIONS_KEY = "animations";
    private static final String ATLAS_KEY = "atlas";

    public static AnimationDto[] loadAnimationDtosFromFile(String path) {
        // Getting JSON animations file into object
        JSONObject jsonAnimations = FileUtils.loadAsJSONObject(path);

        // Slicing texture for animation frames
        JSONObject jsonAtlas = jsonAnimations.getJSONObject(ATLAS_KEY);
        Texture texture = TextureManager.TEXTURE_MAP.get(jsonAtlas.getString("texture"));
        GraphicCore.sliceTexture(texture, jsonAtlas.getInt("rows"), jsonAtlas.getInt("columns"));

        // Loading animations
        JSONArray jsonAnimationKeys = jsonAnimations.getJSONArray(ANIMATIONS_KEY);
        int length = jsonAnimationKeys.length();

        AnimationDto[] animations = new AnimationDto[length];

        for (int i = 0; i < length; i++) {
            String name = jsonAnimationKeys.getString(i);
            JSONObject jsonAnimation = jsonAnimations.getJSONObject(name);

            AnimationDto animationDto = new AnimationDto();
            animationDto.setName(name);
            animationDto.setFps(jsonAnimation.getFloat("fps"));
            animationDto.setFrames(getAllFramesInRange(jsonAnimation.getInt("firstFrame"), jsonAnimation.getInt("lastFrame")));
            animationDto.setTexture(texture);
            animations[i] = animationDto;
        }

        return animations;
    }

    public static AnimationDto[] loadAnimationDtosFromFile(File file) {
        // Getting JSON animations file into object
        JSONObject jsonAnimations = FileUtils.loadAsJSONObject(file);

        // Slicing texture for animation frames
        JSONObject jsonAtlas = jsonAnimations.getJSONObject(ATLAS_KEY);
        Texture texture = TextureManager.TEXTURE_MAP.get(jsonAtlas.getString("texture"));
        GraphicCore.sliceTexture(texture, jsonAtlas.getInt("rows"), jsonAtlas.getInt("columns"));

        // Loading animations
        JSONArray jsonAnimationKeys = jsonAnimations.getJSONArray(ANIMATIONS_KEY);
        int length = jsonAnimationKeys.length();

        AnimationDto[] animations = new AnimationDto[length];

        for (int i = 0; i < length; i++) {
            String name = jsonAnimationKeys.getString(i);
            JSONObject jsonAnimation = jsonAnimations.getJSONObject(name);

            AnimationDto animationDto = new AnimationDto();
            animationDto.setName(name);
            animationDto.setFps(jsonAnimation.getFloat("fps"));
            animationDto.setFrames(getAllFramesInRange(jsonAnimation.getInt("firstFrame"), jsonAnimation.getInt("lastFrame")));
            animationDto.setTexture(texture);
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
        return new Animation(animationDto.getFps(), animationDto.getFrames(), animationDto.getTexture());
    }
}
