package org.dmarshaq.kubix.core.app;

import org.dmarshaq.kubix.core.graphic.element.Camera;
import org.dmarshaq.kubix.core.graphic.base.Shader;
import org.dmarshaq.kubix.core.graphic.base.Texture;
import org.dmarshaq.kubix.core.input.InputManager;
import org.dmarshaq.kubix.core.serialization.Packet;
import org.dmarshaq.kubix.core.serialization.SerializationScanner;
import org.dmarshaq.kubix.core.util.FileUtils;
import org.dmarshaq.kubix.core.util.IndexedHashMap;
import org.dmarshaq.kubix.core.graphic.base.Layer;
import org.dmarshaq.kubix.core.graphic.base.LayerManager;
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
    private static int MIN_SCREEN_UNIT_WIDTH = 16;
    private static int MIN_SCREEN_UNIT_HEIGHT = 9;
    // FPS CAP
    private static double TICK_TIME = 5.0; // tick time in milliseconds
    // STATES
    private static boolean RUNNING = true;
    // RENDER
    private static boolean DRAW_GIZMOS = true;
    private static int MAX_QUADS_PER_BATCH = 256;
    private static int MAX_LINES_PER_BATCH = 1028;
    // PUBLIC METHODS

    // Getters
    public static boolean isRunning() {
        return RUNNING;
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
    public static double getTickTime() {
        return TICK_TIME;
    }
    public static boolean isDrawGizmos() {
        return DRAW_GIZMOS;
    }
    public static int getMaxQuadsPerBatch() {
        return MAX_QUADS_PER_BATCH;
    }
    public static int getMaxLinesPerBatch() {
        return MAX_LINES_PER_BATCH;
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

    // Setters
    protected static void setTitle(String title) {
        TITLE = title;
    }
    protected static void setUnitSize(int unitSize) {
        UNIT_SIZE = unitSize;
    }
    protected static void setFullScreen(boolean fullScreen) {
        FULL_SCREEN = fullScreen;
    }
    protected static void setTickTime(double tickTime) {
        TICK_TIME = tickTime;
    }
    protected static void setDrawGizmos(boolean drawGizmos) {
        DRAW_GIZMOS = drawGizmos;
    }
    protected static void setMaxQuadsPerBatch(int maxQuadsPerBatch) {
        MAX_QUADS_PER_BATCH = maxQuadsPerBatch;
    }
    protected static void setMaxLinesPerBatch(int maxLinesPerBatch) {
        MAX_LINES_PER_BATCH = maxLinesPerBatch;
    }
    public static void setMainCamera(Camera camera) {
        MAIN_CAMERA = camera;
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
    public static void setRunning(boolean state) {
        RUNNING = state;
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
