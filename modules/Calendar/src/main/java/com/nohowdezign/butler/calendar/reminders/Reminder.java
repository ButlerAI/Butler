package com.nohowdezign.butler.calendar.reminders;

import com.nohowdezign.butler.database.Database;
import com.nohowdezign.butler.modules.annotations.Execute;
import com.nohowdezign.butler.modules.annotations.Initialize;
import com.nohowdezign.butler.modules.annotations.ModuleLogic;
import com.nohowdezign.butler.processing.LanguageProcessor;
import com.nohowdezign.butler.responder.Responder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Noah Howard
 * Create reminders for tasks that you need to do
 */
//@ModuleLogic
public class Reminder {
    private static Logger logger = LoggerFactory.getLogger(Reminder.class);

    @Initialize
    public void initReminders() {
    }

    @Execute
    public void addReminder(String query, Responder responder) {
        LanguageProcessor languageProcessor = new LanguageProcessor();
        // Check for the time a user wants the reminder in the query
        if(languageProcessor.getNamedEntity(query, "DATE") != null) {
            logger.debug("The input has a date!");
        }
    }

}
