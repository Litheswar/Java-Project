package com.smarttravelplanner.model;

import java.sql.Timestamp;

public class Alert {
    private int id;
    private int userId;
    private String message;
    private String severity;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    
    // Default constructor
    public Alert() {}
    
    // Constructor without ID (for creating new alerts)
    public Alert(int userId, String message, String severity) {
        this.userId = userId;
        this.message = message;
        this.severity = severity;
    }
    
    // Constructor with all fields (for reading from database)
    public Alert(int id, int userId, String message, String severity, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.userId = userId;
        this.message = message;
        this.severity = severity;
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
    
    public int getUserId() {
        return userId;
    }
    
    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public String getSeverity() {
        return severity;
    }
    
    public void setSeverity(String severity) {
        this.severity = severity;
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
        return "Alert{" +
                "id=" + id +
                ", userId=" + userId +
                ", message='" + message + '\'' +
                ", severity='" + severity + '\'' +
                '}';
    }
}