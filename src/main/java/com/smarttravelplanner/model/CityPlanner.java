package com.smarttravelplanner.model;

public class CityPlanner extends Planner {
    private String destination;
    private String travelMode; // Road, Rail, Air, Mixed
    
    // Default constructor
    public CityPlanner() {
        super("City Plan");
    }
    
    // Parameterized constructor
    public CityPlanner(String destination, String travelMode) {
        super("City Plan");
        this.destination = destination;
        this.travelMode = travelMode;
    }
    
    // Getters and Setters
    public String getDestination() {
        return destination;
    }
    
    public void setDestination(String destination) {
        this.destination = destination;
    }
    
    public String getTravelMode() {
        return travelMode;
    }
    
    public void setTravelMode(String travelMode) {
        this.travelMode = travelMode;
    }
    
    @Override
    public void computeRoute() {
        System.out.println("Computing route for city plan to " + destination);
        // Add route segments
        addRouteSegment("Start from home");
        addRouteSegment("Travel to " + destination + " by " + travelMode);
        addRouteSegment("Explore " + destination);
        addRouteSegment("Return home");
        
        // Set estimated distance and time based on travel mode
        switch (travelMode.toLowerCase()) {
            case "road":
                setEstimatedDistance(500);
                setEstimatedTime(8);
                break;
            case "rail":
                setEstimatedDistance(500);
                setEstimatedTime(6);
                break;
            case "air":
                setEstimatedDistance(500);
                setEstimatedTime(3);
                break;
            default:
                setEstimatedDistance(500);
                setEstimatedTime(8);
                break;
        }
    }
    
    @Override
    public void optimizeRoute() {
        System.out.println("Optimizing city route for efficiency");
        // Simple optimization - just ensure segments are in order
    }
    
    @Override
    public void displayRoute() {
        System.out.println("=== City Plan Route ===");
        for (int i = 0; i < getRouteSegments().size(); i++) {
            System.out.println((i + 1) + ". " + getRouteSegments().get(i));
        }
        System.out.println("Estimated Distance: " + getEstimatedDistance() + " km");
        System.out.println("Estimated Time: " + getEstimatedTime() + " hours");
    }
    
    @Override
    public String toString() {
        return "CityPlanner{" +
                "destination='" + destination + '\'' +
                ", travelMode='" + travelMode + '\'' +
                "} " + super.toString();
    }
}