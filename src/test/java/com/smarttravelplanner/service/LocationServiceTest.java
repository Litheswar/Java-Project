package com.smarttravelplanner.service;

import com.smarttravelplanner.model.Destination;
import java.util.List;

public class LocationServiceTest {
    
    public static void main(String[] args) {
        System.out.println("Running LocationService tests...");
        
        try {
            testLocationServiceCreation();
            System.out.println("✓ testLocationServiceCreation passed");
        } catch (Exception e) {
            System.err.println("✗ testLocationServiceCreation failed: " + e.getMessage());
            e.printStackTrace();
        }
        
        try {
            testGetAllDestinations();
            System.out.println("✓ testGetAllDestinations passed");
        } catch (Exception e) {
            System.err.println("✗ testGetAllDestinations failed: " + e.getMessage());
            e.printStackTrace();
        }
        
        try {
            testGetDestinationById();
            System.out.println("✓ testGetDestinationById passed");
        } catch (Exception e) {
            System.err.println("✗ testGetDestinationById failed: " + e.getMessage());
            e.printStackTrace();
        }
        
        try {
            testGetAllCountries();
            System.out.println("✓ testGetAllCountries passed");
        } catch (Exception e) {
            System.err.println("✗ testGetAllCountries failed: " + e.getMessage());
            e.printStackTrace();
        }
        
        try {
            testGetDestinationsByCountry();
            System.out.println("✓ testGetDestinationsByCountry passed");
        } catch (Exception e) {
            System.err.println("✗ testGetDestinationsByCountry failed: " + e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println("All LocationService tests completed.");
    }
    
    public static void testLocationServiceCreation() {
        System.out.println("Testing LocationService creation...");
        LocationService locationService = new LocationService();
        
        assertNotNull(locationService, "LocationService should not be null");
        assertNotNull(locationService.getAllDestinations(), "Destinations list should not be null");
        System.out.println("LocationService creation test passed.");
    }
    
    public static void testGetAllDestinations() {
        System.out.println("Testing getAllDestinations method...");
        LocationService locationService = new LocationService();
        List<Destination> destinations = locationService.getAllDestinations();
        
        // Should have at least the sample destinations
        assertTrue(destinations.size() >= 3, "Should have at least 3 destinations");
        System.out.println("getAllDestinations test passed.");
    }
    
    public static void testGetDestinationById() {
        System.out.println("Testing getDestinationById method...");
        LocationService locationService = new LocationService();
        
        // Test getting an existing destination
        Destination destination = locationService.getDestinationById(1);
        assertNotNull(destination, "Destination should not be null");
        assertEqual(1, destination.getId(), "ID should be 1");
        assertEqual("France", destination.getCountry(), "Country should be France");
        
        // Test getting a non-existing destination
        Destination nonExisting = locationService.getDestinationById(999);
        assertNull(nonExisting, "Non-existing destination should be null");
        System.out.println("getDestinationById test passed.");
    }
    
    public static void testGetAllCountries() {
        System.out.println("Testing getAllCountries method...");
        LocationService locationService = new LocationService();
        List<String> countries = locationService.getAllCountries();
        
        assertNotNull(countries, "Countries list should not be null");
        assertFalse(countries.isEmpty(), "Countries list should not be empty");
        assertTrue(countries.contains("France"), "Should contain France");
        assertTrue(countries.contains("Japan"), "Should contain Japan");
        assertTrue(countries.contains("India"), "Should contain India");
        System.out.println("getAllCountries test passed.");
    }
    
    public static void testGetDestinationsByCountry() {
        System.out.println("Testing getDestinationsByCountry method...");
        LocationService locationService = new LocationService();
        List<Destination> franceDestinations = locationService.getDestinationsByCountry("France");
        
        assertNotNull(franceDestinations, "France destinations list should not be null");
        assertFalse(franceDestinations.isEmpty(), "France destinations list should not be empty");
        assertEqual("France", franceDestinations.get(0).getCountry(), "Country should be France");
        System.out.println("getDestinationsByCountry test passed.");
    }
    
    // Custom assertion methods to replace JUnit assertions
    private static void assertEqual(Object expected, Object actual, String message) {
        if (expected == null && actual == null) return;
        if (expected != null && expected.equals(actual)) return;
        throw new AssertionError(message + ". Expected: " + expected + ", but was: " + actual);
    }
    
    private static void assertEqual(int expected, int actual, String message) {
        if (expected == actual) return;
        throw new AssertionError(message + ". Expected: " + expected + ", but was: " + actual);
    }
    
    private static void assertNotNull(Object actual, String message) {
        if (actual != null) return;
        throw new AssertionError(message + ". Expected not null, but was null");
    }
    
    private static void assertNull(Object actual, String message) {
        if (actual == null) return;
        throw new AssertionError(message + ". Expected null, but was: " + actual);
    }
    
    private static void assertTrue(boolean condition, String message) {
        if (condition) return;
        throw new AssertionError(message + ". Expected true, but was false");
    }
    
    private static void assertFalse(boolean condition, String message) {
        if (!condition) return;
        throw new AssertionError(message + ". Expected false, but was true");
    }
}