package com.smarttravelplanner.utils;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnectionTest {
    public static void main(String[] args) {
        System.out.println("Testing database connection directly...");
        try {
            // Test basic connection without using the DBConnection class
            String url = "jdbc:postgresql://localhost:5432/smart_travel_db";
            String user = "postgres";
            String password = "Lithu19!";
            
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected successfully to: " + conn.getMetaData().getURL());
            System.out.println("Database product name: " + conn.getMetaData().getDatabaseProductName());
            System.out.println("Database product version: " + conn.getMetaData().getDatabaseProductVersion());
            conn.close();
            System.out.println("Database connection test completed successfully!");
        } catch (Exception e) {
            System.err.println("Failed to connect to database: " + e.getMessage());
            e.printStackTrace();
        }
    }
}