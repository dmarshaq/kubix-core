package org.dmarshaq.app;

import org.dmarshaq.graphics.*;
import org.dmarshaq.mathj.MathJ;
import org.dmarshaq.mathj.Matrix4f;

import java.util.ArrayList;
import java.util.List;

public class Snapshot {
    private final List<SpriteDTO> spriteDTOList = new ArrayList<>();
    private SpriteDTO[] spriteDTOArray;

    // CAMERA DATA
    private Camera camera;

    public void addSpriteToRender(Sprite sprite) {
        SpriteDTO spriteDTO = SpriteDTO.builder()
                .color(MathJ.Math2D.toVector4f(sprite.getColor()))
                .layer(sprite.getLayer())
                .width(sprite.getWidth())
                .height(sprite.getHeight())
                .shader(sprite.getShader())
                .subTexture(sprite.getSubTexture())
                .texture(sprite.getTexture())
                .transform(Matrix4f.duplicate(sprite.getTransform()))
                .build();

        spriteDTO.getLayer().incrementRenderSpriteCount();
        spriteDTOList.add(spriteDTO);
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


        spriteDTOArray = new SpriteDTO[spriteDTOList.size()];
        // Layers l1 > l2 > l3
        // Shaders s1
        while (!spriteDTOList.isEmpty()) {
            Shader currentShaderLoaded = spriteDTOList.get(0).getShader();
            for (int i = 0; i < spriteDTOList.size();) {
                SpriteDTO spriteDTO = spriteDTOList.get(i);

                if (currentShaderLoaded == spriteDTO.getShader()) {
                    int layerIndex = spriteDTO.getLayer().getIndex();
                    spriteDTOArray[ startIndexesLayer[layerIndex] ] = spriteDTO;
                    startIndexesLayer[layerIndex]++;

                    spriteDTOList.remove(i);
                }
                else {
                    i++;
                }
            }
        }
    }

    public SpriteDTO[] getSpriteDataArray() {
        return spriteDTOArray;
    }

    public void setCameraToRender(Camera camera) {
        this.camera = Camera.copy(camera);
    }

    public Camera getCamera() {
        return camera;
    }
}
