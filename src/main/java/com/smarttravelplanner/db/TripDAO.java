package com.smarttravelplanner.db;

import com.smarttravelplanner.model.Trip;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TripDAO extends BaseDAO {
    
    /**
     * Creates a new trip in the database
     * @param trip The trip to create
     * @return The ID of the created trip, or -1 if creation failed
     * @throws SQLException if a database error occurs
     */
    public int createTrip(Trip trip) throws SQLException {
        String sql = "INSERT INTO trips (user_id, destination_id, trip_type, travel_mode, stay_type, meal_type, trip_days, meals_per_day, total_estimated_cost, total_budget) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING id";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setObject(1, trip.getUserId());
            stmt.setInt(2, trip.getDestinationId());
            stmt.setString(3, trip.getTripType());
            stmt.setString(4, trip.getTravelMode());
            stmt.setString(5, trip.getStayType());
            stmt.setString(6, trip.getMealType());
            stmt.setInt(7, trip.getTripDays());
            stmt.setInt(8, trip.getMealsPerDay());
            stmt.setDouble(9, trip.getTotalEstimatedCost());
            stmt.setDouble(10, trip.getTotalBudget());
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        }
        return -1;
    }
    
    /**
     * Retrieves a trip by its ID
     * @param id The ID of the trip to retrieve
     * @return The Trip object, or null if not found
     * @throws SQLException if a database error occurs
     */
    public Trip getTripById(int id) throws SQLException {
        String sql = "SELECT * FROM trips WHERE id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToTrip(rs);
                }
            }
        }
        return null;
    }
    
    /**
     * Retrieves all trips for a specific user
     * @param userId The ID of the user
     * @return A list of trips for the user
     * @throws SQLException if a database error occurs
     */
    public List<Trip> getTripsByUserId(UUID userId) throws SQLException {
        List<Trip> trips = new ArrayList<>();
        String sql = "SELECT * FROM trips WHERE user_id = ? ORDER BY created_at DESC";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setObject(1, userId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    trips.add(mapResultSetToTrip(rs));
                }
            }
        }
        return trips;
    }
    
    /**
     * Retrieves all trips from the database
     * @return A list of all trips
     * @throws SQLException if a database error occurs
     */
    public List<Trip> getAllTrips() throws SQLException {
        List<Trip> trips = new ArrayList<>();
        String sql = "SELECT * FROM trips ORDER BY created_at DESC";
        
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                trips.add(mapResultSetToTrip(rs));
            }
        }
        return trips;
    }
    
    /**
     * Updates a trip in the database
     * @param trip The trip to update
     * @return true if the update was successful, false otherwise
     * @throws SQLException if a database error occurs
     */
    public boolean updateTrip(Trip trip) throws SQLException {
        String sql = "UPDATE trips SET user_id = ?, destination_id = ?, trip_type = ?, travel_mode = ?, stay_type = ?, meal_type = ?, trip_days = ?, meals_per_day = ?, total_estimated_cost = ?, total_budget = ?, updated_at = CURRENT_TIMESTAMP WHERE id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setObject(1, trip.getUserId());
            stmt.setInt(2, trip.getDestinationId());
            stmt.setString(3, trip.getTripType());
            stmt.setString(4, trip.getTravelMode());
            stmt.setString(5, trip.getStayType());
            stmt.setString(6, trip.getMealType());
            stmt.setInt(7, trip.getTripDays());
            stmt.setInt(8, trip.getMealsPerDay());
            stmt.setDouble(9, trip.getTotalEstimatedCost());
            stmt.setDouble(10, trip.getTotalBudget());
            stmt.setInt(11, trip.getId());
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }
    
    /**
     * Deletes a trip from the database
     * @param id The ID of the trip to delete
     * @return true if the deletion was successful, false otherwise
     * @throws SQLException if a database error occurs
     */
    public boolean deleteTrip(int id) throws SQLException {
        String sql = "DELETE FROM trips WHERE id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }
    
    /**
     * Maps a ResultSet row to a Trip object
     * @param rs The ResultSet containing trip data
     * @return A Trip object
     * @throws SQLException if a database error occurs
     */
    private Trip mapResultSetToTrip(ResultSet rs) throws SQLException {
        return new Trip(
            rs.getInt("id"),
            (UUID) rs.getObject("user_id"),
            rs.getInt("destination_id"),
            rs.getString("trip_type"),
            rs.getString("travel_mode"),
            rs.getString("stay_type"),
            rs.getString("meal_type"),
            rs.getInt("trip_days"),
            rs.getInt("meals_per_day"),
            rs.getDouble("total_estimated_cost"),
            rs.getDouble("total_budget"),
            rs.getTimestamp("created_at"),
            rs.getTimestamp("updated_at")
        );
    }
}