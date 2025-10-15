package com.smarttravelplanner.db;

import java.sql.SQLException;
import java.util.List;

public class DatabaseTest {
    
    public static void main(String[] args) {
        try {
            // Initialize the database
            DatabaseInitializer.initializeDatabase();
            
            // Test UserDAO
            System.out.println("\n=== Testing UserDAO ===");
            UserDAO userDAO = new UserDAO();
            int userId = userDAO.insertUser("Jane Smith", 28, 2, 15000.00);
            System.out.println("Inserted user with ID: " + userId);
            
            List<String> users = userDAO.getAllUsers();
            System.out.println("All users:");
            for (String user : users) {
                System.out.println("  " + user);
            }
            
            // Test DestinationDAO
            System.out.println("\n=== Testing DestinationDAO ===");
            DestinationDAO destinationDAO = new DestinationDAO();
            
            // Get affordable destinations
            List<String> affordableDestinations = destinationDAO.getAffordableDestinations(5000.00);
            System.out.println("Affordable destinations (Budget <= ₹5000):");
            for (String destination : affordableDestinations) {
                System.out.println("  " + destination);
            }
            
            // Get states with base budget for India
            List<String> stateBudgets = destinationDAO.getStatesWithBaseBudget("India");
            System.out.println("\nState budgets in India:");
            for (String state : stateBudgets) {
                System.out.println("  " + state);
            }
            
            // Get sustainable destinations
            List<String> sustainableDestinations = destinationDAO.getSustainableDestinations(8);
            System.out.println("\nSustainable destinations (Score >= 8):");
            for (String destination : sustainableDestinations) {
                System.out.println("  " + destination);
            }
            
            // Test TripDAO
            System.out.println("\n=== Testing TripDAO ===");
            TripDAO tripDAO = new TripDAO();
            int tripId = tripDAO.insertTrip(userId, 1, 1, 1, 5, 3, "rail", "standard", "veg", 12000.00);
            System.out.println("Inserted trip with ID: " + tripId);
            
            List<String> trips = tripDAO.getAllTrips();
            System.out.println("All trips:");
            for (String trip : trips) {
                System.out.println("  " + trip);
            }
            
            System.out.println("\nDatabase test completed successfully!");
            
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}