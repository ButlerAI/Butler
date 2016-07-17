package com.nohowdezign.butler.calendar.reminders;

import com.nohowdezign.butler.database.Database;

/**
 * @author Noah Howard
 * Initializes the database and serves as an intermediary for making database calls
 * for the reminder module
 */
public class ReminderStorage {
    private Database database;

    public void init() {
        database = new Database();
        database.executeQuery("CREATE TABLE IF NOT EXISTS" +
                " reminders (id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " name VARCHAR NOT NULL, reminder VARCHAR NOT NULL," +
                " time VARCHAR);");
    }

    /**
     * Creates a reminder in the database
     * @param name is the name of the user who wants the reminder
     * @param reminder is the string version of what they are being reminded to do
     */
    public void addReminder(String name, String reminder) {
        database.executeQuery(String.format("INSERT INTO reminders (name, reminder)" +
                " VALUES ('%s', '%s');", name, reminder));
    }

    /**
     * Creates a reminder in the database
     * @param name is the name of the user who wants the reminder
     * @param reminder is the string version of what they are being reminded to do
     * @param time is the time that the reminder should be sent to the user
     */
    public void addReminder(String name, String reminder, String time) {
        database.executeQuery(String.format("INSERT INTO reminders (name, reminder, time)" +
                " VALUES ('%s', '%s', '%s');", name, reminder, time));
    }

}
