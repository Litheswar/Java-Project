package com.smarttravelplanner.model;

import java.sql.Timestamp;
import java.util.UUID;

public class User {
    private UUID id;
    private String name;
    private int age;
    private int familyCount;
    private double budget;
    private String email;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    
    // Default constructor
    public User() {
    }
    
    // Constructor without ID (for creating new users)
    public User(String name, int age, int familyCount, double budget, String email) {
        this.name = name;
        this.age = age;
        this.familyCount = familyCount;
        this.budget = budget;
        this.email = email;
    }
    
    // Constructor with all fields (for reading from database)
    public User(UUID id, String name, int age, int familyCount, double budget, String email, 
               Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.familyCount = familyCount;
        this.budget = budget;
        this.email = email;
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
    
    public int getAge() {
        return age;
    }
    
    public void setAge(int age) {
        this.age = age;
    }
    
    public int getFamilyCount() {
        return familyCount;
    }
    
    public void setFamilyCount(int familyCount) {
        this.familyCount = familyCount;
    }
    
    public double getBudget() {
        return budget;
    }
    
    public void setBudget(double budget) {
        this.budget = budget;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
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
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", familyCount=" + familyCount +
                ", budget=" + budget +
                ", email='" + email + '\'' +
                '}';
    }
}