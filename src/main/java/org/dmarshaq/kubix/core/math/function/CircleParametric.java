package org.dmarshaq.kubix.core.math.function;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.dmarshaq.kubix.core.math.vector.Vector2;

@Setter
@Getter
@AllArgsConstructor
public class CircleParametric implements AbstractParametric<Float, Vector2> {
    private float radius;
    private Vector2 center;

    @Override
    public Vector2 parametric(Float t) {
        return new Vector2((float)(radius * Math.cos(t)) + center.x(), (float)(radius * Math.sin(t)) + center.y());
    }
}
