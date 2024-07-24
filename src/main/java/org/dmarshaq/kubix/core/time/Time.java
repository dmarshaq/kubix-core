package org.dmarshaq.kubix.core.time;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class Time {
    private static final List<Timer> timerList = new ArrayList<>();

    public static class DeltaTime {
        private static double tick = 0;
        /**
         * @return time per one tick in seconds
         */
        public static double getTickSeconds() {
            return tick / 1000;
        }
        /**
         * @return time per one tick in milliseconds
         */
        public static double getTickMilliseconds() {
            return tick;
        }
        /**
         * @param t time per one tick in milliseconds
         */
        public static void setTickTime(double t) {
            tick = t;
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
        @Setter
        private boolean repeatable;
        @Setter
        private boolean frozen;
        private float totalTime;
        @Setter
        @Getter
        private float currentTime;

        public Timer(float milliseconds, boolean frozen) {
            timerList.add(this);
            totalTime = milliseconds;
            currentTime = milliseconds;
            this.frozen = frozen;
        }

        boolean iterateTimer() {
            if (!frozen) {
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

        public void resetTimer() {
            currentTime = totalTime;
        }

        public void resetTimer(float milliseconds) {
            totalTime = milliseconds;
            currentTime = milliseconds;
        }

        private float countTimer(float timer_time) {
            if (timer_time == -1) {
                return -1;
            }

            timer_time -= (float) DeltaTime.getTickMilliseconds();
            if (timer_time <= 0) {
                return 0;
            }

            return timer_time;
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
