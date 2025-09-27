package travelplanner;

import java.util.Arrays;

/**
 * City plan implementation for urban destinations.
 * Demonstrates Inheritance from Plan class.
 */
public class CityPlan extends Plan {
    private static final double CITY_SUSTAINABILITY_BASE = 0.6;

    public CityPlan(String planName, Location destination) {
        super(planName, destination);
    }

    /**
     * Generates a city route with typical urban activities.
     * Demonstrates implementation of abstract method.
     */
    @Override
    public void generateRoute() {
        routeSteps.addAll(Arrays.asList(
            "Arrive at airport/train station",
            "Check into hotel",
            "Visit main city landmarks",
            "Explore local markets",
            "Experience city nightlife",
            "Shopping district tour",
            "Visit museums/galleries",
            "Local cuisine experience",
            "Departure"
        ));
        
        // Simulate distance and time calculations
        totalDistance = 50.0; // km
        estimatedTime = 3.0; // hours
    }

    /**
     * Calculates sustainability score for city plans.
     * Considers factors like public transport usage.
     */
    @Override
    public double calculateSustainabilityScore() {
        // Base score adjusted by factors like public transport availability
        return CITY_SUSTAINABILITY_BASE;
    }
}