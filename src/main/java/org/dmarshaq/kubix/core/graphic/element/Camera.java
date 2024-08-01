package org.dmarshaq.kubix.core.graphic.element;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.dmarshaq.kubix.core.app.Context;
import org.dmarshaq.kubix.core.app.Graphic;
import org.dmarshaq.kubix.core.math.base.MathCore;
import org.dmarshaq.kubix.core.math.base.Rectangle;
import org.dmarshaq.kubix.core.math.matrix.Matrix4x4;
import org.dmarshaq.kubix.core.math.vector.Vector;
import org.dmarshaq.kubix.core.math.vector.Vector2;

@Getter
@EqualsAndHashCode
@ToString
public class Camera {

    private final Rectangle fov;

    public Camera(float x, float y, float width, float height) {
        this.fov = new Rectangle(new Vector2(x, y), width, height);
        fov.setCenter(new Vector2(x, y));

        if (Context.getMainCamera() == null) {
            Context.setMainCamera(this);
        }
    }

    public Vector2 screenToWorld(Vector<Integer> screenPosition) {
        return new Vector2(
                fov.getPosition().x() + (float) screenPosition.getValues().intArray()[0] * fov.getWidth() / Graphic.getInstance().getWindow().width(),
                fov.getPosition().y() + (float) screenPosition.getValues().intArray()[1] * fov.getHeight() / Graphic.getInstance().getWindow().height()
                );
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
