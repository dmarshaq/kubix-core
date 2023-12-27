package app;

public class DeltaTime {
    private static float time = 0;

    public static float getSeconds() {
        return time;
    }
    public static float getMiliSeconds() {
        return time * 1000f;
    }
    public static void setTime(float t) {
        time = t / 1000000000f;
    }

}
