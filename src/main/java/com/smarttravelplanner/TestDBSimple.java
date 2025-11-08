package com.smarttravelplanner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestDBSimple {
    public static void main(String[] args) {
        System.out.println("Testing database connection...");
        
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/smart_travel_db", 
                "postgres", 
                "Lithu19!"
            );
            System.out.println("✅ Database connection successful!");
            connection.close();
        } catch (ClassNotFoundException e) {
            System.err.println("❌ PostgreSQL JDBC Driver not found!");
            System.err.println("Please ensure the PostgreSQL JDBC driver is in your classpath.");
        } catch (SQLException e) {
            System.err.println("❌ Database connection failed!");
            System.err.println("Error: " + e.getMessage());
        }
    }
}