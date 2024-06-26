package org.dmarshaq.graphics;

public enum Layer {
    BACKGROUND,
    DEFAULT,
    PLAYER,
    L1,
    L2,
    L3,
    L4,
    L5,
    UI,
    GIZMOS,
    ;

    private float zOrder;
    private int index;
    private int renderedSpritesCount;

    Layer() {
    }

    public float zOrder() {
        return zOrder;
    }

    public int countRenderSprites() {
        return renderedSpritesCount;
    }

    public int getIndex() {
        return index;
    }

    public static void loadIndexes() {
        int i = 0;
        for (Layer l : Layer.values()) {
            l.zOrder = (float) i / Layer.values().length;
            l.index = i;
            i++;
        }
    }

    public static void clearRenderSpritesCount() {
        for (Layer l : Layer.values()) {
            l.renderedSpritesCount = 0;
        }
    }

    public void incrementRenderSpriteCount() {
        renderedSpritesCount++;
    }

}
