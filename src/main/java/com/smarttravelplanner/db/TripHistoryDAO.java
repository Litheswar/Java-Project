package com.smarttravelplanner.db;

import com.smarttravelplanner.model.TripHistory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

<<<<<<< HEAD
public class TripHistoryDAO {
    
    private Connection getConnection() throws SQLException {
        // Database connection parameters
        String url = "jdbc:postgresql://localhost:5432/smart_travel_db";
        String username = "postgres";
        String password = "Lithu19!"; // Updated to match application.properties
        
        return DriverManager.getConnection(url, username, password);
    }
=======
public class TripHistoryDAO extends BaseDAO {
>>>>>>> parent of a75ffb45 (Connected Backend to Database)
    
    /**
     * Creates a new trip history entry in the database
     * @param tripHistory The trip history entry to create
     * @return The ID of the created trip history entry, or -1 if creation failed
     * @throws SQLException if a database error occurs
     */
    public int createTripHistory(TripHistory tripHistory) throws SQLException {
        String sql = "INSERT INTO trip_history (user_id, trip_id, status) VALUES (?, ?, ?) RETURNING id";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setObject(1, tripHistory.getUserId());
            stmt.setInt(2, tripHistory.getTripId());
            stmt.setString(3, tripHistory.getStatus());
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        }
        return -1;
    }
    
    /**
     * Retrieves a trip history entry by its ID
     * @param id The ID of the trip history entry to retrieve
     * @return The TripHistory object, or null if not found
     * @throws SQLException if a database error occurs
     */
    public TripHistory getTripHistoryById(int id) throws SQLException {
        String sql = "SELECT * FROM trip_history WHERE id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToTripHistory(rs);
                }
            }
        }
        return null;
    }
    
    /**
     * Retrieves all trip history entries for a specific user
     * @param userId The ID of the user
     * @return A list of trip history entries for the user
     * @throws SQLException if a database error occurs
     */
    public List<TripHistory> getTripHistoryByUserId(UUID userId) throws SQLException {
        List<TripHistory> tripHistories = new ArrayList<>();
        String sql = "SELECT * FROM trip_history WHERE user_id = ? ORDER BY timestamp DESC";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setObject(1, userId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    tripHistories.add(mapResultSetToTripHistory(rs));
                }
            }
        }
        return tripHistories;
    }
    
    /**
     * Retrieves all trip history entries for a specific trip
     * @param tripId The ID of the trip
     * @return A list of trip history entries for the trip
     * @throws SQLException if a database error occurs
     */
    public List<TripHistory> getTripHistoryByTripId(int tripId) throws SQLException {
        List<TripHistory> tripHistories = new ArrayList<>();
        String sql = "SELECT * FROM trip_history WHERE trip_id = ? ORDER BY timestamp DESC";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, tripId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    tripHistories.add(mapResultSetToTripHistory(rs));
                }
            }
        }
        return tripHistories;
    }
    
    /**
     * Retrieves all trip history entries from the database
     * @return A list of all trip history entries
     * @throws SQLException if a database error occurs
     */
    public List<TripHistory> getAllTripHistory() throws SQLException {
        List<TripHistory> tripHistories = new ArrayList<>();
        String sql = "SELECT * FROM trip_history ORDER BY timestamp DESC";
        
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                tripHistories.add(mapResultSetToTripHistory(rs));
            }
        }
        return tripHistories;
    }
    
    /**
     * Updates a trip history entry in the database
     * @param tripHistory The trip history entry to update
     * @return true if the update was successful, false otherwise
     * @throws SQLException if a database error occurs
     */
    public boolean updateTripHistory(TripHistory tripHistory) throws SQLException {
        String sql = "UPDATE trip_history SET user_id = ?, trip_id = ?, timestamp = ?, status = ? WHERE id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setObject(1, tripHistory.getUserId());
            stmt.setInt(2, tripHistory.getTripId());
            stmt.setTimestamp(3, tripHistory.getTimestamp());
            stmt.setString(4, tripHistory.getStatus());
            stmt.setInt(5, tripHistory.getId());
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }
    
    /**
     * Deletes a trip history entry from the database
     * @param id The ID of the trip history entry to delete
     * @return true if the deletion was successful, false otherwise
     * @throws SQLException if a database error occurs
     */
    public boolean deleteTripHistory(int id) throws SQLException {
        String sql = "DELETE FROM trip_history WHERE id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }
    
    /**
     * Maps a ResultSet row to a TripHistory object
     * @param rs The ResultSet containing trip history data
     * @return A TripHistory object
     * @throws SQLException if a database error occurs
     */
    private TripHistory mapResultSetToTripHistory(ResultSet rs) throws SQLException {
        return new TripHistory(
            rs.getInt("id"),
            (UUID) rs.getObject("user_id"),
            rs.getInt("trip_id"),
            rs.getTimestamp("timestamp"),
            rs.getString("status")
        );
    }
}