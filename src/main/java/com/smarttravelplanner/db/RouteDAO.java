package com.smarttravelplanner.db;

import com.smarttravelplanner.model.Route;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RouteDAO {
    
    private Connection getConnection() throws SQLException {
        // Database connection parameters
        String url = "jdbc:postgresql://localhost:5432/smart_travel_db";
        String username = "postgres";
        String password = "Lithu19!"; // Updated to match application.properties
        
        return DriverManager.getConnection(url, username, password);
    }
    
    /**
     * Creates a new route in the database
     * @param route The route to create
     * @return The ID of the created route, or -1 if creation failed
     * @throws SQLException if a database error occurs
     */
    public int createRoute(Route route) throws SQLException {
        String sql = "INSERT INTO routes (trip_id, origin, destination, mode_of_transport, distance, estimated_time, cost) VALUES (?, ?, ?, ?, ?, ?, ?) RETURNING id";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, route.getTripId());
            stmt.setString(2, route.getOrigin());
            stmt.setString(3, route.getDestination());
            stmt.setString(4, route.getModeOfTransport());
            stmt.setDouble(5, route.getDistance());
            stmt.setDouble(6, route.getEstimatedTime());
            stmt.setDouble(7, route.getCost());
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        }
        return -1;
    }
    
    /**
     * Retrieves a route by its ID
     * @param id The ID of the route to retrieve
     * @return The Route object, or null if not found
     * @throws SQLException if a database error occurs
     */
    public Route getRouteById(int id) throws SQLException {
        String sql = "SELECT * FROM routes WHERE id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToRoute(rs);
                }
            }
        }
        return null;
    }
    
    /**
     * Retrieves all routes for a specific trip
     * @param tripId The ID of the trip
     * @return A list of routes for the trip
     * @throws SQLException if a database error occurs
     */
    public List<Route> getRoutesByTripId(int tripId) throws SQLException {
        List<Route> routes = new ArrayList<>();
        String sql = "SELECT * FROM routes WHERE trip_id = ? ORDER BY created_at";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, tripId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    routes.add(mapResultSetToRoute(rs));
                }
            }
        }
        return routes;
    }
    
    /**
     * Retrieves all routes from the database
     * @return A list of all routes
     * @throws SQLException if a database error occurs
     */
    public List<Route> getAllRoutes() throws SQLException {
        List<Route> routes = new ArrayList<>();
        String sql = "SELECT * FROM routes ORDER BY created_at";
        
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                routes.add(mapResultSetToRoute(rs));
            }
        }
        return routes;
    }
    
    /**
     * Updates a route in the database
     * @param route The route to update
     * @return true if the update was successful, false otherwise
     * @throws SQLException if a database error occurs
     */
    public boolean updateRoute(Route route) throws SQLException {
        String sql = "UPDATE routes SET trip_id = ?, origin = ?, destination = ?, mode_of_transport = ?, distance = ?, estimated_time = ?, cost = ?, updated_at = CURRENT_TIMESTAMP WHERE id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, route.getTripId());
            stmt.setString(2, route.getOrigin());
            stmt.setString(3, route.getDestination());
            stmt.setString(4, route.getModeOfTransport());
            stmt.setDouble(5, route.getDistance());
            stmt.setDouble(6, route.getEstimatedTime());
            stmt.setDouble(7, route.getCost());
            stmt.setInt(8, route.getId());
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }
    
    /**
     * Deletes a route from the database
     * @param id The ID of the route to delete
     * @return true if the deletion was successful, false otherwise
     * @throws SQLException if a database error occurs
     */
    public boolean deleteRoute(int id) throws SQLException {
        String sql = "DELETE FROM routes WHERE id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }
    
    /**
     * Maps a ResultSet row to a Route object
     * @param rs The ResultSet containing route data
     * @return A Route object
     * @throws SQLException if a database error occurs
     */
    private Route mapResultSetToRoute(ResultSet rs) throws SQLException {
        return new Route(
            rs.getInt("id"),
            rs.getInt("trip_id"),
            rs.getString("origin"),
            rs.getString("destination"),
            rs.getString("mode_of_transport"),
            rs.getDouble("distance"),
            rs.getDouble("estimated_time"),
            rs.getDouble("cost"),
            rs.getTimestamp("created_at"),
            rs.getTimestamp("updated_at")
        );
    }
}