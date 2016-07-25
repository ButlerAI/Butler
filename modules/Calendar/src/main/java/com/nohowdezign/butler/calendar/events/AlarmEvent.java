package com.nohowdezign.butler.calendar.events;

import com.nohowdezign.butler.eventsystem.Event;

import java.time.ZonedDateTime;

/**
 * @author Noah Howard
 */
public class AlarmEvent extends Event {
    private ZonedDateTime alarmTime;

    public AlarmEvent(ZonedDateTime alarmTime) {
        this.alarmTime = alarmTime;
    }

    public ZonedDateTime getAlarmTime() {
        return alarmTime;
    }
}
