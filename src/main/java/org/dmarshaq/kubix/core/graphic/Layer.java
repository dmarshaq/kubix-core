package org.dmarshaq.kubix.core.graphic;

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
    private int renderedSpritesCount;
    
    public float zOrder() {
        return zOrder;
    }

    public int countRenderSprites() {
        return renderedSpritesCount;
    }

    public static void loadIndexes() {
        for (Layer l : Layer.values()) {
            l.zOrder = (float) l.ordinal() / Layer.values().length;
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
