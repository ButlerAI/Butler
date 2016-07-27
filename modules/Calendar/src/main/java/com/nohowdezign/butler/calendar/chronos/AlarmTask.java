package com.nohowdezign.butler.calendar.chronos;

import com.nohowdezign.butler.calendar.events.AlarmEvent;
import com.nohowdezign.butler.database.Database;
import com.nohowdezign.butler.eventsystem.DispatchEvent;

import java.time.ZonedDateTime;
import java.util.TimerTask;

/**
 * @author Noah Howard
 */
public class AlarmTask extends TimerTask {
    private int id;
    private ZonedDateTime alarmTime;
    private String alarmType;
    private Database database = new Database();

    public AlarmTask(int id, ZonedDateTime alarmTime, String alarmType) {
        this.id = id;
        this.alarmTime = alarmTime;
        this.alarmType = alarmType;
    }

    @Override
    public void run() {
        System.out.println("Raising alarm");
        DispatchEvent.raiseEvent(new AlarmEvent(alarmTime));
        if(alarmType.equals("once")) {
            database.executeQuery("UPDATE alarms SET enabled = 0 WHERE id = " + this.id);
        } else if(alarmType.equals("weekly")) {
            // Add one week to alarm and put it back in the database
            ZonedDateTime newTime = alarmTime.plusDays(7);
            database.executeQuery(String.format("UPDATE alarms SET time = '%s' WHERE id = %s", newTime.toString(), id));
        } else if(alarmType.equals("daily")) {
            // Add one day to alarm and put it back in the database
            ZonedDateTime newTime = alarmTime.plusDays(1);
            database.executeQuery(String.format("UPDATE alarms SET time = '%s' WHERE id = %s", newTime.toString(), id));
        }
    }

}
