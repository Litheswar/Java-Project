package travelplanner;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Main class for the Smart Travel Planner application.
 * Demonstrates all required OOP concepts and integrates all modules.
 */
public class TravelPlanner {
    private LocationRepository locationRepository;
    private CostManager costManager;
    private Scanner scanner;

    public TravelPlanner() {
        this.locationRepository = new LocationRepository();
        this.costManager = new CostManager();
        this.scanner = new Scanner(System.in);
        
        // Initialize with sample data
        locationRepository.initializeWithSampleData();
    }

    /**
     * Main method to run the travel planner application.
     */
    public static void main(String[] args) {
        TravelPlanner planner = new TravelPlanner();
        planner.run();
    }

    /**
     * Runs the main application loop.
     */
    public void run() {
        System.out.println("=== Smart Travel Planner ===");
        System.out.println("Plan your sustainable trip with us!\n");

        boolean running = true;
        while (running) {
            System.out.println("1. Plan a new trip");
            System.out.println("2. View available destinations");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");

            int choice = getIntInput();
            System.out.println();

            switch (choice) {
                case 1:
                    planNewTrip();
                    break;
                case 2:
                    viewDestinations();
                    break;
                case 3:
                    running = false;
                    System.out.println("Thank you for using Smart Travel Planner!");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.\n");
            }
        }
    }

