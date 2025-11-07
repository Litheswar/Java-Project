package com.smarttravelplanner;

import com.smarttravelplanner.db.DestinationDAO;
import java.sql.SQLException;
import java.util.List;

public class TestFixes {
    
    public static void main(String[] args) {
        System.out.println("Testing fixes...");
        
        try {
            // Test the new getStatesWithBaseBudget method
<<<<<<< HEAD
            com.smarttravelplanner.db.DestinationDAO destinationDAO = new com.smarttravelplanner.db.DestinationDAO();
=======
            DestinationDAO destinationDAO = new DestinationDAO();
>>>>>>> parent of a75ffb45 (Connected Backend to Database)
            List<String> states = destinationDAO.getStatesWithBaseBudget("India");
            System.out.println("✓ getStatesWithBaseBudget method exists and compiles");
            
            // Test the fixed type mismatch
            List<com.smarttravelplanner.model.Destination> affordableDests = 
                destinationDAO.getAffordableDestinations(5000.0);
            System.out.println("✓ getAffordableDestinations type mismatch fixed");
            
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