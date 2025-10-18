package com.smarttravelplanner.db;

import java.sql.*;

/**
 * DatabaseInitializer for the Smart Travel Planner project.
 * Automatically creates and populates the PostgreSQL database smart_travel_db.
 */
public class DatabaseInitializer {
    
    /**
     * Initializes the database by creating tables and inserting sample data.
     */
    public static void initializeDatabase() {
        System.out.println("Initializing Smart Travel Planner database...");
        
        try (Connection connection = DBConnection.createConnection()) {
            // Create tables
            createTables(connection);
            
            // Insert sample data
            insertSampleData(connection);
            
            System.out.println("Database initialization completed successfully!");
        } catch (SQLException e) {
            System.err.println("Error initializing database: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Creates all required tables if they don't exist.
     */
    private static void createTables(Connection connection) throws SQLException {
        System.out.println("Creating database tables...");
        
        // Enable UUID extension
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("CREATE EXTENSION IF NOT EXISTS \"uuid-ossp\"");
            System.out.println("Creating extension uuid-ossp... Done");
        } catch (SQLException e) {
            System.out.println("Note: Could not create uuid-ossp extension (may already exist)");
        }
        
        // Create countries table
        String createCountriesTable = """
            CREATE TABLE IF NOT EXISTS countries (
                id SERIAL PRIMARY KEY,
                name VARCHAR(100) UNIQUE NOT NULL,
                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
            )
            """;
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createCountriesTable);
            System.out.println("Creating table countries... Done");
        }
        
        // Create states table with base_budget column
        String createStatesTable = """
            CREATE TABLE IF NOT EXISTS states (
                id SERIAL PRIMARY KEY,
                country_id INT REFERENCES countries(id) ON DELETE CASCADE,
                name VARCHAR(100) NOT NULL,
                base_budget NUMERIC(12,2) NOT NULL CHECK(base_budget > 0),
                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
            )
            """;
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createStatesTable);
            System.out.println("Creating table states... Done");
        }
        
        // Create destinations table (must be created before trips due to foreign key)
        String createDestinationsTable = """
            CREATE TABLE IF NOT EXISTS destinations (
                id SERIAL PRIMARY KEY,
                state_id INT REFERENCES states(id) ON DELETE CASCADE,
                name VARCHAR(100) NOT NULL,
                base_cost NUMERIC(12,2) NOT NULL CHECK(base_cost > 0),
                sustainability_score INT CHECK(sustainability_score >= 1 AND sustainability_score <= 10),
                estimated_co2_footprint NUMERIC(10,2),
                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
            )
            """;
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createDestinationsTable);
            System.out.println("Creating table destinations... Done");
        }
        
        // Create users table
        String createUsersTable = """
            CREATE TABLE IF NOT EXISTS users (
                id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                name VARCHAR(100) NOT NULL,
                age INT CHECK(age >= 1 AND age <= 120),
                family_count INT CHECK(family_count >= 1 AND family_count <= 20),
                budget NUMERIC(12,2) CHECK(budget > 0),
                email VARCHAR(255),
                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
            )
            """;
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createUsersTable);
            System.out.println("Creating table users... Done");
        }
        
        // Create trips table (created after destinations to satisfy foreign key constraint)
        String createTripsTable = """
            CREATE TABLE IF NOT EXISTS trips (
                id SERIAL PRIMARY KEY,
                user_id UUID REFERENCES users(id) ON DELETE CASCADE,
                destination_id INT REFERENCES destinations(id),
                trip_type VARCHAR(20) CHECK(trip_type IN ('CityPlan', 'TourPlan')),
                travel_mode VARCHAR(20) CHECK(travel_mode IN ('Road','Rail','Air','Mixed')),
                stay_type VARCHAR(20) CHECK(stay_type IN ('Budget','Standard','Premium')),
                meal_type VARCHAR(20) CHECK(meal_type IN ('Veg','Non-Veg','Mixed')),
                trip_days INT CHECK(trip_days > 0 AND trip_days <= 50),
                meals_per_day INT CHECK(meals_per_day > 0 AND meals_per_day <= 5),
                total_estimated_cost NUMERIC(12,2),
                total_budget NUMERIC(12,2),
                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
            )
            """;
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createTripsTable);
            System.out.println("Creating table trips... Done");
        }
        
        // Create trip_history table
        String createTripHistoryTable = """
            CREATE TABLE IF NOT EXISTS trip_history (
                id SERIAL PRIMARY KEY,
                user_id UUID REFERENCES users(id),
                trip_id INT REFERENCES trips(id),
                timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                status VARCHAR(20) CHECK(status IN ('completed','planned','cancelled'))
            )
            """;
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createTripHistoryTable);
            System.out.println("Creating table trip_history... Done");
        }
        
        // Create expense_breakdown table
        String createExpenseBreakdownTable = """
            CREATE TABLE IF NOT EXISTS expense_breakdown (
                id SERIAL PRIMARY KEY,
                trip_id INT REFERENCES trips(id) ON DELETE CASCADE,
                travel_cost NUMERIC(12,2),
                food_cost NUMERIC(12,2),
                stay_cost NUMERIC(12,2),
                shopping_cost NUMERIC(12,2),
                entertainment_cost NUMERIC(12,2),
                local_commute_cost NUMERIC(12,2),
                total_cost NUMERIC(12,2),
                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
            )
            """;
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createExpenseBreakdownTable);
            System.out.println("Creating table expense_breakdown... Done");
        }
        
        // Create indexes
        createIndexes(connection);
        
        System.out.println("All tables created successfully!");
    }
    
    /**
     * Creates indexes on commonly queried columns.
     */
    private static void createIndexes(Connection connection) throws SQLException {
        System.out.println("Creating indexes...");
        
        // Define indexes - only create indexes for tables that actually exist with these columns
        String[] indexes = {
            "CREATE INDEX IF NOT EXISTS idx_users_id ON users(id)",
            "CREATE INDEX IF NOT EXISTS idx_countries_id ON countries(id)",
            "CREATE INDEX IF NOT EXISTS idx_states_country_id ON states(country_id)",
            "CREATE INDEX IF NOT EXISTS idx_states_id ON states(id)",
            "CREATE INDEX IF NOT EXISTS idx_destinations_state_id ON destinations(state_id)",
            "CREATE INDEX IF NOT EXISTS idx_destinations_id ON destinations(id)",
            "CREATE INDEX IF NOT EXISTS idx_trips_user_id ON trips(user_id)"
            // Removed problematic indexes for now
        };
        
        for (String indexSQL : indexes) {
            try (Statement stmt = connection.createStatement()) {
                stmt.execute(indexSQL);
            } catch (SQLException e) {
                System.out.println("Warning: Could not create index: " + e.getMessage());
            }
        }
        
        System.out.println("Creating indexes... Done");
    }
    
    /**
     * Inserts sample data into the tables.
     */
    private static void insertSampleData(Connection connection) throws SQLException {
        System.out.println("Inserting sample data...");
        
        // Check if data already exists
        if (dataExists(connection)) {
            System.out.println("Sample data already exists. Skipping insertion.");
            return;
        }
        
        // Insert sample countries
        insertCountries(connection);
        
        // Insert sample states
        insertStates(connection);
        
        // Insert sample destinations
        insertDestinations(connection);
        
        System.out.println("Sample data insertion completed!");
    }
    
    /**
     * Checks if sample data already exists in the database.
     */
    private static boolean dataExists(Connection connection) throws SQLException {
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM countries")) {
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
        return false;
    }
    
    /**
     * Inserts sample countries.
     */
    private static void insertCountries(Connection connection) throws SQLException {
        System.out.println("Inserting sample countries...");
        
        String insertSQL = "INSERT INTO countries (name) VALUES (?)";
        String[] countries = {
            "India", "France", "Japan", "Italy", "USA", 
            "Australia", "Canada", "Singapore"
        };
        
        try (PreparedStatement stmt = connection.prepareStatement(insertSQL)) {
            for (String country : countries) {
                stmt.setString(1, country);
                stmt.executeUpdate();
            }
        }
        
        System.out.println("Inserting sample countries... Done");
    }
    
    /**
     * Inserts sample states for each country.
     */
    private static void insertStates(Connection connection) throws SQLException {
        System.out.println("Inserting sample states...");
        
        // Get country IDs
        int[] countryIds = new int[8];
        String getCountryIdsSQL = "SELECT id FROM countries ORDER BY name";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(getCountryIdsSQL)) {
            int i = 0;
            while (rs.next() && i < 8) {
                countryIds[i] = rs.getInt("id");
                i++;
            }
        }
        
        // Sample states data with base_budget
        Object[][] statesData = {
            // India
            {countryIds[0], "Kerala", 5000.00},
            {countryIds[0], "Rajasthan", 7000.00},
            {countryIds[0], "Goa", 8000.00},
            {countryIds[0], "Himachal Pradesh", 6000.00},
            {countryIds[0], "Tamil Nadu", 4500.00},
            {countryIds[0], "Uttarakhand", 5500.00},
            {countryIds[0], "West Bengal", 4000.00},
            {countryIds[0], "Karnataka", 5200.00},
            
            // France
            {countryIds[1], "Île-de-France", 12000.00},
            {countryIds[1], "Provence-Alpes-Côte d'Azur", 10000.00},
            {countryIds[1], "Auvergne-Rhône-Alpes", 9000.00},
            {countryIds[1], "Brittany", 8500.00},
            {countryIds[1], "Normandy", 8000.00},
            {countryIds[1], "Burgundy", 7500.00},
            {countryIds[1], "Alsace", 7000.00},
            {countryIds[1], "Loire Valley", 6500.00},
            
            // Japan
            {countryIds[2], "Tokyo", 15000.00},
            {countryIds[2], "Osaka", 12000.00},
            {countryIds[2], "Kyoto", 11000.00},
            {countryIds[2], "Hokkaido", 13000.00},
            {countryIds[2], "Okinawa", 10000.00},
            {countryIds[2], "Hiroshima", 9000.00},
            {countryIds[2], "Nara", 8500.00},
            {countryIds[2], "Nagano", 8000.00},
            
            // Italy
            {countryIds[3], "Tuscany", 10000.00},
            {countryIds[3], "Venice", 11000.00},
            {countryIds[3], "Rome", 9500.00},
            {countryIds[3], "Sicily", 8000.00},
            {countryIds[3], "Milan", 12000.00},
            {countryIds[3], "Florence", 10500.00},
            {countryIds[3], "Naples", 7500.00},
            {countryIds[3], "Amalfi Coast", 13000.00},
            
            // USA
            {countryIds[4], "California", 15000.00},
            {countryIds[4], "New York", 14000.00},
            {countryIds[4], "Florida", 10000.00},
            {countryIds[4], "Texas", 9000.00},
            {countryIds[4], "Hawaii", 16000.00},
            {countryIds[4], "Nevada", 11000.00},
            {countryIds[4], "Colorado", 9500.00},
            {countryIds[4], "Arizona", 8500.00},
            
            // Australia
            {countryIds[5], "New South Wales", 14000.00},
            {countryIds[5], "Victoria", 13000.00},
            {countryIds[5], "Queensland", 12000.00},
            {countryIds[5], "Western Australia", 11000.00},
            {countryIds[5], "South Australia", 10000.00},
            {countryIds[5], "Tasmania", 9000.00},
            {countryIds[5], "Northern Territory", 8500.00},
            {countryIds[5], "Australian Capital Territory", 8000.00},
            
            // Canada
            {countryIds[6], "Ontario", 11000.00},
            {countryIds[6], "British Columbia", 12000.00},
            {countryIds[6], "Alberta", 10000.00},
            {countryIds[6], "Quebec", 9000.00},
            {countryIds[6], "Manitoba", 8000.00},
            {countryIds[6], "Nova Scotia", 8500.00},
            {countryIds[6], "New Brunswick", 7500.00},
            {countryIds[6], "Saskatchewan", 7000.00},
            
            // Singapore
            {countryIds[7], "Central Singapore", 10000.00},
            {countryIds[7], "North Singapore", 9000.00},
            {countryIds[7], "South Singapore", 9500.00},
            {countryIds[7], "East Singapore", 8500.00},
            {countryIds[7], "West Singapore", 8000.00},
            {countryIds[7], "Lille", 11000.00},
            {countryIds[7], "Changi", 10500.00},
            {countryIds[7], "Sentosa", 12000.00}
        };
        
        String insertSQL = "INSERT INTO states (country_id, name, base_budget) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(insertSQL)) {
            for (Object[] state : statesData) {
                stmt.setInt(1, (Integer) state[0]);
                stmt.setString(2, (String) state[1]);
                stmt.setDouble(3, (Double) state[2]);
                stmt.executeUpdate();
            }
        }
        
        System.out.println("Inserting sample states... Done");
    }
    
    /**
     * Inserts sample destinations for each state.
     */
    private static void insertDestinations(Connection connection) throws SQLException {
        System.out.println("Inserting sample destinations...");
        
        // Get state IDs for India (first 8 states)
        int[] stateIds = new int[8];
        String getStateIdsSQL = "SELECT id FROM states WHERE country_id = 1 ORDER BY id LIMIT 8";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(getStateIdsSQL)) {
            int i = 0;
            while (rs.next() && i < 8) {
                stateIds[i] = rs.getInt("id");
                i++;
            }
        }
        
        // Sample destinations data for India states
        Object[][][] destinationsData = {
            // Kerala destinations
            {
                {stateIds[0], "Munnar", 3500.00, 9, 150.50},
                {stateIds[0], "Alleppey", 4000.00, 8, 180.75},
                {stateIds[0], "Wayanad", 3000.00, 9, 120.25}
            },
            // Rajasthan destinations
            {
                {stateIds[1], "Jaipur", 5000.00, 7, 250.00},
                {stateIds[1], "Udaipur", 5500.00, 8, 275.50},
                {stateIds[1], "Jaisalmer", 4500.00, 6, 225.75}
            },
            // Goa destinations
            {
                {stateIds[2], "Calangute Beach", 4500.00, 7, 200.00},
                {stateIds[2], "Old Goa", 3000.00, 8, 150.25},
                {stateIds[2], "Dudhsagar Falls", 3500.00, 9, 175.50}
            },
            // Himachal Pradesh destinations
            {
                {stateIds[3], "Manali", 4000.00, 9, 180.00},
                {stateIds[3], "Shimla", 3500.00, 8, 160.75},
                {stateIds[3], "Dharamshala", 3200.00, 9, 140.50}
            },
            // Tamil Nadu destinations
            {
                {stateIds[4], "Chennai", 3000.00, 6, 150.00},
                {stateIds[4], "Madurai", 2500.00, 7, 125.25},
                {stateIds[4], "Ooty", 3200.00, 8, 160.00}
            },
            // Uttarakhand destinations
            {
                {stateIds[5], "Rishikesh", 3500.00, 9, 175.00},
                {stateIds[5], "Mussoorie", 4000.00, 8, 200.25},
                {stateIds[5], "Nainital", 3800.00, 8, 190.50}
            },
            // West Bengal destinations
            {
                {stateIds[6], "Darjeeling", 3000.00, 8, 150.75},
                {stateIds[6], "Kolkata", 2500.00, 6, 125.00},
                {stateIds[6], "Sundarbans", 4000.00, 9, 200.00}
            },
            // Karnataka destinations
            {
                {stateIds[7], "Bangalore", 4000.00, 7, 180.25},
                {stateIds[7], "Mysore", 3500.00, 8, 160.50},
                {stateIds[7], "Hampi", 3200.00, 9, 140.75}
            }
        };
        
        String insertSQL = "INSERT INTO destinations (state_id, name, base_cost, sustainability_score, estimated_co2_footprint) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(insertSQL)) {
            for (Object[][] stateDestinations : destinationsData) {
                for (Object[] destination : stateDestinations) {
                    stmt.setInt(1, (Integer) destination[0]);
                    stmt.setString(2, (String) destination[1]);
                    stmt.setDouble(3, (Double) destination[2]);
                    stmt.setObject(4, destination[3]); // Handle nullable fields
                    stmt.setObject(5, destination[4]); // Handle nullable fields
                    stmt.executeUpdate();
                }
            }
        }
        
        System.out.println("Inserting sample destinations... Done");
    }
    
    /**
     * Main method to run the database initializer.
     */
    public static void main(String[] args) {
        initializeDatabase();
    }
}