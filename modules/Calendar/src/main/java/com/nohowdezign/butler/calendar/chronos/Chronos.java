package com.nohowdezign.butler.calendar.chronos;

import com.nohowdezign.butler.database.UserProfile;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Timer;

/**
 * @author Noah Howard
 */
public class Chronos {
    private UserProfile profile = new UserProfile();

    public void createAlarm(int id, String alarmTime, String alarmType) {
        ZonedDateTime zdt = getDateTimeFromString(alarmTime);
        long timeUntilAlarm = zdt.toInstant().toEpochMilli() - System.currentTimeMillis();
        if(!hasTimerAlreadyPassed(zdt)) {
            Timer t = new Timer();
            AlarmTask mTask = new AlarmTask(id, zdt, alarmType);
            t.schedule(mTask, timeUntilAlarm);
        }
    }

    public ZonedDateTime getDateTimeFromString(String alarmTime) {
        LocalDateTime alarmDateTime = LocalDateTime.parse(alarmTime);
        ZonedDateTime zdt = alarmDateTime.atZone(ZoneId.of(
                profile.getAttributeFromProfile("timezone", "name", UserProfile.DEFAULT_USER)));
        return zdt;
    }

    public boolean hasTimerAlreadyPassed(ZonedDateTime zdt) {
        return (zdt.toInstant().toEpochMilli() - System.currentTimeMillis()) < 0;
    }

}
