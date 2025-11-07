package com.smarttravelplanner.db;

import com.smarttravelplanner.model.ExpenseBreakdown;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExpenseBreakdownDAO {
    
    private Connection getConnection() throws SQLException {
        // Database connection parameters
        String url = "jdbc:postgresql://localhost:5432/smart_travel_db";
        String username = "postgres";
        String password = "Lithu19!"; // Updated to match application.properties
        
        return DriverManager.getConnection(url, username, password);
    }
    
    /**
     * Creates a new expense breakdown in the database
     * @param expenseBreakdown The expense breakdown to create
     * @return The ID of the created expense breakdown, or -1 if creation failed
     * @throws SQLException if a database error occurs
     */
    public int createExpenseBreakdown(ExpenseBreakdown expenseBreakdown) throws SQLException {
        String sql = "INSERT INTO expense_breakdown (trip_id, travel_cost, food_cost, stay_cost, shopping_cost, entertainment_cost, local_commute_cost, total_cost) VALUES (?, ?, ?, ?, ?, ?, ?, ?) RETURNING id";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, expenseBreakdown.getTripId());
            stmt.setDouble(2, expenseBreakdown.getTravelCost());
            stmt.setDouble(3, expenseBreakdown.getFoodCost());
            stmt.setDouble(4, expenseBreakdown.getStayCost());
            stmt.setDouble(5, expenseBreakdown.getShoppingCost());
            stmt.setDouble(6, expenseBreakdown.getEntertainmentCost());
            stmt.setDouble(7, expenseBreakdown.getLocalCommuteCost());
            stmt.setDouble(8, expenseBreakdown.getTotalCost());
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        }
        return -1;
    }
    
    /**
     * Retrieves an expense breakdown by its ID
     * @param id The ID of the expense breakdown to retrieve
     * @return The ExpenseBreakdown object, or null if not found
     * @throws SQLException if a database error occurs
     */
    public ExpenseBreakdown getExpenseBreakdownById(int id) throws SQLException {
        String sql = "SELECT * FROM expense_breakdown WHERE id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToExpenseBreakdown(rs);
                }
            }
        }
        return null;
    }
    
    /**
     * Retrieves an expense breakdown for a specific trip
     * @param tripId The ID of the trip
     * @return The ExpenseBreakdown object, or null if not found
     * @throws SQLException if a database error occurs
     */
    public ExpenseBreakdown getExpenseBreakdownByTripId(int tripId) throws SQLException {
        String sql = "SELECT * FROM expense_breakdown WHERE trip_id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, tripId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToExpenseBreakdown(rs);
                }
            }
        }
        return null;
    }
    
    /**
     * Retrieves all expense breakdowns from the database
     * @return A list of all expense breakdowns
     * @throws SQLException if a database error occurs
     */
    public List<ExpenseBreakdown> getAllExpenseBreakdowns() throws SQLException {
        List<ExpenseBreakdown> expenseBreakdowns = new ArrayList<>();
        String sql = "SELECT * FROM expense_breakdown ORDER BY created_at DESC";
        
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                expenseBreakdowns.add(mapResultSetToExpenseBreakdown(rs));
            }
        }
        return expenseBreakdowns;
    }
    
    /**
     * Updates an expense breakdown in the database
     * @param expenseBreakdown The expense breakdown to update
     * @return true if the update was successful, false otherwise
     * @throws SQLException if a database error occurs
     */
    public boolean updateExpenseBreakdown(ExpenseBreakdown expenseBreakdown) throws SQLException {
        String sql = "UPDATE expense_breakdown SET trip_id = ?, travel_cost = ?, food_cost = ?, stay_cost = ?, shopping_cost = ?, entertainment_cost = ?, local_commute_cost = ?, total_cost = ?, updated_at = CURRENT_TIMESTAMP WHERE id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, expenseBreakdown.getTripId());
            stmt.setDouble(2, expenseBreakdown.getTravelCost());
            stmt.setDouble(3, expenseBreakdown.getFoodCost());
            stmt.setDouble(4, expenseBreakdown.getStayCost());
            stmt.setDouble(5, expenseBreakdown.getShoppingCost());
            stmt.setDouble(6, expenseBreakdown.getEntertainmentCost());
            stmt.setDouble(7, expenseBreakdown.getLocalCommuteCost());
            stmt.setDouble(8, expenseBreakdown.getTotalCost());
            stmt.setInt(9, expenseBreakdown.getId());
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }
    
    /**
     * Deletes an expense breakdown from the database
     * @param id The ID of the expense breakdown to delete
     * @return true if the deletion was successful, false otherwise
     * @throws SQLException if a database error occurs
     */
    public boolean deleteExpenseBreakdown(int id) throws SQLException {
        String sql = "DELETE FROM expense_breakdown WHERE id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }
    
    /**
     * Maps a ResultSet row to an ExpenseBreakdown object
     * @param rs The ResultSet containing expense breakdown data
     * @return An ExpenseBreakdown object
     * @throws SQLException if a database error occurs
     */
    private ExpenseBreakdown mapResultSetToExpenseBreakdown(ResultSet rs) throws SQLException {
        return new ExpenseBreakdown(
            rs.getInt("id"),
            rs.getInt("trip_id"),
            rs.getDouble("travel_cost"),
            rs.getDouble("food_cost"),
            rs.getDouble("stay_cost"),
            rs.getDouble("shopping_cost"),
            rs.getDouble("entertainment_cost"),
            rs.getDouble("local_commute_cost"),
            rs.getDouble("total_cost"),
            rs.getTimestamp("created_at"),
            rs.getTimestamp("updated_at")
        );
    }
}