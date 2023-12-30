package org.dmarshaq.graphics;

import org.dmarshaq.mathj.Rect;

import java.util.HashMap;

import static org.dmarshaq.app.GameContext.Player.*;

public enum Anim {
    PLAYER_IDLE (0, "res/player/player_idle.png", 1000, 5, PLAYER_PIX_WIDTH, PLAYER_PIX_HEIGHT),
    PLAYER_WALK (1, "res/player/player_walk.png", 1000, 6, PLAYER_PIX_WIDTH, PLAYER_PIX_HEIGHT),
    PLAYER_RUN (2, "res/player/player_run.png", 800, 6, PLAYER_PIX_WIDTH, PLAYER_PIX_HEIGHT),
    PLAYER_JUMP (3, "res/player/player_jump.png", 1000, 4, PLAYER_PIX_WIDTH, PLAYER_PIX_HEIGHT),
    PLAYER_FALL (4, "res/player/player_fall.png", 1000, 4, PLAYER_PIX_WIDTH, PLAYER_PIX_HEIGHT),
    PLAYER_ROLL (5, "res/player/player_roll.png", 1000, 11, PLAYER_PIX_WIDTH, PLAYER_PIX_HEIGHT);


    private int index; // corresponds to array index in animation list

    private String animPath;
    private float duration;
    private int framesInAnim;
    private int framePixWidth;
    private int framePixHeight;
    private Texture[] textures;

    Anim(int index, String animPath, float duration, int framesInAnim, int framePixWidth, int framePixHeight) {
        this.index = index;
        this.animPath = animPath;
        this.duration = duration;
        this.framesInAnim = framesInAnim;
        this.framePixWidth = framePixWidth;
        this.framePixHeight = framePixHeight;
    }

    public int getIndex() {
        return index;
    }

    public String getAnimPath() {
        return animPath;
    }

    public float getDuration() {
        return duration;
    }

    public int getFramesInAnim() {
        return framesInAnim;
    }

    public int getFramePixWidth() {
        return framePixWidth;
    }

    public int getFramePixHeight() {
        return framePixHeight;
    }

    public Texture[] getTextures() {
        return textures;
    }

    public void setTextures(Texture[] tex) {
        this.textures = tex;
    }

    public static Anim getIdleAnimInList(HashMap<Anim, Texture[]> anims) {
        for (Anim anim : anims.keySet()) {
            if (anim.index == 0) return anim;
        }
        return null;
    }

}
