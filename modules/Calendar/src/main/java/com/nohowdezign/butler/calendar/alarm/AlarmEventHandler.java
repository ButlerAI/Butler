package com.nohowdezign.butler.calendar.alarm;

import com.nohowdezign.butler.calendar.events.AlarmEvent;
import com.nohowdezign.butler.eventsystem.annotations.ReceiveEvent;
import com.nohowdezign.butler.utils.Constants;

/**
 * @author Noah Howard
 */
public class AlarmEventHandler {

    @ReceiveEvent
    public void onAlarm(AlarmEvent event) {
        Constants.DEFAULT_RESPONDER.respondWithMessage("Your alarm is going off.");
    }

}
