package org.dmarshaq.kubix.core.serialization.texture;

import org.dmarshaq.kubix.core.graphic.resource.Texture;
import org.dmarshaq.kubix.core.serialization.Packet;
import org.dmarshaq.kubix.core.util.FileUtils;
import org.dmarshaq.kubix.core.util.IndexedHashMap;


import java.io.File;
import java.util.*;


public class TextureManager {
    public static final IndexedHashMap<String, Texture> TEXTURE_MAP = new IndexedHashMap<>();
    private static final List<TextureDto> TEXTURE_DTOS = new ArrayList<>();
    // Ordinal 0 is for NO_TEXTURE.
    static int textureCounter = 0;

    public static void loadPackets(Packet[] inputPackets, List<Packet> outputPackets) {
        TEXTURE_MAP.put("notexture", Texture.NO_TEXTURE);
        textureCounter++;

        loadTextureDtosFromPackets(inputPackets);

        loadAndCompareTextureDtosFromImages();

        convertTextureDtosIntoTextureMap();

        outputPackets.add(TextureScanner.serializeTexturePacket(TEXTURE_DTOS));

        TEXTURE_DTOS.clear();
    }

    private static void convertTextureDtosIntoTextureMap() {
        for (TextureDto textureDto : TextureManager.TEXTURE_DTOS) {
            TEXTURE_MAP.put(textureDto.getName(), TextureScanner.toTexture(textureDto));
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

    // TODO: JAR texture loading.
    private static void loadAndCompareTextureDtosFromImages()  {

        List<File> files = FileUtils.findAllFilesInResources("texture", ".png");
        TextureDto empty = new TextureDto();
        for (File file : files) {
            empty.setName(file.getName().substring(0, file.getName().length() - 4));
            empty.setLastModified(file.lastModified());
            if (TEXTURE_DTOS.contains(empty)) {


                int index = TEXTURE_DTOS.indexOf(empty);
                if (TEXTURE_DTOS.get(index).getLastModified() != empty.getLastModified()) {
                    System.out.println("Resource: " + TEXTURE_DTOS.get(index).getName() + ", out of date");
                    TEXTURE_DTOS.set(index, TextureScanner.loadTextureDtoFromImage(file));
                }
            }
            else {
                TEXTURE_DTOS.add(TextureScanner.loadTextureDtoFromImage(file));
            }
        }
    }




}
