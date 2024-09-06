package org.dmarshaq.kubix.core.app;

import lombok.Getter;
import org.dmarshaq.kubix.core.graphic.base.*;
import org.dmarshaq.kubix.core.graphic.base.animation.Animation;
import org.dmarshaq.kubix.core.graphic.base.layer.Layer;
import org.dmarshaq.kubix.core.graphic.base.layer.LayerManager;
import org.dmarshaq.kubix.core.graphic.base.text.Font;
import org.dmarshaq.kubix.core.graphic.base.texture.Texture;
import org.dmarshaq.kubix.core.graphic.element.Camera;
import org.dmarshaq.kubix.core.input.InputManager;
import org.dmarshaq.kubix.core.serialization.animation.AnimationManager;
import org.dmarshaq.kubix.core.serialization.font.FontManager;
import org.dmarshaq.kubix.core.serialization.texture.TextureManager;
import org.dmarshaq.kubix.core.serialization.shader.ShaderManager;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.function.Supplier;

public abstract class Context {
    @Getter
    private static Context instance;

    public Context(Supplier<List<String>> resourcesSupplier) {
        if (instance == null) {
            this.resourcesSupplier = resourcesSupplier;
            instance = this;
        }
    }

    private Supplier<List<String>> resourcesSupplier;

    public final static String TEXTURE_BASIC_PUP_BLACK = "texture/font/basic_pup_black.png";
    public final static String SHADER_BASIC_QUAD = "shader/basic_quad.glsl";
    public final static String SHADER_BASIC_LINE = "shader/basic_line.glsl";
    public final static String SHADER_BASIC_CIRCLE = "shader/basic_circle.glsl";
    public final static String SHADER_UI_QUAD = "shader/ui_quad.glsl";
    public final static String FONT_BASIC_PUP_BLACK = "font/basic_pup_black.fnt";

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
    private static boolean DEBUG_RENDER = false;
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
    public static boolean isDebugRender() {
        return DEBUG_RENDER;
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
    protected static void setDebugRender(boolean debugRender) {
        DEBUG_RENDER = debugRender;
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
    public static HashMap<String, Texture> TEXTURES;
    public static HashMap<String, Shader> SHADERS;
    public static HashMap<String, Layer> LAYERS;
    public static HashMap<String, Animation> ANIMATIONS;
    public static HashMap<String, Font> FONTS;

    // Object methods
    public abstract void initContextProperties();

    final void loadResources() {
        List<String> resources = resourcesSupplier.get();

        TEXTURE_MANAGER.loadResourcesJar();
        TEXTURE_MANAGER.loadResources(resources);

        SHADER_MANAGER.loadResourcesJar();
        SHADER_MANAGER.loadResources(resources);

        ANIMATION_MANAGER.loadResourcesJar();
        ANIMATION_MANAGER.loadResources(resources);

        FONT_MANAGER.loadResourcesJar();
        FONT_MANAGER.loadResources(resources);

        TEXTURES = TEXTURE_MANAGER.TEXTURE_MAP;
        SHADERS = SHADER_MANAGER.SHADER_MAP;
        LAYERS = LayerManager.LAYER_MAP;
        ANIMATIONS = ANIMATION_MANAGER.ANIMATION_MAP;
        FONTS = FONT_MANAGER.FONT_MAP;

        InputManager.mapGLFWKeyCodes();
    }

    public final TextureManager TEXTURE_MANAGER = new TextureManager();
    public final ShaderManager SHADER_MANAGER = new ShaderManager();
    public final AnimationManager ANIMATION_MANAGER = new AnimationManager();
    public final FontManager FONT_MANAGER = new FontManager();


}
