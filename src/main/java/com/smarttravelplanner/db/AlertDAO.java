package com.smarttravelplanner.db;

import com.smarttravelplanner.model.Alert;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlertDAO {
    
    private Connection getConnection() throws SQLException {
        // Database connection parameters
        String url = "jdbc:postgresql://localhost:5432/smart_travel_db";
        String username = "postgres";
        String password = "Lithu19!"; // Updated to match application.properties
        
        return DriverManager.getConnection(url, username, password);
    }
    
    /**
     * Creates a new alert in the database
     * @param alert The alert to create
     * @return The ID of the created alert, or -1 if creation failed
     * @throws SQLException if a database error occurs
     */
    public int createAlert(Alert alert) throws SQLException {
        String sql = "INSERT INTO alerts (user_id, message, severity) VALUES (?, ?, ?) RETURNING id";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, alert.getUserId());
            stmt.setString(2, alert.getMessage());
            stmt.setString(3, alert.getSeverity());
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        }
        return -1;
    }
    
    /**
     * Retrieves an alert by its ID
     * @param id The ID of the alert to retrieve
     * @return The Alert object, or null if not found
     * @throws SQLException if a database error occurs
     */
    public Alert getAlertById(int id) throws SQLException {
        String sql = "SELECT * FROM alerts WHERE id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToAlert(rs);
                }
            }
        }
        return null;
    }
    
    /**
     * Retrieves all alerts for a specific user
     * @param userId The ID of the user
     * @return A list of alerts for the user
     * @throws SQLException if a database error occurs
     */
    public List<Alert> getAlertsByUserId(int userId) throws SQLException {
        List<Alert> alerts = new ArrayList<>();
        String sql = "SELECT * FROM alerts WHERE user_id = ? ORDER BY created_at DESC";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, userId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    alerts.add(mapResultSetToAlert(rs));
                }
            }
        }
        return alerts;
    }
    
    /**
     * Retrieves all alerts from the database
     * @return A list of all alerts
     * @throws SQLException if a database error occurs
     */
    public List<Alert> getAllAlerts() throws SQLException {
        List<Alert> alerts = new ArrayList<>();
        String sql = "SELECT * FROM alerts ORDER BY created_at DESC";
        
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                alerts.add(mapResultSetToAlert(rs));
            }
        }
        return alerts;
    }
    
    /**
     * Updates an alert in the database
     * @param alert The alert to update
     * @return true if the update was successful, false otherwise
     * @throws SQLException if a database error occurs
     */
    public boolean updateAlert(Alert alert) throws SQLException {
        String sql = "UPDATE alerts SET user_id = ?, message = ?, severity = ?, updated_at = CURRENT_TIMESTAMP WHERE id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, alert.getUserId());
            stmt.setString(2, alert.getMessage());
            stmt.setString(3, alert.getSeverity());
            stmt.setInt(4, alert.getId());
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }
    
    /**
     * Deletes an alert from the database
     * @param id The ID of the alert to delete
     * @return true if the deletion was successful, false otherwise
     * @throws SQLException if a database error occurs
     */
    public boolean deleteAlert(int id) throws SQLException {
        String sql = "DELETE FROM alerts WHERE id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }
    
    /**
     * Maps a ResultSet row to an Alert object
     * @param rs The ResultSet containing alert data
     * @return An Alert object
     * @throws SQLException if a database error occurs
     */
    private Alert mapResultSetToAlert(ResultSet rs) throws SQLException {
        return new Alert(
            rs.getInt("id"),
            rs.getInt("user_id"),
            rs.getString("message"),
            rs.getString("severity"),
            rs.getTimestamp("created_at"),
            rs.getTimestamp("updated_at")
        );
    }
}