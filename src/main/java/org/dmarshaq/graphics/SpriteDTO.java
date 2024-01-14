package org.dmarshaq.graphics;

import org.dmarshaq.app.Layer;
import org.dmarshaq.mathj.*;

public class SpriteDTO {
//    private Anim[] animations;
//    private float animTimer = 0;
//    private float[] animDuration = null;
//    private Anim currentAnim = null;

//    private RectComponent rectComponent;
//    private VertexArray mesh;
//    private Texture texture;
//    private Shader shader;
//    private float layer;
//    private Matrix4f ml_matrix; // will offset sprite on screen based of shape position
//    private Matrix4f rf_matrix; // reflection matrix

    private Matrix4f transform;
    private Texture texture;
    private Layer layer;

    public SpriteDTO(Matrix4f transform, Texture texture, Layer layer) {
        this.transform = transform;
        this.texture = texture;
        this.layer = layer;

        layer.incrementSpriteCount();
    }

    public Matrix4f getTransform() {
        return transform;
    }

    public Texture getTexture() {
        return texture;
    }

    public Layer getLayer() {
        return layer;
    }



    /*
    public SpriteDTO(RectComponent rect, Anim[] animations, Shader shader) {
        this.animations = animations;
        loadAnimationsIntoTextures(this.animations);
        // Sets first animation in this sprite animation list to be played, no need to set texture, because when animation is played texture is switched automatically.
        setCurrentAnim(this.animations[0]);

        this.shader = shader;
        rectComponent = rect;

        buildMesh();

        rf_matrix = Matrix4f.scale(1, 1);
    }
    */

    /*
    public void render() {
        ml_matrix = rectComponent.getReferenceTransform();
        ml_matrix.multiply( Matrix4f.translate(rectComponent.getOffsetObject()) );

        shader.setUniformMatrix4f("ml_matrix", ml_matrix);
        shader.setUniformMatrix4f("rf_matrix", rf_matrix);

        texture.bind();
        shader.enable();
        mesh.render();
        shader.disable();
        texture.unbind();
    }
    */

    /*
    private void loadAnimationsIntoTextures(Anim[] anims) {
        for (Anim anim : anims) {
            if (anim.getTextures() == null) {
                String animationPath = anim.getAnimPath();
                Texture[] animationTextures = new Texture[anim.getFramesInAnim()];
                for (int x = 0; x < animationTextures.length; x++) {
                    animationTextures[x] = new Texture(animationPath, new Rect(x * anim.getFramePixWidth(), 0, anim.getFramePixWidth(), anim.getFramePixHeight()));
                }
                anim.setTextures(animationTextures);
            }
        }
    }
    */

//    private void buildMesh() {
//        float[] vertices = new float[] {
//                0f, 0f, 0f,
//                0f, rectComponent.height, 0f,
//                rectComponent.width, rectComponent.height, 0f,
//                rectComponent.width, 0f, 0f
//        };
//
//        byte[] indices = new byte[] {
//                0, 1, 2,
//                2, 3, 0
//        };
//
//        float[] tcs = new float[] {
//                0, 1,
//                0, 0,
//                1, 0,
//                1, 1
//        };
//
//        mesh = new VertexArray(vertices, indices, tcs);
//    }
//
//    public void setOffset(Vector2f offset) {
//        rectComponent.setPosition(offset);
//    }
//
//    public void setTransform(Matrix4f transform) {
//        rectComponent.setReferenceTransform(transform);
//    }
//
//    public void flipY(boolean bool) {
//        if (bool) {
//            rf_matrix = Matrix4f.translate(new Vector3f(rectComponent.width, 0f, 0f));
//            rf_matrix.multiply(Matrix4f.scale(-1, 1));
//        }
//        else {
//            rf_matrix = Matrix4f.scale(1, 1);
//        }
//    }
//
//    public void flipY(boolean bool, float xOffset) {
//        if (bool) {
//            rf_matrix = Matrix4f.translate(new Vector3f(rectComponent.width + xOffset, 0f, 0f));
//            rf_matrix.multiply(Matrix4f.scale(-1, 1));
//        }
//        else {
//            rf_matrix = Matrix4f.scale(1, 1);
//        }
//    }

    /*
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
    */
//    public float getHeight() {
//        return rectComponent.height;
//    }
//
//    public float getWidth() {
//        return rectComponent.width;
//    }
}
