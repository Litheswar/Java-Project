package com.smarttravelplanner.service;

import com.smarttravelplanner.db.DestinationDAO;
import com.smarttravelplanner.model.Destination;

import java.sql.SQLException;
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
    public List<Destination> getAllDestinations() throws SQLException {
        return destinationDAO.getAllDestinations();
    }
    
    /**
     * Retrieves a destination by its ID
     * @param id The destination ID
     * @return Destination object or null if not found
     */
    public Destination getDestinationById(int id) throws SQLException {
        return destinationDAO.getDestinationById(id);
    }
    
    /**
     * Adds a new destination to the database
     * @param destination The destination to add
     * @return true if successful, false otherwise
     */
    public boolean addDestination(Destination destination) throws SQLException {
        int id = destinationDAO.createDestination(destination);
        if (id > 0) {
            destination.setId(id);
            return true;
        }
        return false;
    }
}