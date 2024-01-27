package org.dmarshaq.time;

public class Time {
    public static class DeltaTime {
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

    public static class Timer {
        public static float countTimer(float timer_time) {
            if (timer_time == -1) {
                return -1;
            }

            timer_time -= DeltaTime.getMiliSeconds();
            if (timer_time <= 0) {
                return 0;
            }

            return timer_time;
        }

    }

}
