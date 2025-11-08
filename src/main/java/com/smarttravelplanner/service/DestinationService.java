package com.smarttravelplanner.service;

import com.smarttravelplanner.db.DestinationDAO;
import com.smarttravelplanner.model.Destination;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DestinationService {
    
    private final DestinationDAO destinationDAO;
    
    public DestinationService() {
        this.destinationDAO = new DestinationDAO();
    }
    
    /**
     * Retrieves all destinations from the database
     * @return List of Destination objects
     */
    public List<Destination> getAllDestinations() {
        try {
            return destinationDAO.getAllDestinations();
        } catch (SQLException e) {
            System.err.println("Error retrieving destinations: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    
    /**
     * Retrieves a destination by its ID
     * @param id The destination ID
     * @return Destination object or null if not found
     */
    public Destination getDestinationById(int id) {
        try {
            return destinationDAO.getDestinationById(id);
        } catch (SQLException e) {
            System.err.println("Error retrieving destination: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * Adds a new destination to the database
     * @param destination The destination to add
     * @return true if successful, false otherwise
     */
    public boolean addDestination(Destination destination) {
        try {
            return destinationDAO.createDestination(destination) > 0;
        } catch (SQLException e) {
            System.err.println("Error adding destination: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Updates an existing destination in the database
     * @param destination The destination to update
     * @return true if successful, false otherwise
     */
    public boolean updateDestination(Destination destination) {
        try {
            return destinationDAO.updateDestination(destination);
        } catch (SQLException e) {
            System.err.println("Error updating destination: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Deletes a destination from the database
     * @param id The ID of the destination to delete
     * @return true if successful, false otherwise
     */
    public boolean deleteDestination(int id) {
        try {
            return destinationDAO.deleteDestination(id);
        } catch (SQLException e) {
            System.err.println("Error deleting destination: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}