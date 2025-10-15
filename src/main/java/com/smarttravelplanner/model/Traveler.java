package com.smarttravelplanner.model;

import com.smarttravelplanner.exceptions.InvalidAgeException;
import com.smarttravelplanner.exceptions.InvalidFamilyCountException;
import com.smarttravelplanner.exceptions.InvalidInputException;

public class Traveler {
    private String name;
    private int age;
    private int familyCount;
    private double budget;
    
    // Default constructor
    public Traveler() {}
    
    // Parameterized constructor
    public Traveler(String name, int age, int familyCount, double budget) 
            throws InvalidAgeException, InvalidFamilyCountException, InvalidInputException {
        setName(name);
        setAge(age);
        setFamilyCount(familyCount);
        setBudget(budget);
    }
    
    // Getters and Setters with validation
    public String getName() {
        return name;
    }
    
    public void setName(String name) throws InvalidInputException {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidInputException("Name cannot be empty");
        }
        this.name = name.trim();
    }
    
    public int getAge() {
        return age;
    }
    
    public void setAge(int age) throws InvalidAgeException {
        if (age < 10 || age > 100) {
            throw new InvalidAgeException("Age must be between 10 and 100");
        }
        this.age = age;
    }
    
    public int getFamilyCount() {
        return familyCount;
    }
    
    public void setFamilyCount(int familyCount) throws InvalidFamilyCountException {
        if (familyCount < 1 || familyCount > 10) {
            throw new InvalidFamilyCountException("Family count must be between 1 and 10");
        }
        this.familyCount = familyCount;
    }
    
    public double getBudget() {
        return budget;
    }
    
    public void setBudget(double budget) throws InvalidInputException {
        if (budget < 5000 || budget > 10000000) {
            throw new InvalidInputException("Budget must be between ₹5,000 and ₹1,00,00,000");
        }
        this.budget = budget;
    }
    
    @Override
    public String toString() {
        return "Traveler{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", familyCount=" + familyCount +
                ", budget=" + budget +
                '}';
    }
}