package com.smarttravelplanner.model;

import java.sql.Timestamp;
import java.util.UUID;

public class Trip {
    private int id;
    private UUID userId;
    private int destinationId;
    private String tripType;
    private String travelMode;
    private String stayType;
    private String mealType;
    private int tripDays;
    private int mealsPerDay;
    private double totalEstimatedCost;
    private double totalBudget;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    
    // Default constructor
    public Trip() {
    }
    
    // Constructor without ID (for creating new trips)
    public Trip(UUID userId, int destinationId, String tripType, String travelMode, String stayType, 
               String mealType, int tripDays, int mealsPerDay, double totalEstimatedCost, double totalBudget) {
        this.userId = userId;
        this.destinationId = destinationId;
        this.tripType = tripType;
        this.travelMode = travelMode;
        this.stayType = stayType;
        this.mealType = mealType;
        this.tripDays = tripDays;
        this.mealsPerDay = mealsPerDay;
        this.totalEstimatedCost = totalEstimatedCost;
        this.totalBudget = totalBudget;
    }
    
    // Constructor with all fields (for reading from database)
    public Trip(int id, UUID userId, int destinationId, String tripType, String travelMode, String stayType, 
               String mealType, int tripDays, int mealsPerDay, double totalEstimatedCost, double totalBudget,
               Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.userId = userId;
        this.destinationId = destinationId;
        this.tripType = tripType;
        this.travelMode = travelMode;
        this.stayType = stayType;
        this.mealType = mealType;
        this.tripDays = tripDays;
        this.mealsPerDay = mealsPerDay;
        this.totalEstimatedCost = totalEstimatedCost;
        this.totalBudget = totalBudget;
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
    
    public UUID getUserId() {
        return userId;
    }
    
    public void setUserId(UUID userId) {
        this.userId = userId;
    }
    
    public int getDestinationId() {
        return destinationId;
    }
    
    public void setDestinationId(int destinationId) {
        this.destinationId = destinationId;
    }
    
    public String getTripType() {
        return tripType;
    }
    
    public void setTripType(String tripType) {
        this.tripType = tripType;
    }
    
    public String getTravelMode() {
        return travelMode;
    }
    
    public void setTravelMode(String travelMode) {
        this.travelMode = travelMode;
    }
    
    public String getStayType() {
        return stayType;
    }
    
    public void setStayType(String stayType) {
        this.stayType = stayType;
    }
    
    public String getMealType() {
        return mealType;
    }
    
    public void setMealType(String mealType) {
        this.mealType = mealType;
    }
    
    public int getTripDays() {
        return tripDays;
    }
    
    public void setTripDays(int tripDays) {
        this.tripDays = tripDays;
    }
    
    public int getMealsPerDay() {
        return mealsPerDay;
    }
    
    public void setMealsPerDay(int mealsPerDay) {
        this.mealsPerDay = mealsPerDay;
    }
    
    public double getTotalEstimatedCost() {
        return totalEstimatedCost;
    }
    
    public void setTotalEstimatedCost(double totalEstimatedCost) {
        this.totalEstimatedCost = totalEstimatedCost;
    }
    
    public double getTotalBudget() {
        return totalBudget;
    }
    
    public void setTotalBudget(double totalBudget) {
        this.totalBudget = totalBudget;
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
        return "Trip{" +
                "id=" + id +
                ", userId=" + userId +
                ", destinationId=" + destinationId +
                ", tripType='" + tripType + '\'' +
                ", travelMode='" + travelMode + '\'' +
                ", stayType='" + stayType + '\'' +
                ", mealType='" + mealType + '\'' +
                ", tripDays=" + tripDays +
                ", mealsPerDay=" + mealsPerDay +
                ", totalEstimatedCost=" + totalEstimatedCost +
                ", totalBudget=" + totalBudget +
                '}';
    }
}