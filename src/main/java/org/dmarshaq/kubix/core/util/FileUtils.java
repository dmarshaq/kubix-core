package org.dmarshaq.kubix.core.util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLClassLoader;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.dmarshaq.kubix.core.util.BufferUtils.createByteBuffer;

public interface FileUtils {

    static String loadAsString(String filePath) {
        String result;

        try {
            InputStream inputStream = URLClassLoader.getSystemResourceAsStream(filePath);
            result = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.out.println("Could not read resource file " + filePath + " due to: " + e.toString());
            throw new RuntimeException(e);
        }

        return result;
    }

    static String loadAsString(File file) {
        String result;

        try {
            InputStream inputStream = new FileInputStream(file);
            result = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.out.println("Could not read resource file " + file.getPath() + " due to: " + e.toString());
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

    static List<Path> findAllFilesInResourcesJAR(String path, String fileType)  {
        try {
            List<Path> result = getPathsFromResourceJAR(path);
            for (int i = 0; i < result.size(); i++) {
                if (!result.get(i).toString().endsWith(fileType)) {
                    result.remove(i);
                    i--;
                }
            }
            return result;
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Get all paths from a folder that inside the JAR file
    private static List<Path> getPathsFromResourceJAR(String folder)
            throws URISyntaxException, IOException {

        List<Path> result;

        // get path of the current running JAR
        String jarPath = FileUtils.class.getProtectionDomain()
                .getCodeSource()
                .getLocation()
                .toURI()
                .getPath();

        // file walks JAR
        URI uri = URI.create("jar:file:" + jarPath);
        try (FileSystem fs = FileSystems.newFileSystem(uri, Collections.emptyMap())) {
            result = Files.walk(fs.getPath(folder))
                    .filter(Files::isRegularFile)
                    .collect(Collectors.toList());
        }

        return result;

    }
}
