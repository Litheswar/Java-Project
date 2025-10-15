package com.smarttravelplanner.model;

import java.util.ArrayList;
import java.util.List;

public class Country {
    private String name;
    private List<State> states;
    
    public Country() {
        this.states = new ArrayList<>();
    }
    
    public Country(String name) {
        this.name = name;
        this.states = new ArrayList<>();
    }
    
    // Getters and Setters
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public List<State> getStates() {
        return new ArrayList<>(states);
    }
    
    public void addState(State state) {
        this.states.add(state);
    }
    
    public void setStates(List<State> states) {
        this.states = new ArrayList<>(states);
    }
    
    @Override
    public String toString() {
        return "Country{name='" + name + '\'' + ", states=" + states.size() + " states}";
    }
}