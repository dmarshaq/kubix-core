package org.dmarshaq.app;

import org.dmarshaq.graphics.Anim;
import org.dmarshaq.graphics.Sprite;
import org.dmarshaq.mathj.*;

public class Snapshot {
    // CAMERA DATA
    public Rect cameraFov;

    // ENTITIES DATA
    public int[] ENTITY_ID;
    public Matrix4f[] ENTITY_TRANSFORM;
    public Entity[] ENTITY_TYPE;
    public boolean[] ENTITY_FLIP;
    public Anim[] ENTITY_CURRENT_ANIM;

    // ENVIRONMENT DATA
    public int[] CHUNKS_ID;

    public void initEntityData(int entityCount) {
        ENTITY_ID = new int[entityCount];
        ENTITY_TRANSFORM = new Matrix4f[entityCount];
        ENTITY_TYPE = new Entity[entityCount];
        ENTITY_FLIP = new boolean[entityCount];
        ENTITY_CURRENT_ANIM = new Anim[entityCount];
    }
}
