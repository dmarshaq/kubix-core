package org.dmarshaq.kubix.core.graphic;

import lombok.Getter;
import org.dmarshaq.kubix.core.app.Context;
import org.dmarshaq.kubix.math.MathCore;
import org.dmarshaq.kubix.math.Rectangle;
import org.dmarshaq.kubix.math.matrix.Matrix4x4;
import org.dmarshaq.kubix.math.vector.Vector2;

@Getter
public class Camera {

    private final Rectangle fov;

    public Camera(float x, float y, float width, float height) {
        this.fov = new Rectangle(0, 0, width, height);
        fov.setCenter(new Vector2(x, y));

        if (Context.getMainCamera() == null) {
            Context.setMainCamera(this);
        }
    }

    public void setCameraFov(float width, float height) {
        fov.setWidth(width);
        fov.setHeight(height);
    }

    public Matrix4x4 projectionMatrix() {
        Vector2 center = fov.center();
        return MathCore.orthographic((fov.getWidth() / -2f) + center.x(), (fov.getWidth() / 2f) + center.x(), (fov.getHeight()/ -2f) + center.y(), (fov.getHeight() / 2f) + center.y(), -1f, 1f);
    }
}
