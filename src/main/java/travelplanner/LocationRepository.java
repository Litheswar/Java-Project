package travelplanner;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository for managing travel destinations using File I/O.
 * Demonstrates File I/O operations for storing location data.
 */
public class LocationRepository {
    private static final String FILE_PATH = "locations.txt";
    private List<Location> locations;

    public LocationRepository() {
        this.locations = new ArrayList<>();
        loadLocations();
    }

    /**
     * Adds a new location to the repository
     */
    public void addLocation(Location location) {
        locations.add(location);
        saveLocations();
    }

    /**
     * Gets all locations from the repository
     */
    public List<Location> getAllLocations() {
        return new ArrayList<>(locations);
    }

    /**
     * Finds a location by name
     */
    public Location findLocationByName(String name) {
        for (Location location : locations) {
            if (location.getName().equalsIgnoreCase(name)) {
                return location;
            }
        }
        return null;
    }

    /**
     * Saves locations to a text file
     */
    private void saveLocations() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH))) {
            for (Location location : locations) {
                writer.println(location.getName() + "|" + 
                              location.getCountry() + "|" + 
                              location.getDescription() + "|" + 
                              location.getLatitude() + "|" + 
                              location.getLongitude() + "|" + 
                              location.getBestSeason() + "|" + 
                              location.getAverageCostPerDay());
            }
        } catch (IOException e) {
            System.err.println("Error saving locations: " + e.getMessage());
        }
    }

    /**
     * Loads locations from a text file
     */
    private void loadLocations() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 7) {
                    Location location = new Location();
                    location.setName(parts[0]);
                    location.setCountry(parts[1]);
                    location.setDescription(parts[2]);
                    location.setLatitude(Double.parseDouble(parts[3]));
                    location.setLongitude(Double.parseDouble(parts[4]));
                    location.setBestSeason(parts[5]);
                    location.setAverageCostPerDay(Double.parseDouble(parts[6]));
                    locations.add(location);
                }
            }
        } catch (FileNotFoundException e) {
            // File doesn't exist yet, which is fine for a new application
            System.out.println("Locations file not found. Starting with empty repository.");
        } catch (IOException e) {
            System.err.println("Error loading locations: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error parsing location data: " + e.getMessage());
        }
    }

    /**
     * Initializes the repository with some sample data if it's empty
     */
    public void initializeWithSampleData() {
        if (locations.isEmpty()) {
            // Add some sample locations
            locations.add(new Location("Paris", "France", "City of Light", 48.8566, 2.3522, "Spring/Fall", 150.0));
            locations.add(new Location("Tokyo", "Japan", "Modern metropolis", 35.6762, 139.6503, "Spring/Fall", 120.0));
            locations.add(new Location("New York", "USA", "The Big Apple", 40.7128, -74.0060, "Spring/Fall", 200.0));
            locations.add(new Location("London", "UK", "Historic capital", 51.5074, -0.1278, "Spring/Fall", 130.0));
            locations.add(new Location("Sydney", "Australia", "Harbor city", -33.8688, 151.2093, "Spring/Fall", 140.0));
            saveLocations();
        }
    }
}