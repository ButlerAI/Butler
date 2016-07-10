package com.nohowdezign.butler.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Noah Howard
 */
public class UserProfile {
    public static String DEFAULT_USER;
    private Database db = new Database();
    private List<String> users = new ArrayList<>();

    public UserProfile() {
        db.executeQuery("CREATE TABLE IF NOT EXISTS" +
                " users (id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " name VARCHAR NOT NULL, location VARCHAR);");
    }

    public void load() {
        ResultSet users = db.executeQuery("SELECT * FROM users");
        if(users != null) {
            try {
                while (users.next()) {
                    DEFAULT_USER = users.getString("name");
                    this.users.add(DEFAULT_USER);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void createProfile(String user, String location) {
        db.executeQuery(String.format("INSERT INTO users (name, location) VALUES ('%s', '%s')", user, location));
    }

    public String getUserLocation(String user) {
        String toReturn = "New York";
        ResultSet set = db.executeQuery(String.format("SELECT * FROM users WHERE name = '%s'", user));
        try {
            toReturn = set.getString("location");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return toReturn;
    }

}
