package com.smarttravelplanner.model;

public class DestinationSuggestion {
    private String name;
    private double estimatedCost;
    private double savings;
    private String bestMonth;
    
    // Default constructor
    public DestinationSuggestion() {
    }
    
    // Constructor with all fields
    public DestinationSuggestion(String name, double estimatedCost, double userBudget, String bestMonth) {
        this.name = name;
        this.estimatedCost = estimatedCost;
        this.savings = Math.max(0, userBudget - estimatedCost);
        this.bestMonth = bestMonth;
    }
    
    // Getters and Setters
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public double getEstimatedCost() {
        return estimatedCost;
    }
    
    public void setEstimatedCost(double estimatedCost) {
        this.estimatedCost = estimatedCost;
    }
    
    public double getSavings() {
        return savings;
    }
    
    public void setSavings(double savings) {
        this.savings = savings;
    }
    
    public String getBestMonth() {
        return bestMonth;
    }
    
    public void setBestMonth(String bestMonth) {
        this.bestMonth = bestMonth;
    }
    
    @Override
    public String toString() {
        return "DestinationSuggestion{" +
                "name='" + name + '\'' +
                ", estimatedCost=" + estimatedCost +
                ", savings=" + savings +
                ", bestMonth='" + bestMonth + '\'' +
                '}';
    }
}