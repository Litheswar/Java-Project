package travelplanner;

import java.util.Arrays;

/**
 * Tour plan implementation for scenic/natural destinations.
 * Demonstrates Inheritance from Plan class.
 */
public class TourPlan extends Plan {
    private static final double TOUR_SUSTAINABILITY_BASE = 0.8;

    public TourPlan(String planName, Location destination) {
        super(planName, destination);
    }

    /**
     * Generates a tour route with scenic activities.
     * Demonstrates implementation of abstract method.
     */
    @Override
    public void generateRoute() {
        routeSteps.addAll(Arrays.asList(
            "Arrive at destination",
            "Check into eco-lodge/hotel",
            "Guided nature tour",
            "Hiking/exploration activities",
            "Local cultural experience",
            "Wildlife observation",
            "Sustainable dining options",
            "Relaxation time",
            "Departure"
        ));
        
        // Simulate distance and time calculations
        totalDistance = 120.0; // km
        estimatedTime = 8.0; // hours
    }

    /**
     * Calculates sustainability score for tour plans.
     * Typically higher due to nature focus.
     */
    @Override
    public double calculateSustainabilityScore() {
        // Base score for eco-friendly tour plans
        return TOUR_SUSTAINABILITY_BASE;
    }
}