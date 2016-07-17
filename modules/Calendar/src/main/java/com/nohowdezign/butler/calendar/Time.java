package com.nohowdezign.butler.calendar;

import com.nohowdezign.butler.modules.annotations.Execute;
import com.nohowdezign.butler.modules.annotations.ModuleLogic;
import com.nohowdezign.butler.responder.Responder;

import java.time.LocalDateTime;

/**
 * @author Noah Howard
 * A simple response to what time it is
 */
@ModuleLogic(subjectWord = "time")
public class Time {

    @Execute
    public void getTheTime(Responder responder) {
        LocalDateTime ldt = LocalDateTime.now();
        responder.respondWithMessage("It is currently " + ldt.getHour() + ":" + ldt.getMinute() +
                " on " + ldt.getMonth() + " " + ldt.getDayOfMonth() + ", " + ldt.getYear());
    }

}
