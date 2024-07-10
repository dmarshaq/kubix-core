package org.dmarshaq.kubix.core.graphic;

public class SubTexture {
    private final float x;
    private final float y;
    private final float width;
    private final float height;


    public SubTexture(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public float x() {
        return x;
    }

    public float y() {
        return y;
    }

    public float percentWidth() {
        return width;
    }

    public float percentHeight() {
        return height;
    }
}