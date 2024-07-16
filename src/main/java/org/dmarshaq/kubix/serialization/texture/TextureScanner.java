package org.dmarshaq.kubix.serialization.texture;

import org.dmarshaq.kubix.core.serialization.Packet;
import org.dmarshaq.kubix.core.serialization.SerializationScanner;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.*;

import static org.dmarshaq.kubix.core.util.FileUtils.loadAsImage;

public class TextureScanner extends SerializationScanner {

    static final String HEADER = "TEX";

    static TextureDto[] deserializeTexturePacket(Packet packet) {
        byte[] src = packet.getData();
        int pointer = Short.BYTES + readShort(src, 0);
        int dtoCount = readShort(src, pointer);
        pointer += Short.BYTES;
        TextureDto[] textureDtos = new TextureDto[dtoCount];
        for (int i = 0; i < dtoCount; i++) {
            textureDtos[i] = new TextureDto();

            String name = readStringASCII(src, pointer);
            textureDtos[i].setName(name);
            pointer += Short.BYTES + name.length();

            textureDtos[i].setLastModified(readLong(src, pointer));
            pointer += Long.BYTES;

            textureDtos[i].setWidth(readInt(src, pointer));
            pointer += Integer.BYTES;

            textureDtos[i].setHeight(readInt(src, pointer));
            pointer += Integer.BYTES;

            int[] pixels = new int[readInt(src, pointer)];
            pointer += Integer.BYTES;
            for (int j = 0; j < pixels.length; j++) {
                pixels[j] = readInt(src, pointer);
                pointer += Integer.BYTES;
            }
            textureDtos[i].setData(pixels);
        }
        return textureDtos;
    }

    static Packet serializeTexturePacket(List<TextureDto> textureDtoList) {
        byte[] stream = getBytes(textureDtoList);


        int pointer = writeBytes(stream, 0, (char) HEADER.length());
        pointer = writeBytes(stream, pointer, HEADER);
        pointer = writeBytes(stream, pointer, (short) textureDtoList.size());

        for (TextureDto textureDto : textureDtoList) {
            pointer = writeBytes(stream, pointer, (short) textureDto.getName().length());
            pointer = writeBytes(stream, pointer, textureDto.getName());
            pointer = writeBytes(stream, pointer, textureDto.getLastModified());
            pointer = writeBytes(stream, pointer, textureDto.getWidth());
            pointer = writeBytes(stream, pointer, textureDto.getHeight());
            pointer = writeBytes(stream, pointer, textureDto.getWidth() * textureDto.getHeight());
            pointer = writeBytes(stream, pointer, textureDto.getData());
        }


        return new Packet("TEX", stream);
    }

    private static byte[] getBytes(List<TextureDto> textureDtoList) {
        int textureCount = textureDtoList.size();
        int namesLength = textureCount * Short.BYTES;
        int pixelsLength = 0;
        for (TextureDto t : textureDtoList) {
            namesLength += t.getName().length();
            pixelsLength += t.getWidth() * t.getHeight();
        }
        return new byte[Short.BYTES + HEADER.length() + Short.BYTES + namesLength + (textureCount * (Long.BYTES + Integer.BYTES + Integer.BYTES + Integer.BYTES)) + pixelsLength * Integer.BYTES];
    }

    static TextureDto loadTextureDtoFromImage(File file) {
        TextureDto textureDto = new TextureDto();
        int[] pixels = null;

        BufferedImage image = loadAsImage(file);
        int width = image.getWidth();
        int height = image.getHeight();
        pixels = new int[width * height];
        image.getRGB(0 ,0, width, height, pixels, 0 , width);

        int[] data = new int[width * height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int index = x + y * width;
                int a = (pixels[index] & 0xff000000) >> 24;
                int r = (pixels[index] & 0xff0000) >> 16;
                int g = (pixels[index] & 0xff00) >> 8;
                int b = (pixels[index] & 0xff);
                data[x + (height - 1 - y) * width] = a << 24 | b << 16 | g << 8 | r;
            }
        }

        textureDto.setName(file.getName().substring(0, file.getName().length() - 4));
        textureDto.setLastModified(file.lastModified());
        textureDto.setWidth(width);
        textureDto.setHeight(height);
        textureDto.setData(data);
        return textureDto;
    }
}
