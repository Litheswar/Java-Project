package com.smarttravelplanner.model;

import java.sql.Timestamp;

public class Expense {
    private int id;
    private int tripId;
    private String category;
    private String description;
    private double amount;
    private Timestamp expenseDate;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    
    // Default constructor
    public Expense() {}
    
    // Constructor without ID (for creating new expenses)
    public Expense(int tripId, String category, String description, double amount, Timestamp expenseDate) {
        this.tripId = tripId;
        this.category = category;
        this.description = description;
        this.amount = amount;
        this.expenseDate = expenseDate;
    }
    
    // Constructor with all fields (for reading from database)
    public Expense(int id, int tripId, String category, String description, double amount, Timestamp expenseDate, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.tripId = tripId;
        this.category = category;
        this.description = description;
        this.amount = amount;
        this.expenseDate = expenseDate;
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
    
    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public double getAmount() {
        return amount;
    }
    
    public void setAmount(double amount) {
        this.amount = amount;
    }
    
    public Timestamp getExpenseDate() {
        return expenseDate;
    }
    
    public void setExpenseDate(Timestamp expenseDate) {
        this.expenseDate = expenseDate;
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
        return "Expense{" +
                "id=" + id +
                ", tripId=" + tripId +
                ", category='" + category + '\'' +
                ", description='" + description + '\'' +
                ", amount=" + amount +
                ", expenseDate=" + expenseDate +
                '}';
    }
}