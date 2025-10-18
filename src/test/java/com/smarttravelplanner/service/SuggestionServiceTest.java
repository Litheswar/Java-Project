package com.smarttravelplanner.service;

import com.smarttravelplanner.model.Destination;
import java.util.List;

public class SuggestionServiceTest {
    
    public static void main(String[] args) {
        System.out.println("Running SuggestionService tests...");
        
        try {
            testSuggestionServiceCreation();
            System.out.println("✓ testSuggestionServiceCreation passed");
        } catch (Exception e) {
            System.err.println("✗ testSuggestionServiceCreation failed: " + e.getMessage());
            e.printStackTrace();
        }
        
        try {
            testSuggestDestinationsByBudget();
            System.out.println("✓ testSuggestDestinationsByBudget passed");
        } catch (Exception e) {
            System.err.println("✗ testSuggestDestinationsByBudget failed: " + e.getMessage());
            e.printStackTrace();
        }
        
        try {
            testSuggestCostAdjustments();
            System.out.println("✓ testSuggestCostAdjustments passed");
        } catch (Exception e) {
            System.err.println("✗ testSuggestCostAdjustments failed: " + e.getMessage());
            e.printStackTrace();
        }
        
        try {
            testSuggestDestinationsByCountry();
            System.out.println("✓ testSuggestDestinationsByCountry passed");
        } catch (Exception e) {
            System.err.println("✗ testSuggestDestinationsByCountry failed: " + e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println("All SuggestionService tests completed.");
    }
    
    public static void testSuggestionServiceCreation() {
        System.out.println("Testing SuggestionService creation...");
        LocationService locationService = new LocationService();
        SuggestionService suggestionService = new SuggestionService(locationService);
        assertNotNull(suggestionService, "SuggestionService should not be null");
        System.out.println("SuggestionService creation test passed.");
    }
    
    public static void testSuggestDestinationsByBudget() {
        System.out.println("Testing suggestDestinationsByBudget method...");
        LocationService locationService = new LocationService();
        SuggestionService suggestionService = new SuggestionService(locationService);
        // Test with a budget that should match some destinations
        List<Destination> suggestions = suggestionService.suggestDestinationsByBudget(5000.00);
        
        assertNotNull(suggestions, "Suggestions list should not be null");
        // Should find destinations within 10% of 5000 (4500-5500)
        assertFalse(suggestions.isEmpty(), "Suggestions list should not be empty");
        System.out.println("suggestDestinationsByBudget test passed.");
    }
    
    public static void testSuggestCostAdjustments() {
        System.out.println("Testing suggestCostAdjustments method...");
        LocationService locationService = new LocationService();
        SuggestionService suggestionService = new SuggestionService(locationService);
        Destination destination = new Destination(1, "France", "Hauts-de-France", "Lille", 5000.00);
        
        // Test when budget is sufficient
        String adjustments1 = suggestionService.suggestCostAdjustments(destination, 6000.00);
        assertNotNull(adjustments1, "Adjustments string should not be null");
        assertTrue(adjustments1.contains("left in your budget"), "Should contain budget information");
        
        // Test when budget is insufficient
        String adjustments2 = suggestionService.suggestCostAdjustments(destination, 4000.00);
        assertNotNull(adjustments2, "Adjustments string should not be null");
        assertTrue(adjustments2.contains("To fit within your budget"), "Should contain adjustment suggestions");
        System.out.println("suggestCostAdjustments test passed.");
    }
    
    public static void testSuggestDestinationsByCountry() {
        System.out.println("Testing suggestDestinationsByCountry method...");
        LocationService locationService = new LocationService();
        SuggestionService suggestionService = new SuggestionService(locationService);
        List<Destination> franceDestinations = suggestionService.suggestDestinationsByCountry("France");
        
        assertNotNull(franceDestinations, "France destinations list should not be null");
        assertFalse(franceDestinations.isEmpty(), "France destinations list should not be empty");
        assertEqual("France", franceDestinations.get(0).getCountry(), "Country should be France");
        System.out.println("suggestDestinationsByCountry test passed.");
    }
    
    // Custom assertion methods to replace JUnit assertions
    private static void assertNotNull(Object actual, String message) {
        if (actual != null) return;
        throw new AssertionError(message + ". Expected not null, but was null");
    }
    
    private static void assertFalse(boolean condition, String message) {
        if (!condition) return;
        throw new AssertionError(message + ". Expected false, but was true");
    }
    
    private static void assertTrue(boolean condition, String message) {
        if (condition) return;
        throw new AssertionError(message + ". Expected true, but was false");
    }
    
    private static void assertEqual(String expected, String actual, String message) {
        if (expected == null && actual == null) return;
        if (expected != null && expected.equals(actual)) return;
        throw new AssertionError(message + ". Expected: " + expected + ", but was: " + actual);
    }
}