package com.smarttravelplanner.model;

import java.sql.Timestamp;

public class ExpenseBreakdown {
    private int id;
    private int tripId;
    private double travelCost;
    private double foodCost;
    private double stayCost;
    private double shoppingCost;
    private double entertainmentCost;
    private double localCommuteCost;
    private double totalCost;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    
    // Default constructor
    public ExpenseBreakdown() {
    }
    
    // Constructor without ID (for creating new expense breakdowns)
    public ExpenseBreakdown(int tripId, double travelCost, double foodCost, double stayCost, 
                           double shoppingCost, double entertainmentCost, double localCommuteCost, double totalCost) {
        this.tripId = tripId;
        this.travelCost = travelCost;
        this.foodCost = foodCost;
        this.stayCost = stayCost;
        this.shoppingCost = shoppingCost;
        this.entertainmentCost = entertainmentCost;
        this.localCommuteCost = localCommuteCost;
        this.totalCost = totalCost;
    }
    
    // Constructor with all fields (for reading from database)
    public ExpenseBreakdown(int id, int tripId, double travelCost, double foodCost, double stayCost, 
                           double shoppingCost, double entertainmentCost, double localCommuteCost, double totalCost,
                           Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.tripId = tripId;
        this.travelCost = travelCost;
        this.foodCost = foodCost;
        this.stayCost = stayCost;
        this.shoppingCost = shoppingCost;
        this.entertainmentCost = entertainmentCost;
        this.localCommuteCost = localCommuteCost;
        this.totalCost = totalCost;
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
    
    public int getTripId() {
        return tripId;
    }
    
    public void setTripId(int tripId) {
        this.tripId = tripId;
    }
    
    public double getTravelCost() {
        return travelCost;
    }
    
    public void setTravelCost(double travelCost) {
        this.travelCost = travelCost;
    }
    
    public double getFoodCost() {
        return foodCost;
    }
    
    public void setFoodCost(double foodCost) {
        this.foodCost = foodCost;
    }
    
    public double getStayCost() {
        return stayCost;
    }
    
    public void setStayCost(double stayCost) {
        this.stayCost = stayCost;
    }
    
    public double getShoppingCost() {
        return shoppingCost;
    }
    
    public void setShoppingCost(double shoppingCost) {
        this.shoppingCost = shoppingCost;
    }
    
    public double getEntertainmentCost() {
        return entertainmentCost;
    }
    
    public void setEntertainmentCost(double entertainmentCost) {
        this.entertainmentCost = entertainmentCost;
    }
    
    public double getLocalCommuteCost() {
        return localCommuteCost;
    }
    
    public void setLocalCommuteCost(double localCommuteCost) {
        this.localCommuteCost = localCommuteCost;
    }
    
    public double getTotalCost() {
        return totalCost;
    }
    
    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
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
        return "ExpenseBreakdown{" +
                "id=" + id +
                ", tripId=" + tripId +
                ", travelCost=" + travelCost +
                ", foodCost=" + foodCost +
                ", stayCost=" + stayCost +
                ", shoppingCost=" + shoppingCost +
                ", entertainmentCost=" + entertainmentCost +
                ", localCommuteCost=" + localCommuteCost +
                ", totalCost=" + totalCost +
                '}';
    }
}