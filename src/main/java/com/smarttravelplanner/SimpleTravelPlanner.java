package com.smarttravelplanner;

public class SimpleTravelPlanner {
    public static void main(String[] args) {
        System.out.println("==================================");
        System.out.println("    SMART TRAVEL PLANNER v1.0");
        System.out.println("==================================");
        System.out.println();
        System.out.println("Welcome to the Smart Travel Planner!");
        System.out.println();
        System.out.println("This is a simplified version of the application.");
        System.out.println("In a full implementation, you would be able to:");
        System.out.println("  - Plan trips with detailed itineraries");
        System.out.println("  - Estimate costs based on your preferences");
        System.out.println("  - Get sustainability reports");
        System.out.println("  - Receive smart optimization suggestions");
        System.out.println("  - View travel history and recommendations");
        System.out.println();
        System.out.println("Press Enter to exit...");
        try {
            System.in.read();
        } catch (Exception e) {
            // Ignore
        }
    }
}