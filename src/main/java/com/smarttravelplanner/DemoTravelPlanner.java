package com.smarttravelplanner;

import java.util.Scanner;

public class DemoTravelPlanner {
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        System.out.println("==================================");
        System.out.println("    SMART TRAVEL PLANNER DEMO");
        System.out.println("==================================");
        System.out.println();
        
        // User Registration
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        
        System.out.print("Enter your age: ");
        int age = Integer.parseInt(scanner.nextLine());
        
        System.out.print("Enter number of family members: ");
        int familyCount = Integer.parseInt(scanner.nextLine());
        
        System.out.print("Enter your budget (₹): ");
        double budget = Double.parseDouble(scanner.nextLine());
        
        System.out.println();
        System.out.println("Welcome, " + name + "!");
        System.out.println("Age: " + age);
        System.out.println("Family members: " + familyCount);
        System.out.println("Budget: ₹" + String.format("%.2f", budget));
        System.out.println();
        
        // Trip Configuration
        System.out.println("=== Trip Configuration ===");
        System.out.print("Enter trip days (1-50): ");
        int tripDays = Integer.parseInt(scanner.nextLine());
        
        System.out.print("Enter meals per day (1-5): ");
        int mealsPerDay = Integer.parseInt(scanner.nextLine());
        
        System.out.println();
        System.out.println("Available Countries:");
        System.out.println("1. France");
        System.out.println("2. Japan");
        System.out.println("3. India");
        System.out.println("4. Italy");
        System.out.println("5. Australia");
        System.out.println("6. UAE");
        System.out.println("7. USA");
        System.out.println("8. Singapore");
        
        System.out.print("Select country (1-8): ");
        int countryChoice = Integer.parseInt(scanner.nextLine());
        String[] countries = {"France", "Japan", "India", "Italy", "Australia", "UAE", "USA", "Singapore"};
        String country = countries[countryChoice - 1];
        
        System.out.println();
        System.out.println("Selected Country: " + country);
        System.out.println("Trip Duration: " + tripDays + " days");
        System.out.println("Meals per Day: " + mealsPerDay);
        System.out.println();
        
        // Travel Mode Selection
        System.out.println("=== Travel Mode Selection ===");
        System.out.println("1. Road");
        System.out.println("2. Rail");
        System.out.println("3. Air");
        System.out.println("4. Mixed");
        
        System.out.print("Select (1-4): ");
        int travelChoice = Integer.parseInt(scanner.nextLine());
        String[] travelModes = {"Road", "Rail", "Air", "Mixed"};
        String travelMode = travelModes[travelChoice - 1];
        
        System.out.println();
        
        // Cost Estimation
        System.out.println("=== Cost Estimation ===");
        double estimatedCost = calculateCost(tripDays, mealsPerDay, familyCount, travelMode, country);
        
        System.out.println("Estimated cost: ₹" + String.format("%.2f", estimatedCost));
        System.out.println("Your budget: ₹" + String.format("%.2f", budget));
        
        if (estimatedCost <= budget) {
            double difference = budget - estimatedCost;
            System.out.println();
            System.out.println("✅ You're under budget by ₹" + String.format("%.2f", difference) + "!");
        } else {
            double difference = estimatedCost - budget;
            System.out.println();
            System.out.println("⚠️ Budget exceeded by ₹" + String.format("%.2f", difference) + ".");
        }
        
        System.out.println();
        
        // Sustainability Report
        System.out.println("=== Sustainability Report ===");
        generateSustainabilityReport(travelMode);
        
        System.out.println();
        System.out.println("Thank you for using Smart Travel Planner Demo!");
        System.out.println("In a full implementation, you would have access to many more features including:");
        System.out.println("  - Database integration for persistent storage");
        System.out.println("  - Smart optimization suggestions");
        System.out.println("  - Detailed expense breakdowns");
        System.out.println("  - Travel history tracking");
        System.out.println("  - Personalized recommendations");
        
        scanner.close();
    }
    
    private static double calculateCost(int tripDays, int mealsPerDay, int familyCount, String travelMode, String country) {
        // Simplified cost calculation
        double baseCost = 0;
        
        switch (country) {
            case "France": baseCost = 8000; break;
            case "Japan": baseCost = 9000; break;
            case "India": baseCost = 4000; break;
            case "Italy": baseCost = 7500; break;
            case "Australia": baseCost = 10000; break;
            case "UAE": baseCost = 8500; break;
            case "USA": baseCost = 9500; break;
            case "Singapore": baseCost = 7000; break;
            default: baseCost = 5000; break;
        }
        
        double travelCost = 0;
        switch (travelMode) {
            case "Road": travelCost = 2000; break;
            case "Rail": travelCost = 3000; break;
            case "Air": travelCost = 8000; break;
            case "Mixed": travelCost = 5000; break;
            default: travelCost = 3000; break;
        }
        
        double mealCost = mealsPerDay * 500 * tripDays;
        double stayCost = tripDays * 2000;
        
        return (baseCost + travelCost + mealCost + stayCost) * familyCount;
    }
    
    private static void generateSustainabilityReport(String travelMode) {
        System.out.println("Travel Mode: " + travelMode);
        
        int score;
        double co2Footprint;
        String tips;
        
        switch (travelMode.toLowerCase()) {
            case "road":
                score = 9;
                co2Footprint = 1.0;
                tips = "Great choice! Road travel has minimal environmental impact.";
                break;
            case "rail":
                score = 8;
                co2Footprint = 1.2;
                tips = "Excellent! Train travel is one of the most sustainable options.";
                break;
            case "air":
                score = 4;
                co2Footprint = 3.5;
                tips = "Choosing rail travel instead of air can reduce emissions by 60%.";
                break;
            case "mixed":
                score = 7;
                co2Footprint = 2.0;
                tips = "Consider using trains for longer distances to reduce your carbon footprint.";
                break;
            default:
                score = 6;
                co2Footprint = 2.0;
                tips = "Consider more sustainable travel options for future trips.";
                break;
        }
        
        System.out.println("Sustainability Score: " + score + "/10");
        System.out.println("Estimated CO2 footprint: " + String.format("%.2f", co2Footprint) + " kg per day");
        System.out.println("Tip: " + tips);
    }
}