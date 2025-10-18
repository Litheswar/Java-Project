package com.smarttravelplanner;

import java.sql.SQLException;

public class DAOTest {
    
    public static void main(String[] args) {
        System.out.println("Testing new DAOs...");
        
        try {
            // Test CountryDAO
            testCountryDAO();
            
            // Test StateDAO
            testStateDAO();
            
            // Test DestinationDAO
            testDestinationDAO();
            
            System.out.println("All DAO tests completed successfully!");
        } catch (Exception e) {
            System.err.println("Error during testing: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static void testCountryDAO() throws SQLException {
        System.out.println("\n--- Testing CountryDAO ---");
        // CountryDAO countryDAO = new CountryDAO(); // This needs to be updated to use Spring
        
        // Create a country
        // Country country = new Country("France");
        // int countryId = countryDAO.createCountry(country);
        // System.out.println("Created country with ID: " + countryId);
        // 
        // // Read the country
        // Country retrievedCountry = countryDAO.getCountryById(countryId);
        // System.out.println("Retrieved country: " + retrievedCountry);
        // 
        // // Update the country
        // retrievedCountry.setName("Italy");
        // boolean updated = countryDAO.updateCountry(retrievedCountry);
        // System.out.println("Country updated: " + updated);
        // 
        // // Verify update
        // Country updatedCountry = countryDAO.getCountryById(countryId);
        // System.out.println("Updated country: " + updatedCountry);
        // 
        // // Get all countries
        // List<Country> allCountries = countryDAO.getAllCountries();
        // System.out.println("Total countries in database: " + allCountries.size());
        // 
        // // Delete the country
        // boolean deleted = countryDAO.deleteCountry(countryId);
        // System.out.println("Country deleted: " + deleted);
    }
    
    private static void testStateDAO() throws SQLException {
        System.out.println("\n--- Testing StateDAO ---");
        // StateDAO stateDAO = new StateDAO(); // This needs to be updated to use Spring
        // CountryDAO countryDAO = new CountryDAO(); // This needs to be updated to use Spring
        
        // First create a country to reference
        // Country country = new Country("Test Country");
        // int countryId = countryDAO.createCountry(country);
        // 
        // // Create a state
        // State state = new State(countryId, "Test State", 1000.0);
        // int stateId = stateDAO.createState(state);
        // System.out.println("Created state with ID: " + stateId);
        // 
        // // Read the state
        // State retrievedState = stateDAO.getStateById(stateId);
        // System.out.println("Retrieved state: " + retrievedState);
        // 
        // // Update the state
        // retrievedState.setName("Updated Test State");
        // retrievedState.setBaseBudget(1500.0);
        // boolean updated = stateDAO.updateState(retrievedState);
        // System.out.println("State updated: " + updated);
        // 
        // // Verify update
        // State updatedState = stateDAO.getStateById(stateId);
        // System.out.println("Updated state: " + updatedState);
        // 
        // // Get all states
        // List<State> allStates = stateDAO.getAllStates();
        // System.out.println("Total states in database: " + allStates.size());
        // 
        // // Delete the state
        // boolean deleted = stateDAO.deleteState(stateId);
        // System.out.println("State deleted: " + deleted);
        // 
        // // Delete the country
        // countryDAO.deleteCountry(countryId);
    }
    
    private static void testDestinationDAO() throws SQLException {
        System.out.println("\n--- Testing DestinationDAO ---");
        // DestinationDAO destinationDAO = new DestinationDAO(); // This needs to be updated to use Spring
        // StateDAO stateDAO = new StateDAO(); // This needs to be updated to use Spring
        // CountryDAO countryDAO = new CountryDAO(); // This needs to be updated to use Spring
        
        // First create a country and state to reference
        // Country country = new Country("Test Country");
        // int countryId = countryDAO.createCountry(country);
        // 
        // State state = new State(countryId, "Test State", 1000.0);
        // int stateId = stateDAO.createState(state);
        // 
        // // Create a destination
        // Destination destination = new Destination(stateId, "Test Destination", 500.0, 8, 10.5);
        // int destinationId = destinationDAO.createDestination(destination);
        // System.out.println("Created destination with ID: " + destinationId);
        // 
        // // Read the destination
        // Destination retrievedDestination = destinationDAO.getDestinationById(destinationId);
        // System.out.println("Retrieved destination: " + retrievedDestination);
        // 
        // // Update the destination
        // retrievedDestination.setName("Updated Test Destination");
        // retrievedDestination.setBaseCost(600.0);
        // retrievedDestination.setSustainabilityScore(9);
        // boolean updated = destinationDAO.updateDestination(retrievedDestination);
        // System.out.println("Destination updated: " + updated);
        // 
        // // Verify update
        // Destination updatedDestination = destinationDAO.getDestinationById(destinationId);
        // System.out.println("Updated destination: " + updatedDestination);
        // 
        // // Get all destinations
        // List<Destination> allDestinations = destinationDAO.getAllDestinations();
        // System.out.println("Total destinations in database: " + allDestinations.size());
        // 
        // // Delete the destination
        // boolean deleted = destinationDAO.deleteDestination(destinationId);
        // System.out.println("Destination deleted: " + deleted);
        // 
        // // Delete the state and country
        // stateDAO.deleteState(stateId);
        // countryDAO.deleteCountry(countryId);
    }
}