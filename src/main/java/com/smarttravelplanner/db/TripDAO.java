package com.smarttravelplanner.db;

import com.smarttravelplanner.model.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class TripDAO {
    
    private final DataSource dataSource;
    
    @Autowired
    public TripDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    private Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
    
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
    
    // Backward compatibility method
    public int insertTrip(int userId, int countryId, int stateId, int destinationId, int tripDays, 
                         int mealsPerDay, String travelMode, String stayType, String mealType, 
                         double totalEstimatedCost) throws SQLException {
        String sql = "INSERT INTO trips (user_id, country_id, state_id, destination_id, trip_days, " +
                     "meals_per_day, travel_mode, stay_type, meal_type, total_estimated_cost) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING id";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setObject(1, userId);
            stmt.setInt(2, countryId);
            stmt.setInt(3, stateId);
            stmt.setInt(4, destinationId);
            stmt.setInt(5, tripDays);
            stmt.setInt(6, mealsPerDay);
            stmt.setString(7, travelMode);
            stmt.setString(8, stayType);
            stmt.setString(9, mealType);
            stmt.setDouble(10, totalEstimatedCost);
            
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
    
    // Backward compatibility method
    public List<String> getAllTripsAsString() throws SQLException {
        List<String> trips = new ArrayList<>();
        String sql = "SELECT t.*, u.name as user_name, c.name as country_name, s.name as state_name, " +
                     "d.name as destination_name FROM trips t " +
                     "JOIN users u ON t.user_id = u.id " +
                     "JOIN countries c ON t.country_id = c.id " +
                     "JOIN states s ON t.state_id = s.id " +
                     "JOIN destinations d ON t.destination_id = d.id";
        
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                String trip = String.format("ID: %d, User: %s, Country: %s, State: %s, Destination: %s, " +
                                          "Days: %d, Meals/Day: %d, Travel Mode: %s, Stay Type: %s, " +
                                          "Meal Type: %s, Estimated Cost: %.2f",
                    rs.getInt("id"),
                    rs.getString("user_name"),
                    rs.getString("country_name"),
                    rs.getString("state_name"),
                    rs.getString("destination_name"),
                    rs.getInt("trip_days"),
                    rs.getInt("meals_per_day"),
                    rs.getString("travel_mode"),
                    rs.getString("stay_type"),
                    rs.getString("meal_type"),
                    rs.getDouble("total_estimated_cost"));
                trips.add(trip);
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