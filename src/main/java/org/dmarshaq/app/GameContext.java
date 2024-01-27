package org.dmarshaq.app;

import org.dmarshaq.graphics.*;

public class GameContext {
    // SCREEN & TILE SETTINGS
    public static final String TITLE = "2D Platformer";
    public static final int ORIGINAL_TILE_SIZE = 16; // 16 x 16 tile
    public static final int SCALE = 4;
    public static final int UNIT_SIZE = ORIGINAL_TILE_SIZE * SCALE; // 64 x 64 tile

    public static final boolean FULLSCREEN = true;
    // Only important if full screen false
    public static final int MAX_SCREEN_COL = 16;
    public static final int MAX_SCREEN_ROW = 9;
    public static final int SCREEN_WIDTH = UNIT_SIZE * MAX_SCREEN_COL;
    public static final int SCREEN_HEIGHT = UNIT_SIZE * MAX_SCREEN_ROW;
    public static final double RATIO = (double) MAX_SCREEN_COL / MAX_SCREEN_ROW;



    // FPS
    public static final int FPS = 240; // frames per second

    // UNSORTED
    private static boolean running = true;
    public static final int MAX_KEYS = 5;

    // RENDER
    public static boolean gizmos = true;
    public static final class Render {
        public static final int MAX_QUADS_PER_BATCH = 1024; // 10000
    }

    // TIMERS ALL IN MILLISECONDS
    public static float timer_player = 5000;
    public static float timer_test = 2000;
    public static float timer_physics_test = 2000;


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
            System.out.println("Hello!");
        }

        // CAMERA
        public static final Camera camera = new Camera(0f, 0f, 32f, 18f);

    }
}
