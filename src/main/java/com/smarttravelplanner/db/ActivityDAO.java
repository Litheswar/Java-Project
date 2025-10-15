package com.smarttravelplanner.db;

import com.smarttravelplanner.model.Activity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ActivityDAO extends BaseDAO {
    
    /**
     * Creates a new activity in the database
     * @param activity The activity to create
     * @return The ID of the created activity, or -1 if creation failed
     * @throws SQLException if a database error occurs
     */
    public int createActivity(Activity activity) throws SQLException {
        String sql = "INSERT INTO activities (destination_id, name, description, cost) VALUES (?, ?, ?, ?) RETURNING id";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, activity.getDestinationId());
            stmt.setString(2, activity.getName());
            stmt.setString(3, activity.getDescription());
            stmt.setDouble(4, activity.getCost());
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        }
        return -1;
    }
    
    /**
     * Retrieves an activity by its ID
     * @param id The ID of the activity to retrieve
     * @return The Activity object, or null if not found
     * @throws SQLException if a database error occurs
     */
    public Activity getActivityById(int id) throws SQLException {
        String sql = "SELECT * FROM activities WHERE id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToActivity(rs);
                }
            }
        }
        return null;
    }
    
    /**
     * Retrieves all activities for a specific destination
     * @param destinationId The ID of the destination
     * @return A list of activities for the destination
     * @throws SQLException if a database error occurs
     */
    public List<Activity> getActivitiesByDestinationId(int destinationId) throws SQLException {
        List<Activity> activities = new ArrayList<>();
        String sql = "SELECT * FROM activities WHERE destination_id = ? ORDER BY name";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, destinationId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    activities.add(mapResultSetToActivity(rs));
                }
            }
        }
        return activities;
    }
    
    /**
     * Retrieves all activities from the database
     * @return A list of all activities
     * @throws SQLException if a database error occurs
     */
    public List<Activity> getAllActivities() throws SQLException {
        List<Activity> activities = new ArrayList<>();
        String sql = "SELECT * FROM activities ORDER BY name";
        
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                activities.add(mapResultSetToActivity(rs));
            }
        }
        return activities;
    }
    
    /**
     * Updates an activity in the database
     * @param activity The activity to update
     * @return true if the update was successful, false otherwise
     * @throws SQLException if a database error occurs
     */
    public boolean updateActivity(Activity activity) throws SQLException {
        String sql = "UPDATE activities SET destination_id = ?, name = ?, description = ?, cost = ?, updated_at = CURRENT_TIMESTAMP WHERE id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, activity.getDestinationId());
            stmt.setString(2, activity.getName());
            stmt.setString(3, activity.getDescription());
            stmt.setDouble(4, activity.getCost());
            stmt.setInt(5, activity.getId());
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }
    
    /**
     * Deletes an activity from the database
     * @param id The ID of the activity to delete
     * @return true if the deletion was successful, false otherwise
     * @throws SQLException if a database error occurs
     */
    public boolean deleteActivity(int id) throws SQLException {
        String sql = "DELETE FROM activities WHERE id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }
    
    /**
     * Maps a ResultSet row to an Activity object
     * @param rs The ResultSet containing activity data
     * @return An Activity object
     * @throws SQLException if a database error occurs
     */
    private Activity mapResultSetToActivity(ResultSet rs) throws SQLException {
        return new Activity(
            rs.getInt("id"),
            rs.getInt("destination_id"),
            rs.getString("name"),
            rs.getString("description"),
            rs.getDouble("cost"),
            rs.getTimestamp("created_at"),
            rs.getTimestamp("updated_at")
        );
    }
}