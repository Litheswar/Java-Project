package com.smarttravelplanner.db;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

/**
 * Utility class to verify that all required tables exist in the database.
 */
public class TableVerification {
    
    // List of required tables
    private static final List<String> REQUIRED_TABLES = Arrays.asList(
        "countries", "states", "destinations", "trip_history", 
        "expense_breakdown", "alerts", "routes"
    );
    
    /**
     * Verifies that all required tables exist in the database.
     * @return true if all tables exist, false otherwise
     */
    public static boolean verifyTables() {
        System.out.println("Verifying database tables...");
        
        try (Connection connection = DBConnection.createConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();
            
            boolean allTablesExist = true;
            
            for (String tableName : REQUIRED_TABLES) {
                if (tableExists(metaData, tableName)) {
                    System.out.println("✓ Table '" + tableName + "' exists");
                } else {
                    System.out.println("✗ Table '" + tableName + "' is missing");
                    allTablesExist = false;
                }
            }
            
            if (allTablesExist) {
                System.out.println("All required tables exist in the database.");
            } else {
                System.out.println("Some required tables are missing.");
            }
            
            return allTablesExist;
        } catch (SQLException e) {
            System.err.println("Error verifying tables: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Checks if a specific table exists in the database.
     * @param metaData Database metadata
     * @param tableName Name of the table to check
     * @return true if table exists, false otherwise
     * @throws SQLException if there's an error accessing the database metadata
     */
    private static boolean tableExists(DatabaseMetaData metaData, String tableName) throws SQLException {
        try (ResultSet rs = metaData.getTables(null, null, tableName, new String[]{"TABLE"})) {
            return rs.next();
        }
    }
    
    /**
     * Main method to run the table verification.
     */
    public static void main(String[] args) {
        boolean result = verifyTables();
        if (result) {
            System.out.println("Database verification completed successfully.");
        } else {
            System.out.println("Database verification failed. Please run the database initialization script.");
        }
    }
}