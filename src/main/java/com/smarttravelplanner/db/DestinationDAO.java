package com.smarttravelplanner.db;

import com.smarttravelplanner.model.Destination;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DestinationDAO extends BaseDAO {
    
    /**
     * Creates a new destination in the database
     * @param destination The destination to create
     * @return The ID of the created destination, or -1 if creation failed
     * @throws SQLException if a database error occurs
     */
    public int createDestination(Destination destination) throws SQLException {
        String sql = "INSERT INTO destinations (state_id, name, base_cost, sustainability_score, estimated_co2_footprint) VALUES (?, ?, ?, ?, ?) RETURNING id";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, destination.getStateId());
            stmt.setString(2, destination.getName());
            stmt.setDouble(3, destination.getBaseCost());
            
            // Handle nullable fields
            if (destination.getSustainabilityScore() != null) {
                stmt.setInt(4, destination.getSustainabilityScore());
            } else {
                stmt.setNull(4, Types.INTEGER);
            }
            
            if (destination.getEstimatedCo2Footprint() != null) {
                stmt.setDouble(5, destination.getEstimatedCo2Footprint());
            } else {
                stmt.setNull(5, Types.DOUBLE);
            }
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        }
        return -1;
    }
    
    /**
     * Retrieves a destination by its ID
     * @param id The ID of the destination to retrieve
     * @return The Destination object, or null if not found
     * @throws SQLException if a database error occurs
     */
    public Destination getDestinationById(int id) throws SQLException {
        String sql = "SELECT * FROM destinations WHERE id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToDestination(rs);
                }
            }
        }
        return null;
    }
    
    /**
     * Retrieves all destinations for a specific state
     * @param stateId The ID of the state
     * @return A list of destinations for the state
     * @throws SQLException if a database error occurs
     */
    public List<Destination> getDestinationsByStateId(int stateId) throws SQLException {
        List<Destination> destinations = new ArrayList<>();
        String sql = "SELECT * FROM destinations WHERE state_id = ? ORDER BY name";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, stateId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    destinations.add(mapResultSetToDestination(rs));
                }
            }
        }
        return destinations;
    }
    
    /**
     * Retrieves all destinations from the database
     * @return A list of all destinations
     * @throws SQLException if a database error occurs
     */
    public List<Destination> getAllDestinations() throws SQLException {
        List<Destination> destinations = new ArrayList<>();
        String sql = "SELECT * FROM destinations ORDER BY name";
        
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                destinations.add(mapResultSetToDestination(rs));
            }
        }
        return destinations;
    }
    
    /**
     * Updates an existing destination in the database
     * @param destination The destination to update
     * @return true if the update was successful, false otherwise
     * @throws SQLException if a database error occurs
     */
    public boolean updateDestination(Destination destination) throws SQLException {
        String sql = "UPDATE destinations SET state_id = ?, name = ?, base_cost = ?, sustainability_score = ?, estimated_co2_footprint = ?, updated_at = CURRENT_TIMESTAMP WHERE id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, destination.getStateId());
            stmt.setString(2, destination.getName());
            stmt.setDouble(3, destination.getBaseCost());
            
            // Handle nullable fields
            if (destination.getSustainabilityScore() != null) {
                stmt.setInt(4, destination.getSustainabilityScore());
            } else {
                stmt.setNull(4, Types.INTEGER);
            }
            
            if (destination.getEstimatedCo2Footprint() != null) {
                stmt.setDouble(5, destination.getEstimatedCo2Footprint());
            } else {
                stmt.setNull(5, Types.DOUBLE);
            }
            
            stmt.setInt(6, destination.getId());
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }
    
    /**
     * Deletes a destination from the database
     * @param id The ID of the destination to delete
     * @return true if the deletion was successful, false otherwise
     * @throws SQLException if a database error occurs
     */
    public boolean deleteDestination(int id) throws SQLException {
        String sql = "DELETE FROM destinations WHERE id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }
    
    // Backward compatibility method
    public List<Destination> getAffordableDestinations(double budget) throws SQLException {
        List<Destination> destinations = new ArrayList<>();
        String sql = "SELECT d.*, s.name as state_name, c.name as country_name " +
                     "FROM destinations d " +
                     "JOIN states s ON d.state_id = s.id " +
                     "JOIN countries c ON s.country_id = c.id " +
                     "WHERE d.base_cost <= ? " +
                     "ORDER BY d.base_cost";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setDouble(1, budget);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Destination dest = new Destination(
                        rs.getInt("id"),
                        rs.getString("country_name"),
                        rs.getString("state_name"),
                        rs.getString("name"),
                        rs.getDouble("base_cost")
                    );
                    destinations.add(dest);
                }
            }
        }
        return destinations;
    }
    
    // Backward compatibility method
    public List<String> getStatesWithBaseBudget(String country) throws SQLException {
        List<String> states = new ArrayList<>();
        String sql = "SELECT s.name, s.base_budget " +
                     "FROM states s " +
                     "JOIN countries c ON s.country_id = c.id " +
                     "WHERE c.name = ? " +
                     "ORDER BY s.name";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, country);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String stateInfo = rs.getString("name") + " (Base Budget: ₹" + 
                                      String.format("%.0f", rs.getDouble("base_budget")) + ")";
                    states.add(stateInfo);
                }
            }
        }
        return states;
    }
    
    // Backward compatibility method
    public List<Destination> getSustainableDestinations(int minScore) throws SQLException {
        List<Destination> destinations = new ArrayList<>();
        String sql = "SELECT d.*, s.name as state_name, c.name as country_name " +
                     "FROM destinations d " +
                     "JOIN states s ON d.state_id = s.id " +
                     "JOIN countries c ON s.country_id = c.id " +
                     "WHERE d.sustainability_score >= ? " +
                     "ORDER BY d.sustainability_score DESC";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, minScore);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Destination dest = new Destination(
                        rs.getInt("id"),
                        rs.getString("country_name"),
                        rs.getString("state_name"),
                        rs.getString("name"),
                        rs.getDouble("base_cost")
                    );
                    // Set additional properties
                    if (rs.getObject("sustainability_score") != null) {
                        dest.setSustainabilityScore(rs.getInt("sustainability_score"));
                    }
                    if (rs.getObject("estimated_co2_footprint") != null) {
                        dest.setEstimatedCo2Footprint(rs.getDouble("estimated_co2_footprint"));
                    }
                    destinations.add(dest);
                }
            }
        }
        return destinations;
    }
    
    /**
     * Maps a ResultSet row to a Destination object
     * @param rs The ResultSet containing destination data
     * @return A Destination object
     * @throws SQLException if a database error occurs
     */
    private Destination mapResultSetToDestination(ResultSet rs) throws SQLException {
        Destination destination = new Destination();
        destination.setId(rs.getInt("id"));
        destination.setStateId(rs.getInt("state_id"));
        destination.setName(rs.getString("name"));
        destination.setBaseCost(rs.getDouble("base_cost"));
        
        // Handle nullable fields
        if (rs.getObject("sustainability_score") != null) {
            destination.setSustainabilityScore(rs.getInt("sustainability_score"));
        }
        
        if (rs.getObject("estimated_co2_footprint") != null) {
            destination.setEstimatedCo2Footprint(rs.getDouble("estimated_co2_footprint"));
        }
        
        destination.setCreatedAt(rs.getTimestamp("created_at"));
        destination.setUpdatedAt(rs.getTimestamp("updated_at"));
        
        return destination;
    }
}