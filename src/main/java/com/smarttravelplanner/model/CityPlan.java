package com.smarttravelplanner.model;

import java.util.ArrayList;
import java.util.List;

public class CityPlan extends Planner {
    private List<Destination> destinations;
    private double totalCost;
    private String planName;
    
    // Default constructor
    public CityPlan() {
        super("City Plan");
        this.destinations = new ArrayList<>();
        this.totalCost = 0.0;
        this.planName = "City Plan";
    }
    
    // Parameterized constructor
    public CityPlan(String planName) {
        super(planName);
        this.destinations = new ArrayList<>();
        this.totalCost = 0.0;
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
    
    public double calculateCost() {
        double cost = 0.0;
        for (Destination dest : destinations) {
            cost += dest.getBaseCost();
        }
        this.totalCost = cost;
        return cost;
    }
    
    @Override
    public void computeRoute() {
        // Implementation for computing route for city plan
        System.out.println("Computing route for city plan");
    }
    
    @Override
    public void optimizeRoute() {
        // Implementation for optimizing route for city plan
        System.out.println("Optimizing route for city plan");
    }
    
    @Override
    public void displayRoute() {
        // Implementation for displaying route for city plan
        System.out.println("Displaying route for city plan");
    }
}