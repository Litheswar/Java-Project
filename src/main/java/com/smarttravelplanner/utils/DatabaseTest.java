package com.smarttravelplanner.utils;

import com.smarttravelplanner.db.DBConnection;
import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseTest {
    public static void main(String[] args) {
        System.out.println("Testing database connection...");
        try {
            Connection conn = DBConnection.getConnection();
            System.out.println("Connected successfully to: " + conn.getMetaData().getURL());
            System.out.println("Database product name: " + conn.getMetaData().getDatabaseProductName());
            System.out.println("Database product version: " + conn.getMetaData().getDatabaseProductVersion());
            conn.close();
        } catch (SQLException e) {
            System.err.println("Failed to connect to database: " + e.getMessage());
            e.printStackTrace();
        }
    }
}