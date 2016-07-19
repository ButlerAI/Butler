package com.nohowdezign.butler.calendar;

import com.nohowdezign.butler.database.Database;
import com.nohowdezign.butler.database.UserProfile;
import com.nohowdezign.butler.intent.AbstractIntent;
import com.nohowdezign.butler.intent.annotations.Intent;
import com.nohowdezign.butler.modules.annotations.Initialize;
import com.nohowdezign.butler.responder.Responder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Noah Howard
 */
public class Alarm {
    private List<String> alarms = new ArrayList<>();
    private Database database = new Database();

    @Initialize
    public void init() {
        database.executeQuery("CREATE TABLE IF NOT EXISTS" +
                " alarms (id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " name VARCHAR NOT NULL, time VARCHAR);");

        if(database.doesColumnExist("alarms", "time")) {
            ResultSet set = database.executeQuery("SELECT * FROM alarms;");
            try {
                while (set.next()) {
                    alarms.add(set.getString("time"));
                }
            } catch(SQLException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Initialized Alarm");
    }

    @Intent(keyword = "alarm")
    public void setAlarm(AbstractIntent intent, Responder responder) {
        String time = intent.getOptionalArguments().get("DATE");

        // Attempt to load in the time the user wakes up
        if(time == null) {
            UserProfile profile = new UserProfile();
            if(database.doesColumnExist("users", "wakeTime")) {
                time = profile.getAttributeFromProfile("wakeTime", "name", UserProfile.DEFAULT_USER);
            }
        }

        database.executeQuery(String.format("INSERT INTO alarms (name, time) VALUES ('%s', '%s')",
                UserProfile.DEFAULT_USER, time));

        responder.respondWithMessage("I have set an alarm for " + time);
    }

}
