package com.smarttravelplanner.model;

import java.sql.Timestamp;

public class Destination {
    private int id;
    private int stateId;
    private String name;
    private double baseCost;
    private Integer sustainabilityScore; // Can be null
    private Double estimatedCo2Footprint; // Can be null
    private Timestamp createdAt;
    private Timestamp updatedAt;
    
    // For backward compatibility with existing code
    private String country;
    private String state;
    private String city;
    
    // Default constructor
    public Destination() {}
    
    // Constructor without ID (for creating new destinations)
    public Destination(int stateId, String name, double baseCost, Integer sustainabilityScore, Double estimatedCo2Footprint) {
        this.stateId = stateId;
        this.name = name;
        this.baseCost = baseCost;
        this.sustainabilityScore = sustainabilityScore;
        this.estimatedCo2Footprint = estimatedCo2Footprint;
    }
    
    // Constructor with all fields (for reading from database)
    public Destination(int id, int stateId, String name, double baseCost, Integer sustainabilityScore, 
                      Double estimatedCo2Footprint, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.stateId = stateId;
        this.name = name;
        this.baseCost = baseCost;
        this.sustainabilityScore = sustainabilityScore;
        this.estimatedCo2Footprint = estimatedCo2Footprint;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    
    // Constructor for backward compatibility
    public Destination(int id, String country, String state, String city, double baseCost) {
        this.id = id;
        this.country = country;
        this.state = state;
        this.city = city;
        this.baseCost = baseCost;
    }
    
    // Getters and Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getStateId() {
        return stateId;
    }
    
    public void setStateId(int stateId) {
        this.stateId = stateId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public double getBaseCost() {
        return baseCost;
    }
    
    public void setBaseCost(double baseCost) {
        this.baseCost = baseCost;
    }
    
    public Integer getSustainabilityScore() {
        return sustainabilityScore;
    }
    
    public void setSustainabilityScore(Integer sustainabilityScore) {
        this.sustainabilityScore = sustainabilityScore;
    }
    
    public Double getEstimatedCo2Footprint() {
        return estimatedCo2Footprint;
    }
    
    public void setEstimatedCo2Footprint(Double estimatedCo2Footprint) {
        this.estimatedCo2Footprint = estimatedCo2Footprint;
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
    public String getCountry() {
        return country;
    }
    
    public void setCountry(String country) {
        this.country = country;
    }
    
    public String getState() {
        return state;
    }
    
    public void setState(String state) {
        this.state = state;
    }
    
    public String getCity() {
        return city;
    }
    
    public void setCity(String city) {
        this.city = city;
    }
    
    @Override
    public String toString() {
        return "Destination{" +
                "id=" + id +
                ", stateId=" + stateId +
                ", name='" + name + '\'' +
                ", baseCost=" + baseCost +
                ", sustainabilityScore=" + sustainabilityScore +
                ", estimatedCo2Footprint=" + estimatedCo2Footprint +
                '}';
    }
}