package org.dmarshaq.app;

import org.dmarshaq.graphics.Camera;
import org.dmarshaq.serialization.Packet;
import org.dmarshaq.serialization.SerializationScanner;
import org.dmarshaq.utils.FileUtils;

import java.awt.*;
import java.util.List;

public abstract class Context {

    // SCREEN & TILE SETTINGS
    private static String TITLE = "";
    private static int UNIT_SIZE = 64; // 64 x 64 unit size

    private static boolean FULL_SCREEN = true;
    private static Camera MAIN_CAMERA;
    public static int MIN_SCREEN_UNIT_WIDTH = 16;
    public static int MIN_SCREEN_UNIT_HEIGHT = 9;


    // FPS
    private static int FPS_CAP = 240; // frames per second

    // STATES
    private static boolean running = true;

    // KEYS
    private static int MAX_KEYS = 11;

    // RENDER
    private static boolean DRAW_GIZMOS = true;
    private static int MAX_QUADS_PER_BATCH = 256;

    // PUBLIC METHODS
    public static void stopRunning() {
        running = false;
    }
    public static boolean isRunning() {
        return running;
    }


    public static String getTitle() {
        return TITLE;
    }
    public static int getUnitSize() {
        return UNIT_SIZE;
    }
    public static boolean isFullScreen() {
        return FULL_SCREEN;
    }
    public static int getFPSCap() {
        return FPS_CAP;
    }
    public static int getMaxInputKeys() {
        return MAX_KEYS;
    }
    public static boolean isDrawGizmos() {
        return DRAW_GIZMOS;
    }
    public static int getMaxQuadsPerBatch() {
        return MAX_QUADS_PER_BATCH;
    }
    public static Camera getMainCamera() {
        return  MAIN_CAMERA;
    }
    public static int getMinScreenUnitWidth() {
        return MIN_SCREEN_UNIT_WIDTH;
    }
    public static int getMinScreenUnitHeight() {
        return MIN_SCREEN_UNIT_HEIGHT;
    }


    protected static void setTitle(String title) {
        Context.TITLE = title;
    }
    protected static void setUnitSize(int unitSize) {
        Context.UNIT_SIZE = unitSize;
    }
    protected static void setFullScreen(boolean fullScreen) {
        Context.FULL_SCREEN = fullScreen;
    }
    protected static void setFpsCap(int fpsCap) {
        Context.FPS_CAP = fpsCap;
    }
    protected static void setDrawGizmos(boolean drawGizmos) {
        Context.DRAW_GIZMOS = drawGizmos;
    }
    protected static void setMaxQuadsPerBatch(int maxQuadsPerBatch) {
        Context.MAX_QUADS_PER_BATCH = maxQuadsPerBatch;
    }
    public static void setMainCamera(Camera camera) {
        Context.MAIN_CAMERA = camera;
    }
    protected static void setMinScreenUnitWidth(int minScreenUnitWidth) {
        MIN_SCREEN_UNIT_WIDTH = minScreenUnitWidth;
    }
    protected static void setMinScreenUnitHeight(int minScreenUnitHeight) {
        MIN_SCREEN_UNIT_HEIGHT = minScreenUnitHeight;
    }
    protected static void setClearColor(Color color) {
        Render.setClearScreenColor(color);
    }

    public abstract void initContextProperties();

    protected abstract List<Packet> loadResourcesFromPackets(Packet[] packets);

    final void loadResources() {
        SerializationScanner.serializeResourcesIntoPackets(loadResourcesFromPackets(SerializationScanner.deserializeResourcesIntoPackets(FileUtils.findAllFilesInResources("", ".kub"))));
    }


}
