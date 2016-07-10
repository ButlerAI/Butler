package com.nohowdezign.butler.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

/**
 * @author Noah Howard
 */
public class Database {
    private static Logger logger = LoggerFactory.getLogger(Database.class);
    private Connection databaseConnection;

    public Database() {
        try {
            Class.forName("org.sqlite.JDBC");
            databaseConnection = DriverManager.getConnection("jdbc:sqlite:Butler.db");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet executeQuery(String sqlQuery) {
        Statement statement;
        try {
            statement = databaseConnection.createStatement();
            statement.execute(sqlQuery);
            return statement.getResultSet();
        } catch(SQLException e) {
            logger.debug(e.getLocalizedMessage());
        }
        return null;
    }

}
