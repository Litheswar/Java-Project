package travelplanner;

/**
 * Test class to verify the enhanced Travel Planner features work correctly.
 */
public class EnhancedTravelPlannerTest {
    public static void main(String[] args) {
        System.out.println("=== Enhanced Travel Planner Test ===\n");
        
        // Test creating a country
        Country country = new Country("Test Country");
        System.out.println("Created country: " + country.getName());
        
        // Test creating a state
        State state = new State("Test State", "Test Country");
        System.out.println("Created state: " + state.getName() + " in " + state.getCountry());
        
        // Test adding state to country
        country.addState(state);
        System.out.println("Added state to country. Country now has " + country.getStates().size() + " states");
        
        // Test creating a location
        Location location = new Location("Test City", "Test Country", "A test destination", 
                                       45.0, -75.0, "Summer", 100.0);
        System.out.println("Created location: " + location.getName());
        
        // Test adding location to state
        state.addDestination(location);
        System.out.println("Added location to state. State now has " + state.getDestinations().size() + " destinations");
        
        // Test creating a route
        Route route = new Route("Test Route", "City A", "City B", 100.0, 2.0, 50.0);
        System.out.println("Created route: " + route.getName());
        
        // Test adding route to state
        state.addRoute(route);
        System.out.println("Added route to state. State now has " + state.getRoutes().size() + " routes");
        
        // Test calculating route cost for different transport modes
        System.out.println("Route costs:");
        for (TransportMode mode : TransportMode.values()) {
            double cost = route.calculateCost(mode);
            System.out.println("  " + mode + ": $" + String.format("%.2f", cost));
        }
        
        // Test NoAffordableDestinationException
        try {
            throw new NoAffordableDestinationException("No affordable destinations found", 100.0, 200.0);
        } catch (NoAffordableDestinationException e) {
            System.out.println("\nCaught NoAffordableDestinationException: " + e.getMessage());
            System.out.println("Budget: $" + e.getBudget());
            System.out.println("Minimum required: $" + e.getMinimumRequired());
            System.out.println("Shortfall: $" + e.getShortfall());
        }
        
        // Test CountryRepository
        CountryRepository repository = new CountryRepository();
        System.out.println("\nCountry repository loaded " + repository.getAllCountries().size() + " countries");
        
        System.out.println("\nEnhanced test completed successfully!");
    }
}