package com.smarttravelplanner.db;

import java.sql.*;
import java.util.UUID;

public class SchemaTest {
    
    public static void main(String[] args) {
        try {
            // Test database connection
            Connection connection = DBConnection.createConnection();
            System.out.println("Connected to database successfully!");
            
            // Test users table
            testUsersTable(connection);
            
            // Test countries table
            testCountriesTable(connection);
            
            // Test states table
            testStatesTable(connection);
            
            // Test destinations table
            testDestinationsTable(connection);
            
            // Test trips table
            testTripsTable(connection);
            
            // Test trip_history table
            testTripHistoryTable(connection);
            
            // Test expense_breakdown table
            testExpenseBreakdownTable(connection);
            
            // Test affordable destinations query
            testAffordableDestinationsQuery(connection);
            
            // Test sustainability score query
            testSustainabilityScoreQuery(connection);
            
            System.out.println("All tests passed successfully!");
            
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static void testUsersTable(Connection connection) throws SQLException {
        System.out.println("\nTesting users table...");
        
        // Insert a test user
        String insertSQL = "INSERT INTO users (name, age, family_count, budget, email) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(insertSQL)) {
            stmt.setString(1, "Test User");
            stmt.setInt(2, 30);
            stmt.setInt(3, 2);
            stmt.setDouble(4, 10000.00);
            stmt.setString(5, "test@example.com");
            stmt.executeUpdate();
        }
        
        // Query the user
        String selectSQL = "SELECT * FROM users WHERE name = ?";
        try (PreparedStatement stmt = connection.prepareStatement(selectSQL)) {
            stmt.setString(1, "Test User");
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    System.out.println("User ID: " + rs.getObject("id", UUID.class));
                    System.out.println("Name: " + rs.getString("name"));
                    System.out.println("Age: " + rs.getInt("age"));
                    System.out.println("Family count: " + rs.getInt("family_count"));
                    System.out.println("Budget: " + rs.getDouble("budget"));
                    System.out.println("Email: " + rs.getString("email"));
                }
            }
        }
        
