package com.nohowdezign.butler.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Noah Howard
 */
public class UserProfile {
    private static Logger logger = LoggerFactory.getLogger(UserProfile.class);
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
                while(users.next()) {
                    DEFAULT_USER = users.getString("name");
                    this.users.add(DEFAULT_USER);
                }
            } catch (SQLException e) {
                logger.debug(e.getLocalizedMessage());
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
            logger.debug(e.getLocalizedMessage());
        }

        return toReturn;
    }

    /**
     * Adds a generic attribute to the profile, formatted as a string
     * @param attribute the name of the column to add
     */
    public void addAttributeToProfile(String attribute) {
        if(!db.doesColumnExist("users", attribute)) {
            db.executeQuery(String.format("ALTER TABLE users ADD COLUMN %s VARCHAR;", attribute));
        }
    }

    /**
     * Get a dynamic attribute from the profile
     * @param columnName is the name of the column to get the attribute from
     * @param attributeType is the type of attribute, such as 'type' = 'value'
     * @param attributeValue the value of the attribute to get
     */
    public String getAttributeFromProfile(String columnName, String attributeType, String attributeValue) {
        String toReturn = "";
        ResultSet set = db.executeQuery(String.format("SELECT * FROM users where %s = '%s'",
                columnName, attributeValue));
        try {
            toReturn = set.getString(columnName);
        } catch (SQLException e) {
            logger.debug(e.getLocalizedMessage());
        }
        return toReturn;
    }

    public String setAttributeOnProfile(String columnName, String oldAttributeValue, String newAttributeValue) {
        String toReturn = "";
        ResultSet set = db.executeQuery(String.format("UPDATE users SET %s = '%s' WHERE %s = '%s'",
                columnName, newAttributeValue, columnName, oldAttributeValue));
        try {
            toReturn = set.getString(columnName);
        } catch (SQLException e) {
            logger.debug(e.getLocalizedMessage());
        }
        return toReturn;
    }

}
