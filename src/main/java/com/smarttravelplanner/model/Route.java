package com.smarttravelplanner.model;

import java.sql.Timestamp;

public class Route {
    private int id;
    private int tripId;
    private String origin;
    private String destination;
    private String modeOfTransport;
    private double distance;
    private double estimatedTime;
    private double cost;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    // Default constructor
    public Route() {}
    
    // Constructor without ID (for creating new routes)
    public Route(int tripId, String origin, String destination, String modeOfTransport, double distance, double estimatedTime, double cost) {
        this.tripId = tripId;
        this.origin = origin;
        this.destination = destination;
        this.modeOfTransport = modeOfTransport;
        this.distance = distance;
        this.estimatedTime = estimatedTime;
        this.cost = cost;
    }
    
    // Constructor with all fields (for reading from database)
    public Route(int id, int tripId, String origin, String destination, String modeOfTransport, double distance, double estimatedTime, double cost, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.tripId = tripId;
        this.origin = origin;
        this.destination = destination;
        this.modeOfTransport = modeOfTransport;
        this.distance = distance;
        this.estimatedTime = estimatedTime;
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
    
    public int getTripId() {
        return tripId;
    }
    
    public void setTripId(int tripId) {
        this.tripId = tripId;
    }
    
    public String getOrigin() {
        return origin;
    }
    
    public void setOrigin(String origin) {
        this.origin = origin;
    }
    
    public String getDestination() {
        return destination;
    }
    
    public void setDestination(String destination) {
        this.destination = destination;
    }
    
    public String getModeOfTransport() {
        return modeOfTransport;
    }
    
    public void setModeOfTransport(String modeOfTransport) {
        this.modeOfTransport = modeOfTransport;
    }
    
    public double getDistance() {
        return distance;
    }
    
    public void setDistance(double distance) {
        this.distance = distance;
    }
    
    public double getEstimatedTime() {
        return estimatedTime;
    }
    
    public void setEstimatedTime(double estimatedTime) {
        this.estimatedTime = estimatedTime;
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
        return "Route{" +
                "id=" + id +
                ", tripId=" + tripId +
                ", origin='" + origin + '\'' +
                ", destination='" + destination + '\'' +
                ", modeOfTransport='" + modeOfTransport + '\'' +
                ", distance=" + distance +
                ", estimatedTime=" + estimatedTime +
                ", cost=" + cost +
                '}';
    }
}