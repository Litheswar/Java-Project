package com.smarttravelplanner.utils;

import com.smarttravelplanner.model.Planner;

/**
 * Optimizes route based on distance (shortest path)
 * @param planner The planner to optimize
 */
public class RouteOptimizer {
    
    /**
     * Optimizes route based on distance (shortest path)
     * @param planner The planner to optimize
     */
    public static void optimizeByDistance(Planner planner) {
        System.out.println("Optimizing route by shortest distance");
        planner.optimizeRoute();
    }
    
    /**
     * Optimizes route based on time (least time)
     * @param planner The planner to optimize
     */
    public static void optimizeByTime(Planner planner) {
        System.out.println("Optimizing route by least time");
        planner.optimizeRoute();
    }
    
    /**
     * Optimizes route based on cost (least cost)
     * This is a simplified implementation
     * @param planner The planner to optimize
     * @param costPerKm Cost per kilometer for different travel modes
     */
    public static void optimizeByCost(Planner planner, double costPerKm) {
        System.out.println("Optimizing route by least cost");
        planner.optimizeRoute();
    }
    
    /**
     * Calculates the sustainability score based on travel mode
     * @param travelMode The travel mode
     * @return Sustainability score (0-10)
     */
    public static int calculateSustainabilityScore(String travelMode) {
        switch (travelMode.toLowerCase()) {
            case "road":
                return 4; // Car travel has moderate environmental impact
            case "rail":
                return 9; // Train travel is very sustainable
            case "air":
                return 2; // Air travel has high environmental impact
            default:
                return 5; // Default score
        }
    }
    
    /**
     * Gets the travel mode multiplier for cost calculations
     * @param travelMode The travel mode
     * @return Multiplier value
     */
    public static double getTravelModeMultiplier(String travelMode) {
        switch (travelMode.toLowerCase()) {
            case "road":
                return 1.0; // Base rate for road travel
            case "rail":
                return 1.2; // Trains might be slightly more expensive
            case "air":
                return 2.0; // Air travel is more expensive
            default:
                return 1.0; // Default rate
        }
    }
}