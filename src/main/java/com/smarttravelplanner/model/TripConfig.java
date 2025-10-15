package com.smarttravelplanner.model;

import java.util.ArrayList;

public abstract class TripConfig {
    protected int tripDays;
    protected int mealsPerDay;
    protected double totalBudget;
    protected String country;
    protected String state;
    protected String destination;
    protected ArrayList<String> availableCountries;
    protected ArrayList<String> availableStates;
    protected ArrayList<String> availableDestinations;
    
    // Default constructor
    public TripConfig() {
        this.availableCountries = new ArrayList<>();
        this.availableStates = new ArrayList<>();
        this.availableDestinations = new ArrayList<>();
        initializeStaticData();
    }
    
    // Parameterized constructor
    public TripConfig(int tripDays, int mealsPerDay, double totalBudget) {
        this.tripDays = tripDays;
        this.mealsPerDay = mealsPerDay;
        this.totalBudget = totalBudget;
        this.availableCountries = new ArrayList<>();
        this.availableStates = new ArrayList<>();
        this.availableDestinations = new ArrayList<>();
        initializeStaticData();
    }
    
    // Initialize static data
    private void initializeStaticData() {
        // Add sample countries
        availableCountries.add("France");
        availableCountries.add("Japan");
        availableCountries.add("India");
        availableCountries.add("Italy");
        availableCountries.add("Australia");
        
        // Add sample states
        availableStates.add("Hauts-de-France");
        availableStates.add("Kyoto");
        availableStates.add("Kerala");
        availableStates.add("Tuscany");
        availableStates.add("Queensland");
        
        // Add sample destinations
        availableDestinations.add("Lille");
        availableDestinations.add("Gion");
        availableDestinations.add("Munnar");
        availableDestinations.add("Florence");
        availableDestinations.add("Cairns");
    }
    
    // Getters and Setters with validation
    public int getTripDays() {
        return tripDays;
    }
    
    public void setTripDays(int tripDays) {
        if (tripDays < 1 || tripDays > 50) {
            throw new IllegalArgumentException("Trip days must be between 1 and 50");
        }
        this.tripDays = tripDays;
    }
    
    public int getMealsPerDay() {
        return mealsPerDay;
    }
    
    public void setMealsPerDay(int mealsPerDay) {
        if (mealsPerDay < 1 || mealsPerDay > 5) {
            throw new IllegalArgumentException("Meals per day must be between 1 and 5");
        }
        this.mealsPerDay = mealsPerDay;
    }
    
    public double getTotalBudget() {
        return totalBudget;
    }
    
    public void setTotalBudget(double totalBudget) {
        this.totalBudget = totalBudget;
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
    
    public String getDestination() {
        return destination;
    }
    
    public void setDestination(String destination) {
        this.destination = destination;
    }
    
    public ArrayList<String> getAvailableCountries() {
        return new ArrayList<>(availableCountries);
    }
    
    public ArrayList<String> getAvailableStates() {
        return new ArrayList<>(availableStates);
    }
    
    public ArrayList<String> getAvailableDestinations() {
        return new ArrayList<>(availableDestinations);
    }
    
    // Abstract methods to be implemented by subclasses
    public abstract void configureTrip();
    public abstract void displayConfiguration();
    
    @Override
    public String toString() {
        return "TripConfig{" +
                "tripDays=" + tripDays +
                ", mealsPerDay=" + mealsPerDay +
                ", totalBudget=" + totalBudget +
                ", country='" + country + '\'' +
                ", state='" + state + '\'' +
                ", destination='" + destination + '\'' +
                '}';
    }
}