package com.smarttravelplanner.service;

import com.smarttravelplanner.model.CityPlan;
import com.smarttravelplanner.model.Destination;
import com.smarttravelplanner.model.TourPlan;

import java.util.ArrayList;
import java.util.List;

public class PlannerServiceTest {
    
    public static void main(String[] args) {
        System.out.println("Running PlannerService tests...");
        
        try {
            testPlannerServiceCreation();
            System.out.println("✓ testPlannerServiceCreation passed");
        } catch (Exception e) {
            System.err.println("✗ testPlannerServiceCreation failed: " + e.getMessage());
            e.printStackTrace();
        }
        
        try {
            testCreateCityPlan();
            System.out.println("✓ testCreateCityPlan passed");
        } catch (Exception e) {
            System.err.println("✗ testCreateCityPlan failed: " + e.getMessage());
            e.printStackTrace();
        }
        
        try {
            testCreateTourPlan();
            System.out.println("✓ testCreateTourPlan passed");
        } catch (Exception e) {
            System.err.println("✗ testCreateTourPlan failed: " + e.getMessage());
            e.printStackTrace();
        }
        
        try {
            testValidatePlan();
            System.out.println("✓ testValidatePlan passed");
        } catch (Exception e) {
            System.err.println("✗ testValidatePlan failed: " + e.getMessage());
            e.printStackTrace();
        }
        
        try {
            testGetRouteSteps();
            System.out.println("✓ testGetRouteSteps passed");
        } catch (Exception e) {
            System.err.println("✗ testGetRouteSteps failed: " + e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println("All PlannerService tests completed.");
    }
    
    public static void testPlannerServiceCreation() {
        System.out.println("Testing PlannerService creation...");
        PlannerService plannerService = new PlannerService();
        
        assertNotNull(plannerService, "PlannerService should not be null");
        System.out.println("PlannerService creation test passed.");
    }
    
    public static void testCreateCityPlan() {
        System.out.println("Testing createCityPlan method...");
        PlannerService plannerService = new PlannerService();
        Destination destination = new Destination(1, "France", "Hauts-de-France", "Lille", 5000.00);
        
        CityPlan cityPlan = plannerService.createCityPlan("My City Trip", destination);
        
        assertNotNull(cityPlan, "CityPlan should not be null");
        assertEqual("My City Trip", cityPlan.getPlanName(), "Plan name should match");
        assertEqual(1, cityPlan.getDestinations().size(), "Should have one destination");
        assertEqual(destination, cityPlan.getDestinations().get(0), "Destination should match");
        System.out.println("createCityPlan test passed.");
    }
    
    public static void testCreateTourPlan() {
        System.out.println("Testing createTourPlan method...");
        PlannerService plannerService = new PlannerService();
        List<Destination> destinations = new ArrayList<>();
        destinations.add(new Destination(1, "France", "Hauts-de-France", "Lille", 5000.00));
        destinations.add(new Destination(2, "Japan", "Kyoto", "Gion", 4800.00));
        
        TourPlan tourPlan = plannerService.createTourPlan("My Tour", destinations, 7, "Adventure");
        
        assertNotNull(tourPlan, "TourPlan should not be null");
        assertEqual("My Tour", tourPlan.getPlanName(), "Plan name should match");
        assertEqual(7, tourPlan.getDurationDays(), "Duration should match");
        assertEqual("Adventure", tourPlan.getTourType(), "Tour type should match");
        assertEqual(2, tourPlan.getDestinations().size(), "Should have two destinations");
        System.out.println("createTourPlan test passed.");
    }
    
    public static void testValidatePlan() {
        System.out.println("Testing validatePlan method...");
        PlannerService plannerService = new PlannerService();
        
        // Test with valid plan
        CityPlan validPlan = new CityPlan("Valid Plan");
        validPlan.addDestination(new Destination(1, "France", "Hauts-de-France", "Lille", 5000.00));
        assertTrue(plannerService.validatePlan(validPlan), "Valid plan should return true");
        
        // Test with invalid plan
        CityPlan invalidPlan = new CityPlan("Invalid Plan");
        assertFalse(plannerService.validatePlan(invalidPlan), "Invalid plan should return false");
        System.out.println("validatePlan test passed.");
    }
    
    public static void testGetRouteSteps() {
        System.out.println("Testing getRouteSteps method...");
        PlannerService plannerService = new PlannerService();
        List<Destination> destinations = new ArrayList<>();
        destinations.add(new Destination(1, "France", "Hauts-de-France", "Lille", 5000.00));
        destinations.add(new Destination(2, "Japan", "Kyoto", "Gion", 4800.00));
        
        CityPlan plan = new CityPlan("Test Plan");
        for (Destination dest : destinations) {
            plan.addDestination(dest);
        }
        
        List<String> steps = plannerService.getRouteSteps(plan);
        
        assertNotNull(steps, "Steps should not be null");
        assertEqual(2, steps.size(), "Should have two steps");
        assertTrue(steps.get(0).contains("Lille"), "First step should contain Lille");
        assertTrue(steps.get(1).contains("Gion"), "Second step should contain Gion");
        System.out.println("getRouteSteps test passed.");
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
    
    private static void assertNotNull(Object actual, String message) {
        if (actual != null) return;
        throw new AssertionError(message + ". Expected not null, but was null");
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