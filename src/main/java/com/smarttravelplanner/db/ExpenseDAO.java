package com.smarttravelplanner.db;

import com.smarttravelplanner.model.Expense;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExpenseDAO extends BaseDAO {
    
    /**
     * Creates a new expense in the database
     * @param expense The expense to create
     * @return The ID of the created expense, or -1 if creation failed
     * @throws SQLException if a database error occurs
     */
    public int createExpense(Expense expense) throws SQLException {
        String sql = "INSERT INTO expenses (trip_id, category, amount, description) VALUES (?, ?, ?, ?) RETURNING id";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, expense.getTripId());
            stmt.setString(2, expense.getCategory());
            stmt.setDouble(3, expense.getAmount());
            stmt.setString(4, expense.getDescription());
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        }
        return -1;
    }
    
    /**
     * Retrieves an expense by its ID
     * @param id The ID of the expense to retrieve
     * @return The Expense object, or null if not found
     * @throws SQLException if a database error occurs
     */
    public Expense getExpenseById(int id) throws SQLException {
        String sql = "SELECT * FROM expenses WHERE id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToExpense(rs);
                }
            }
        }
        return null;
    }
    
    /**
     * Retrieves all expenses for a specific trip
     * @param tripId The ID of the trip
     * @return A list of expenses for the trip
     * @throws SQLException if a database error occurs
     */
    public List<Expense> getExpensesByTripId(int tripId) throws SQLException {
        List<Expense> expenses = new ArrayList<>();
        String sql = "SELECT * FROM expenses WHERE trip_id = ? ORDER BY created_at";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, tripId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    expenses.add(mapResultSetToExpense(rs));
                }
            }
        }
        return expenses;
    }
    
    /**
     * Retrieves all expenses from the database
     * @return A list of all expenses
     * @throws SQLException if a database error occurs
     */
    public List<Expense> getAllExpenses() throws SQLException {
        List<Expense> expenses = new ArrayList<>();
        String sql = "SELECT * FROM expenses ORDER BY created_at";
        
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                expenses.add(mapResultSetToExpense(rs));
            }
        }
        return expenses;
    }
    
    /**
     * Updates an expense in the database
     * @param expense The expense to update
     * @return true if the update was successful, false otherwise
     * @throws SQLException if a database error occurs
     */
    public boolean updateExpense(Expense expense) throws SQLException {
        String sql = "UPDATE expenses SET trip_id = ?, category = ?, amount = ?, description = ?, updated_at = CURRENT_TIMESTAMP WHERE id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, expense.getTripId());
            stmt.setString(2, expense.getCategory());
            stmt.setDouble(3, expense.getAmount());
            stmt.setString(4, expense.getDescription());
            stmt.setInt(5, expense.getId());
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }
    
    /**
     * Deletes an expense from the database
     * @param id The ID of the expense to delete
     * @return true if the deletion was successful, false otherwise
     * @throws SQLException if a database error occurs
     */
    public boolean deleteExpense(int id) throws SQLException {
        String sql = "DELETE FROM expenses WHERE id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }
    
    /**
     * Maps a ResultSet row to an Expense object
     * @param rs The ResultSet containing expense data
     * @return An Expense object
     * @throws SQLException if a database error occurs
     */
    private Expense mapResultSetToExpense(ResultSet rs) throws SQLException {
        return new Expense(
            rs.getInt("id"),
            rs.getInt("trip_id"),
            rs.getString("category"),
            rs.getString("description"),
            rs.getDouble("amount"),
            rs.getTimestamp("expense_date"),
            rs.getTimestamp("created_at"),
            rs.getTimestamp("updated_at")
        );
    }
}