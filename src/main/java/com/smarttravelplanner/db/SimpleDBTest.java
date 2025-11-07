package com.smarttravelplanner.db;

import java.sql.Connection;
import java.sql.SQLException;

public class SimpleDBTest {
    
    public static void main(String[] args) {
        System.out.println("Testing database connection...");
        
        try {
            Connection conn = DBConnection.getConnection();
            if (conn != null && !conn.isClosed()) {
                System.out.println("Database connection successful!");
                conn.close();
            } else {
                System.out.println("Failed to establish database connection.");
            }
        } catch (SQLException e) {
            System.err.println("Database connection error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}