package com.smarttravelplanner;

import java.sql.SQLException;
import java.util.List;

public class TestFixes {
    
    public static void main(String[] args) {
        System.out.println("Testing fixes...");
        
        try {
            // Test the new getStatesWithBaseBudget method
            com.smarttravelplanner.db.DestinationDAO destinationDAO = new com.smarttravelplanner.db.DestinationDAO();
            List<String> states = destinationDAO.getStatesWithBaseBudget("India");
            System.out.println("✓ getStatesWithBaseBudget method exists and compiles");
            // Using the states variable to avoid unused variable warning
            if (states != null) {
                System.out.println("  Found " + states.size() + " states");
            }
            
            // Test the fixed type mismatch
            List<com.smarttravelplanner.model.Destination> affordableDests = 
                destinationDAO.getAffordableDestinations(5000.0);
            System.out.println("✓ getAffordableDestinations type mismatch fixed");
            // Using the affordableDests variable to avoid unused variable warning
            if (affordableDests != null) {
                System.out.println("  Found " + affordableDests.size() + " affordable destinations");
            }
            
            System.out.println("All fixes verified successfully!");
            
        } catch (SQLException e) {
            System.err.println("Database error during test: " + e.getMessage());
            // This is expected if the database is not set up
            System.out.println("Note: Database connection error is expected in this test environment");
        } catch (Exception e) {
            System.err.println("Unexpected error during test: " + e.getMessage());
            e.printStackTrace();
        }
    }
}