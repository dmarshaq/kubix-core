package graphics;

import app.GameContext;
import mathj.*;
import time.Time;

import java.util.HashMap;
import java.util.Set;

public class Sprite {
    private HashMap<Anim, Texture[]> animList = null;
    private float animTimer = 0;
    private float[] animDuration = null;
    private Anim currentAnim = null;

    private Rect shape;
    private VertexArray mesh;
    private Texture texture;
    private Shader shader;
    private Matrix4f ml_matrix; // will offset sprite on screen based of shape position
    private Matrix4f rf_matrix; // reflection matrix

    public Sprite(Rect rect, String texturePath, Shader shader) {
        this.shader = shader;
        shape = rect;
        texture = new Texture(texturePath);

        float[] vertices = new float[] {
                0f, 0f, 0f,
                0f, shape.height, 0f,
                shape.width, shape.height, 0f,
                shape.width, 0f, 0f
        };

        byte[] indices = new byte[] {
                0, 1, 2,
                2, 3, 0
        };

        float[] tcs = new float[] {
                0, 1,
                0, 0,
                1, 0,
                1, 1
        };

        mesh = new VertexArray(vertices, indices, tcs);
        rf_matrix = Matrix4f.scale(1, 1);

//        System.out.println("Sprite created: " + shape);
    }

    public Sprite(Rect rect, String[][] animationPaths,  Rect frameSize, int[] framesPerAnimation, final float[] animationDurations, Shader shader) {
        this.shader = shader;
        shape = rect;

        animList = loadAnimationsIntoTextures(animationPaths, frameSize, framesPerAnimation);
        texture = animList.get(Anim.IDLE)[0];
        animDuration = animationDurations;
        setCurrentAnim(Anim.IDLE);

        float[] vertices = new float[] {
                0f, 0f, 0f,
                0f, shape.height, 0f,
                shape.width, shape.height, 0f,
                shape.width, 0f, 0f
        };

        byte[] indices = new byte[] {
                0, 1, 2,
                2, 3, 0
        };

        float[] tcs = new float[] {
                0, 1,
                0, 0,
                1, 0,
                1, 1
        };

        mesh = new VertexArray(vertices, indices, tcs);
        rf_matrix = Matrix4f.scale(1, 1);

//        System.out.println("Sprite created: " + shape);
    }

    public void render() {
        ml_matrix = Matrix4f.translate(shape.getPosition());
        shader.setUniformMatrix4f("ml_matrix", ml_matrix);
        shader.setUniformMatrix4f("rf_matrix", rf_matrix);
        texture.bind();
        shader.enable();
        mesh.render();
        shader.disable();
        texture.unbind();
    }

    private HashMap<Anim, Texture[]> loadAnimationsIntoTextures(String[][] animationsPaths, Rect frameSize, int[] framesPerAnimation) {
        HashMap<Anim, Texture[]> result = new HashMap<Anim, Texture[]>();

        for (int i = 0; i < animationsPaths.length; i++) {
            String[] anim = animationsPaths[i];
            String animationPath = anim[1];
            Texture[] animationTextures = new Texture[framesPerAnimation[i]];
            for (int x = 0; x < animationTextures.length; x++) {
                animationTextures[x] = new Texture(animationPath, new Rect(x * frameSize.width, 0, frameSize.width, frameSize.height, 0));
            }
            result.put(Anim.getAnim(anim[0]), animationTextures);
        }

        return result;
    }

    public void setPosition(Vector3f pos) {
        shape.setPosition(pos);
    }

    public void flipY(boolean bool) {
        if (bool) {
            rf_matrix = Matrix4f.scale(-1, 1);
            rf_matrix = Matrix4f.translate(new Vector3f(shape.width, 0f, 0f)).multiply(rf_matrix);
        }
        else {
            rf_matrix = Matrix4f.scale(1, 1);
        }
    }

    public void flipY(boolean bool, float xOffset) {
        if (bool) {
            rf_matrix = Matrix4f.scale(-1, 1);
            rf_matrix = Matrix4f.translate(new Vector3f(shape.width + xOffset, 0f, 0f)).multiply(rf_matrix);
        }
        else {
            rf_matrix = Matrix4f.scale(1, 1);
        }
    }

    public boolean hasAnim() {
        return animList != null;
    }

    public void setCurrentAnim(Anim anim) {
        currentAnim = anim;
        animTimer = animDuration[Anim.getID(anim)];
    }

    public Anim getCurrentAnim() {
        return currentAnim;
    }

    public void cycleCurrentAnim(MathJ.Easing easing) {
        animTimer -= Time.DeltaTime.getMiliSeconds();

        float timeRemapped = MathJ.Easing.applyEasing((animDuration[Anim.getID(currentAnim)] - animTimer) / animDuration[Anim.getID(currentAnim)], easing);
        float keyFramesCount = animList.get(currentAnim).length;

        for (int i = (int) (keyFramesCount - 1); i >= 0; i--) {
            float keyFrameTime = i / keyFramesCount;
            if (keyFrameTime <= timeRemapped) {
                texture = animList.get(currentAnim)[i];
                break;
            }
        }
        if (animTimer <= 0) {
            animTimer = animDuration[Anim.getID(currentAnim)];
        }

    }
}
