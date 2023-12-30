package org.dmarshaq.graphics;

import org.dmarshaq.mathj.*;
import org.dmarshaq.time.Time;

import java.util.HashMap;

import static org.lwjgl.opengl.GL11.GL_NO_ERROR;
import static org.lwjgl.opengl.GL11.glGetError;

public class Sprite {
    private Anim[] animations;
    private float animTimer = 0;
    private float[] animDuration = null;
    private Anim currentAnim = null;

    private Rect shape;
    private VertexArray mesh;
    private Texture texture;
    private Shader shader;
    private Matrix4f ml_matrix; // will offset sprite on screen based of shape position
    private Matrix4f rf_matrix; // reflection matrix

    public Sprite(Rect rect, Texture texture, Shader shader) {
        this.texture = texture;

        this.shader = shader;
        shape = rect;

        buildMesh();

        rf_matrix = Matrix4f.scale(1, 1);

    }

    public Sprite(Rect rect, Anim[] animations, Shader shader) {
        this.animations = animations;
        loadAnimationsIntoTextures(this.animations);
        texture = animations[0].getTextures()[0];
        setCurrentAnim(animations[0]);

        this.shader = shader;
        shape = rect;

        buildMesh();

        rf_matrix = Matrix4f.scale(1, 1);
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
//        int error = glGetError();
//        if (error != GL_NO_ERROR)
//            System.out.println(error + " < 1");
    }

    private void loadAnimationsIntoTextures(Anim[] anims) {
        for (Anim anim : anims) {
            if (anim.getTextures() == null) {
                String animationPath = anim.getAnimPath();
                Texture[] animationTextures = new Texture[anim.getFramesInAnim()];
                for (int x = 0; x < animationTextures.length; x++) {
                    animationTextures[x] = new Texture(animationPath, new Rect(x * anim.getFramePixWidth(), 0, anim.getFramePixWidth(), anim.getFramePixHeight(), 0));
                }
                anim.setTextures(animationTextures);
            }
        }
    }

    private void buildMesh() {
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
        return animations != null;
    }

    public void setCurrentAnim(Anim anim) {
        currentAnim = anim;
        animTimer = anim.getDuration();
    }

    public Anim getCurrentAnim() {
        return currentAnim;
    }

    public void cycleCurrentAnim(MathJ.Easing easing) {
        animTimer -= Time.DeltaTime.getMiliSeconds();

        float timeRemapped = MathJ.Easing.applyEasing((currentAnim.getDuration() - animTimer) / currentAnim.getDuration(), easing);
        float keyFramesCount = currentAnim.getFramesInAnim();

        for (int i = (int) (keyFramesCount - 1); i >= 0; i--) {
            float keyFrameTime = i / keyFramesCount;
            if (keyFrameTime <= timeRemapped) {
                texture = currentAnim.getTextures()[i];
                break;
            }
        }
        if (animTimer <= 0) {
            animTimer = currentAnim.getDuration();
        }

    }

    public float getHeight() {
        return shape.height;
    }

    public float getWidth() {
        return shape.width;
    }
}
