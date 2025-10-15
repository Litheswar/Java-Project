package com.smarttravelplanner.utils;

import com.smarttravelplanner.model.Destination;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {
    private static final String DESTINATIONS_FILE = "destinations.json";
    
    public FileHandler() {
        // Default constructor
    }
    
    /**
     * Loads destinations from a simple text file (since we can't use Gson)
     * @return List of destinations or null if file doesn't exist or an error occurs
     */
    public List<Destination> loadDestinations() {
        try (BufferedReader reader = new BufferedReader(new FileReader(DESTINATIONS_FILE))) {
            List<Destination> destinations = new ArrayList<>();
            String line;
            int id = 1;
            
            // Simple format: country,state,city,baseCost (one per line)
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4) {
                    try {
                        String country = parts[0].trim();
                        String state = parts[1].trim();
                        String city = parts[2].trim();
                        double baseCost = Double.parseDouble(parts[3].trim());
                        destinations.add(new Destination(id++, country, state, city, baseCost));
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid number format in line: " + line);
                    }
                }
            }
            return destinations;
        } catch (FileNotFoundException e) {
            System.out.println("Destinations file not found. Creating sample data.");
            return createSampleDestinations();
        } catch (IOException e) {
            System.err.println("Error reading destinations file: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Creates sample destinations when file is not found
     * @return List of sample destinations
     */
    private List<Destination> createSampleDestinations() {
        List<Destination> destinations = new ArrayList<>();
        destinations.add(new Destination(1, "France", "Hauts-de-France", "Lille", 5000.00));
        destinations.add(new Destination(2, "Japan", "Kyoto", "Gion", 4800.00));
        destinations.add(new Destination(3, "India", "Karnataka", "Bangalore", 5500.00));
        return destinations;
    }
    
    /**
     * Saves destinations to a simple text file
     * @param destinations The list of destinations to save
     * @return true if successful, false otherwise
     */
    public boolean saveDestinations(List<Destination> destinations) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(DESTINATIONS_FILE))) {
            // Simple format: country,state,city,baseCost (one per line)
            for (Destination dest : destinations) {
                writer.println(dest.getCountry() + "," + dest.getState() + "," + 
                              dest.getCity() + "," + dest.getBaseCost());
            }
            return true;
        } catch (IOException e) {
            System.err.println("Error saving destinations to file: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Saves trip plan to a text file
     * @param content The content to save
     * @param fileName The name of the file to save to
     * @return true if successful, false otherwise
     */
    public boolean saveTripPlan(String content, String fileName) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName, true))) {
            writer.println("=== Trip Plan ===");
            writer.println(content);
            writer.println();
            return true;
        } catch (IOException e) {
            System.err.println("Error saving trip plan to file: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Reads trip plan from a text file
     * @param fileName The name of the file to read from
     * @return The content of the file or null if an error occurs
     */
    public String readTripPlan(String fileName) {
        try {
            StringBuilder content = new StringBuilder();
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            reader.close();
            return content.toString();
        } catch (IOException e) {
            System.err.println("Error reading trip plan from file: " + e.getMessage());
            return null;
        }
    }
}