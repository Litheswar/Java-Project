package com.smarttravelplanner.model;

public class TripCostBreakdown {
    private double totalCost;
    private double accommodationCost;
    private double foodCost;
    private double transportationCost;
    private double activitiesCost;
    
    // Default constructor
    public TripCostBreakdown() {
    }
    
    // Constructor with all fields
    public TripCostBreakdown(double totalCost, double accommodationCost, double foodCost, 
                           double transportationCost, double activitiesCost) {
        this.totalCost = totalCost;
        this.accommodationCost = accommodationCost;
        this.foodCost = foodCost;
        this.transportationCost = transportationCost;
        this.activitiesCost = activitiesCost;
    }
    
    // Getters and Setters
    public double getTotalCost() {
        return totalCost;
    }
    
    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }
    
    public double getAccommodationCost() {
        return accommodationCost;
    }
    
    public void setAccommodationCost(double accommodationCost) {
        this.accommodationCost = accommodationCost;
    }
    
    public double getFoodCost() {
        return foodCost;
    }
    
    public void setFoodCost(double foodCost) {
        this.foodCost = foodCost;
    }
    
    public double getTransportationCost() {
        return transportationCost;
    }
    
    public void setTransportationCost(double transportationCost) {
        this.transportationCost = transportationCost;
    }
    
    public double getActivitiesCost() {
        return activitiesCost;
    }
    
    public void setActivitiesCost(double activitiesCost) {
        this.activitiesCost = activitiesCost;
    }
    
    @Override
    public String toString() {
        return "TripCostBreakdown{" +
                "totalCost=" + totalCost +
                ", accommodationCost=" + accommodationCost +
                ", foodCost=" + foodCost +
                ", transportationCost=" + transportationCost +
                ", activitiesCost=" + activitiesCost +
                '}';
    }
}