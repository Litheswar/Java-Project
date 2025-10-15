package com.smarttravelplanner.model;

import java.util.ArrayList;
import java.util.List;

public class TourPlan extends Planner {
    private List<Destination> destinations;
    private double totalCost;
    private int durationDays;
    private String tourType;
    private String planName;
    
    // Default constructor
    public TourPlan() {
        super("Tour Plan");
        this.destinations = new ArrayList<>();
        this.totalCost = 0.0;
        this.durationDays = 0;
        this.tourType = "";
        this.planName = "Tour Plan";
    }
    
    // Parameterized constructor
    public TourPlan(String planName, int durationDays, String tourType) {
        super(planName);
        this.destinations = new ArrayList<>();
        this.totalCost = 0.0;
        this.durationDays = durationDays;
        this.tourType = tourType;
        this.planName = planName;
    }
    
    // Getters and Setters
    public String getPlanName() {
        return planName;
    }
    
    public void setPlanName(String planName) {
        this.planName = planName;
    }
    
    public List<Destination> getDestinations() {
        return new ArrayList<>(destinations);
    }
    
    public void setDestinations(List<Destination> destinations) {
        this.destinations = new ArrayList<>(destinations);
    }
    
    public void addDestination(Destination destination) {
        this.destinations.add(destination);
    }
    
    public void removeDestination(int id) {
        this.destinations.removeIf(dest -> dest.getId() == id);
    }
    
    public double getTotalCost() {
        return totalCost;
    }
    
    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }
    
    public int getDurationDays() {
        return durationDays;
    }
    
    public void setDurationDays(int durationDays) {
        this.durationDays = durationDays;
    }
    
    public String getTourType() {
        return tourType;
    }
    
    public void setTourType(String tourType) {
        this.tourType = tourType;
    }
    
    public double calculateCost() {
        double cost = 0.0;
        for (Destination dest : destinations) {
            // Base cost with 30% markup for tours
            cost += dest.getBaseCost() * 1.3;
        }
        // Add daily cost: ₹100 per day
        cost += 100 * durationDays;
        this.totalCost = cost;
        return cost;
    }
    
    @Override
    public void computeRoute() {
        // Implementation for computing route for tour plan
        System.out.println("Computing route for tour plan");
    }
    
    @Override
    public void optimizeRoute() {
        // Implementation for optimizing route for tour plan
        System.out.println("Optimizing route for tour plan");
    }
    
    @Override
    public void displayRoute() {
        // Implementation for displaying route for tour plan
        System.out.println("Displaying route for tour plan");
    }
}