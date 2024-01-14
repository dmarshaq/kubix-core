package org.dmarshaq.app;

import org.dmarshaq.graphics.Sprite;
import org.dmarshaq.graphics.SpriteDTO;
import org.dmarshaq.mathj.*;

import java.util.ArrayList;
import java.util.List;

public class Snapshot {
    private List<Sprite> spriteList = new ArrayList<>();
    private Sprite[] spriteArray;
    private int[] startIndexes;
    // CAMERA DATA
    public Rect cameraFov;

    // ENTITIES DATA
//    public SpriteDTO[] ENTITY_SPRITE_DTO;
//    public int[] ENTITY_ID;
//    public Matrix4f[] ENTITY_TRANSFORM;
//    public Entity[] ENTITY_TYPE;
//    public boolean[] ENTITY_FLIP;
//    public Anim[] ENTITY_CURRENT_ANIM;

    // ENVIRONMENT DATA
//    public int[] CHUNKS_ID;

    // TODO Caveman shit out of this!
//    public void initSpriteDataArray(int length) {
//        spriteArray = new Sprite[length];
//    }

    public void addSpriteToRender(SpriteDTO spriteDTO) {
        Sprite sprite = new Sprite(spriteDTO);

//        startIndexes[sprite.getLayer().getIndex()]++;
        sprite.getLayer().incrementRenderSpriteCount();
        spriteList.add(sprite);
    }

    public void closeSpriteInputStream() {
        startIndexes = new int[Layer.values().length];
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

    public int[] getStartIndexes() {
        return startIndexes;
    }
}