        System.out.println("Users table test passed!");
    }
    
    private static void testCountriesTable(Connection connection) throws SQLException {
        System.out.println("\nTesting countries table...");
        
        // Count countries
        String countSQL = "SELECT COUNT(*) FROM countries";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(countSQL)) {
            if (rs.next()) {
                int count = rs.getInt(1);
                System.out.println("Number of countries: " + count);
                if (count != 8) {
                    throw new SQLException("Expected 8 countries, found " + count);
                }
            }
        }
        
        System.out.println("Countries table test passed!");
    }
    
    private static void testStatesTable(Connection connection) throws SQLException {
        System.out.println("\nTesting states table...");
        
        // Count states
        String countSQL = "SELECT COUNT(*) FROM states";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(countSQL)) {
            if (rs.next()) {
                int count = rs.getInt(1);
                System.out.println("Number of states: " + count);
                if (count != 64) {
                    throw new SQLException("Expected 64 states, found " + count);
                }
            }
        }
        
        System.out.println("States table test passed!");
    }
    
    private static void testDestinationsTable(Connection connection) throws SQLException {
        System.out.println("\nTesting destinations table...");
        
        // Count destinations
        String countSQL = "SELECT COUNT(*) FROM destinations";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(countSQL)) {
            if (rs.next()) {
                int count = rs.getInt(1);
                System.out.println("Number of destinations: " + count);
                if (count < 192) {
                    throw new SQLException("Expected at least 192 destinations, found " + count);
                }
            }
        }
        
        System.out.println("Destinations table test passed!");
    }
    
    private static void testTripsTable(Connection connection) throws SQLException {
        System.out.println("\nTesting trips table...");
        
        // Insert a test trip
        String insertSQL = "INSERT INTO trips (user_id, destination_id, trip_type, travel_mode, stay_type, meal_type, trip_days, meals_per_day, total_estimated_cost, total_budget) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS)) {
            // Get a user ID
            String userIdSQL = "SELECT id FROM users WHERE name = ?";
            UUID userId = null;
            try (PreparedStatement userIdStmt = connection.prepareStatement(userIdSQL)) {
                userIdStmt.setString(1, "Test User");
                try (ResultSet rs = userIdStmt.executeQuery()) {
                    if (rs.next()) {
                        userId = rs.getObject("id", UUID.class);
                    }
                }
            }
            
            if (userId == null) {
                throw new SQLException("Could not find user for test");
            }
            
            stmt.setObject(1, userId);
            stmt.setInt(2, 1);
            stmt.setString(3, "CityPlan");
            stmt.setString(4, "Mixed");
            stmt.setString(5, "Standard");
            stmt.setString(6, "Mixed");
            stmt.setInt(7, 5);
            stmt.setInt(8, 3);
            stmt.setDouble(9, 8000.00);
            stmt.setDouble(10, 10000.00);
            stmt.executeUpdate();
        }
        
        System.out.println("Trips table test passed!");
    }
    
    private static void testTripHistoryTable(Connection connection) throws SQLException {
        System.out.println("\nTesting trip_history table...");
        
        // Insert a test trip history record
        String insertSQL = "INSERT INTO trip_history (user_id, trip_id, status) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(insertSQL)) {
            // Get a user ID
            String userIdSQL = "SELECT id FROM users WHERE name = ?";
            UUID userId = null;
            try (PreparedStatement userIdStmt = connection.prepareStatement(userIdSQL)) {
                userIdStmt.setString(1, "Test User");
                try (ResultSet rs = userIdStmt.executeQuery()) {
                    if (rs.next()) {
                        userId = rs.getObject("id", UUID.class);
                    }
                }
            }
            
            stmt.setObject(1, userId);
            stmt.setInt(2, 1);
            stmt.setString(3, "planned");
            stmt.executeUpdate();
        }
        
        System.out.println("Trip history table test passed!");
    }
    
    private static void testExpenseBreakdownTable(Connection connection) throws SQLException {
        System.out.println("\nTesting expense_breakdown table...");
        
        // Insert a test expense breakdown
        String insertSQL = "INSERT INTO expense_breakdown (trip_id, travel_cost, food_cost, stay_cost, shopping_cost, entertainment_cost, local_commute_cost, total_cost) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(insertSQL)) {
            stmt.setInt(1, 1);
            stmt.setDouble(2, 2000.00);
            stmt.setDouble(3, 1500.00);
            stmt.setDouble(4, 3000.00);
            stmt.setDouble(5, 500.00);
            stmt.setDouble(6, 500.00);
            stmt.setDouble(7, 300.00);
            stmt.setDouble(8, 8000.00);
            stmt.executeUpdate();
        }
        
        System.out.println("Expense breakdown table test passed!");
    }
    
    private static void testAffordableDestinationsQuery(Connection connection) throws SQLException {
        System.out.println("\nTesting affordable destinations query...");
        
        // Query affordable destinations
        String querySQL = "SELECT d.name AS destination, s.name AS state, c.name AS country, d.base_cost " +
                         "FROM destinations d " +
                         "JOIN states s ON d.state_id = s.id " +
                         "JOIN countries c ON s.country_id = c.id " +
                         "WHERE d.base_cost <= ? " +
                         "ORDER BY d.base_cost";
        try (PreparedStatement stmt = connection.prepareStatement(querySQL)) {
            stmt.setDouble(1, 5000.00);
            try (ResultSet rs = stmt.executeQuery()) {
                System.out.println("Affordable destinations (<= ₹5000):");
                int count = 0;
                while (rs.next() && count < 5) {
                    System.out.println("  " + rs.getString("destination") + 
                                     ", " + rs.getString("state") + 
                                     ", " + rs.getString("country") + 
                                     " (₹" + rs.getDouble("base_cost") + ")");
                    count++;
                }
            }
        }
        
        System.out.println("Affordable destinations query test passed!");
    }
    
    private static void testSustainabilityScoreQuery(Connection connection) throws SQLException {
        System.out.println("\nTesting sustainability score query...");
        
        // Query destinations with high sustainability scores
        String querySQL = "SELECT d.name AS destination, s.name AS state, c.name AS country, d.sustainability_score " +
                         "FROM destinations d " +
                         "JOIN states s ON d.state_id = s.id " +
                         "JOIN countries c ON s.country_id = c.id " +
                         "WHERE d.sustainability_score >= ? " +
                         "ORDER BY d.sustainability_score DESC";
        try (PreparedStatement stmt = connection.prepareStatement(querySQL)) {
            stmt.setInt(1, 9);
            try (ResultSet rs = stmt.executeQuery()) {
                System.out.println("High sustainability destinations (score >= 9):");
                int count = 0;
                while (rs.next() && count < 5) {
                    System.out.println("  " + rs.getString("destination") + 
                                     ", " + rs.getString("state") + 
                                     ", " + rs.getString("country") + 
                                     " (Score: " + rs.getInt("sustainability_score") + "/10)");
                    count++;
                }
            }
        }
        
        System.out.println("Sustainability score query test passed!");
    }
}