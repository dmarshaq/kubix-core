package graphics;

import mathj.*;

import java.util.Objects;

public class Sprite {
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
}
