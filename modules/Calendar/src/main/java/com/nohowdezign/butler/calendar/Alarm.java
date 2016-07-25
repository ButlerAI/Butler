package com.nohowdezign.butler.calendar;

import com.nohowdezign.butler.calendar.chronos.Chronos;
import com.nohowdezign.butler.database.Database;
import com.nohowdezign.butler.database.UserProfile;
import com.nohowdezign.butler.intent.AbstractIntent;
import com.nohowdezign.butler.intent.annotations.Intent;
import com.nohowdezign.butler.modules.annotations.Initialize;
import com.nohowdezign.butler.responder.Responder;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.time.TimeAnnotations;
import edu.stanford.nlp.time.TimeAnnotator;
import edu.stanford.nlp.time.TimeExpression;
import edu.stanford.nlp.util.CoreMap;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author Noah Howard
 */
public class Alarm {
    private List<String> alarms = new ArrayList<>();
    private Database database = new Database();
    private Chronos chronos = new Chronos();

    @Initialize
    public void init() {
        database.executeQuery("CREATE TABLE IF NOT EXISTS" +
                " alarms (id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " name VARCHAR NOT NULL, time VARCHAR, enabled INTEGER);");

        if(database.doesColumnExist("alarms", "time")) {
            ResultSet set = database.executeQuery("SELECT * FROM alarms;");
            try {
                while(set.next()) {
                    if(set.getInt("enabled") == 1) {
                        chronos.createAlarm(set.getString("time"));
                        alarms.add(set.getString("time"));
                        System.out.println(alarms);
                    }
                }
            } catch(SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Intent(keyword = "alarm")
    public void setAlarm(AbstractIntent intent, Responder responder) {
        LocalDateTime ldt = LocalDateTime.now();
        String time = parseTimeFromSentence(intent.getOptionalArguments().get("TIME"),
                ldt.getYear() + "-" + ldt.getMonth().getValue() + "-" + ldt.getDayOfMonth());

        // Attempt to load in the time the user wakes up
        if(time == null) {
            UserProfile profile = new UserProfile();
            if(database.doesColumnExist("users", "wakeTime")) {
                time = profile.getAttributeFromProfile("wakeTime", "name", UserProfile.DEFAULT_USER);
            }
        }

        if(time == null) {
            // Time is STILL null... we gotta stop this train before it crashes
            responder.respondWithMessage("I couldn't hear you say a time, I'm sorry.");
        } else if(chronos.hasTimerAlreadyPassed(chronos.getDateTimeFromString(time))) {
            responder.respondWithMessage("The time you are trying to create an alarm for has already passed.");
        } else {
            database.executeQuery(String.format("INSERT INTO alarms (name, time) VALUES ('%s', '%s')",
                    UserProfile.DEFAULT_USER, time));

            chronos.createAlarm(time);
            responder.respondWithMessage("I have set an alarm for " + intent.getOptionalArguments().get("TIME"));
        }
    }

    private String parseTimeFromSentence(String sentence, String date) {
        String toReturn = "";
        Properties props = new Properties();
        AnnotationPipeline pipeline = new AnnotationPipeline();
        pipeline.addAnnotator(new TokenizerAnnotator(false));
        pipeline.addAnnotator(new WordsToSentencesAnnotator(false));
        pipeline.addAnnotator(new POSTaggerAnnotator(false));
        pipeline.addAnnotator(new TimeAnnotator("sutime", props));
        Annotation annotation = new Annotation(sentence);
        // Set the current date so that the document has a frame of reference
        annotation.set(CoreAnnotations.DocDateAnnotation.class, date);
        pipeline.annotate(annotation);
        if(annotation.get(TimeAnnotations.TimexAnnotations.class) != null) {
            List<CoreMap> timexAnnsAll = annotation.get(TimeAnnotations.TimexAnnotations.class);
            for (CoreMap cm : timexAnnsAll) {
                toReturn = cm.get(TimeExpression.Annotation.class).getTemporal().toString();
            }
        }
        return toReturn;
    }

}
