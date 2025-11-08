package com.smarttravelplanner.service;

import com.smarttravelplanner.model.Traveler;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TripHistoryManager {
    private static final String TRIP_HISTORY_FILE = "data/trip_history.txt";
    
    public TripHistoryManager() {
        // Constructor intentionally left empty
    }
    
    /**
     * Saves a trip summary to the history file
     * @param traveler The traveler information
     * @param country The country
     * @param destination The destination
     * @param budget The budget
     * @param cost The estimated cost
     * @return true if successful, false otherwise
     */
    public boolean saveTripToHistory(Traveler traveler, String country, String destination, 
                                   double budget, double cost) {
        try {
            // Create data directory if it doesn't exist
            File dataDir = new File("data");
            if (!dataDir.exists()) {
                dataDir.mkdirs();
            }
            
            // Write to trip history file
            FileWriter writer = new FileWriter(TRIP_HISTORY_FILE, true);
            writer.write("Traveler: " + traveler.getName() + 
                        " | Country: " + country + 
                        " | Destination: " + destination + 
                        " | Budget: ₹" + String.format("%.0f", budget) + 
                        " | Cost: ₹" + String.format("%.0f", cost) + 
                        " | Date: " + java.time.LocalDateTime.now().toString() + "\n");
            writer.close();
            
            return true;
        } catch (IOException e) {
            System.err.println("Error saving trip to history: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Loads trip history from file
     * @return List of trip history entries
     */
    public List<String> loadTripHistory() {
        List<String> history = new ArrayList<>();
        
        try {
            File historyFile = new File(TRIP_HISTORY_FILE);
            if (!historyFile.exists()) {
                return history; // Return empty list if file doesn't exist
            }
            
            BufferedReader reader = new BufferedReader(new FileReader(TRIP_HISTORY_FILE));
            String line;
            
            while ((line = reader.readLine()) != null) {
                history.add(line);
            }
            
            reader.close();
        } catch (IOException e) {
            System.err.println("Error loading trip history: " + e.getMessage());
        }
        
        return history;
    }
    
    /**
     * Displays trip history in a formatted way
     */
    public void displayTripHistory() {
        List<String> history = loadTripHistory();
        
        if (history.isEmpty()) {
            System.out.println("No trip history found.");
            return;
        }
        
        System.out.println("\n=== Trip History ===");
        for (int i = 0; i < history.size(); i++) {
            System.out.println((i + 1) + ". " + history.get(i));
        }
    }
    
    /**
     * Clears trip history
     * @return true if successful, false otherwise
     */
    public boolean clearTripHistory() {
        try {
            File historyFile = new File(TRIP_HISTORY_FILE);
            if (historyFile.exists()) {
                return historyFile.delete();
            }
            return true; // File doesn't exist, so technically cleared
        } catch (Exception e) {
            System.err.println("Error clearing trip history: " + e.getMessage());
            return false;
        }
    }
}