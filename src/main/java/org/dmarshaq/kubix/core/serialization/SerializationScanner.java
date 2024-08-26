package org.dmarshaq.kubix.core.serialization;

import java.io.*;
import java.util.List;

public class SerializationScanner {
    public static Packet[] deserializeResourcesIntoPackets(List<String> files) {
        Packet[] packets = new Packet[files.size()];

        byte[] data = null;
        int i = 0;
        for (String file : files) {
            data = readFromFile(file);
            packets[i] = new Packet(readStringASCII(data, 0), data);
            i++;
        }

        return packets;
    }

    public static void serializeResourcesIntoPackets(List<Packet> packets) {
        for(int i = 1; i <= packets.size(); i++) {
            saveToFile("src/main/resources/packet/packet" + i + ".kub", packets.get(i - 1).getData());
        }
    }

    protected static int writeBytes(byte[] dest, int pointer, byte[] src) {
        for (byte b : src) {
            dest[pointer++] = b;
        }
        return pointer;
    }

    protected static int writeBytes(byte[] dest, int pointer, short[] src) {
        for (short s : src) {
            pointer = writeBytes(dest, pointer, s);
        }
        return pointer;
    }

    protected static int writeBytes(byte[] dest, int pointer, char[] src) {
        for (char c : src) {
            pointer = writeBytes(dest, pointer, c);
        }
        return pointer;
    }

    protected static int writeBytes(byte[] dest, int pointer, int[] src) {
        for (int i : src) {
            pointer = writeBytes(dest, pointer, i);
        }
        return pointer;
    }

    protected static int writeBytes(byte[] dest, int pointer, long[] src) {
        for (long l : src) {
            pointer = writeBytes(dest, pointer, l);
        }
        return pointer;
    }

    protected static int writeBytes(byte[] dest, int pointer, float[] src) {
        for (float f : src) {
            pointer = writeBytes(dest, pointer, f);
        }
        return pointer;
    }

    protected static int writeBytes(byte[] dest, int pointer, double[] src) {
        for (double d : src) {
            pointer = writeBytes(dest, pointer, d);
        }
        return pointer;
    }

    protected static int writeBytes(byte[] dest, int pointer, boolean[] src) {
        for (boolean b : src) {
            pointer = writeBytes(dest, pointer, b);
        }
        return pointer;
    }

    /*
    Note: doesn't specify string length nor store data as chars, Data is stored in bytes according to ASCII
     */
    protected static int writeBytes(byte[] dest, int pointer, String string) {
        return writeBytes(dest, pointer, string.getBytes());
    }

    protected static int writeBytes(byte[] dest, int pointer, byte value) {
        dest[pointer] = value;
        pointer++;
        return pointer;
    }

    protected static int writeBytes(byte[] dest, int pointer, short value) {
        dest[pointer++] = (byte) ((value >> 8) & 0xff);
        dest[pointer++] = (byte) ((value >> 0) & 0xff);
        return pointer;
    }

    protected static int writeBytes(byte[] dest, int pointer, char value) {
        dest[pointer++] = (byte) ((value >> 8) & 0xff);
        dest[pointer++] = (byte) ((value >> 0) & 0xff);
        return pointer;
    }

    protected static int writeBytes(byte[] dest, int pointer, int value) {
        dest[pointer++] = (byte) ((value >> 24) & 0xff);
        dest[pointer++] = (byte) ((value >> 16) & 0xff);
        dest[pointer++] = (byte) ((value >> 8) & 0xff);
        dest[pointer++] = (byte) ((value >> 0) & 0xff);
        return pointer;
    }

    protected static int writeBytes(byte[] dest, int pointer, long value) {
        dest[pointer++] = (byte) ((value >> 56) & 0xff);
        dest[pointer++] = (byte) ((value >> 48) & 0xff);
        dest[pointer++] = (byte) ((value >> 40) & 0xff);
        dest[pointer++] = (byte) ((value >> 32) & 0xff);
        dest[pointer++] = (byte) ((value >> 24) & 0xff);
        dest[pointer++] = (byte) ((value >> 16) & 0xff);
        dest[pointer++] = (byte) ((value >> 8) & 0xff);
        dest[pointer++] = (byte) ((value >> 0) & 0xff);
        return pointer;
    }

    protected static int writeBytes(byte[] dest, int pointer, float value) {
        int data = Float.floatToIntBits(value);
        return writeBytes(dest, pointer, data);
    }

    protected static int writeBytes(byte[] dest, int pointer, double value) {
        long data = Double.doubleToLongBits(value);
        return writeBytes(dest, pointer, data);
    }

    protected static int writeBytes(byte[] dest, int pointer, boolean value) {
        dest[pointer++] = (byte)(value ? 1 : 0);
        return pointer;
    }

    protected static byte readByte(byte[] src, int pointer) {
        return src[pointer];
    }

    protected static byte[] readBytes(byte[] src, int pointer, int length) {
        byte[] bytes = new byte[length];
        for (int i = 0; i < length; i++) {
            bytes[i] = readByte(src, pointer);
            pointer++;
        }
        return bytes;
    }

    protected static short readShort(byte[] src, int pointer) {
        return (short) (((src[pointer] & 0xff ) << 8) | (src[pointer + 1] & 0xff));
    }

    protected static char readChar(byte[] src, int pointer) {
        return (char) (((src[pointer] & 0xff ) << 8) | (src[pointer + 1] & 0xff));
    }

    protected static int readInt(byte[] src, int pointer) {
        return (int) (((src[pointer] & 0xff) << 24) | ((src[pointer + 1] & 0xff) << 16) | ((src[pointer + 2] & 0xff) << 8) | (src[pointer + 3] & 0xff));
    }

    protected static long readLong(byte[] src, int pointer) {
        return (long) ((((long) src[pointer] & 0xff) << 56) | (((long) src[pointer + 1] & 0xff) << 48) | (((long) src[pointer + 2] & 0xff) << 40) | (((long) src[pointer + 3] & 0xff) << 32) | (((long) src[pointer + 4] & 0xff) << 24) | (((long) src[pointer + 5] & 0xff) << 16) | (((long) src[pointer + 6] & 0xff) << 8) | (((long) src[pointer + 7] & 0xff)));
    }

    protected static float readFloat(byte[] src, int pointer) {
        return Float.intBitsToFloat(readInt(src, pointer));
    }

    protected static double readDouble(byte[] src, int pointer) {
        return Double.longBitsToDouble(readLong(src, pointer));
    }

    protected static boolean readBoolean(byte[] src, int pointer) {
        return src[pointer] != 0;
    }

    protected static String readStringASCII(byte[] src, int pointer) {
        StringBuilder nameBuilder = new StringBuilder();

        char nameLength = readChar(src, pointer);
        pointer += 2;
        for (char c = 0; c < nameLength; c++) {
            nameBuilder.append((char) src[pointer]);
            pointer++;
        }

        return nameBuilder.toString();
    }

    private static void saveToFile(String path, byte[] data) {
        try {
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(path));
            stream.write(data);
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static byte[] readFromFile(String path) {
        byte[] buffer = null;
        try {
            BufferedInputStream stream = new BufferedInputStream(new FileInputStream(path));
            buffer = new byte[stream.available()];
            stream.read(buffer);
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }
}
