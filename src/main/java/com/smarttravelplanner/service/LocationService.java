package com.smarttravelplanner.service;

import com.smarttravelplanner.model.Destination;
import com.smarttravelplanner.utils.FileHandler;

import java.util.ArrayList;
import java.util.List;

public class LocationService {
    private List<Destination> destinations;
    private FileHandler fileHandler;
    
    public LocationService() {
        this.destinations = new ArrayList<>();
        this.fileHandler = new FileHandler();
        loadDestinations();
    }
    
    /**
     * Loads destinations from file or creates sample data if file doesn't exist
     */
    private void loadDestinations() {
        // Try to load from file first
        List<Destination> loadedDestinations = fileHandler.loadDestinations();
        if (loadedDestinations != null && !loadedDestinations.isEmpty()) {
            this.destinations = loadedDestinations;
        } else {
            // Create sample data if file doesn't exist or is empty
            createSampleDestinations();
            // Save to file for future use
            fileHandler.saveDestinations(destinations);
        }
    }
    
    /**
     * Creates sample destinations for demonstration
     */
    private void createSampleDestinations() {
        destinations.add(new Destination(1, "France", "Hauts-de-France", "Lille", 5000.00));
        destinations.add(new Destination(2, "Japan", "Kyoto", "Gion", 4800.00));
        destinations.add(new Destination(3, "India", "Kerala", "Munnar", 3500.00));
        destinations.add(new Destination(4, "Italy", "Tuscany", "Florence", 5500.00));
        destinations.add(new Destination(5, "Australia", "Queensland", "Cairns", 6200.00));
    }
    
    /**
     * Gets all destinations
     * @return List of all destinations
     */
    public List<Destination> getAllDestinations() {
        return new ArrayList<>(destinations);
    }
    
    /**
     * Gets a destination by its ID
     * @param id The destination ID
     * @return Destination object or null if not found
     */
    public Destination getDestinationById(int id) {
        return destinations.stream()
                .filter(destination -> destination.getId() == id)
                .findFirst()
                .orElse(null);
    }
    
    /**
     * Gets destinations by country
     * @param country The country name
     * @return List of destinations in the specified country
     */
    public List<Destination> getDestinationsByCountry(String country) {
        List<Destination> result = new ArrayList<>();
        for (Destination destination : destinations) {
            if (destination.getCountry().equalsIgnoreCase(country)) {
                result.add(destination);
            }
        }
        return result;
    }
    
    /**
     * Adds a new destination
     * @param destination The destination to add
     */
    public void addDestination(Destination destination) {
        destinations.add(destination);
        fileHandler.saveDestinations(destinations);
    }
    
    /**
     * Removes a destination by ID
     * @param id The ID of the destination to remove
     * @return true if removed, false if not found
     */
    public boolean removeDestination(int id) {
        boolean removed = destinations.removeIf(destination -> destination.getId() == id);
        if (removed) {
            fileHandler.saveDestinations(destinations);
        }
        return removed;
    }
    
    /**
     * Gets all unique countries
     * @return List of country names
     */
    public List<String> getAllCountries() {
        List<String> countries = new ArrayList<>();
        for (Destination destination : destinations) {
            String country = destination.getCountry();
            if (!countries.contains(country)) {
                countries.add(country);
            }
        }
        return countries;
    }
    
    /**
     * Gets all states in a country
     * @param country The country name
     * @return List of state names
     */
    public List<String> getStatesInCountry(String country) {
        List<String> states = new ArrayList<>();
        for (Destination destination : destinations) {
            if (destination.getCountry().equalsIgnoreCase(country)) {
                String state = destination.getState();
                if (!states.contains(state)) {
                    states.add(state);
                }
            }
        }
        return states;
    }
}