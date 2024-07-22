package org.dmarshaq.kubix.core.util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
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
        List<File> result = new ArrayList<>();

        try {
            Path dir = Paths.get(URLClassLoader.getSystemResource(path).toURI());
            Stream<Path> filePathStream = Files.walk(dir);
            filePathStream.forEach(filePath -> {
                // Filter images with .png
                if (Files.isRegularFile(filePath)) {
                    if (filePath.toString().endsWith(fileType)) {
                        result.add(filePath.toFile());
                    }
                }
            });
        } catch (URISyntaxException | IOException e) {
            System.out.println("Could not find resource file " + fileType + " in " + path + " due to: " + e.toString());
        }

        return result;
    }

    static List<String> findAllFilesInResourcesJar(String path, String fileType)  {
        List<String> result = new ArrayList<>();

        try {
            List<Path> pathsFromJar = getPathsFromResourceJAR(path);
            for (int i = 0; i < pathsFromJar.size(); i++) {
                if (!pathsFromJar.get(i).toString().endsWith(fileType)) {
                    pathsFromJar.remove(i);
                    i--;
                }
                else {
                    result.add(pathsFromJar.get(i).toString());
                }
            }
        } catch (URISyntaxException | IOException e) {
            System.out.println("Could not find Jar resource file " + fileType + " in " + path + " due to: " + e.toString());
        }

        return result;
    }

    // Get all paths from a folder that inside the JAR file
    private static List<Path> getPathsFromResourceJAR(String folder) throws URISyntaxException, IOException {
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
