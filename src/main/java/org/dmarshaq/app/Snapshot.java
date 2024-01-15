package org.dmarshaq.app;

import org.dmarshaq.graphics.Camera;
import org.dmarshaq.graphics.Sprite;
import org.dmarshaq.graphics.SpriteDTO;
import org.dmarshaq.mathj.*;

import java.util.ArrayList;
import java.util.List;

public class Snapshot {
    private final List<Sprite> spriteList = new ArrayList<>();
    private Sprite[] spriteArray;
    // CAMERA DATA
    private Camera camera;

    public void addSpriteToRender(SpriteDTO spriteDTO) {
        Sprite sprite = new Sprite(spriteDTO);

        sprite.getLayer().incrementRenderSpriteCount();
        spriteList.add(sprite);
    }

    public void closeSpriteInputStream() {
        int[] startIndexes = new int[Layer.values().length];
        for (int i = 1; i < startIndexes.length; i++) {
            startIndexes[i] = Layer.values()[i - 1].countRenderSprites() + startIndexes[i - 1];
        }
        spriteArray = new Sprite[spriteList.size()];

        for (Sprite sprite : spriteList) {
            int layerIndex = sprite.getLayer().getIndex();
            spriteArray[startIndexes[layerIndex]] = sprite;
            startIndexes[layerIndex]++;
        }

    }

    public Sprite[] getSpriteDataArray() {
        return spriteArray;
    }

    public void setCameraToRender(Camera camera) {
        this.camera = Camera.copy(camera);
    }

    public Camera getCamera() {
        return camera;
    }
}
