package com.smarttravelplanner.service;

import com.smarttravelplanner.db.DestinationDAO;
import com.smarttravelplanner.model.Destination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class DestinationService {
    
    private final DestinationDAO destinationDAO;
    
    @Autowired
    public DestinationService(DestinationDAO destinationDAO) {
        this.destinationDAO = destinationDAO;
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
    
    /**
     * Updates an existing destination in the database
     * @param destination The destination to update
     * @return true if successful, false otherwise
     */
    public boolean updateDestination(Destination destination) throws SQLException {
        return destinationDAO.updateDestination(destination);
    }
    
    /**
     * Deletes a destination from the database
     * @param id The ID of the destination to delete
     * @return true if successful, false otherwise
     */
    public boolean deleteDestination(int id) throws SQLException {
        return destinationDAO.deleteDestination(id);
    }
}