package travelplanner;

/**
 * Simple test class to verify the Travel Planner components work correctly.
 */
public class SimpleTest {
    public static void main(String[] args) {
        System.out.println("=== Simple Travel Planner Test ===\n");
        
        // Test creating a location
        Location location = new Location("Test City", "Test Country", "A test destination", 
                                       45.0, -75.0, "Summer", 100.0);
        System.out.println("Created location: " + location.getName());
        
        // Test creating persons with different roles
        Person adult = new Person("Adult", 35, Person.Role.ADULT);
        Person child = new Person("Child", 10, Person.Role.CHILD);
        Person senior = new Person("Senior", 70, Person.Role.SENIOR);
        
        System.out.println("Adult cost multiplier: " + adult.getCostMultiplier());
        System.out.println("Child cost multiplier: " + child.getCostMultiplier());
        System.out.println("Senior cost multiplier: " + senior.getCostMultiplier());
        
        // Test accommodation types
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
        
        // Test transport modes
        System.out.println("Car cost multiplier: " + TransportMode.CAR.getCostMultiplier());
        System.out.println("Bus cost multiplier: " + TransportMode.BUS.getCostMultiplier());
        System.out.println("Train cost multiplier: " + TransportMode.TRAIN.getCostMultiplier());
        System.out.println("Flight cost multiplier: " + TransportMode.FLIGHT.getCostMultiplier());
        
        System.out.println("Car sustainability score: " + TransportMode.CAR.getSustainabilityScore());
        System.out.println("Bus sustainability score: " + TransportMode.BUS.getSustainabilityScore());
        System.out.println("Train sustainability score: " + TransportMode.TRAIN.getSustainabilityScore());
        System.out.println("Flight sustainability score: " + TransportMode.FLIGHT.getSustainabilityScore());
        
        // Test exception handling
        try {
            throw new InvalidDestinationException("Test destination not found");
        } catch (InvalidDestinationException e) {
            System.out.println("Caught InvalidDestinationException: " + e.getMessage());
        }
        
        try {
            throw new InsufficientFundsException("Budget too low", 100.0, 200.0);
        } catch (InsufficientFundsException e) {
            System.out.println("Caught InsufficientFundsException: " + e.getMessage());
        }
        
        System.out.println("\nSimple test completed successfully!");
    }
}