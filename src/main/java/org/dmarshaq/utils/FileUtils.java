package org.dmarshaq.utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URLClassLoader;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.dmarshaq.utils.BufferUtils.createByteBuffer;

public interface FileUtils {

    static String loadAsString(String filePath) {
        String result = "";

        try {
            InputStream inputStream = URLClassLoader.getSystemResourceAsStream(filePath);
            result = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (Exception e) {
            System.out.println("Could not read resource file " + filePath + " due to: " + e.toString());
            throw new RuntimeException(e);
        }

        return result;
    }

    static BufferedImage loadAsImage(String filePath) {
        BufferedImage result;

        try {
            InputStream inputStream = URLClassLoader.getSystemResourceAsStream(filePath);

            result = ImageIO.read(inputStream);
        } catch (IOException e) {
            System.out.println("Could not read resource file " + filePath + " due to: " + e.toString());
            throw new RuntimeException(e);
        }

        return result;
    }

    static BufferedImage loadAsImage(File file) {
        BufferedImage result;

        try {
            result = ImageIO.read(file);
        } catch (IOException e) {
            System.out.println("Could not read resource file " + file + " due to: " + e.toString());
            throw new RuntimeException(e);
        }

        return result;
    }

    static ByteBuffer loadAsByteBuffer(String filePath) {
        try {
            InputStream inputStream = URLClassLoader.getSystemResourceAsStream(filePath);
            return createByteBuffer(inputStream.readAllBytes());
        } catch (IOException e) {
            System.out.println("Could not read resource file " + filePath + " due to: " + e.toString());
            throw new RuntimeException(e);
        }

    }

    static List<File> findAllFilesInResources(String path, String fileType)  {
        List<File> files = new ArrayList<>();
        // Search files tree
        Path dir = null;
        try {
            dir = Paths.get(URLClassLoader.getSystemResource(path).toURI());
        }
        catch (URISyntaxException e) {
            e.printStackTrace();
        }
        try (Stream<Path> filePathStream= Files.walk(dir)) {
            filePathStream.forEach(filePath -> {
                // Filter images with .png
                if (Files.isRegularFile(filePath)) {
                    if (filePath.toString().endsWith(fileType)) {
                        files.add(filePath.toFile());
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return files;
    }
}
