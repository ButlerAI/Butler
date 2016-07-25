package com.nohowdezign.butler.calendar.chronos;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Timer;

/**
 * @author Noah Howard
 */
public class Chronos {

    public void createAlarm(String alarmTime) {
        ZonedDateTime zdt = getDateTimeFromString(alarmTime);
        long timeUntilAlarm = zdt.toInstant().toEpochMilli() - System.currentTimeMillis();
        if(!hasTimerAlreadyPassed(zdt)) {
            Timer t = new Timer();
            AlarmTask mTask = new AlarmTask(zdt);
            t.schedule(mTask, timeUntilAlarm);
        }
    }

    public ZonedDateTime getDateTimeFromString(String alarmTime) {
        LocalDateTime alarmDateTime = LocalDateTime.parse(alarmTime);
        ZonedDateTime zdt = alarmDateTime.atZone(ZoneId.of("America/New_York"));
        return zdt;
    }

    public boolean hasTimerAlreadyPassed(ZonedDateTime zdt) {
        return (zdt.toInstant().toEpochMilli() - System.currentTimeMillis()) < 0;
    }

}
