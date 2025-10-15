package com.smarttravelplanner.model;

/**
 * Plain Java test class for User (Traveler) without using JUnit
 */
public class UserTest {
    
    public static void main(String[] args) {
        System.out.println("Running User (Traveler) tests...");
        
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
        
        System.out.println("All User (Traveler) tests completed.");
    }
    
    public static void testDefaultConstructor() {
        System.out.println("Testing default constructor...");
        Traveler user = new Traveler();
        
        assertNull(user.getName(), "Name should be null");
        assertEqual(0, user.getAge(), "Age should be 0");
        assertEqual(0, user.getFamilyCount(), "Family count should be 0");
        assertEqual(0.0, user.getBudget(), "Budget should be 0.0");
        
        System.out.println("Default constructor test passed.");
    }
    
    public static void testParameterizedConstructor() {
        System.out.println("Testing parameterized constructor...");
        try {
            Traveler user = new Traveler("John Doe", 30, 2, 5000.00);
            
            assertEqual("John Doe", user.getName(), "Name should be John Doe");
            assertEqual(30, user.getAge(), "Age should be 30");
            assertEqual(2, user.getFamilyCount(), "Family count should be 2");
            assertEqual(5000.00, user.getBudget(), "Budget should be 5000.00");
            
            System.out.println("Parameterized constructor test passed.");
        } catch (Exception e) {
            throw new RuntimeException("Exception should not be thrown: " + e.getMessage(), e);
        }
    }
    
    public static void testGettersAndSetters() {
        System.out.println("Testing getters and setters...");
        Traveler user = new Traveler();
        
        try {
            user.setName("Jane Smith");
            user.setAge(25);
            user.setFamilyCount(4);
            user.setBudget(7500.00);
            
            assertEqual("Jane Smith", user.getName(), "Name should be Jane Smith");
            assertEqual(25, user.getAge(), "Age should be 25");
            assertEqual(4, user.getFamilyCount(), "Family count should be 4");
            assertEqual(7500.00, user.getBudget(), "Budget should be 7500.00");
            
            System.out.println("Getters and setters test passed.");
        } catch (Exception e) {
            throw new RuntimeException("Exception should not be thrown: " + e.getMessage(), e);
        }
    }
    
    public static void testToString() {
        System.out.println("Testing toString method...");
        try {
            Traveler user = new Traveler("John Doe", 30, 2, 5000.00);
            String expected = "Traveler{name='John Doe', age=30, familyCount=2, budget=5000.0}";
            String actual = user.toString();
            
            assertEqual(expected, actual, "toString should match expected format");
            
            System.out.println("toString test passed.");
        } catch (Exception e) {
            throw new RuntimeException("Exception should not be thrown: " + e.getMessage(), e);
        }
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