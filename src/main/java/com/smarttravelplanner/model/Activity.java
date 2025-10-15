package com.smarttravelplanner.model;

import java.sql.Timestamp;

public class Activity {
    private int id;
    private int destinationId;
    private String name;
    private String description;
    private double cost;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    
    // Default constructor
    public Activity() {}
    
    // Constructor without ID (for creating new activities)
    public Activity(int destinationId, String name, String description, double cost) {
        this.destinationId = destinationId;
        this.name = name;
        this.description = description;
        this.cost = cost;
    }
    
    // Constructor with all fields (for reading from database)
    public Activity(int id, int destinationId, String name, String description, double cost, 
                   Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.destinationId = destinationId;
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    
    // Getters and Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getDestinationId() {
        return destinationId;
    }
    
    public void setDestinationId(int destinationId) {
        this.destinationId = destinationId;
    }
    
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
    
    public double getCost() {
        return cost;
    }
    
    public void setCost(double cost) {
        this.cost = cost;
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
    
    @Override
    public String toString() {
        return "Activity{" +
                "id=" + id +
                ", destinationId=" + destinationId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", cost=" + cost +
                '}';
    }
}