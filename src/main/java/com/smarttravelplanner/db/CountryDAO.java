package com.smarttravelplanner.db;

import com.smarttravelplanner.model.Country;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CountryDAO extends BaseDAO {
    
    /**
     * Creates a new country in the database
     * @param country The country to create
     * @return The ID of the created country, or -1 if creation failed
     * @throws SQLException if a database error occurs
     */
    public int createCountry(Country country) throws SQLException {
        String sql = "INSERT INTO countries (name) VALUES (?) RETURNING id";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, country.getName());
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        }
        return -1;
    }
    
    /**
     * Retrieves a country by its ID
     * @param id The ID of the country to retrieve
     * @return The Country object, or null if not found
     * @throws SQLException if a database error occurs
     */
    public Country getCountryById(int id) throws SQLException {
        String sql = "SELECT * FROM countries WHERE id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToCountry(rs);
                }
            }
        }
        return null;
    }
    
    /**
     * Retrieves a country by its name
     * @param name The name of the country to retrieve
     * @return The Country object, or null if not found
     * @throws SQLException if a database error occurs
     */
    public Country getCountryByName(String name) throws SQLException {
        String sql = "SELECT * FROM countries WHERE name = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, name);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToCountry(rs);
                }
            }
        }
        return null;
    }
    
    /**
     * Retrieves all countries from the database
     * @return A list of all countries
     * @throws SQLException if a database error occurs
     */
    public List<Country> getAllCountries() throws SQLException {
        List<Country> countries = new ArrayList<>();
        String sql = "SELECT * FROM countries ORDER BY name";
        
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                countries.add(mapResultSetToCountry(rs));
            }
        }
        return countries;
    }
    
    /**
     * Updates a country in the database
     * @param country The country to update
     * @return true if the update was successful, false otherwise
     * @throws SQLException if a database error occurs
     */
    public boolean updateCountry(Country country) throws SQLException {
        String sql = "UPDATE countries SET name = ?, updated_at = CURRENT_TIMESTAMP WHERE id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, country.getName());
            stmt.setInt(2, country.getId());
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }
    
    /**
     * Deletes a country from the database
     * @param id The ID of the country to delete
     * @return true if the deletion was successful, false otherwise
     * @throws SQLException if a database error occurs
     */
    public boolean deleteCountry(int id) throws SQLException {
        String sql = "DELETE FROM countries WHERE id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }
    
    /**
     * Maps a ResultSet row to a Country object
     * @param rs The ResultSet containing country data
     * @return A Country object
     * @throws SQLException if a database error occurs
     */
    private Country mapResultSetToCountry(ResultSet rs) throws SQLException {
        return new Country(
            rs.getInt("id"),
            rs.getString("name"),
            rs.getTimestamp("created_at"),
            rs.getTimestamp("updated_at")
        );
    }
}