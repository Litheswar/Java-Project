package com.smarttravelplanner.db;

import java.sql.*;

/**
 * Test class for DatabaseInitializer.
 */
public class DatabaseInitializerTest {
    
    public static void main(String[] args) {
        System.out.println("Testing DatabaseInitializer...");
        
        try {
            // Initialize the database
            DatabaseInitializer.initializeDatabase();
            
            // Verify the database was initialized correctly
            verifyDatabaseInitialization();
            
            System.out.println("DatabaseInitializer test completed successfully!");
        } catch (Exception e) {
            System.err.println("Error during DatabaseInitializer test: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Verifies that the database was initialized correctly.
     */
    private static void verifyDatabaseInitialization() throws SQLException {
        try (Connection connection = DBConnection.createConnection()) {
            // Check that tables exist
            checkTableExists(connection, "users");
            checkTableExists(connection, "countries");
            checkTableExists(connection, "states");
            checkTableExists(connection, "destinations");
            checkTableExists(connection, "trips");
            checkTableExists(connection, "trip_history");
            checkTableExists(connection, "expense_breakdown");
            
            // Check that sample data was inserted
            checkSampleDataInserted(connection);
            
            System.out.println("Database verification completed successfully!");
        }
    }
    
    /**
     * Checks if a table exists in the database.
     */
    private static void checkTableExists(Connection connection, String tableName) throws SQLException {
        String query = "SELECT COUNT(*) FROM information_schema.tables WHERE table_name = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, tableName);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    System.out.println("✓ Table '" + tableName + "' exists");
                } else {
                    System.out.println("✗ Table '" + tableName + "' does not exist");
                }
            }
        }
    }
    
    /**
     * Checks that sample data was inserted.
     */
    private static void checkSampleDataInserted(Connection connection) throws SQLException {
        // Check countries
        checkRowCount(connection, "countries", 8, "countries");
        
        // Check states
        checkRowCount(connection, "states", 64, "states");
        
        // Check destinations
        checkRowCount(connection, "destinations", 192, "destinations");
        
        // Check users
        checkRowCount(connection, "users", 1, "users");
        
        // Check trips
        checkRowCount(connection, "trips", 1, "trips");
    }
    
    /**
     * Checks the row count of a table.
     */
    private static void checkRowCount(Connection connection, String tableName, int expectedCount, String displayName) throws SQLException {
        String query = "SELECT COUNT(*) FROM " + tableName;
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                int count = rs.getInt(1);
                if (count >= expectedCount) {
                    System.out.println("✓ " + displayName + " table has at least " + expectedCount + " rows (" + count + " found)");
                } else {
                    System.out.println("✗ " + displayName + " table has only " + count + " rows, expected at least " + expectedCount);
                }
            }
        }
    }
}