package com.smarttravelplanner.model;

import java.sql.Timestamp;
import java.util.UUID;

public class TripHistory {
    private int id;
    private UUID userId;
    private int tripId;
    private Timestamp timestamp;
    private String status;
    
    // Default constructor
    public TripHistory() {
    }
    
    // Constructor without ID (for creating new trip history entries)
    public TripHistory(UUID userId, int tripId, String status) {
        this.userId = userId;
        this.tripId = tripId;
        this.status = status;
        this.timestamp = new Timestamp(System.currentTimeMillis());
    }
    
    // Constructor with all fields (for reading from database)
    public TripHistory(int id, UUID userId, int tripId, Timestamp timestamp, String status) {
        this.id = id;
        this.userId = userId;
        this.tripId = tripId;
        this.timestamp = timestamp;
        this.status = status;
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
    
    public int getTripId() {
        return tripId;
    }
    
    public void setTripId(int tripId) {
        this.tripId = tripId;
    }
    
    public Timestamp getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    @Override
    public String toString() {
        return "TripHistory{" +
                "id=" + id +
                ", userId=" + userId +
                ", tripId=" + tripId +
                ", timestamp=" + timestamp +
                ", status='" + status + '\'' +
                '}';
    }
}