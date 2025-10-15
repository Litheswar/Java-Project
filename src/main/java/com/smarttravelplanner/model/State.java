package com.smarttravelplanner.model;

public class State {
    private String name;
    private String description;
    private double averageCost;
    
    public State() {}
    
    public State(String name, String description, double averageCost) {
        this.name = name;
        this.description = description;
        this.averageCost = averageCost;
    }
    
    // Getters and Setters
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public double getAverageCost() {
        return averageCost;
    }
    
    public void setAverageCost(double averageCost) {
        this.averageCost = averageCost;
    }
    
    @Override
    public String toString() {
        return "State{name='" + name + "', description='" + description + "', averageCost=" + averageCost + "}";
    }
}