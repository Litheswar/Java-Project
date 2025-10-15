package com.smarttravelplanner.model;

import java.util.ArrayList;

/**
 * Plain Java test class for Plan without using JUnit
 */
public class PlanTest {
    
    public static void main(String[] args) {
        System.out.println("Running Plan tests...");
        
        try {
            testCityPlanCreation();
            System.out.println("✓ testCityPlanCreation passed");
        } catch (Exception e) {
            System.err.println("✗ testCityPlanCreation failed: " + e.getMessage());
            e.printStackTrace();
        }
        
        try {
            testTourPlanCreation();
            System.out.println("✓ testTourPlanCreation passed");
        } catch (Exception e) {
            System.err.println("✗ testTourPlanCreation failed: " + e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println("All Plan tests completed.");
    }
    
    public static void testCityPlanCreation() {
        System.out.println("Testing CityPlanner creation...");
        CityPlanner cityPlan = new CityPlanner();
        
        assertEqual("City Plan", cityPlan.getPlanType(), "Plan type should be 'City Plan'");
        assertNotNull(cityPlan.getRouteSegments(), "Route segments should not be null");
        assertTrue(cityPlan.getRouteSegments().isEmpty(), "Route segments should be empty");
        
        System.out.println("CityPlanner creation test passed.");
    }
    
    public static void testTourPlanCreation() {
        System.out.println("Testing TourPlanner creation...");
        TourPlanner tourPlan = new TourPlanner();
        
        assertEqual("Tour Plan", tourPlan.getPlanType(), "Plan type should be 'Tour Plan'");
        assertNotNull(tourPlan.getRouteSegments(), "Route segments should not be null");
        assertTrue(tourPlan.getRouteSegments().isEmpty(), "Route segments should be empty");
        assertNotNull(tourPlan.getDestinations(), "Destinations should not be null");
        assertTrue(tourPlan.getDestinations().isEmpty(), "Destinations should be empty");
        
        System.out.println("TourPlanner creation test passed.");
    }
    
    // Custom assertion methods to replace JUnit assertions
    private static void assertEqual(Object expected, Object actual, String message) {
        if (expected == null && actual == null) return;
        if (expected != null && expected.equals(actual)) return;
        throw new AssertionError(message + ". Expected: " + expected + ", but was: " + actual);
    }
    
    private static void assertNotNull(Object actual, String message) {
        if (actual != null) return;
        throw new AssertionError(message + ". Expected not null, but was null");
    }
    
    private static void assertTrue(boolean condition, String message) {
        if (condition) return;
        throw new AssertionError(message + ". Expected true, but was false");
    }
}