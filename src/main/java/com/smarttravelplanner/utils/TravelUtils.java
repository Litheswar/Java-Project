package com.smarttravelplanner.utils;

import com.smarttravelplanner.model.Destination;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TravelUtils {
    
    /**
     * Saves trip summary to a text file
     * @param content The content to save
     * @param fileName The name of the file to save to
     * @return true if successful, false otherwise
     */
    public static boolean saveTripSummaryToFile(String content, String fileName) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName, true))) {
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            
            writer.println("=== Trip Summary - " + now.format(formatter) + " ===");
            writer.println(content);
            writer.println();
            
            return true;
        } catch (IOException e) {
            System.err.println("Error saving trip summary to file: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Formats a list of destinations as a readable string
     * @param destinations List of destinations
     * @return Formatted string
     */
    public static String formatDestinations(List<Destination> destinations) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < destinations.size(); i++) {
            Destination dest = destinations.get(i);
            sb.append((i + 1)).append(". ")
              .append(dest.getCity()).append(", ")
              .append(dest.getState()).append(", ")
              .append(dest.getCountry())
              .append(" (Base Cost: $").append(String.format("%.2f", dest.getBaseCost())).append(")")
              .append("\n");
        }
        return sb.toString();
    }
    
    /**
     * Validates if a family size is reasonable
     * @param familySize The family size to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidFamilySize(int familySize) {
        return familySize > 0 && familySize <= 20;
    }
    
    /**
     * Validates if a travel mode is supported
     * @param travelMode The travel mode to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidTravelMode(String travelMode) {
        if (travelMode == null) return false;
        
        switch (travelMode.toLowerCase()) {
            case "budget":
            case "standard":
            case "luxury":
            case "car":
            case "plane":
            case "train":
            case "bus":
                return true;
            default:
                return false;
        }
    }
}