package org.dmarshaq.graphics;

import org.dmarshaq.mathj.MathJ;
import org.dmarshaq.time.Time;

public class Animation {
    private float timer;
    private float duration;

    private int animationRow;

    private final int frames;
    private final Texture texture;

    public Animation(int animationRow, Texture texture) {
        this.animationRow = animationRow;
        this.texture = texture;

        // width of each subtexture if always a percent length from 1, therefor proportional to number of slices in each row
        this.frames = (int) ( 1 / this.texture.getSubTextures()[0].width() );

        // default duration for unsigned Animations
        float duration = (float) frames / 30;
        this.timer = duration;
        this.duration = duration;
    }

    public Animation(float duration, int animationRow, Texture texture) {
        this.timer = duration;
        this.duration = duration;

        this.animationRow = animationRow;
        this.texture = texture;

        // width of each subtexture if always a percent length from 1, therefor proportional to number of slices in each row
        this.frames = (int) ( 1 / this.texture.getSubTextures()[0].width() );
    }

    /*
    Returns subtexture index
     */
    public int playCycle(MathJ.Easing easing) {
        timer -= Time.DeltaTime.getMiliSeconds();

        int index = 0;

        float timeRemapped = MathJ.Easing.applyEasing((duration - timer) / duration, easing);

        for (int i = frames - 1; i >= 0; i--) {
            float keyFrameTime = (float) i / frames;
            if (keyFrameTime <= timeRemapped) {
                index =  i + animationRow * frames;
                break;
            }
        }
        if (timer <= 0) {
            timer = duration;
        }
        return index;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }

    public void setAnimationRow(int animationRow) {
        this.animationRow = animationRow;
    }
}
