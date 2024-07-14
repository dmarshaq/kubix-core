package org.dmarshaq.kubix.core.app;

import org.dmarshaq.kubix.core.graphic.*;
import org.dmarshaq.kubix.core.mathj.MathJ;
import org.dmarshaq.kubix.core.mathj.Matrix4f;

import java.util.ArrayList;
import java.util.List;

public class Snapshot {
    private final List<SpriteDto> spriteDtoList = new ArrayList<>();
    private SpriteDto[] spriteDtoArray;

    // CAMERA DATA
    private Camera camera;

    public void addSpriteToRender(Sprite sprite) {
        SpriteDto spriteDTO = SpriteDto.builder()
                .color(MathJ.Math2D.toVector4f(sprite.getColor()))
                .layer(sprite.getLayer())
                .width(sprite.getWidth())
                .height(sprite.getHeight())
                .shader(sprite.getShader())
                .textureCroppedRegion(sprite.getSubTexture())
                .texture(sprite.getTexture())
                .transform(Matrix4f.duplicate(sprite.getTransform()))
                .build();

        spriteDTO.getLayer().incrementRenderSpriteCount();
        spriteDtoList.add(spriteDTO);
    }

    public void addSpriteToRender(Sprite[] sprites) {
        for (Sprite sprite : sprites) {
            addSpriteToRender(sprite);
        }
    }

    public void addSpriteToRender(List<Sprite> sprites) {
        for (Sprite sprite : sprites) {
            addSpriteToRender(sprite);
        }
    }

    public void closeSpriteInputStream() {
        int[] startIndexesLayer = new int[Layer.values().length];

        for (int i = 1; i < startIndexesLayer.length; i++) {
            startIndexesLayer[i] = Layer.values()[i - 1].countRenderSprites() + startIndexesLayer[i - 1];
        }


        spriteDtoArray = new SpriteDto[spriteDtoList.size()];
        // Layers l1 > l2 > l3
        // Shaders s1
        while (!spriteDtoList.isEmpty()) {
            Shader currentShaderLoaded = spriteDtoList.get(0).getShader();
            for (int i = 0; i < spriteDtoList.size();) {
                SpriteDto spriteDTO = spriteDtoList.get(i);

                if (currentShaderLoaded == spriteDTO.getShader()) {
                    int layerIndex = spriteDTO.getLayer().ordinal();
                    spriteDtoArray[ startIndexesLayer[layerIndex] ] = spriteDTO;
                    startIndexesLayer[layerIndex]++;

                    spriteDtoList.remove(i);
                }
                else {
                    i++;
                }
            }
        }
    }

    public SpriteDto[] getSpriteDataArray() {
        return spriteDtoArray;
    }

    public void setCameraToRender(Camera camera) {
        this.camera = Camera.copy(camera);
    }

    public Camera getCamera() {
        return camera;
    }
}
