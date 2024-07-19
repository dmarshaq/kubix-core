package org.dmarshaq.kubix.core.app;

import org.dmarshaq.kubix.core.graphic.Camera;
import org.dmarshaq.kubix.core.graphic.render.Shader;
import org.dmarshaq.kubix.core.graphic.render.Texture;
import org.dmarshaq.kubix.core.input.InputManager;
import org.dmarshaq.kubix.core.serialization.Packet;
import org.dmarshaq.kubix.core.serialization.SerializationScanner;
import org.dmarshaq.kubix.core.util.FileUtils;
import org.dmarshaq.kubix.core.util.IndexedHashMap;
import org.dmarshaq.kubix.core.graphic.render.Layer;
import org.dmarshaq.kubix.core.graphic.render.LayerManager;
import org.dmarshaq.kubix.core.serialization.texture.TextureManager;
import org.dmarshaq.kubix.core.serialization.shader.ShaderManager;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class Context {

    // SCREEN & TILE SETTINGS
    private static String TITLE = "";
    private static int UNIT_SIZE = 64; // 64 x 64 unit size

    private static boolean FULL_SCREEN = true;
    private static Color CLEAR_COLOR = new Color(100, 100, 100, 255);
    private static Camera MAIN_CAMERA;
    public static int MIN_SCREEN_UNIT_WIDTH = 16;
    public static int MIN_SCREEN_UNIT_HEIGHT = 9;



    // FPS
    private static int FPS_CAP = 10000; // frames per second

    // STATES
    private static boolean running = true;

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
    public static Color getClearColor() {
        return CLEAR_COLOR;
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
        CLEAR_COLOR = color;
    }

    // Resources
    private static IndexedHashMap<String, Texture> TEXTURES;
    private static Map<String, Shader> SHADERS;
    private static Map<String, Layer> LAYERS;

    public static IndexedHashMap<String, Texture> textures() {
        return TEXTURES;
    }
    public static Map<String, Shader> shaders() {
        return SHADERS;
    }
    public static Map<String, Layer> layers() {
        return LAYERS;
    }

    // Object methods
    public abstract void initContextProperties();

    final void loadResources() {
        SerializationScanner.serializeResourcesIntoPackets(loadResourcesFromPackets(SerializationScanner.deserializeResourcesIntoPackets(FileUtils.findAllFilesInResources("packet", ".kub"))));

        TEXTURES = TextureManager.TEXTURE_MAP;
        SHADERS = ShaderManager.SHADER_MAP;
        LAYERS = LayerManager.LAYER_MAP;
    }

    final List<Packet> loadResourcesFromPackets(Packet[] packets) {
        List<Packet> newPackets = new ArrayList<>();

        // Manager / Loaders, loading respective packets one by one, adding their new packets to new packets list.
        TextureManager.loadPackets(packets, newPackets);
        ShaderManager.loadShadersFromFiles();
        InputManager.mapGLFWKeyCodes();

        return newPackets;
    }
}
