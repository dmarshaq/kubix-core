package org.dmarshaq.kubix.core.graphic;

import org.dmarshaq.kubix.core.app.Context;
import org.dmarshaq.kubix.core.mathj.Matrix4f;
import org.dmarshaq.kubix.core.mathj.Rect;
import org.dmarshaq.kubix.core.mathj.Vector2f;

public class Camera {

    private final Rect fov;

    public Camera(float x, float y, float width, float height) {
        this.fov = new Rect(0, 0, width, height);
        setCameraCenter(new Vector2f(x, y));

        if (Context.getMainCamera() == null) {
            Context.setMainCamera(this);
        }
    }

    public void setCameraFov(float width, float height) {
        fov.width = width;
        fov.height = height;
    }

    public Rect getFov() {
        return fov;
    }

    public void setCameraCenter(Vector2f pos) {
        fov.setCenter(pos);
    }

    public Vector2f getCameraCenter() {
        return fov.center();
    }

    public Matrix4f projectionMatrix() {
        Vector2f center = fov.center();
        return Matrix4f.orthographic((fov.width / -2f) + center.x, (fov.width / 2f) + center.x, (fov.height/ -2f) + center.y, (fov.height / 2f) + center.y, -1f, 1f);
    }

    public static Camera copy(Camera camera) {
        Vector2f center = camera.getCameraCenter();
        Rect fov = camera.getFov();
        return new Camera(center.x, center.y, fov.width, fov.height);
    }
}