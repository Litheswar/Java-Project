package com.smarttravelplanner.utils;

import com.smarttravelplanner.model.Traveler;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FilePersistence {
    private static final String TRIPS_FILE = "data/trips.txt";
    private static final String LOCATIONS_FILE = "data/locations.csv";
    
    /**
     * Saves trip summary to file
     * @param traveler The traveler information
     * @param country The country
     * @param destination The destination
     * @param budget The budget
     * @param cost The estimated cost
     * @return true if successful, false otherwise
     */
    public boolean saveTripSummary(Traveler traveler, String country, String destination, 
                                  double budget, double cost) {
        try {
            // Create data directory if it doesn't exist
            File dataDir = new File("data");
            if (!dataDir.exists()) {
                dataDir.mkdirs();
            }
            
            // Write to trips file
            FileWriter writer = new FileWriter(TRIPS_FILE, true);
            writer.write("Traveler: " + traveler.getName() + 
                        " | Country: " + country + 
                        " | Destination: " + destination + 
                        " | Budget: " + budget + 
                        " | Cost: " + cost + "\n");
            writer.close();
            
            return true;
        } catch (IOException e) {
            System.err.println("Error saving trip summary: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Loads destinations from CSV file
     * @return List of destination data
     */
    public List<String[]> loadDestinations() {
        List<String[]> destinations = new ArrayList<>();
        
        try {
            // Create data directory if it doesn't exist
            File dataDir = new File("data");
            if (!dataDir.exists()) {
                dataDir.mkdirs();
            }
            
            // Create locations file with sample data if it doesn't exist
            File locationsFile = new File(LOCATIONS_FILE);
            if (!locationsFile.exists()) {
                createSampleLocationsFile();
            }
            
            // Read from locations file
            BufferedReader reader = new BufferedReader(new FileReader(LOCATIONS_FILE));
            String line;
            
            // Skip header line
            reader.readLine();
            
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 4) {
                    destinations.add(data);
                }
            }
            
            reader.close();
        } catch (IOException e) {
            System.err.println("Error loading destinations: " + e.getMessage());
        }
        
        return destinations;
    }
    
    /**
     * Creates a sample locations file with default data
     * @throws IOException if file operations fail
     */
    private void createSampleLocationsFile() throws IOException {
        FileWriter writer = new FileWriter(LOCATIONS_FILE);
        writer.write("Country,State,City,BaseCost\n");
        writer.write("France,Hauts-de-France,Lille,5000.00\n");
        writer.write("Japan,Kyoto,Gion,4800.00\n");
        writer.write("India,Kerala,Munnar,3500.00\n");
        writer.write("Italy,Tuscany,Florence,5500.00\n");
        writer.write("Australia,Queensland,Cairns,6200.00\n");
        writer.close();
    }
    
    /**
     * Gets all available countries from the locations file
     * @return List of countries
     */
    public List<String> getAvailableCountries() {
        List<String> countries = new ArrayList<>();
        List<String[]> destinations = loadDestinations();
        
        for (String[] destination : destinations) {
            String country = destination[0];
            if (!countries.contains(country)) {
                countries.add(country);
            }
        }
        
        return countries;
    }
    
    /**
     * Gets all available states for a specific country
     * @param country The country
     * @return List of states
     */
    public List<String> getAvailableStates(String country) {
        List<String> states = new ArrayList<>();
        List<String[]> destinations = loadDestinations();
        
        for (String[] destination : destinations) {
            if (destination[0].equalsIgnoreCase(country)) {
                String state = destination[1];
                if (!states.contains(state)) {
                    states.add(state);
                }
            }
        }
        
        return states;
    }
    
    /**
     * Gets all available destinations for a specific state
     * @param state The state
     * @return List of destinations
     */
    public List<String> getAvailableDestinations(String state) {
        List<String> destinationsList = new ArrayList<>();
        List<String[]> destinations = loadDestinations();
        
        for (String[] destination : destinations) {
            if (destination[1].equalsIgnoreCase(state)) {
                destinationsList.add(destination[2]);
            }
        }
        
        return destinationsList;
    }
}