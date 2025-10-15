package com.smarttravelplanner.model;

import java.sql.Timestamp;

public class State {
    private int id;
    private int countryId;
    private String name;
    private double baseBudget;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    
    // For backward compatibility
    private String description;
    private double averageCost;
    
    public State() {}
    
    public State(int countryId, String name, double baseBudget) {
        this.countryId = countryId;
        this.name = name;
        this.baseBudget = baseBudget;
    }
    
    public State(int id, int countryId, String name, double baseBudget, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.countryId = countryId;
        this.name = name;
        this.baseBudget = baseBudget;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    
    // Constructor for backward compatibility
    public State(String name, String description, double averageCost) {
        this.name = name;
        this.description = description;
        this.averageCost = averageCost;
    }
    
    // Getters and Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getCountryId() {
        return countryId;
    }
    
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public double getBaseBudget() {
        return baseBudget;
    }
    
    public void setBaseBudget(double baseBudget) {
        this.baseBudget = baseBudget;
    }
    
    public Timestamp getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
    
    public Timestamp getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    // Backward compatibility methods
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
        return "State{" + 
                "id=" + id + 
                ", countryId=" + countryId + 
                ", name='" + name + '\'' + 
                ", baseBudget=" + baseBudget + 
                '}';
    }
}