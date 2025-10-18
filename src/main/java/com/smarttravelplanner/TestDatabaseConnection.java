package com.smarttravelplanner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class TestDatabaseConnection {
    
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/smart_travel_db";
        String username = "postgres";
        String password = "Lithu19!";
        
        try {
            // Load the PostgreSQL JDBC driver
            Class.forName("org.postgresql.Driver");
            
            // Establish connection
            Connection connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connected to the database successfully!");
            
            // Check table structure
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT column_name, data_type FROM information_schema.columns WHERE table_name = 'users' ORDER BY ordinal_position");
            
            System.out.println("Users table columns:");
            while (rs.next()) {
                System.out.println(rs.getString("column_name") + " - " + rs.getString("data_type"));
            }
            
            // Close connections
            rs.close();
            stmt.close();
            connection.close();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}