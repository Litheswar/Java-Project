package com.smarttravelplanner.service;

import com.smarttravelplanner.db.DBConnection;
import com.smarttravelplanner.model.Destination;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DestinationService {
    
    /**
     * Retrieves all destinations from the database
     * @return List of Destination objects
     */
    public List<Destination> getAllDestinations() {
        List<Destination> destinations = new ArrayList<>();
        String query = "SELECT id, country, state, city, base_cost FROM destinations ORDER BY id";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Destination destination = new Destination();
                destination.setId(rs.getInt("id"));
                destination.setCountry(rs.getString("country"));
                destination.setState(rs.getString("state"));
                destination.setCity(rs.getString("city"));
                destination.setBaseCost(rs.getDouble("base_cost"));
                destinations.add(destination);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving destinations: " + e.getMessage());
            e.printStackTrace();
        }
        
        return destinations;
    }
    
    /**
     * Retrieves a destination by its ID
     * @param id The destination ID
     * @return Destination object or null if not found
     */
    public Destination getDestinationById(int id) {
        String query = "SELECT id, country, state, city, base_cost FROM destinations WHERE id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Destination destination = new Destination();
                    destination.setId(rs.getInt("id"));
                    destination.setCountry(rs.getString("country"));
                    destination.setState(rs.getString("state"));
                    destination.setCity(rs.getString("city"));
                    destination.setBaseCost(rs.getDouble("base_cost"));
                    return destination;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving destination: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }
    
    /**
     * Adds a new destination to the database
     * @param destination The destination to add
     * @return true if successful, false otherwise
     */
    public boolean addDestination(Destination destination) {
        String query = "INSERT INTO destinations (country, state, city, base_cost) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, destination.getCountry());
            stmt.setString(2, destination.getState());
            stmt.setString(3, destination.getCity());
            stmt.setDouble(4, destination.getBaseCost());
            
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                // Get the generated ID
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        destination.setId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error adding destination: " + e.getMessage());
            e.printStackTrace();
        }
        
        return false;
    }
    
    /**
     * Updates an existing destination in the database
     * @param destination The destination to update
     * @return true if successful, false otherwise
     */
    public boolean updateDestination(Destination destination) {
        String query = "UPDATE destinations SET country = ?, state = ?, city = ?, base_cost = ? WHERE id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, destination.getCountry());
            stmt.setString(2, destination.getState());
            stmt.setString(3, destination.getCity());
            stmt.setDouble(4, destination.getBaseCost());
            stmt.setInt(5, destination.getId());
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error updating destination: " + e.getMessage());
            e.printStackTrace();
        }
        
        return false;
    }
    
    /**
     * Deletes a destination from the database
     * @param id The ID of the destination to delete
     * @return true if successful, false otherwise
     */
    public boolean deleteDestination(int id) {
        String query = "DELETE FROM destinations WHERE id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, id);
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting destination: " + e.getMessage());
            e.printStackTrace();
        }
        
        return false;
    }
}