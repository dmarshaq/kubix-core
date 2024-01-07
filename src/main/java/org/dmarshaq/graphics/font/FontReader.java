package org.dmarshaq.graphics.font;

import java.util.Scanner;

import static org.dmarshaq.utils.FileUtils.loadAsString;


public interface FontReader {


    public static Character[] constructFontData(String filePath)  {
        Character[] result = null;

        int fontCharacterIndex = 0;
        boolean dataFlow = false;
        Scanner fileReader = new Scanner(loadAsString(filePath));
        StringBuilder line = new StringBuilder();
        while (fileReader.hasNextLine()) {
            line.replace(0, line.length(), fileReader.nextLine());

            if (dataFlow) {
                if (line.indexOf("char") == -1) {
                    break;
                }
                int[] data = new int[8];
                for(int e = 0; e < 8; e++) {

                    int start = line.indexOf("=") + 1;
                    line.delete(0, start);
                    int end = line.indexOf(" ");

                    data[e] = Integer.parseInt(line.substring(0, end));
                }
                result[fontCharacterIndex] = new Character(data[0], data[1], data[2], data[3], data[4], data[5], data[6], data[7]);
                fontCharacterIndex++;
            }

            if (line.indexOf("chars count=") > -1 ) {
                result = new Character[Integer.parseInt(line.substring(line.indexOf("=") + 1))];
                dataFlow = true;
            }
        }
        fileReader.close();

        return result;
    }

    private static StringBuilder assign(StringBuilder sb, String s) {
        sb.replace(0, sb.length(), s);
        return sb;
    }
}
