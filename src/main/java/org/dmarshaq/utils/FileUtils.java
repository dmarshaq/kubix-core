package org.dmarshaq.utils;

import org.dmarshaq.graphics.font.FontReader;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URLClassLoader;

public interface FileUtils {

    static String loadAsString(String filePath) {
        StringBuilder result = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(getResource(filePath)));
            String buffer = "";
            while ((buffer = reader.readLine()) != null) {
                result.append(buffer + '\n');
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    static File getResource(String filePath) {
        return new File(getResourcePath(filePath));
    }

    static String getResourcePath(String filePath) {
        return FileUtils.class.getClassLoader().getResource(filePath).getFile();
    }
}
