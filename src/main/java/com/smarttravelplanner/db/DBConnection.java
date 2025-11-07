package com.smarttravelplanner.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    
    /**
     * Creates a direct connection to the PostgreSQL database for initialization purposes
     * @return Connection object
     * @throws SQLException if connection fails
     */
    public static Connection createConnection() throws SQLException {
        // Database connection parameters
        String url = "jdbc:postgresql://localhost:5432/smart_travel_db";
        String username = "postgres";
        String password = "Lithu19!"; // Updated to match application.properties
        
        return DriverManager.getConnection(url, username, password);
    }
}