package com.smarttravelplanner.service;

import com.smarttravelplanner.exceptions.BudgetExceededException;
import com.smarttravelplanner.model.CityPlan;
import com.smarttravelplanner.model.Destination;

public class CostManagerTest {
    
    public static void main(String[] args) {
        System.out.println("Running CostManager tests...");
        
        try {
            testCalculateTotalCost();
            System.out.println("✓ testCalculateTotalCost passed");
        } catch (Exception e) {
            System.err.println("✗ testCalculateTotalCost failed: " + e.getMessage());
            e.printStackTrace();
        }
        
        try {
            testCalculateSustainabilityScore();
            System.out.println("✓ testCalculateSustainabilityScore passed");
        } catch (Exception e) {
            System.err.println("✗ testCalculateSustainabilityScore failed: " + e.getMessage());
            e.printStackTrace();
        }
        
        try {
            testBudgetExceededException();
            System.out.println("✓ testBudgetExceededException passed");
        } catch (Exception e) {
            System.err.println("✗ testBudgetExceededException failed: " + e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println("All CostManager tests completed.");
    }
    
    public static void testCalculateTotalCost() {
        System.out.println("Testing calculateTotalCost method...");
        CostManager costManager = new CostManager();
        CityPlan plan = new CityPlan("Test Plan");
        
        Destination dest1 = new Destination(1, "France", "Hauts-de-France", "Lille", 5000.00);
        Destination dest2 = new Destination(2, "Japan", "Kyoto", "Gion", 4800.00);
        
        plan.addDestination(dest1);
        plan.addDestination(dest2);
        
        try {
            // Family size: 2, Travel mode: standard, Duration: 7 days
            double cost = costManager.calculateTotalCost(plan, 2, "standard", 7);
            
            // Let's calculate the expected cost with the new logic:
            // Base cost: (5000 + 4800) = 9800
            // Travel cost: (5000 + 4800) * 0.2 = 1960
            // Food cost: 30 * 2 * 7 = 420
            // Stay cost: 100 * 2 * 7 = 1400
            // Leisure cost: 200 * 2 * 2 = 800
            // Subtotal: 9800 + 1960 + 420 + 1400 + 800 = 14380
            // Family adjustment: 14380 * (2 * 0.8) = 14380 * 1.6 = 23008
            // Mode adjustment (standard): 23008 * 1.0 = 23008
            assertEqual(23008.00, cost, 0.01, "Cost should match expected value");
            System.out.println("calculateTotalCost test passed.");
        } catch (BudgetExceededException e) {
            throw new RuntimeException("Unexpected BudgetExceededException: " + e.getMessage(), e);
        }
    }
    
    public static void testCalculateSustainabilityScore() {
        System.out.println("Testing calculateSustainabilityScore method...");
        CostManager costManager = new CostManager();
        CityPlan plan = new CityPlan("Test Plan");
        
        Destination dest1 = new Destination(1, "France", "Hauts-de-France", "Lille", 5000.00);
        Destination dest2 = new Destination(2, "Japan", "Kyoto", "Gion", 4800.00);
        
        plan.addDestination(dest1);
        plan.addDestination(dest2);
        
        // Travel mode: train, Duration: 7 days
        int score = costManager.calculateSustainabilityScore(plan, "train", 7);
        
        // Base score: 100
        // Train mode: 100 - 10 = 90
        // Destinations: 90 - (2 * 5) = 80
        // Duration: 80 - (7 / 2) = 80 - 3 = 77
        assertEqual(77, score, "Sustainability score should match expected value");
        System.out.println("calculateSustainabilityScore test passed.");
    }
    
    public static void testBudgetExceededException() {
        System.out.println("Testing BudgetExceededException...");
        CostManager costManager = new CostManager();
        CityPlan plan = new CityPlan("Expensive Plan");
        
        // Add very expensive destinations to exceed budget
        for (int i = 0; i < 10; i++) {
            Destination dest = new Destination(i, "Luxury Country " + i, "State " + i, "City " + i, 10000.00);
            plan.addDestination(dest);
        }
        
        try {
            costManager.calculateTotalCost(plan, 1, "luxury", 30);
            // If we reach here, the exception was not thrown
            throw new AssertionError("Expected BudgetExceededException was not thrown");
        } catch (BudgetExceededException e) {
            // This is expected
            System.out.println("BudgetExceededException correctly thrown: " + e.getMessage());
            System.out.println("BudgetExceededException test passed.");
        }
    }
    
    // Custom assertion methods to replace JUnit assertions
    private static void assertEqual(double expected, double actual, double delta, String message) {
        if (Math.abs(expected - actual) <= delta) return;
        throw new AssertionError(message + ". Expected: " + expected + ", but was: " + actual);
    }
    
    private static void assertEqual(int expected, int actual, String message) {
        if (expected == actual) return;
        throw new AssertionError(message + ". Expected: " + expected + ", but was: " + actual);
    }
}