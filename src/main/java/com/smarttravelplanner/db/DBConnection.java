package com.smarttravelplanner.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = System.getenv("DB_URL") != null ? 
        System.getenv("DB_URL") : "jdbc:postgresql://localhost:5432/smart_travel_db";
    private static final String USER = System.getenv("DB_USER") != null ? 
        System.getenv("DB_USER") : "postgres";
    private static final String PASSWORD = System.getenv("DB_PASSWORD") != null ? 
        System.getenv("DB_PASSWORD") : "Lithu19!";
    
<<<<<<< HEAD
=======
    private static Connection connection = null;
    
    // Private constructor to prevent instantiation
    private DBConnection() {}
    
    /**
     * Gets a connection to the PostgreSQL database
     * @return Connection object
     * @throws SQLException if connection fails
     */
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                Class.forName("org.postgresql.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (ClassNotFoundException e) {
                throw new SQLException("PostgreSQL JDBC Driver not found", e);
            }
        }
        return connection;
    }
    
>>>>>>> parent of a75ffb45 (Connected Backend to Database)
    /**
     * Closes the database connection
     */
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}