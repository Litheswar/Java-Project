package com.smarttravelplanner.model;

public class User {
    private String name;
    private String email;
    private double budget;
    private int familySize;
    private String travelMode;
    
    // Default constructor
    public User() {
    }
    
    // Parameterized constructor
    public User(String name, String email, double budget, int familySize, String travelMode) {
        this.name = name;
        this.email = email;
        this.budget = budget;
        this.familySize = familySize;
        this.travelMode = travelMode;
    }
    
    // Getters and Setters
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public double getBudget() {
        return budget;
    }
    
    public void setBudget(double budget) {
        this.budget = budget;
    }
    
    public int getFamilySize() {
        return familySize;
    }
    
    public void setFamilySize(int familySize) {
        this.familySize = familySize;
    }
    
    public String getTravelMode() {
        return travelMode;
    }
    
    public void setTravelMode(String travelMode) {
        this.travelMode = travelMode;
    }
    
    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", budget=" + budget +
                ", familySize=" + familySize +
                ", travelMode='" + travelMode + '\'' +
                '}';
    }
}