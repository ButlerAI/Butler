package com.nohowdezign.butler.calendar.alarm;

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
import java.time.format.DateTimeParseException;
import java.util.*;

/**
 * @author Noah Howard
 */
public class Alarm {
    private List<String> alarms = new ArrayList<>();
    private Database database = new Database();
    private Chronos chronos = new Chronos();

    private static final Map<String, Integer> DIGITS = new HashMap<String, Integer>();
    private static final Map<String, Integer> specialDigits = new HashMap<String, Integer>();

    static {
        DIGITS.put("oh", 0);
        DIGITS.put("zero", 0);
        DIGITS.put("one", 1);
        DIGITS.put("two", 2);
        DIGITS.put("three", 3);
        DIGITS.put("four", 4);
        DIGITS.put("five", 5);
        DIGITS.put("six", 6);
        DIGITS.put("seven", 7);
        DIGITS.put("eight", 8);
        DIGITS.put("nine", 9);
        DIGITS.put("ten", 10);
        DIGITS.put("eleven", 11);
        DIGITS.put("twelve", 12);
        DIGITS.put("o'clock", 00);
        specialDigits.put("twenty", 20);
        specialDigits.put("thirty", 30);
        specialDigits.put("forty", 40);
        specialDigits.put("fifty", 50);
    }

    private static String parseNumber(String[] tokens) {
        StringBuilder sb = new StringBuilder();

        for (int i = 1; i < tokens.length; ++i) {
            if(tokens[i].equals("colon")) {
                sb.append(":");
            } else if(DIGITS.get(tokens[i]) != null || specialDigits.get(tokens[i]) != null) {
                if(specialDigits.get(tokens[i]) != null && DIGITS.get(tokens[i + 1]) != null) {
                    // Divide value by 10 to remove the zero, since there is a number following the special digit (i.e.
                    // thirty eight)
                    sb.append(specialDigits.get(tokens[i]) / 10);
                } else if(specialDigits.get(tokens[i]) != null) {
                    sb.append(specialDigits.get(tokens[i]));
                } else {
                    sb.append(DIGITS.get(tokens[i]));
                }
            } else {
                sb.append(tokens[i] + " ");
            }
        }

        System.out.println(sb.toString());
        return sb.toString();
    }

    @Initialize
    public void init() {
        database.executeQuery("CREATE TABLE IF NOT EXISTS" +
                " alarms (id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " name VARCHAR NOT NULL, time VARCHAR, type VARCHAR, enabled INTEGER);");

        if(database.doesColumnExist("alarms", "time")) {
            ResultSet set = database.executeQuery("SELECT * FROM alarms;");
            try {
                while(set.next()) {
                    if(set.getInt("enabled") == 1) {
                        chronos.createAlarm(set.getInt("id"), set.getString("time"), set.getString("type"));
                        alarms.add(set.getString("time"));
                    }
                }
            } catch(SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Intent(keyword = "alarm")
    public void setAlarm(AbstractIntent intent, Responder responder) {
        String alarmType = "once";
        LocalDateTime ldt = LocalDateTime.now();
        String time;
        time = parseTimeFromSentence(parseNumber(intent.getOriginalSentence().split(" ")),
                ldt.getYear() + "-" + ldt.getMonth().getValue() + "-" + ldt.getDayOfMonth());

        // Attempt to load in the time the user wakes up
        if(time == null) {
            UserProfile profile = new UserProfile();
            if(database.doesColumnExist("users", "wakeTime")) {
                time = profile.getAttributeFromProfile("wakeTime", "name", UserProfile.DEFAULT_USER);
            }
        }

        if(intent.getOptionalArguments().get("SET") != null) {
            // It's a weekly alarm
            if(intent.getOptionalArguments().get("SET").equals("weekly")) {
                alarmType = "weekly";
            } else if(intent.getOptionalArguments().get("SET").contains("day")) {
                alarmType = "daily";
            }
        }

        try {
            if (time == null) {
                // Time is STILL null... we gotta stop this train before it crashes
                responder.respondWithMessage("I couldn't hear you say a time, I'm sorry.");
            } else if (chronos.hasTimerAlreadyPassed(chronos.getDateTimeFromString(time))) {
                responder.respondWithMessage("The time you are trying to create an alarm for has already passed.");
            } else {
                database.executeQuery(String.format("INSERT INTO alarms (name, time, type, enabled) VALUES ('%s', '%s', '%s', 1)",
                        UserProfile.DEFAULT_USER, time, alarmType));
                try {
                    ResultSet set = database.executeQuery(String.format("SELECT * FROM alarms WHERE time = '%s' AND type = '%s';",
                            time, alarmType));
                    chronos.createAlarm(set.getInt("id"), time, alarmType);
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                responder.respondWithMessage("I have created the alarm for you.");
            }
        } catch(DateTimeParseException e) {
            responder.respondWithMessage("I was unable to create the alarm");
        }
    }

    private String parseTimeFromSentence(String sentence, String date) {
        String toReturn = "";
        Properties props = new Properties();
        AnnotationPipeline pipeline = new AnnotationPipeline();
        pipeline.addAnnotator(new TokenizerAnnotator(false));
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
