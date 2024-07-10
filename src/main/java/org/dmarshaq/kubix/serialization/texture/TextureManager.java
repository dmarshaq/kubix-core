package org.dmarshaq.kubix.serialization.texture;

import org.dmarshaq.kubix.core.graphic.Texture;
import org.dmarshaq.kubix.core.serialization.Packet;
import org.dmarshaq.kubix.core.util.FileUtils;


import java.io.File;
import java.util.*;


public class TextureManager {
    public static final Map<String, Texture> TEXTURE_MAP = new HashMap<>();
    private static final List<TextureDto> TEXTURE_DTOS = new ArrayList<>();

    public static void loadPackets(Packet[] inputPackets, List<Packet> outputPackets) {


        loadTextureDtosFromPackets(inputPackets);

        loadAndCompareTextureDtosFromImages();

        convertTextureDtosIntoTextureMap();

        outputPackets.add(TextureScanner.serializeTexturePacket(TEXTURE_DTOS));

        TEXTURE_DTOS.clear();
    }

    private static void convertTextureDtosIntoTextureMap() {
        for (TextureDto textureDto : TextureManager.TEXTURE_DTOS) {
            TEXTURE_MAP.put(textureDto.getName(), TextureDto.toTexture(textureDto));
        }
    }

    private static void loadTextureDtosFromPackets(Packet[] packets) {
        for (int i = 0; i < packets.length; i++) {
            if (packets[i] != null && packets[i].getHeader().equals(TextureScanner.HEADER)) {
                Collections.addAll(TEXTURE_DTOS, TextureScanner.deserializeTexturePacket(packets[i]));
                packets[i] = null;
            }
        }
    }

    private static void loadAndCompareTextureDtosFromImages()  {
        List<File> files = FileUtils.findAllFilesInResources("textures", ".png");
        TextureDto empty = new TextureDto();
        for (File file : files) {
            empty.setName(file.getName().substring(0, file.getName().length() - 4));
            empty.setLastModified(file.lastModified());
            if (TEXTURE_DTOS.contains(empty)) {
                int index = TEXTURE_DTOS.indexOf(empty);
                if (TEXTURE_DTOS.get(index).getLastModified() != empty.getLastModified()) {
                    System.out.println("Out of data");
                    TEXTURE_DTOS.set(index, TextureScanner.loadTextureDtoFromImage(file, empty.getName()));
                }
            }
            else {
                TEXTURE_DTOS.add(TextureScanner.loadTextureDtoFromImage(file, empty.getName()));
            }
        }
    }


}
