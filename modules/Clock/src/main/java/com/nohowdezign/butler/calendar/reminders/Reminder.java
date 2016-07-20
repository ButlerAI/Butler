package com.nohowdezign.butler.calendar.reminders;

import com.nohowdezign.butler.intent.AbstractIntent;
import com.nohowdezign.butler.intent.annotations.Intent;
import com.nohowdezign.butler.modules.annotations.Initialize;
import com.nohowdezign.butler.responder.Responder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Noah Howard
 * Create reminders for tasks that you need to do
 */
public class Reminder {
    private static Logger logger = LoggerFactory.getLogger(Reminder.class);

    @Initialize
    public void initReminders() {
    }

    @Intent(keyword = "remind")
    public void addReminder(AbstractIntent intent, Responder responder) {
        // Check for the time a user wants the reminder in the query
        if(intent.getOptionalArguments().get("DATE") != null) {
            logger.debug("The input has a date!");
        }
    }

}
