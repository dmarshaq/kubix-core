package app;

import graphics.Anim;
import graphics.Sprite;
import mathj.Rect;
import mathj.RectComponent;
import mathj.Vector3f;

import java.util.Arrays;

import static graphics.Anim.*;
import static mathj.MathJ.*;

public class GameContext {
    // SCREEN & TILE SETTINGS
    public static final String TITLE = "2D Platformer";
    public static final int ORIGINAL_TILE_SIZE = 16; // 16 x 16 tile
    public static final int SCALE = 4;
    public static final int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE; // 64 x 64 tile
    public static final int MAX_SCREEN_COL = 16;
    public static final int MAX_SCREEN_ROW = 12;
    public static final int SCREEN_WIDTH = TILE_SIZE * MAX_SCREEN_COL; // 1024px
    public static final int SCREEN_HEIGHT = TILE_SIZE * MAX_SCREEN_ROW; // 768px

    // FPS
    public static final int FPS = 120; // frames per second

    // PHYSICS
    public static final float ACCELERATION_GRAVITY = 9.81f; // m/s^2

    // UNSORTED
    private static boolean running = true;
    public static final int MAX_KEYS = 5;

    // CAMERA
    private final Rect cameraFov = new Rect(0f, -1f, 4f, 3f, 0f);

    // ENVIRONMENT
    public static final String GROUND_TEXTURE_PATH = "res/environment/ground.png";

    // DEFAULT ENTITY
    public static final Rect DEFAULT_BOUNDING_BOX = new Rect(0f, 0f, 1f, 1f, 0f);

    // PLAYER
    public static class Player {
        public static final int PLAYER_PIX_WIDTH = 22;
        public static final int PLAYER_PIX_HEIGHT = 24;
        public static final Rect PLAYER_BOUNDING_BOX = new Rect(pixelToWorld(8), 0f, pixelToWorld(21), pixelToWorld(21), 0f);

        public static final float PLAYER_SPEED = 1.08f;

        public static final Anim[] PLAYER_ANIMATIONS = new Anim[] {
                PLAYER_IDLE,
                PLAYER_WALK,
                PLAYER_RUN,
                PLAYER_JUMP,
                PLAYER_FALL,
                PLAYER_ROLL
        };
    }

    // SLIME
    public static class Slime {
        public static final String SLIME_TEXTURE_PATH = "res/slime/slime.png";
        public static final float SLIME_WIDTH = pixelToWorld(16);
        public static final float SLIME_HEIGHT = pixelToWorld(16);
        public static final Rect SLIME_BOUNDING_BOX = new Rect(pixelToWorld(1), 0f, pixelToWorld(14), pixelToWorld(14), 0f);
    }

    // ENTITIES DATA
    public static final int MAX_ENTITIES = 16;
    public static final int[] ENTITY_ID = new int[MAX_ENTITIES];
    public static final Vector3f[] ENTITY_POSITION = new Vector3f[MAX_ENTITIES];
    public static final RectComponent[] ENTITY_BOUNDING_BOX = new RectComponent[MAX_ENTITIES];
    public static final Entity[] ENTITY_TYPE = new Entity[MAX_ENTITIES];
    public static final boolean[] ENTITY_FLIP = new boolean[MAX_ENTITIES];
    public static final Anim[] ENTITY_CURRENT_ANIM = new Anim[MAX_ENTITIES];
    public static final Sprite[] ENTITY_SPRITE = new Sprite[MAX_ENTITIES];

    // ENVIRONMENT DATA
    public static final int MAP_SIZE = 8; // 4 chunks in -x direction, 4 chunks in +x direction
    public static final int[] CHUNKS_ID = new int[MAP_SIZE];
    public static final float CHUNKS_WIDTH = 2f;
    public static final float CHUNKS_HEIGHT = 1f;
    public static final Vector3f[] CHUNKS_POSITION = new Vector3f[MAP_SIZE];
    public static final Sprite[] CHUNKS_SPRITE = new Sprite[MAP_SIZE];



    // TIMERS ALL IN MILLISECONDS!
    public static float timer_player = 5000;

    // CONSTRUCTOR
    public GameContext() {
        cleanEntityIDs();

        buildEnvironment();

        instantiateEntities();
    }

    // PRIVATE METHODS
    private void buildEnvironment() {
        float y = 0f - CHUNKS_HEIGHT;
        float z = 0f;
        for (int i = 0; i < MAP_SIZE; i++) {
            float x = ((MAP_SIZE / -2f) + i) * CHUNKS_WIDTH;
            CHUNKS_ID[i] = i;
            CHUNKS_POSITION[i] = new Vector3f(x, y, z);
        }
    }

    private void instantiateEntities() {
        Instantiate(Entity.PLAYER, new Vector3f(0f, 2f, 0f));
        Instantiate(Entity.SLIME, new Vector3f(1f, 5f, 0f));
    }

    // PUBLIC METHODS
    public static void stopRunning() {
        running = false;
    }
    public static boolean isRunning() {
        return running;
    }

    public static int Instantiate(Entity entity, Vector3f position) {
        for (int i = 0; i < MAX_ENTITIES; i++) {
            if (ENTITY_ID[i] == -2) {
                ENTITY_ID[i] = i;
                ENTITY_TYPE[i] = entity;
                ENTITY_POSITION[i] = position;
                ENTITY_FLIP[i] = false;

                switch (ENTITY_TYPE[i]) {
                    case PLAYER:
                        ENTITY_BOUNDING_BOX[i] = new RectComponent(Player.PLAYER_BOUNDING_BOX, ENTITY_POSITION[i]);
                        ENTITY_CURRENT_ANIM[i] = Player.PLAYER_ANIMATIONS[0];
                        break;
                    case SLIME:
                        ENTITY_BOUNDING_BOX[i] = new RectComponent(Slime.SLIME_BOUNDING_BOX, ENTITY_POSITION[i]);
                        break;
                    default:
                        ENTITY_BOUNDING_BOX[i] = new RectComponent(DEFAULT_BOUNDING_BOX, ENTITY_POSITION[i]);
                        ENTITY_CURRENT_ANIM[i] = null;
                }
                return i;
            }
            else if (i == MAX_ENTITIES - 1) {
                System.out.println("Unable to create a new instance of an Entity: " + entity + " at position: " + position);
            }
        }
        return -1;
    }

    public static void Destroy(int entityID) {
        if ( ENTITY_ID[entityID] > -1 ) {
            ENTITY_ID[entityID] = -1;
        }
        else {
            System.out.println("Unable to destroy an instance of an Entity, because it is already destroyed.");
        }
    }

    public void cleanEntityIDs() {
        Arrays.fill(ENTITY_ID, -2);
    }

    public void setCameraCenter(Vector3f pos) {
        cameraFov.setCenter(pos);
    }

    public void setCameraFov(float width, float height) {
        cameraFov.width = width;
        cameraFov.height = height;
    }

    public Rect getCameraFov() {
        return cameraFov;
    }

}
