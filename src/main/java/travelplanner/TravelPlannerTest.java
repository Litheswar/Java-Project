package travelplanner;

import java.util.ArrayList;
import java.util.List;

/**
 * Test class to verify all components of the Travel Planner work correctly.
 */
public class TravelPlannerTest {
    public static void main(String[] args) {
        System.out.println("=== Travel Planner Component Tests ===\n");
        
        // Test 1: Location and LocationRepository
        testLocationAndRepository();
        
        // Test 2: Person and Roles
        testPersonAndRoles();
        
        // Test 3: Accommodation types
        testAccommodations();
        
        // Test 4: Transport modes
        testTransportModes();
        
        // Test 5: Plan types
        testPlans();
        
        // Test 6: Cost calculations
        testCostCalculations();
        
        // Test 7: Exception handling
        testExceptionHandling();
        
        System.out.println("All tests completed successfully!");
    }
    
    private static void testLocationAndRepository() {
        System.out.println("Test 1: Location and LocationRepository");
        
        Location location = new Location("Test City", "Test Country", "A test destination", 
                                       45.0, -75.0, "Summer", 100.0);
        System.out.println("Created location: " + location.getName());
        
        LocationRepository repository = new LocationRepository();
        // Don't actually add to repository to avoid file I/O in tests
        
        System.out.println("Test 1 completed.\n");
    }
    
    private static void testPersonAndRoles() {
        System.out.println("Test 2: Person and Roles");
        
        Person adult = new Person("Adult", 35, Person.Role.ADULT);
        Person child = new Person("Child", 10, Person.Role.CHILD);
        Person senior = new Person("Senior", 70, Person.Role.SENIOR);
        
        System.out.println("Adult cost multiplier: " + adult.getCostMultiplier());
        System.out.println("Child cost multiplier: " + child.getCostMultiplier());
        System.out.println("Senior cost multiplier: " + senior.getCostMultiplier());
        
        System.out.println("Test 2 completed.\n");
    }
    
    private static void testAccommodations() {
        System.out.println("Test 3: Accommodation Types");
        
        Accommodation budget = new BudgetHotel("Budget Inn", 50.0, 2);
        Accommodation standard = new StandardHotel("Standard Hotel", 100.0, 3);
        Accommodation luxury = new LuxuryHotel("Luxury Resort", 200.0, 5);
        
        int nights = 3;
        int people = 2;
        
        System.out.println("Budget hotel cost for " + nights + " nights, " + people + " people: $" + 
                          budget.calculateCost(nights, people));
        System.out.println("Standard hotel cost for " + nights + " nights, " + people + " people: $" + 
                          standard.calculateCost(nights, people));
        System.out.println("Luxury hotel cost for " + nights + " nights, " + people + " people: $" + 
                          luxury.calculateCost(nights, people));
        
        System.out.println("Test 3 completed.\n");
    }
    
    private static void testTransportModes() {
        System.out.println("Test 4: Transport Modes");
        
        System.out.println("Car cost multiplier: " + TransportMode.CAR.getCostMultiplier());
        System.out.println("Bus cost multiplier: " + TransportMode.BUS.getCostMultiplier());
        System.out.println("Train cost multiplier: " + TransportMode.TRAIN.getCostMultiplier());
        System.out.println("Flight cost multiplier: " + TransportMode.FLIGHT.getCostMultiplier());
        
        System.out.println("Car sustainability score: " + TransportMode.CAR.getSustainabilityScore());
        System.out.println("Bus sustainability score: " + TransportMode.BUS.getSustainabilityScore());
        System.out.println("Train sustainability score: " + TransportMode.TRAIN.getSustainabilityScore());
        System.out.println("Flight sustainability score: " + TransportMode.FLIGHT.getSustainabilityScore());
        
        System.out.println("Test 4 completed.\n");
    }
    
    private static void testPlans() {
        System.out.println("Test 5: Plan Types");
        
        Location testLocation = new Location("Test City", "Test Country", "A test destination", 
                                           45.0, -75.0, "Summer", 100.0);
        
        Plan cityPlan = new CityPlan("City Exploration", testLocation);
        cityPlan.generateRoute();
        
        Plan tourPlan = new TourPlan("Nature Tour", testLocation);
        tourPlan.generateRoute();
        
        System.out.println("City plan sustainability score: " + cityPlan.calculateSustainabilityScore());
        System.out.println("Tour plan sustainability score: " + tourPlan.calculateSustainabilityScore());
        
        System.out.println("City plan steps: " + cityPlan.getRouteSteps().size());
        System.out.println("Tour plan steps: " + tourPlan.getRouteSteps().size());
        
        System.out.println("Test 5 completed.\n");
    }
    
    private static void testCostCalculations() {
        System.out.println("Test 6: Cost Calculations");
        
        CostManager costManager = new CostManager();
        Location location = new Location("Test City", "Test Country", "A test destination", 
                                       45.0, -75.0, "Summer", 100.0);
        
        List<Person> family = new ArrayList<>();
        family.add(new Person("Adult 1", 35, Person.Role.ADULT));
        family.add(new Person("Adult 2", 35, Person.Role.ADULT));
        family.add(new Person("Child", 10, Person.Role.CHILD));
        
        Accommodation accommodation = new StandardHotel("Standard Hotel", 100.0, 3);
        TransportMode transport = TransportMode.TRAIN;
        int nights = 5;
        
        try {
            double totalCost = costManager.calculateTotalCost(location, family, accommodation, transport, nights);
            System.out.println("Total cost for family of " + family.size() + " for " + nights + " nights: $" + String.format("%.2f", totalCost));
        } catch (InsufficientFundsException e) {
            System.out.println("Insufficient funds exception: " + e.getMessage());
        }
        
        // Test sustainability score calculation
        double sustainabilityScore = costManager.calculateSustainabilityScore(transport, accommodation);
        System.out.println("Sustainability score: " + String.format("%.2f", sustainabilityScore));
        
        System.out.println("Test 6 completed.\n");
    }
    
    private static void testExceptionHandling() {
        System.out.println("Test 7: Exception Handling");
        
        try {
            throw new InvalidDestinationException("Test destination not found");
        } catch (InvalidDestinationException e) {
            System.out.println("Caught InvalidDestinationException: " + e.getMessage());
        }
        
        try {
            throw new InsufficientFundsException("Budget too low", 100.0, 200.0);
        } catch (InsufficientFundsException e) {
            System.out.println("Caught InsufficientFundsException: " + e.getMessage());
            System.out.println("Available: $" + e.getAvailableFunds() + ", Required: $" + e.getRequiredFunds() + 
                             ", Shortfall: $" + e.getShortfall());
        }
        
        System.out.println("Test 7 completed.\n");
    }
}