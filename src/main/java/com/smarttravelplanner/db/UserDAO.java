package com.smarttravelplanner.db;

import com.smarttravelplanner.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

<<<<<<< HEAD
public class UserDAO {
    
    private Connection getConnection() throws SQLException {
        // Database connection parameters
        String url = "jdbc:postgresql://localhost:5432/smart_travel_db";
        String username = "postgres";
        String password = "Lithu19!"; // Updated to match application.properties
        
        return DriverManager.getConnection(url, username, password);
    }
=======
public class UserDAO extends BaseDAO {
>>>>>>> parent of a75ffb45 (Connected Backend to Database)
    
    /**
     * Creates a new user in the database
     * @param user The user to create
     * @return The ID of the created user, or null if creation failed
     * @throws SQLException if a database error occurs
     */
    public UUID createUser(User user) throws SQLException {
        String sql = "INSERT INTO users (name, age, family_count, budget, email) VALUES (?, ?, ?, ?, ?) RETURNING id";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, user.getName());
            stmt.setInt(2, user.getAge());
            stmt.setInt(3, user.getFamilyCount());
            stmt.setDouble(4, user.getBudget());
            stmt.setString(5, user.getEmail());
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return (UUID) rs.getObject("id");
                }
            }
        }
        return null;
    }
    
    // Backward compatibility method
    public int insertUser(String name, int age, int familyCount, double budget) throws SQLException {
        String sql = "INSERT INTO users (name, age, family_count, budget) VALUES (?, ?, ?, ?) RETURNING id";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, name);
            stmt.setInt(2, age);
            stmt.setInt(3, familyCount);
            stmt.setDouble(4, budget);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        }
        return -1;
    }
    
    /**
     * Retrieves a user by its ID
     * @param id The ID of the user to retrieve
     * @return The User object, or null if not found
     * @throws SQLException if a database error occurs
     */
    public User getUserById(UUID id) throws SQLException {
        String sql = "SELECT * FROM users WHERE id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setObject(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToUser(rs);
                }
            }
        }
        return null;
    }
    
    /**
     * Retrieves all users from the database
     * @return A list of all users
     * @throws SQLException if a database error occurs
     */
    public List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users ORDER BY name";
        
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                users.add(mapResultSetToUser(rs));
            }
        }
        return users;
    }
    
    // Backward compatibility method
    public List<String> getAllUsersAsString() throws SQLException {
        List<String> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
        
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                String user = String.format("ID: %d, Name: %s, Age: %d, Family Count: %d, Budget: %.2f",
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getInt("age"),
                    rs.getInt("family_count"),
                    rs.getDouble("budget"));
                users.add(user);
            }
        }
        return users;
    }
    
    /**
     * Updates a user in the database
     * @param user The user to update
     * @return true if the update was successful, false otherwise
     * @throws SQLException if a database error occurs
     */
    public boolean updateUser(User user) throws SQLException {
        String sql = "UPDATE users SET name = ?, age = ?, family_count = ?, budget = ?, email = ?, updated_at = CURRENT_TIMESTAMP WHERE id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, user.getName());
            stmt.setInt(2, user.getAge());
            stmt.setInt(3, user.getFamilyCount());
            stmt.setDouble(4, user.getBudget());
            stmt.setString(5, user.getEmail());
            stmt.setObject(6, user.getId());
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }
    
    /**
     * Deletes a user from the database
     * @param id The ID of the user to delete
     * @return true if the deletion was successful, false otherwise
     * @throws SQLException if a database error occurs
     */
    public boolean deleteUser(UUID id) throws SQLException {
        String sql = "DELETE FROM users WHERE id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setObject(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }
    
    /**
     * Maps a ResultSet row to a User object
     * @param rs The ResultSet containing user data
     * @return A User object
     * @throws SQLException if a database error occurs
     */
    private User mapResultSetToUser(ResultSet rs) throws SQLException {
        return new User(
            (UUID) rs.getObject("id"),
            rs.getString("name"),
            rs.getInt("age"),
            rs.getInt("family_count"),
            rs.getDouble("budget"),
            rs.getString("email"),
            rs.getTimestamp("created_at"),
            rs.getTimestamp("updated_at")
        );
    }
}