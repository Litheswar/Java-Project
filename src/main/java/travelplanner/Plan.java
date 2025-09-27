package travelplanner;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class representing a travel plan.
 * Demonstrates Abstraction with abstract methods.
 */
public abstract class Plan {
    protected String planName;
    protected Location destination;
    protected List<String> routeSteps;
    protected double totalDistance;
    protected double estimatedTime;

    public Plan(String planName, Location destination) {
        this.planName = planName;
        this.destination = destination;
        this.routeSteps = new ArrayList<>();
    }

    // Getters
    public String getPlanName() {
        return planName;
    }

    public Location getDestination() {
        return destination;
    }

    public List<String> getRouteSteps() {
        return new ArrayList<>(routeSteps);
    }

    public double getTotalDistance() {
        return totalDistance;
    }

    public double getEstimatedTime() {
        return estimatedTime;
    }

    // Setters
    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public void setDestination(Location destination) {
        this.destination = destination;
    }

    public void setTotalDistance(double totalDistance) {
        this.totalDistance = totalDistance;
    }

    public void setEstimatedTime(double estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    /**
     * Adds a step to the route.
     * Demonstrates ArrayList usage for dynamic storage.
     */
    public void addRouteStep(String step) {
        routeSteps.add(step);
    }

    /**
     * Abstract method to generate a route.
     * Must be implemented by subclasses.
     * Demonstrates Abstraction.
     */
    public abstract void generateRoute();

    /**
     * Abstract method to calculate sustainability score.
     * Must be implemented by subclasses.
     * Demonstrates Abstraction.
     */
    public abstract double calculateSustainabilityScore();

    @Override
    public String toString() {
        return "Plan{" +
                "planName='" + planName + '\'' +
                ", destination=" + destination +
                ", routeSteps=" + routeSteps +
                ", totalDistance=" + totalDistance +
                ", estimatedTime=" + estimatedTime +
                '}';
    }
}