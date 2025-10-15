package com.smarttravelplanner.model;

import java.util.ArrayList;

public abstract class Planner {
    protected String planType;
    protected ArrayList<String> routeSegments;
    protected double estimatedDistance;
    protected double estimatedTime;
    
    // Default constructor
    public Planner() {
        this.routeSegments = new ArrayList<>();
    }
    
    // Parameterized constructor
    public Planner(String planType) {
        this.planType = planType;
        this.routeSegments = new ArrayList<>();
    }
    
    // Getters and Setters
    public String getPlanType() {
        return planType;
    }
    
    public void setPlanType(String planType) {
        this.planType = planType;
    }
    
    public ArrayList<String> getRouteSegments() {
        return new ArrayList<>(routeSegments);
    }
    
    public void addRouteSegment(String segment) {
        this.routeSegments.add(segment);
    }
    
    public double getEstimatedDistance() {
        return estimatedDistance;
    }
    
    public void setEstimatedDistance(double estimatedDistance) {
        this.estimatedDistance = estimatedDistance;
    }
    
    public double getEstimatedTime() {
        return estimatedTime;
    }
    
    public void setEstimatedTime(double estimatedTime) {
        this.estimatedTime = estimatedTime;
    }
    
    // Abstract methods to be implemented by subclasses
    public abstract void computeRoute();
    public abstract void optimizeRoute();
    public abstract void displayRoute();
    
    @Override
    public String toString() {
        return "Planner{" +
                "planType='" + planType + '\'' +
                ", routeSegments=" + routeSegments +
                ", estimatedDistance=" + estimatedDistance +
                ", estimatedTime=" + estimatedTime +
                '}';
    }
}