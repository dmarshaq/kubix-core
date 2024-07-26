package org.dmarshaq.kubix.core.graphic.base.animation;

import lombok.Getter;
import lombok.Setter;
import org.dmarshaq.kubix.core.graphic.base.texture.TextureCroppedRegion;
import org.dmarshaq.kubix.core.math.function.AbstractFunction;
import org.dmarshaq.kubix.core.math.function.FloatFunction;
import org.dmarshaq.kubix.core.time.Time;


public abstract class AnimationPlayer {
    @Getter
    @Setter
    private Animation animation;
    @Getter
    @Setter
    private float rate;
    @Getter
    @Setter
    private AbstractFunction<Float> easing;
    @Getter
    @Setter
    private boolean looped;
    @Getter
    @Setter
    private boolean stopped;

    private float currentTime;
    private int currentIndex;
    private float nextFrameChangeTime;

    /**
     * Animation Player is an animation runner, it has update() method, that should be called if you animation to play at current tick.
     * reset() to fully reset animation, to continue playing from beginning, frame() to get current TextureCroppedRegion that can be assigned to sprite.
     * You can also set rate of animation, easing curve, make animation looped, or stop it.
     * Note: If animation is not lopped it will be stopped after first cycle and animationCycleFinishCallback() will be called.
     * Also, when each cycle is finished reset() method is called automatically.
     */
    public AnimationPlayer(Animation animation) {
        this.animation = animation;
        this.rate = 1.0f;
        this.easing = FloatFunction.LINEAR;
    }

    public void reset() {
        currentTime = 0.0f;
        currentIndex = 0;
        nextFrameChangeTime = easing.function((1 / (animation.getFps() / 1000)) / rate);
    }

    public void update() {
        if (!stopped) {
            currentTime += (float) Time.DeltaTime.getTickMilliseconds();

            if (easing.function(currentTime) >= nextFrameChangeTime) {
                nextFrameChangeTime = easing.function((++currentIndex) * (1 / (animation.getFps() / 1000)) / rate);
                if (currentIndex >= animation.getFrames().length) {
                    reset();
                    if (!looped) {
                        stopped = true;
                        animationCycleFinishCallback();
                    }
                }
            }
        }
    }

    public TextureCroppedRegion frame() {
        return animation.getTextureAtlas().getTextureCroppedRegion(animation.getFrames()[currentIndex]);
    }

    /**
     * Only called when animation is not looped and it finished its cycle;
     */
    public abstract void animationCycleFinishCallback();

}
