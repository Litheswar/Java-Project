package com.smarttravelplanner.model;

import java.sql.Timestamp;
import java.util.UUID;

public class Country {
    private UUID id;
    private String name;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    
    public Country() {
    }
    
    public Country(String name) {
        this.name = name;
    }
    
    public Country(UUID id, String name, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    
    // Getters and Setters
    public UUID getId() {
        return id;
    }
    
    public void setId(UUID id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
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
        return "Country{id=" + id + ", name='" + name + '\'' + '}';
    }
}