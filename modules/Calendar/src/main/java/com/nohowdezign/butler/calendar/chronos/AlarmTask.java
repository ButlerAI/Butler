package com.nohowdezign.butler.calendar.chronos;

import com.nohowdezign.butler.calendar.events.AlarmEvent;
import com.nohowdezign.butler.eventsystem.DispatchEvent;

import java.time.ZonedDateTime;
import java.util.TimerTask;

/**
 * @author Noah Howard
 */
public class AlarmTask extends TimerTask {
    private ZonedDateTime alarmTime;

    public AlarmTask(ZonedDateTime alarmTime) {
        this.alarmTime = alarmTime;
    }

    @Override
    public void run() {
        System.out.println("Raising alarm");
        DispatchEvent.raiseEvent(new AlarmEvent(alarmTime));
    }

}
