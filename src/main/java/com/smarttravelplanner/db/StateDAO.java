package com.smarttravelplanner.db;

import com.smarttravelplanner.model.State;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

<<<<<<< HEAD
public class StateDAO {
    
    private Connection getConnection() throws SQLException {
        // Database connection parameters
        String url = "jdbc:postgresql://localhost:5432/smart_travel_db";
        String username = "postgres";
        String password = "Lithu19!"; // Updated to match application.properties
        
        return DriverManager.getConnection(url, username, password);
    }
=======
public class StateDAO extends BaseDAO {
>>>>>>> parent of a75ffb45 (Connected Backend to Database)
    
    /**
     * Creates a new state in the database
     * @param state The state to create
     * @return The ID of the created state, or -1 if creation failed
     * @throws SQLException if a database error occurs
     */
    public int createState(State state) throws SQLException {
        String sql = "INSERT INTO states (country_id, name, base_budget) VALUES (?, ?, ?) RETURNING id";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, state.getCountryId());
            stmt.setString(2, state.getName());
            stmt.setDouble(3, state.getBaseBudget());
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        }
        return -1;
    }
    
    /**
     * Retrieves a state by its ID
     * @param id The ID of the state to retrieve
     * @return The State object, or null if not found
     * @throws SQLException if a database error occurs
     */
    public State getStateById(int id) throws SQLException {
        String sql = "SELECT * FROM states WHERE id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToState(rs);
                }
            }
        }
        return null;
    }
    
    /**
     * Retrieves all states for a specific country
     * @param countryId The ID of the country
     * @return A list of states for the country
     * @throws SQLException if a database error occurs
     */
    public List<State> getStatesByCountryId(int countryId) throws SQLException {
        List<State> states = new ArrayList<>();
        String sql = "SELECT * FROM states WHERE country_id = ? ORDER BY name";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, countryId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    states.add(mapResultSetToState(rs));
                }
            }
        }
        return states;
    }
    
    /**
     * Retrieves all states from the database
     * @return A list of all states
     * @throws SQLException if a database error occurs
     */
    public List<State> getAllStates() throws SQLException {
        List<State> states = new ArrayList<>();
        String sql = "SELECT * FROM states ORDER BY name";
        
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                states.add(mapResultSetToState(rs));
            }
        }
        return states;
    }
    
    /**
     * Updates a state in the database
     * @param state The state to update
     * @return true if the update was successful, false otherwise
     * @throws SQLException if a database error occurs
     */
    public boolean updateState(State state) throws SQLException {
        String sql = "UPDATE states SET country_id = ?, name = ?, base_budget = ?, updated_at = CURRENT_TIMESTAMP WHERE id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, state.getCountryId());
            stmt.setString(2, state.getName());
            stmt.setDouble(3, state.getBaseBudget());
            stmt.setInt(4, state.getId());
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }
    
    /**
     * Deletes a state from the database
     * @param id The ID of the state to delete
     * @return true if the deletion was successful, false otherwise
     * @throws SQLException if a database error occurs
     */
    public boolean deleteState(int id) throws SQLException {
        String sql = "DELETE FROM states WHERE id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }
    
    /**
     * Maps a ResultSet row to a State object
     * @param rs The ResultSet containing state data
     * @return A State object
     * @throws SQLException if a database error occurs
     */
    private State mapResultSetToState(ResultSet rs) throws SQLException {
        return new State(
            rs.getInt("id"),
            rs.getInt("country_id"),
            rs.getString("name"),
            rs.getDouble("base_budget"),
            rs.getTimestamp("created_at"),
            rs.getTimestamp("updated_at")
        );
    }
}