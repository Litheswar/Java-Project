package com.smarttravelplanner;

import com.smarttravelplanner.db.*;
import com.smarttravelplanner.exceptions.BudgetExceededException;
import com.smarttravelplanner.exceptions.InvalidAgeException;
import com.smarttravelplanner.exceptions.InvalidFamilyCountException;
import com.smarttravelplanner.exceptions.InvalidInputException;
import com.smarttravelplanner.model.*;
import com.smarttravelplanner.service.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static CostManager costManager = new CostManager();
    private static SmartOptimizer smartOptimizer = new SmartOptimizer();
    private static TripHistoryManager tripHistoryManager = new TripHistoryManager();

    public static void main(String[] args) {
        System.out.println("==============================");
        System.out.println("    SMART TRAVEL PLANNER 3.0");
        System.out.println("==============================");

        // Initialize the database
        try {
            DatabaseInitializer.initializeDatabase();
        } catch (Exception e) {
            System.err.println("Failed to initialize database: " + e.getMessage());
            System.out.println("Continuing with file-based storage...");
        }

        boolean continuePlanning = true;

        while (continuePlanning) {
            try {
                // Step 1: User Registration
                Traveler traveler = registerTraveler();

                // Step 2: Trip Configuration
                TripConfiguration tripConfig = configureTrip();

                // Step 3: State & Destination Preview (using database)
                // previewDestinationsFromDatabase(tripConfig.getCountry()); // This method needs to be updated to use Spring

                // Step 4: Travel, Stay & Meal Selection
                String travelMode = selectTravelMode();
                String stayType = selectStayType();
                String mealType = selectMealType();

                // Step 5: Trip Planning Engine
                Planner planner = planTrip(tripConfig, travelMode);

                // Step 6: Cost Estimation & Budget Comparison
                double totalCost = estimateCost(planner, traveler, tripConfig);

                // Step 7: Smart Optimizer (if needed)
                if (!costManager.isBudgetSufficient(totalCost, traveler.getBudget())) {
                    applySmartOptimization(planner, traveler, totalCost);
                }

                // Step 8: Travel Companion Recommender
                recommendTravelCompanion(traveler);

                // Step 9: Sustainability Report
                SustainabilityReport sustainabilityReport = generateSustainabilityReport(travelMode);
                displaySustainabilityReport(sustainabilityReport);

                // Step 10: Save trip to history
                saveTripSummary(traveler, tripConfig, totalCost);

                // Step 11: Summary Output
                displaySummary(traveler, tripConfig, planner, totalCost, travelMode, stayType, mealType);

                // End of Flow
                continuePlanning = handleEndOfFlow();

            } catch (InvalidAgeException e) {
                System.err.println("Invalid age: " + e.getMessage());
                System.out.println("Please try again.");
            } catch (InvalidFamilyCountException e) {
                System.err.println("Invalid family count: " + e.getMessage());
                System.out.println("Please try again.");
            } catch (InvalidInputException e) {
                System.err.println("Invalid input: " + e.getMessage());
                System.out.println("Please try again.");
            } catch (BudgetExceededException e) {
                System.err.println("Budget exceeded: " + e.getMessage());
                System.out.println("Please adjust your plans and try again.");
            } catch (Exception e) {
                System.err.println("An error occurred: " + e.getMessage());
                e.printStackTrace();
                System.out.println("Please try again.");
            }
        }

        System.out.println("Thank you for using Smart Travel Planner!");
        scanner.close();
    }

    // Simple class to hold trip configuration data
    private static class TripConfiguration {
        private int tripDays;
        private int mealsPerDay;
        private double totalBudget;
        private String country;
        private String state;
        private String destination;

        public TripConfiguration(int tripDays, int mealsPerDay, double totalBudget,
                                String country, String state, String destination) {
            this.tripDays = tripDays;
            this.mealsPerDay = mealsPerDay;
            this.totalBudget = totalBudget;
            this.country = country;
            this.state = state;
            this.destination = destination;
        }

        // Getters
        public int getTripDays() {
            return tripDays;
        }

        public int getMealsPerDay() {
            return mealsPerDay;
        }

        public double getTotalBudget() {
            return totalBudget;
        }

        public String getCountry() {
            return country;
        }

        public String getState() {
            return state;
        }

        public String getDestination() {
            return destination;
        }
    }

    private static Traveler registerTraveler() throws InvalidAgeException, InvalidFamilyCountException, InvalidInputException {
        System.out.println("\n=== User Registration ===");

        System.out.print("Enter your name (or type EXIT to quit): ");
        String name = scanner.nextLine();
        if ("EXIT".equalsIgnoreCase(name)) {
            System.out.println("Goodbye!");
            System.exit(0);
        }

        int age = 0;
        while (age == 0) {
            try {
                System.out.print("Enter your age (10-100): ");
                age = Integer.parseInt(scanner.nextLine());
                if (age < 10 || age > 100) {
                    System.out.println("Age must be between 10 and 100. Please re-enter or type EXIT to quit.");
                    String input = scanner.nextLine();
                    if ("EXIT".equalsIgnoreCase(input)) {
                        System.out.println("Goodbye!");
                        System.exit(0);
                    }
                    age = Integer.parseInt(input);
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }

        int familyCount = 0;
        while (familyCount == 0) {
            try {
                System.out.print("Enter family members (1-10): ");
                familyCount = Integer.parseInt(scanner.nextLine());
                if (familyCount < 1 || familyCount > 10) {
                    System.out.println("Family members must be between 1-10. Please re-enter.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }

        double budget = 0;
        while (budget == 0) {
            try {
                System.out.print("Enter total budget (₹5,000 - ₹1,00,00,000): ");
                budget = Double.parseDouble(scanner.nextLine());
                if (budget < 5000 || budget > 10000000) {
                    System.out.println("Budget must be between ₹5,000 and ₹1,00,00,000. Please re-enter.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }

        // Validate inputs using the database utility
        if (!InputValidator.isValidAge(age)) {
            throw new InvalidAgeException("Age must be between 1 and 120");
        }
        
        if (!InputValidator.isValidFamilyCount(familyCount)) {
            throw new InvalidFamilyCountException("Family count must be between 1 and 10");
        }
        
        if (!InputValidator.isValidBudget(budget)) {
            throw new InvalidInputException("Budget must be non-negative");
        }

        return new Traveler(name, age, familyCount, budget);
    }

    private static TripConfiguration configureTrip() {
        System.out.println("\n=== Trip Configuration ===");

        int tripDays = 0;
        while (tripDays == 0) {
            try {
                System.out.print("Enter trip days (1-50): ");
                tripDays = Integer.parseInt(scanner.nextLine());
                if (tripDays < 1 || tripDays > 50) {
                    System.out.println("Trip days must be between 1-50. Please re-enter.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }

        int mealsPerDay = 0;
        while (mealsPerDay == 0) {
            try {
                System.out.print("Enter meals per day (1-5): ");
                mealsPerDay = Integer.parseInt(scanner.nextLine());
                if (mealsPerDay < 1 || mealsPerDay > 5) {
                    System.out.println("Meals per day must be between 1-5. Please re-enter.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }

        double totalBudget = 0;
        while (totalBudget == 0) {
            try {
                System.out.print("Reconfirm total budget: ");
                totalBudget = Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }

        // Validate inputs using the database utility
        if (!InputValidator.isValidTripDays(tripDays)) {
            System.out.println("Warning: Trip days should be between 1 and 50.");
        }
        
        if (!InputValidator.isValidMealsPerDay(mealsPerDay)) {
            System.out.println("Warning: Meals per day should be between 1 and 5.");
        }

        // Show available countries and let user select
        List<String> countries = getAvailableCountries();
        System.out.println("\nAvailable Countries:");
        for (int i = 0; i < countries.size(); i++) {
            System.out.println((i + 1) + ". " + countries.get(i));
        }

        int countryChoice = 0;
        while (countryChoice == 0) {
            try {
                System.out.print("Select country (1-8): ");
                countryChoice = Integer.parseInt(scanner.nextLine());
                if (countryChoice < 1 || countryChoice > 8) {
                    System.out.println("Please select a number between 1-8.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }

        String country = "";
        if (countryChoice > 0 && countryChoice <= countries.size()) {
            country = countries.get(countryChoice - 1);
        }

        // For simplicity, we'll use a fixed destination
        return new TripConfiguration(tripDays, mealsPerDay, totalBudget, country, "State", "Lille");
    }

    private static List<String> getAvailableCountries() {
        List<String> countries = new ArrayList<>();
        countries.add("France");
        countries.add("Japan");
        countries.add("India");
        countries.add("Italy");
        countries.add("Australia");
        countries.add("UAE");
        countries.add("USA");
        countries.add("Singapore");
        return countries;
    }

    private static String selectTravelMode() {
        System.out.println("\n=== Travel Mode Selection ===");
        System.out.println("1. Road");
        System.out.println("2. Rail");
        System.out.println("3. Air");
        System.out.println("4. Mixed");

        int choice = 0;
        while (choice == 0) {
            try {
                System.out.print("Select (1-4): ");
                choice = Integer.parseInt(scanner.nextLine());
                if (choice < 1 || choice > 4) {
                    System.out.println("Please select a number between 1-4.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }

        switch (choice) {
            case 1:
                return "Road";
            case 2:
                return "Rail";
            case 3:
                return "Air";
            case 4:
                return "Mixed";
            default:
                return "Road";
        }
    }

    private static String selectStayType() {
        System.out.println("\n=== Stay Type ===");
        System.out.println("1. Budget");
        System.out.println("2. Standard");
        System.out.println("3. Premium");

        int choice = 0;
        while (choice == 0) {
            try {
                System.out.print("Select (1-3): ");
                choice = Integer.parseInt(scanner.nextLine());
                if (choice < 1 || choice > 3) {
                    System.out.println("Please select a number between 1-3.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }

        switch (choice) {
            case 1:
                return "Budget";
            case 2:
                return "Standard";
            case 3:
                return "Premium";
            default:
                return "Standard";
        }
    }

    private static String selectMealType() {
        System.out.println("\n=== Meal Type ===");
        System.out.println("1. Veg");
        System.out.println("2. Non-Veg");
        System.out.println("3. Mixed");

        int choice = 0;
        while (choice == 0) {
            try {
                System.out.print("Select (1-3): ");
                choice = Integer.parseInt(scanner.nextLine());
                if (choice < 1 || choice > 3) {
                    System.out.println("Please select a number between 1-3.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }

        switch (choice) {
            case 1:
                return "Veg";
            case 2:
                return "Non-Veg";
            case 3:
                return "Mixed";
            default:
                return "Mixed";
        }
    }

    private static Planner planTrip(TripConfiguration tripConfig, String travelMode) {
        System.out.println("\n=== Trip Planning ===");

        // For simplicity, we'll create a CityPlanner
        CityPlanner cityPlanner = new CityPlanner(tripConfig.getDestination(), travelMode);
        cityPlanner.computeRoute();
        cityPlanner.optimizeRoute();

        return cityPlanner;
    }

    private static double estimateCost(Planner planner, Traveler traveler, TripConfiguration tripConfig)
            throws BudgetExceededException {
        System.out.println("\n=== Cost Estimation ===");

        double totalCost = costManager.estimateTotalCost(
                planner,
                traveler.getFamilyCount(),
                tripConfig.getTripDays(),
                tripConfig.getMealsPerDay(),
                "Standard" // Default stay type, in a full implementation we would get this from user
        );

        System.out.println("Estimated cost: ₹" + String.format("%.0f", totalCost));
        System.out.println("Your budget: ₹" + String.format("%.0f", traveler.getBudget()));

        // Check if budget is sufficient using the database utility
        if (!InputValidator.isBudgetSufficient(traveler.getBudget(), totalCost)) {
            double difference = totalCost - traveler.getBudget();
            System.out.println("\n⚠️ Budget exceeded by ₹" + String.format("%.0f", difference) +
                    ". Estimated cost ₹" + String.format("%.0f", totalCost) +
                    " exceeds your budget of ₹" + String.format("%.0f", traveler.getBudget()) + ".");

            // Generate alerts for the trip configuration
            List<String> alerts = AlertGenerator.generateAllAlerts(
                tripConfig.getMealsPerDay(),
                "Standard", // Default stay type
                "Road", // Default travel mode
                tripConfig.getTripDays()
            );
            
            if (!alerts.isEmpty()) {
                System.out.println("\n⚠️ Alerts for your trip configuration:");
                for (String alert : alerts) {
                    System.out.println("  - " + alert);
                }
            }

            // Suggest affordable destinations
            // try {
            //     // DestinationDAO destinationDAO = new DestinationDAO(); // This needs to be updated to use Spring
            //     // List<Destination> affordableDestinations = destinationDAO.getAffordableDestinations(traveler.getBudget());
            //     // if (!affordableDestinations.isEmpty()) {
            //     //     System.out.println("\n💰 Affordable destinations within your budget:");
            //     //     for (int i = 0; i < Math.min(5, affordableDestinations.size()); i++) {
            //     //         Destination dest = affordableDestinations.get(i);
            //     //         System.out.println("  " + dest.getCountry() + " - " + dest.getState() + " - " + dest.getCity() + 
            //     //                          " (Cost: ₹" + String.format("%.0f", dest.getBaseCost()) + ")");
            //     //     }
            //     // }
            // // } catch (SQLException e) {
            // //     System.err.println("Database error while fetching affordable destinations: " + e.getMessage());
            // // }

            System.out.println("\nOptions:");
            System.out.println("1. Increase your budget");
            System.out.println("2. View similar destinations within your budget");
            System.out.println("3. Auto-optimize trip (reduce days / switch to budget hotels)");
            System.out.println("4. Exit");

            int choice = 0;
            while (choice == 0) {
                try {
                    System.out.print("Select option (1-4): ");
                    choice = Integer.parseInt(scanner.nextLine());
                    if (choice < 1 || choice > 4) {
                        System.out.println("Please select a number between 1-4.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Please enter a valid number.");
                }
            }

            switch (choice) {
                case 1:
                    System.out.println("Please restart the application to enter a higher budget.");
                    break;
                case 2:
                    System.out.println("Showing similar destinations within your budget...");
                    // In a full implementation, we would show alternative destinations
                    break;
                case 3:
                    System.out.println("Applying smart optimization...");
                    // Optimization will be applied in the next step
                    break;
                case 4:
                    System.out.println("Goodbye!");
                    System.exit(0);
                    break;
            }
        } else {
            double difference = traveler.getBudget() - totalCost;
            System.out.println("\n✅ You're under budget by ₹" + String.format("%.0f", difference) + "!");
            System.out.println("Would you like to upgrade your stay or add leisure activities? (Y/N): ");
            String upgradeChoice = scanner.nextLine();

            if ("Y".equalsIgnoreCase(upgradeChoice) || "YES".equalsIgnoreCase(upgradeChoice)) {
                System.out.println("Consider upgrading to premium accommodation or adding sightseeing tours.");
            }
        }

        return totalCost;
    }

    private static void applySmartOptimization(Planner planner, Traveler traveler, double estimatedCost) {
        System.out.println("\n=== Smart Optimizer ===");
        String optimizationMessage = smartOptimizer.optimizePlan(planner, traveler, estimatedCost);
        System.out.println(optimizationMessage);
        
        // Get route optimization suggestion
        String suggestion = RouteOptimizer.getOptimizationSuggestion("cost", new ArrayList<>());
        System.out.println("💡 Optimization suggestion: " + suggestion);
    }

    private static void recommendTravelCompanion(Traveler traveler) {
        System.out.println("\n=== Travel Companion Recommendation ===");

        if (traveler.getFamilyCount() == 1) {
            System.out.println("You're traveling solo! Recommended: Compact City Plan in Tokyo (budget-efficient).");
        } else if (traveler.getFamilyCount() >= 6) {
            System.out.println("Large group detected (" + traveler.getFamilyCount() + "+ members) — " +
                    "consider family packages in Italy or Goa.");
        } else {
            System.out.println("Perfect group size for a customizable travel experience!");
        }
    }

    private static SustainabilityReport generateSustainabilityReport(String travelMode) {
        SustainabilityReport report = new SustainabilityReport();
        report.setTravelMode(travelMode);

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

        report.setScore(score);
        report.setCo2Footprint(co2Footprint);
        report.setTips(tips);

        // Add CO2 calculation using the database utility
        double co2 = CO2Calculator.calculateCO2Footprint(travelMode, 7, 2); // Default values for demo
        System.out.println("🌍 Estimated CO2 footprint: " + String.format("%.2f", co2) + " kg");
        System.out.println("💡 Sustainability tip: " + CO2Calculator.getSustainabilityTip(co2));

        return report;
    }

    private static void displaySustainabilityReport(SustainabilityReport report) {
        System.out.println("\n" + report.generateReport());
    }

    private static void saveTripSummary(Traveler traveler, TripConfiguration tripConfig, double totalCost) {
        System.out.println("\n=== Saving Trip Summary ===");

        boolean success = tripHistoryManager.saveTripToHistory(
                traveler,
                tripConfig.getCountry(),
                tripConfig.getDestination(),
                traveler.getBudget(),
                totalCost
        );

        if (success) {
            System.out.println("Trip summary saved successfully!");
        } else {
            System.out.println("Failed to save trip summary.");
        }
    }

    private static void displaySummary(Traveler traveler, TripConfiguration tripConfig, Planner planner,
                                      double totalCost, String travelMode, String stayType, String mealType) {
        System.out.println("\n==============================");
        System.out.println("        TRIP SUMMARY");
        System.out.println("==============================");

        // Traveler Details
        System.out.println("\nTraveler Details:");
        System.out.println("  Name: " + traveler.getName());
        System.out.println("  Age: " + traveler.getAge());
        System.out.println("  Family Count: " + traveler.getFamilyCount());
        System.out.println("  Budget: ₹" + String.format("%.0f", traveler.getBudget()));

        // Destination Details
        System.out.println("\nDestination Details:");
        System.out.println("  Country: " + tripConfig.getCountry());
        System.out.println("  State: " + tripConfig.getState());
        System.out.println("  Destination: " + tripConfig.getDestination());
        System.out.println("  Trip Days: " + tripConfig.getTripDays());
        System.out.println("  Meals per Day: " + tripConfig.getMealsPerDay());
        System.out.println("  Reconfirmed Budget: ₹" + String.format("%.0f", tripConfig.getTotalBudget()));

        // Route Summary
        System.out.println("\nRoute Summary:");
        planner.displayRoute();

        // Cost Breakdown
        System.out.println("\nCost Breakdown:");
        System.out.println("  Estimated Total Cost: ₹" + String.format("%.0f", totalCost));
        
        // Travel Preferences
        System.out.println("\nTravel Preferences:");
        System.out.println("  Travel Mode: " + travelMode);
        System.out.println("  Stay Type: " + stayType);
        System.out.println("  Meal Type: " + mealType);
        
        // Expense calculation using the database utility
        double travelExpense = ExpenseCalculator.calculateTravelExpense(5000.0, tripConfig.getTripDays(), traveler.getFamilyCount());
        double foodExpense = ExpenseCalculator.calculateFoodExpense(tripConfig.getMealsPerDay(), tripConfig.getTripDays(), traveler.getFamilyCount());
        double stayExpense = ExpenseCalculator.calculateStayExpense(tripConfig.getTripDays(), traveler.getFamilyCount(), stayType);
        
        System.out.println("\n💰 Detailed Expense Breakdown:");
        System.out.println("  Travel: ₹" + String.format("%.0f", travelExpense));
        System.out.println("  Food: ₹" + String.format("%.0f", foodExpense));
        System.out.println("  Stay: ₹" + String.format("%.0f", stayExpense));
        System.out.println("  Other (Shopping, Leisure): ₹" + String.format("%.0f", totalCost - travelExpense - foodExpense - stayExpense));
    }
    
    private static boolean handleEndOfFlow() {
        System.out.println("\n=== End of Flow ===");
        System.out.println("Would you like to:");
        System.out.println("1) Plan another trip");
        System.out.println("2) View previous trip history");
        System.out.println("3) Exit");
        
        int choice = 0;
        while (choice == 0) {
            try {
                System.out.print("Select option (1-3): ");
                choice = Integer.parseInt(scanner.nextLine());
                if (choice < 1 || choice > 3) {
                    System.out.println("Please select a number between 1-3.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
        
        switch (choice) {
            case 1:
                return true; // Continue planning
            case 2:
                tripHistoryManager.displayTripHistory();
                System.out.println("\nPress Enter to continue...");
                scanner.nextLine();
                return true; // Continue planning
            case 3:
                return false; // Exit
            default:
                return false; // Exit
        }
    }
}