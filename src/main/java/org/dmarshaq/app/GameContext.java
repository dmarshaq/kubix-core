package org.dmarshaq.app;

import org.dmarshaq.graphics.Anim;
import org.dmarshaq.graphics.Camera;
import org.dmarshaq.graphics.SpriteDTO;
import org.dmarshaq.graphics.Texture;
import org.dmarshaq.mathj.*;

import java.util.Arrays;

import static org.dmarshaq.graphics.Anim.*;
import static org.dmarshaq.mathj.MathJ.*;

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

    // UNSORTED
    private static boolean running = true;
    public static final int MAX_KEYS = 5;

    // USER INTERFACE
//    public static final UI GAME_UI = new UI(Matrix4f.orthographic(0f, SCREEN_WIDTH, 0f, SCREEN_HEIGHT, -1f, 1f));

    // FONTS
//    public static final float FONT_BASIC_PUP_BLACK_SCALE = 2f;
//    public static final String FONT_BASIC_PUP_BLACK_DATA_PATH = "font/BasicPupBlack.txt";
//    public static final String FONT_BASIC_PUP_BLACK_ATLAS_PATH = "font/BasicPupBlack.png";
//
//    public static final float FONT_BASIC_PUP_WHITE_SCALE = 2f;
//    public static final String FONT_BASIC_PUP_WHITE_DATA_PATH = "font/BasicPupWhite.txt";
//    public static final String FONT_BASIC_PUP_WHITE_ATLAS_PATH = "font/BasicPupWhite.png";

    // RENDER
    public static final class Render {
        public static final int MAX_QUADS_PER_BATCH = 512;
    }

    // TIMERS ALL IN MILLISECONDS
    public static float timer_player = 5000;


    // PUBLIC METHODS
    public static void stopRunning() {
        running = false;
    }
    public static boolean isRunning() {
        return running;
    }

    // CONSTRUCTOR
    public GameContext() {
        Layer.loadIndexes();
        HelloWorld.init();
    }

    /*
     *   ------------------------------------------------------------------------------
     *   HELLO WORLD GAME CONTEXT
     *   ------------------------------------------------------------------------------
     * */

    public static class HelloWorld {

        public static void init() {
            cleanEntityIDs();
            instantiateEntities();
        }

        // CAMERA
        public static final Camera camera = new Camera(0f, 0f, 4f, 3f);

        // ENVIRONMENT
        public static final String GROUND_TEXTURE_PATH = "environment/ground.png";
        public static final String TILESET_TEXTURE_PATH = "tileset/tileset.png";

        // PLAYER
        public static class Player {
            // REQUIRED FOR INITIALIZATIONS
            public static final Rect PLAYER_BOUNDING_BOX = new Rect(pixelToWorld(8), 0f, pixelToWorld(21), pixelToWorld(21));

            // CONSTANTS DUMP
            public static final float PLAYER_SPEED = 1.08f;

            // ANIMATION RELATED, REQUIRED FOR INITIALIZATIONS
            public static final int PLAYER_PIX_WIDTH = 22;
            public static final int PLAYER_PIX_HEIGHT = 24;
            public static final Anim[] PLAYER_ANIMATIONS  = new Anim[] {
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
            // REQUIRED FOR INITIALIZATIONS
            public static final String SLIME_TEXTURE_PATH = "slime/slime.png";
            public static final Rect SLIME_BOUNDING_BOX = new Rect(pixelToWorld(1), 0f, pixelToWorld(14), pixelToWorld(14));

        }

        // ENTITIES DATA
        public static final int MAX_ENTITIES = 16;
        public static final int[] ENTITY_ID = new int[MAX_ENTITIES];
        public static final Entity[] ENTITY_TYPE = new Entity[MAX_ENTITIES];
        public static final Matrix4f[] ENTITY_TRANSFORM = new Matrix4f[MAX_ENTITIES];
        public static final RectComponent[] ENTITY_BOUNDING_BOX = new RectComponent[MAX_ENTITIES]; // Some abstract rectangle that defines entity boundaries
        public static final SpriteDTO[] ENTITY_SPRITE_DTO = new SpriteDTO[MAX_ENTITIES];
        public static final Anim[] ENTITY_CURRENT_ANIM = new Anim[MAX_ENTITIES];


        // METHODS
        public static int Instantiate(Entity entity, Vector2f position) {
            for (int i = 0; i < MAX_ENTITIES; i++) {
                if (ENTITY_ID[i] == -2) {
                    ENTITY_ID[i] = i;
                    ENTITY_TRANSFORM[i] = Matrix4f.identity();
                    ENTITY_TRANSFORM[i].setTransformPosition(position, 0f);

                    ENTITY_TYPE[i] = entity;

                    switch (ENTITY_TYPE[i]) {
                        case SLIME:
                            ENTITY_BOUNDING_BOX[i] = new RectComponent(Slime.SLIME_BOUNDING_BOX, ENTITY_TRANSFORM[i] );
                            ENTITY_SPRITE_DTO[i] = new SpriteDTO(ENTITY_TRANSFORM[i], Texture.SLIME_TEXTURE, Layer.DEFAULT);
                            break;
                        case PLAYER:
                            // In progress
//                            ENTITY_BOUNDING_BOX[i] = new RectComponent(Player.PLAYER_BOUNDING_BOX, ENTITY_TRANSFORM[i] );
//                            ENTITY_CURRENT_ANIM[i] = Player.PLAYER_ANIMATIONS[0];
                            break;
                        default:
                            // everything is not initialized -> null
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

        public static void cleanEntityIDs() {
            Arrays.fill(ENTITY_ID, -2);
        }

        private static void instantiateEntities() {
            System.out.println(Layer.BACKGROUND.zOrder());
            System.out.println(Layer.DEFAULT.zOrder());
            System.out.println(Layer.PLAYER.zOrder());
            System.out.println(Layer.L1.zOrder());
            System.out.println(Layer.L2.zOrder());
            System.out.println(Layer.L3.zOrder());
            System.out.println(Layer.L4.zOrder());
            System.out.println(Layer.L5.zOrder());
            System.out.println(Layer.UI.zOrder());
        }
    }




}
