package com.smarttravelplanner.model;

import java.util.ArrayList;

public class TourPlanner extends Planner {
    private ArrayList<String> destinations;
    private String travelMode; // Road, Rail, Air, Mixed
    
    // Default constructor
    public TourPlanner() {
        super("Tour Plan");
        this.destinations = new ArrayList<>();
    }
    
    // Parameterized constructor
    public TourPlanner(ArrayList<String> destinations, String travelMode) {
        super("Tour Plan");
        this.destinations = new ArrayList<>(destinations);
        this.travelMode = travelMode;
    }
    
    // Getters and Setters
    public ArrayList<String> getDestinations() {
        return new ArrayList<>(destinations);
    }
    
    public void setDestinations(ArrayList<String> destinations) {
        this.destinations = new ArrayList<>(destinations);
    }
    
    public void addDestination(String destination) {
        this.destinations.add(destination);
    }
    
    public String getTravelMode() {
        return travelMode;
    }
    
    public void setTravelMode(String travelMode) {
        this.travelMode = travelMode;
    }
    
    @Override
    public void computeRoute() {
        System.out.println("Computing route for tour plan");
        // Add route segments
        addRouteSegment("Start from home");
        
        for (String destination : destinations) {
            addRouteSegment("Travel to " + destination + " by " + travelMode);
            addRouteSegment("Explore " + destination);
        }
        
        addRouteSegment("Return home");
        
        // Set estimated distance and time based on number of destinations and travel mode
        int numDestinations = destinations.size();
        switch (travelMode.toLowerCase()) {
            case "road":
                setEstimatedDistance(numDestinations * 300);
                setEstimatedTime(numDestinations * 6);
                break;
            case "rail":
                setEstimatedDistance(numDestinations * 300);
                setEstimatedTime(numDestinations * 4);
                break;
            case "air":
                setEstimatedDistance(numDestinations * 300);
                setEstimatedTime(numDestinations * 2);
                break;
            default:
                setEstimatedDistance(numDestinations * 300);
                setEstimatedTime(numDestinations * 6);
                break;
        }
    }
    
    @Override
    public void optimizeRoute() {
        System.out.println("Optimizing tour route for minimal distance");
        // Simple optimization - sort destinations alphabetically
        destinations.sort(String::compareTo);
    }
    
    @Override
    public void displayRoute() {
        System.out.println("=== Tour Plan Route ===");
        for (int i = 0; i < getRouteSegments().size(); i++) {
            System.out.println((i + 1) + ". " + getRouteSegments().get(i));
        }
        System.out.println("Destinations: " + destinations);
        System.out.println("Estimated Distance: " + getEstimatedDistance() + " km");
        System.out.println("Estimated Time: " + getEstimatedTime() + " hours");
    }
    
    @Override
    public String toString() {
        return "TourPlanner{" +
                "destinations=" + destinations +
                ", travelMode='" + travelMode + '\'' +
                "} " + super.toString();
    }
}