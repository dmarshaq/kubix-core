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
