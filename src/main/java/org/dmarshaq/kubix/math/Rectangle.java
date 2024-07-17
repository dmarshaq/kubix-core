package org.dmarshaq.kubix.math;

import lombok.Setter;
import org.dmarshaq.kubix.math.vector.Vector;
import org.dmarshaq.kubix.math.vector.Vector2;

@Setter
public class Rectangle implements AbstractRectangle<Float, Vector2> {
    private Vector2 position;
    private float width;
    private float height;

    public Rectangle() {
        this.position = new Vector2(0, 0);
        this.width = 0;
        this.height = 0;
    }

    public Rectangle(float x, float y, float width, float height) {
        this.position = new Vector2(x, y);
        this.width = width;
        this.height = height;
    }

    public Rectangle(Vector2 pos, float width, float height) {
        this.position = pos;
        this.width = width;
        this.height = height;
    }

    public String toString() {
        return "at: " + position + " width: " + width + " height: " + height;
    }


    public Vector2 center() {
        return new Vector2(width/2, height/2).add(position);
    }

    public void setCenter(Vector2 position) {
        this.position = new Vector2(width/-2, height/-2).add(position);
    }

    @Override
    public Float getWidth() {
        return width;
    }

    @Override
    public Float getHeight() {
        return height;
    }

    @Override
    public Vector2 getPosition() {
        return position;
    }

}
