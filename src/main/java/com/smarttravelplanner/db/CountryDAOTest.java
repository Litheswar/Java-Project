package com.smarttravelplanner.db;

import com.smarttravelplanner.model.Country;
import java.sql.SQLException;
import java.util.List;

public class CountryDAOTest {
    
    public static void main(String[] args) {
        System.out.println("Testing CountryDAO...");
        
        try {
            CountryDAO countryDAO = new CountryDAO();
            
            // Test 1: Create a country
            System.out.println("\n1. Testing createCountry...");
            Country country = new Country("Test Country");
            int countryId = countryDAO.createCountry(country);
            if (countryId > 0) {
                System.out.println("✓ Country created successfully with ID: " + countryId);
            } else {
                System.out.println("✗ Failed to create country");
            }
            
            // Test 2: Retrieve the country
            System.out.println("\n2. Testing getCountryById...");
            Country retrievedCountry = countryDAO.getCountryById(countryId);
            if (retrievedCountry != null && "Test Country".equals(retrievedCountry.getName())) {
                System.out.println("✓ Country retrieved successfully: " + retrievedCountry.getName());
            } else {
                System.out.println("✗ Failed to retrieve country");
            }
            
            // Test 3: Update the country
            System.out.println("\n3. Testing updateCountry...");
            retrievedCountry.setName("Updated Test Country");
            boolean updated = countryDAO.updateCountry(retrievedCountry);
            if (updated) {
                System.out.println("✓ Country updated successfully");
            } else {
                System.out.println("✗ Failed to update country");
            }
            
            // Verify update
            Country updatedCountry = countryDAO.getCountryById(countryId);
            if (updatedCountry != null && "Updated Test Country".equals(updatedCountry.getName())) {
                System.out.println("✓ Update verification passed");
            } else {
                System.out.println("✗ Update verification failed");
            }
            
            // Test 4: Get all countries
            System.out.println("\n4. Testing getAllCountries...");
            List<Country> allCountries = countryDAO.getAllCountries();
            if (allCountries != null) {
                System.out.println("✓ Retrieved " + allCountries.size() + " countries");
            } else {
                System.out.println("✗ Failed to retrieve countries");
            }
            
            // Test 5: Delete the country
            System.out.println("\n5. Testing deleteCountry...");
            boolean deleted = countryDAO.deleteCountry(countryId);
            if (deleted) {
                System.out.println("✓ Country deleted successfully");
            } else {
                System.out.println("✗ Failed to delete country");
            }
            
            System.out.println("\n=== CountryDAO test completed ===");
            
        } catch (SQLException e) {
            System.err.println("Database error during test: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Unexpected error during test: " + e.getMessage());
            e.printStackTrace();
        }
    }
}