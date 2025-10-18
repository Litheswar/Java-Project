package com.smarttravelplanner.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class DBConnection {
    
    private final DataSource dataSource;
    
    @Autowired
    public DBConnection(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    /**
     * Gets a connection to the PostgreSQL database
     * @return Connection object
     * @throws SQLException if connection fails
     */
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
    
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