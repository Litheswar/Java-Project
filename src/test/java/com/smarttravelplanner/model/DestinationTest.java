package com.smarttravelplanner.model;

/**
 * Plain Java test class for Destination without using JUnit
 */
public class DestinationTest {
    
    public static void main(String[] args) {
        System.out.println("Running Destination tests...");
        
        try {
            testDefaultConstructor();
            System.out.println("✓ testDefaultConstructor passed");
        } catch (Exception e) {
            System.err.println("✗ testDefaultConstructor failed: " + e.getMessage());
            e.printStackTrace();
        }
        
        try {
            testParameterizedConstructor();
            System.out.println("✓ testParameterizedConstructor passed");
        } catch (Exception e) {
            System.err.println("✗ testParameterizedConstructor failed: " + e.getMessage());
            e.printStackTrace();
        }
        
        try {
            testGettersAndSetters();
            System.out.println("✓ testGettersAndSetters passed");
        } catch (Exception e) {
            System.err.println("✗ testGettersAndSetters failed: " + e.getMessage());
            e.printStackTrace();
        }
        
        try {
            testToString();
            System.out.println("✓ testToString passed");
        } catch (Exception e) {
            System.err.println("✗ testToString failed: " + e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println("All Destination tests completed.");
    }
    
    public static void testDefaultConstructor() {
        System.out.println("Testing default constructor...");
        Destination destination = new Destination();
        
        // Check default values
        assertEqual(0, destination.getId(), "ID should be 0");
        assertNull(destination.getCountry(), "Country should be null");
        assertNull(destination.getState(), "State should be null");
        assertNull(destination.getCity(), "City should be null");
        assertEqual(0.0, destination.getBaseCost(), "Base cost should be 0.0");
        
        System.out.println("Default constructor test passed.");
    }
    
    public static void testParameterizedConstructor() {
        System.out.println("Testing parameterized constructor...");
        Destination destination = new Destination(1, "France", "Hauts-de-France", "Lille", 5000.00);
        
        assertEqual(1, destination.getId(), "ID should be 1");
        assertEqual("France", destination.getCountry(), "Country should be France");
        assertEqual("Hauts-de-France", destination.getState(), "State should be Hauts-de-France");
        assertEqual("Lille", destination.getCity(), "City should be Lille");
        assertEqual(5000.00, destination.getBaseCost(), "Base cost should be 5000.00");
        
        System.out.println("Parameterized constructor test passed.");
    }
    
    public static void testGettersAndSetters() {
        System.out.println("Testing getters and setters...");
        Destination destination = new Destination();
        
        destination.setId(2);
        destination.setCountry("Japan");
        destination.setState("Kyoto");
        destination.setCity("Gion");
        destination.setBaseCost(4800.00);
        
        assertEqual(2, destination.getId(), "ID should be 2");
        assertEqual("Japan", destination.getCountry(), "Country should be Japan");
        assertEqual("Kyoto", destination.getState(), "State should be Kyoto");
        assertEqual("Gion", destination.getCity(), "City should be Gion");
        assertEqual(4800.00, destination.getBaseCost(), "Base cost should be 4800.00");
        
        System.out.println("Getters and setters test passed.");
    }
    
    public static void testToString() {
        System.out.println("Testing toString method...");
        Destination destination = new Destination(1, "France", "Hauts-de-France", "Lille", 5000.00);
        String expected = "Destination{id=1, country='France', state='Hauts-de-France', city='Lille', baseCost=5000.0}";
        String actual = destination.toString();
        
        assertEqual(expected, actual, "toString should match expected format");
        
        System.out.println("toString test passed.");
    }
    
    // Custom assertion methods to replace JUnit assertions
    private static void assertEqual(Object expected, Object actual, String message) {
        if (expected == null && actual == null) return;
        if (expected != null && expected.equals(actual)) return;
        throw new AssertionError(message + ". Expected: " + expected + ", but was: " + actual);
    }
    
    private static void assertEqual(double expected, double actual, String message) {
        if (Math.abs(expected - actual) < 0.01) return; // Using 0.01 as tolerance
        throw new AssertionError(message + ". Expected: " + expected + ", but was: " + actual);
    }
    
    private static void assertNull(Object actual, String message) {
        if (actual == null) return;
        throw new AssertionError(message + ". Expected null, but was: " + actual);
    }
}