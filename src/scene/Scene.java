package scene;

import graphics.Shader;
import graphics.VertexArray;

public class Scene {

    private VertexArray background;

    public Scene() {
        float[] vertices = new float[] {
            -10f, -10f * 9f / 16f, 0f,
            -10f, 10f * 9f / 16f, 0f,
            0f, 10f * 9f / 16f, 0f,
            0f, -10f * 9f / 16f, 0f,
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

        background = new VertexArray(vertices, indices, tcs);
    }

    public void render() {
//        Shader.BG.enable();
//        background.render();
//        Shader.BG.disable();
    }
}
