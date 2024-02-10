package org.dmarshaq.time;

import java.util.ArrayList;
import java.util.List;

public class Time {
    private static final List<Timer> timerList = new ArrayList<>();

    public static class DeltaTime {
        private static float time = 0;

        public static float getSeconds() {
            return time;
        }
        public static float getMilliseconds() {
            return time * 1000f;
        }
        public static void setTime(float t) {
            time = t / 1000000000f;
        }

    }

    public static void updateTimers() {
        boolean state;
        for (int i = 0; i < timerList.size(); i++) {
            state = timerList.get(i).iterateTimer();
            if (!state) {
                timerList.remove(i);
            }
        }
    }

    public abstract static class Timer {
        private boolean repeatable;
        private float totalTime;
        private float currentTime;

        public Timer(float milliseconds) {
            timerList.add(this);
            totalTime = milliseconds;
            currentTime = milliseconds;
            repeatable = false;
        }

        boolean iterateTimer() {
            if ((currentTime = countTimer(currentTime)) == 0) {
                timerOut();

                if (!repeatable) {
                    return false;
                }
            }
            return true;
        }

        public float getCurrentTime() {
            return totalTime - currentTime;
        }

        private float countTimer(float timer_time) {
            if (timer_time == -1) {
                return -1;
            }

            timer_time -= DeltaTime.getMilliseconds();
            if (timer_time <= 0) {
                return 0;
            }

            return timer_time;
        }

        public void setRepeatable(boolean repeatable) {
            this.repeatable = repeatable;
        }
        public void setNextTime(float milliseconds) {
            totalTime = milliseconds;
            currentTime = totalTime;
        }

        /**
         * Be sure to setRepeatable() if you want to repeat this timer,
         * also setNextTime() for next timer iteration otherwise it's
         * going to alarm in next update.
         */
        public abstract void timerOut();
    }



}
