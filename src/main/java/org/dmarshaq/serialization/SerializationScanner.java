package org.dmarshaq.serialization;

import java.io.*;

public class SerializationScanner {

    public static final byte[] HEADER = "KUB".getBytes();
    public static final short VERSION = 0x0100; // VERSION 1.0

    public static int writeBytes(byte[] dest, int pointer, byte[] src) {
        for (byte b : src) {
            dest[pointer++] = b;
        }
        return pointer;
    }

    public static int writeBytes(byte[] dest, int pointer, short[] src) {
        for (short s : src) {
            pointer = writeBytes(dest, pointer, s);
        }
        return pointer;
    }

    public static int writeBytes(byte[] dest, int pointer, char[] src) {
        for (char c : src) {
            pointer = writeBytes(dest, pointer, c);
        }
        return pointer;
    }

    public static int writeBytes(byte[] dest, int pointer, int[] src) {
        for (int i : src) {
            pointer = writeBytes(dest, pointer, i);
        }
        return pointer;
    }

    public static int writeBytes(byte[] dest, int pointer, long[] src) {
        for (long l : src) {
            pointer = writeBytes(dest, pointer, l);
        }
        return pointer;
    }

    public static int writeBytes(byte[] dest, int pointer, float[] src) {
        for (float f : src) {
            pointer = writeBytes(dest, pointer, f);
        }
        return pointer;
    }

    public static int writeBytes(byte[] dest, int pointer, double[] src) {
        for (double d : src) {
            pointer = writeBytes(dest, pointer, d);
        }
        return pointer;
    }

    public static int writeBytes(byte[] dest, int pointer, boolean[] src) {
        for (boolean b : src) {
            pointer = writeBytes(dest, pointer, b);
        }
        return pointer;
    }

    /*
    Note: doesn't specify string length nor store data as chars, Data is stored in bytes according to ASCII
     */
    public static int writeBytes(byte[] dest, int pointer, String string) {
        return writeBytes(dest, pointer, string.getBytes());
    }

    public static int writeBytes(byte[] dest, int pointer, byte value) {
        dest[pointer] = value;
        pointer++;
        return pointer;
    }

    public static int writeBytes(byte[] dest, int pointer, short value) {
        dest[pointer++] = (byte) ((value >> 8) & 0xff);
        dest[pointer++] = (byte) ((value >> 0) & 0xff);
        return pointer;
    }

    public static int writeBytes(byte[] dest, int pointer, char value) {
        dest[pointer++] = (byte) ((value >> 8) & 0xff);
        dest[pointer++] = (byte) ((value >> 0) & 0xff);
        return pointer;
    }

    public static int writeBytes(byte[] dest, int pointer, int value) {
        dest[pointer++] = (byte) ((value >> 24) & 0xff);
        dest[pointer++] = (byte) ((value >> 16) & 0xff);
        dest[pointer++] = (byte) ((value >> 8) & 0xff);
        dest[pointer++] = (byte) ((value >> 0) & 0xff);
        return pointer;
    }

    public static int writeBytes(byte[] dest, int pointer, long value) {
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

    public static int writeBytes(byte[] dest, int pointer, float value) {
        int data = Float.floatToIntBits(value);
        return writeBytes(dest, pointer, data);
    }

    public static int writeBytes(byte[] dest, int pointer, double value) {
        long data = Double.doubleToLongBits(value);
        return writeBytes(dest, pointer, data);
    }

    public static int writeBytes(byte[] dest, int pointer, boolean value) {
        dest[pointer++] = (byte)(value ? 1 : 0);
        return pointer;
    }

    public static byte readByte(byte[] src, int pointer) {
        return src[pointer];
    }

    public static short readShort(byte[] src, int pointer) {
        return (short) (((src[pointer] & 0xff ) << 8) | (src[pointer + 1] & 0xff));
    }

    public static char readChar(byte[] src, int pointer) {
        return (char) (((src[pointer] & 0xff ) << 8) | (src[pointer + 1] & 0xff));
    }

    public static int readInt(byte[] src, int pointer) {
        return (int) (((src[pointer] & 0xff) << 24) | ((src[pointer + 1] & 0xff) << 16) | ((src[pointer + 2] & 0xff) << 8) | (src[pointer + 3] & 0xff));
    }

    public static long readLong(byte[] src, int pointer) {
        return (long) ((((long) src[pointer] & 0xff) << 56) | (((long) src[pointer + 1] & 0xff) << 48) | (((long) src[pointer + 2] & 0xff) << 40) | (((long) src[pointer + 3] & 0xff) << 32) | (((long) src[pointer + 4] & 0xff) << 24) | (((long) src[pointer + 5] & 0xff) << 16) | (((long) src[pointer + 6] & 0xff) << 8) | (((long) src[pointer + 7] & 0xff)));
    }

    public static float readFloat(byte[] src, int pointer) {
        return Float.intBitsToFloat(readInt(src, pointer));
    }

    public static double readDouble(byte[] src, int pointer) {
        return Double.longBitsToDouble(readLong(src, pointer));
    }

    public static boolean readBoolean(byte[] src, int pointer) {
        return src[pointer] != 0;
    }

    public static void saveToFile(String path, byte[] data) {
        try {
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(path));
            stream.write(data);
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static byte[] readFromFile(String path) {
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
