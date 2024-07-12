package org.dmarshaq.kubix.core.time;

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
                if (timerList.get(i).repeatable) {
                    timerList.get(i).resetTimer();
                }
                else {
                    timerList.remove(i);
                    i--;
                }
            }
        }
    }

    public abstract static class Timer {
        private boolean repeatable, freezed;
        private float totalTime;
        private float currentTime;

        public Timer(float milliseconds, boolean freezed) {
            timerList.add(this);
            totalTime = milliseconds;
            currentTime = milliseconds;
            this.freezed = freezed;
        }

        boolean iterateTimer() {
            if (!freezed) {
                if ((currentTime = countTimer(currentTime)) == 0) {
                    timerOut();
                    return false;
                }
            }
            return true;
        }

        public float getTimeSinceStart() {
            return totalTime - currentTime;
        }

        public float getCurrentTime() {
            return currentTime;
        }

        public void setCurrentTime(float milliseconds) {
            currentTime = milliseconds;
        }

        public void resetTimer() {
            currentTime = totalTime;
        }

        public void resetTimer(float milliseconds) {
            totalTime = milliseconds;
            currentTime = milliseconds;
        }

        public void freeze() {
            freezed = true;
        }

        public void unfreeze() {
            freezed = false;
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