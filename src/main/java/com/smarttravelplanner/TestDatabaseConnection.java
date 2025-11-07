package com.smarttravelplanner;

import com.smarttravelplanner.db.*;
import com.smarttravelplanner.model.*;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class TestDatabaseConnection {
    
    public static void main(String[] args) {
        System.out.println("Testing database connectivity and DAOs...");
        
        try {
            // Test CountryDAO
            testCountryDAO();
            
            // Test UserDAO
            testUserDAO();
            
            System.out.println("All tests completed successfully!");
        } catch (Exception e) {
            System.err.println("Error during testing: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static void testCountryDAO() throws SQLException {
        System.out.println("\n--- Testing CountryDAO ---");
        CountryDAO countryDAO = new CountryDAO();
        
        // Create a country
        Country country = new Country("Test Country");
        int countryId = countryDAO.createCountry(country);
        System.out.println("Created country with ID: " + countryId);
        
        // Read the country
        Country retrievedCountry = countryDAO.getCountryById(countryId);
        System.out.println("Retrieved country: " + retrievedCountry);
        
        // Update the country
        retrievedCountry.setName("Updated Test Country");
        boolean updated = countryDAO.updateCountry(retrievedCountry);
        System.out.println("Country updated: " + updated);
        
        // Verify update
        Country updatedCountry = countryDAO.getCountryById(countryId);
        System.out.println("Updated country: " + updatedCountry);
        
        // Get all countries
        List<Country> allCountries = countryDAO.getAllCountries();
        System.out.println("Total countries in database: " + allCountries.size());
        
        // Delete the country
        boolean deleted = countryDAO.deleteCountry(countryId);
        System.out.println("Country deleted: " + deleted);
    }
    
    private static void testUserDAO() throws SQLException {
        System.out.println("\n--- Testing UserDAO ---");
        UserDAO userDAO = new UserDAO();
        
        // Create a user
        User user = new User("Test User", 30, 4, 5000.0, "test@example.com");
        UUID userId = userDAO.createUser(user);
        System.out.println("Created user with ID: " + userId);
        
        // Read the user
        User retrievedUser = userDAO.getUserById(userId);
        System.out.println("Retrieved user: " + retrievedUser);
        
        // Update the user
        retrievedUser.setName("Updated Test User");
        retrievedUser.setBudget(6000.0);
        boolean updated = userDAO.updateUser(retrievedUser);
        System.out.println("User updated: " + updated);
        
        // Verify update
        User updatedUser = userDAO.getUserById(userId);
        System.out.println("Updated user: " + updatedUser);
        
        // Get all users
        List<User> allUsers = userDAO.getAllUsers();
        System.out.println("Total users in database: " + allUsers.size());
        
        // Delete the user
        boolean deleted = userDAO.deleteUser(userId);
        System.out.println("User deleted: " + deleted);
    }
}