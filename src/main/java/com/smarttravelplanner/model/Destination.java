package com.smarttravelplanner.model;

public class Destination {
    private int id;
    private String country;
    private String state;
    private String city;
    private double baseCost;
    
    // Default constructor
    public Destination() {}
    
    // Parameterized constructor
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
    
    public double getBaseCost() {
        return baseCost;
    }
    
    public void setBaseCost(double baseCost) {
        this.baseCost = baseCost;
    }
    
    @Override
    public String toString() {
        return "Destination{" +
                "id=" + id +
                ", country='" + country + '\'' +
                ", state='" + state + '\'' +
                ", city='" + city + '\'' +
                ", baseCost=" + baseCost +
                '}';
    }
}