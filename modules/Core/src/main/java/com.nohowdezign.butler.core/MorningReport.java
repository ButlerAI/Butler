package com.nohowdezign.butler.core;

import com.nohowdezign.butler.calendar.events.AlarmEvent;
import com.nohowdezign.butler.database.UserProfile;
import com.nohowdezign.butler.eventsystem.annotations.ReceiveEvent;
import com.nohowdezign.butler.utils.Constants;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * @author Noah Howard
 */
public class MorningReport {
    private UserProfile profile = new UserProfile();

    @ReceiveEvent
    public void onAlarm(AlarmEvent event) {
        String timeZone = profile.getAttributeFromProfile("timezone", "name", UserProfile.DEFAULT_USER);
        LocalDateTime localDateTime = LocalDateTime.now();
        ZonedDateTime noon = LocalDateTime.parse("12:00pm").atZone(ZoneId.of(timeZone));
        if(event.getAlarmTime().isBefore(noon)) {
            Constants.DEFAULT_RESPONDER.respondWithMessage("The time is currently "
                    + localDateTime.getHour() + ":" + localDateTime.getMinute());
        }
    }

}