    /**
     * Handles the process of planning a new trip.
     */
    private void planNewTrip() {
        try {
            // Get destination
            Location destination = selectDestination();
            if (destination == null) {
                System.out.println("Invalid destination selected.\n");
                return;
            }

            // Get family members
            List<Person> familyMembers = getFamilyMembers();

            // Get accommodation preference
            Accommodation accommodation = selectAccommodation();

            // Get transport mode
            TransportMode transportMode = selectTransportMode();

            // Get trip duration
            System.out.print("Enter number of nights for your stay: ");
            int numberOfNights = getIntInput();

            // Get budget
            System.out.print("Enter your total budget: $");
            double budget = getDoubleInput();

            // Calculate total cost
            double totalCost = costManager.calculateTotalCost(
                destination, familyMembers, accommodation, transportMode, numberOfNights);

            // Display cost breakdown
            displayCostBreakdown(destination, familyMembers, accommodation, transportMode, 
                               numberOfNights, totalCost, budget);

            // Check if budget is sufficient
            if (totalCost > budget) {
                handleInsufficientBudget(budget, totalCost);
            } else {
                // Generate travel plan
                generateTravelPlan(destination, familyMembers, accommodation, transportMode, numberOfNights);
            }

        } catch (InsufficientFundsException e) {
            System.out.println("Budget calculation error: " + e.getMessage());
            System.out.println("Available funds: $" + String.format("%.2f", e.getAvailableFunds()));
            System.out.println("Required funds: $" + String.format("%.2f", e.getRequiredFunds()));
            System.out.println("Shortfall: $" + String.format("%.2f", e.getShortfall()));
        } catch (Exception e) {
            System.out.println("An error occurred while planning your trip: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println();
    }

    /**
     * Allows user to select a destination.
     */
    private Location selectDestination() {
        List<Location> locations = locationRepository.getAllLocations();
        if (locations.isEmpty()) {
            System.out.println("No destinations available.");
            return null;
        }

        System.out.println("Available destinations:");
        for (int i = 0; i < locations.size(); i++) {
            System.out.println((i + 1) + ". " + locations.get(i).getName() + 
                             " (" + locations.get(i).getCountry() + ")");
        }

        System.out.print("Select destination (enter number): ");
        int choice = getIntInput();

        if (choice < 1 || choice > locations.size()) {
            return null;
        }

        return locations.get(choice - 1);
    }

    /**
     * Gets family member details from user.
     */
    private List<Person> getFamilyMembers() {
        List<Person> familyMembers = new ArrayList<>();
        
        System.out.print("Enter number of family members: ");
        int count = getIntInput();

        for (int i = 0; i < count; i++) {
            System.out.println("Enter details for family member " + (i + 1) + ":");
            System.out.print("Name: ");
            String name = scanner.nextLine();
            
            System.out.print("Age: ");
            int age = getIntInput();
            
            Person.Role role = selectRole(age);
            familyMembers.add(new Person(name, age, role));
        }

        return familyMembers;
    }

    /**
     * Allows user to select a role based on age.
     */
    private Person.Role selectRole(int age) {
        if (age < 13) {
            return Person.Role.CHILD;
        } else if (age >= 65) {
            return Person.Role.SENIOR;
        } else {
            return Person.Role.ADULT;
        }
    }

    /**
     * Allows user to select accommodation type.
     */
    private Accommodation selectAccommodation() {
        System.out.println("Select accommodation type:");
        System.out.println("1. Budget Hotel ($50/night)");
        System.out.println("2. Standard Hotel ($100/night)");
        System.out.println("3. Luxury Hotel ($200/night)");
        System.out.print("Enter choice: ");

        int choice = getIntInput();
        
        switch (choice) {
            case 1:
                return new BudgetHotel("Budget Hotel", 50.0, 2);
            case 2:
                return new StandardHotel("Standard Hotel", 100.0, 4);
            case 3:
                return new LuxuryHotel("Luxury Hotel", 200.0, 5);
            default:
                System.out.println("Invalid choice. Defaulting to Budget Hotel.");
                return new BudgetHotel("Budget Hotel", 50.0, 2);
        }
    }

    /**
     * Allows user to select transport mode.
     */
    private TransportMode selectTransportMode() {
        System.out.println("Select transport mode:");
        System.out.println("1. Car");
        System.out.println("2. Bus");
        System.out.println("3. Train");
        System.out.println("4. Flight");
        System.out.print("Enter choice: ");

        int choice = getIntInput();
        
        switch (choice) {
            case 1:
                return TransportMode.CAR;
            case 2:
                return TransportMode.BUS;
            case 3:
                return TransportMode.TRAIN;
            case 4:
                return TransportMode.FLIGHT;
            default:
                System.out.println("Invalid choice. Defaulting to Bus.");
                return TransportMode.BUS;
        }
    }

    /**
     * Displays cost breakdown for the trip.
     */
    private void displayCostBreakdown(Location destination, List<Person> familyMembers, 
                                    Accommodation accommodation, TransportMode transportMode,
                                    int numberOfNights, double totalCost, double budget) {
        System.out.println("\n=== Trip Cost Breakdown ===");
        System.out.println("Destination: " + destination.getName() + ", " + destination.getCountry());
        System.out.println("Family Members: " + familyMembers.size());
        System.out.println("Accommodation: " + accommodation.getName());
        System.out.println("Transport Mode: " + transportMode);
        System.out.println("Duration: " + numberOfNights + " nights");
        System.out.println("-----------------------------");
        
        // Calculate individual costs for breakdown
        double accommodationCost = accommodation.calculateCost(numberOfNights, familyMembers.size());
        double foodCost = 30.0 * numberOfNights * familyMembers.size(); // Simplified
        double transportCost = destination.getAverageCostPerDay() * 2 * transportMode.getCostMultiplier() * familyMembers.size();
        double shoppingCost = 50.0 * familyMembers.size(); // Simplified
        double emergencyBuffer = totalCost * 0.15;
        
        System.out.println("Accommodation: $" + String.format("%.2f", accommodationCost));
        System.out.println("Food: $" + String.format("%.2f", foodCost));
        System.out.println("Transport: $" + String.format("%.2f", transportCost));
        System.out.println("Shopping: $" + String.format("%.2f", shoppingCost));
        System.out.println("Emergency Buffer (15%): $" + String.format("%.2f", emergencyBuffer));
        System.out.println("-----------------------------");
        System.out.println("Total Estimated Cost: $" + String.format("%.2f", totalCost));
        System.out.println("Your Budget: $" + String.format("%.2f", budget));
        
        double difference = budget - totalCost;
        if (difference >= 0) {
            System.out.println("Remaining Budget: $" + String.format("%.2f", difference));
        } else {
            System.out.println("Budget Shortfall: $" + String.format("%.2f", Math.abs(difference)));
        }
    }

    /**
     * Handles insufficient budget scenarios.
     */
    private void handleInsufficientBudget(double budget, double requiredCost) {
        System.out.println("\n=== Budget Alert ===");
        System.out.println("Your budget is insufficient for this trip plan.");
        System.out.println(costManager.suggestCostSavingAlternatives(budget, requiredCost));
    }

    /**
     * Generates and displays the travel plan.
     */
    private void generateTravelPlan(Location destination, List<Person> familyMembers, 
                                  Accommodation accommodation, TransportMode transportMode,
                                  int numberOfNights) {
        System.out.println("\n=== Your Smart Travel Plan ===");
        
        // Determine plan type based on destination
        Plan plan;
        if (destination.getName().equals("Paris") || destination.getName().equals("New York") || 
            destination.getName().equals("London")) {
            plan = new CityPlan("City Exploration Plan", destination);
        } else {
            plan = new TourPlan("Scenic Tour Plan", destination);
        }
        
        // Generate route
        plan.generateRoute();
        
        // Display plan details
        System.out.println("Plan Type: " + plan.getPlanName());
        System.out.println("Destination: " + destination.getName() + ", " + destination.getCountry());
        System.out.println("Duration: " + numberOfNights + " nights");
        System.out.println("Best Season to Visit: " + destination.getBestSeason());
        System.out.println("\n--- Itinerary ---");
        
        List<String> steps = plan.getRouteSteps();
        for (int i = 0; i < steps.size(); i++) {
            System.out.println((i + 1) + ". " + steps.get(i));
        }
        
        // Calculate and display sustainability score
        double sustainabilityScore = costManager.calculateSustainabilityScore(transportMode, accommodation);
        System.out.println("\n--- Sustainability Metrics ---");
        System.out.println("Sustainability Score: " + String.format("%.2f", sustainabilityScore) + "/1.00");
        System.out.println("Eco-points earned: " + (int)(sustainabilityScore * 100));
        
        if (sustainabilityScore >= 0.7) {
            System.out.println("Great job! You've chosen an eco-friendly travel option.");
        } else if (sustainabilityScore >= 0.5) {
            System.out.println("Good effort! Consider more sustainable options for higher scores.");
        } else {
            System.out.println("Consider choosing public transport or eco-lodges for better sustainability.");
        }
    }

    /**
     * Displays all available destinations.
     */
    private void viewDestinations() {
        List<Location> locations = locationRepository.getAllLocations();
        if (locations.isEmpty()) {
            System.out.println("No destinations available.\n");
            return;
        }

        System.out.println("=== Available Destinations ===");
        for (Location location : locations) {
            System.out.println("Name: " + location.getName());
            System.out.println("Country: " + location.getCountry());
            System.out.println("Description: " + location.getDescription());
            System.out.println("Best Season: " + location.getBestSeason());
            System.out.println("Average Daily Cost: $" + location.getAverageCostPerDay());
            System.out.println("-----------------------------");
        }
        System.out.println();
    }

    /**
     * Gets integer input from user with error handling.
     */
    private int getIntInput() {
        while (true) {
            try {
                String input = scanner.nextLine().trim();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a valid number: ");
            }
        }
    }

    /**
     * Gets double input from user with error handling.
     */
    private double getDoubleInput() {
        while (true) {
            try {
                String input = scanner.nextLine().trim();
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a valid number: ");
            }
        }
    }
}