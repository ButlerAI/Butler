package com.nohowdezign.butler.calendar.chronos;

import java.util.Timer;

/**
 * @author Noah Howard
 */
public class Chronos {

    public void createAlarm(long timeUntilAlarm) {
        Timer t = new Timer();
        AlarmTask mTask = new AlarmTask();
        t.scheduleAtFixedRate(mTask, 0, timeUntilAlarm);
    }

}
