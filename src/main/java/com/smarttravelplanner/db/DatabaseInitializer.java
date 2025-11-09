package com.smarttravelplanner.db;

import java.sql.*;
import java.util.UUID;

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
        
        try (Connection connection = DBConnection.getConnection()) {
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
        
        // Create trips table
        String createTripsTable = """
            CREATE TABLE IF NOT EXISTS trips (
                id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                user_id UUID REFERENCES users(id) ON DELETE CASCADE,
                destination_id UUID REFERENCES destinations(id),
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
        
        // Create expenses table
        String createExpensesTable = """
            CREATE TABLE IF NOT EXISTS expenses (
                id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                trip_id UUID REFERENCES trips(id) ON DELETE CASCADE,
                category VARCHAR(50) NOT NULL,
                amount NUMERIC(12,2) NOT NULL,
                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
            )
            """;
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createExpensesTable);
            System.out.println("Creating table expenses... Done");
        }
        
        // Create activities table
        String createActivitiesTable = """
            CREATE TABLE IF NOT EXISTS activities (
                id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                trip_id UUID REFERENCES trips(id) ON DELETE CASCADE,
                name VARCHAR(100) NOT NULL,
                description TEXT,
                cost NUMERIC(12,2),
                duration INTERVAL,
                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
            )
            """;
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createActivitiesTable);
            System.out.println("Creating table activities... Done");
        }
        
        // Create countries table
        String createCountriesTable = """
            CREATE TABLE IF NOT EXISTS countries (
                id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                name TEXT NOT NULL UNIQUE
            )
            """;
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createCountriesTable);
            System.out.println("Creating table countries... Done");
        }
        
        // Create states table
        String createStatesTable = """
            CREATE TABLE IF NOT EXISTS states (
                id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                name TEXT NOT NULL,
                country_id UUID REFERENCES countries(id) ON DELETE CASCADE
            )
            """;
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createStatesTable);
            System.out.println("Creating table states... Done");
        }
        
        // Create destinations table
        String createDestinationsTable = """
            CREATE TABLE IF NOT EXISTS destinations (
                id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                name TEXT NOT NULL,
                state_id UUID REFERENCES states(id) ON DELETE CASCADE,
                base_cost NUMERIC NOT NULL,
                sustainability_score NUMERIC,
                co2_footprint NUMERIC
            )
            """;
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createDestinationsTable);
            System.out.println("Creating table destinations... Done");
        }
        
        // Create trip_history table
        String createTripHistoryTable = """
            CREATE TABLE IF NOT EXISTS trip_history (
                id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                user_id UUID REFERENCES users(id) ON DELETE CASCADE,
                trip_id UUID REFERENCES trips(id) ON DELETE CASCADE,
                date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
            )
            """;
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createTripHistoryTable);
            System.out.println("Creating table trip_history... Done");
        }
        
        // Create expense_breakdown table
        String createExpenseBreakdownTable = """
            CREATE TABLE IF NOT EXISTS expense_breakdown (
                id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                trip_id UUID REFERENCES trips(id) ON DELETE CASCADE,
                category TEXT NOT NULL,
                amount NUMERIC NOT NULL
            )
            """;
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createExpenseBreakdownTable);
            System.out.println("Creating table expense_breakdown... Done");
        }
        
        // Create alerts table
        String createAlertsTable = """
            CREATE TABLE IF NOT EXISTS alerts (
                id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                user_id UUID REFERENCES users(id) ON DELETE CASCADE,
                trip_id UUID REFERENCES trips(id) ON DELETE CASCADE,
                alert_type TEXT NOT NULL,
                message TEXT NOT NULL,
                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
            )
            """;
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createAlertsTable);
            System.out.println("Creating table alerts... Done");
        }
        
        // Create routes table
        String createRoutesTable = """
            CREATE TABLE IF NOT EXISTS routes (
                id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                trip_id UUID REFERENCES trips(id) ON DELETE CASCADE,
                start_destination UUID REFERENCES destinations(id) ON DELETE CASCADE,
                end_destination UUID REFERENCES destinations(id) ON DELETE CASCADE,
                route_order INTEGER NOT NULL
            )
            """;
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createRoutesTable);
            System.out.println("Creating table routes... Done");
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
        
        // Define indexes
        String[] indexes = {
            "CREATE INDEX IF NOT EXISTS idx_users_id ON users(id)",
            "CREATE INDEX IF NOT EXISTS idx_countries_id ON countries(id)",
            "CREATE INDEX IF NOT EXISTS idx_states_country_id ON states(country_id)",
            "CREATE INDEX IF NOT EXISTS idx_states_id ON states(id)",
            "CREATE INDEX IF NOT EXISTS idx_destinations_state_id ON destinations(state_id)",
            "CREATE INDEX IF NOT EXISTS idx_destinations_id ON destinations(id)",
            "CREATE INDEX IF NOT EXISTS idx_trips_user_id ON trips(user_id)",
            "CREATE INDEX IF NOT EXISTS idx_trips_destination_id ON trips(destination_id)",
            "CREATE INDEX IF NOT EXISTS idx_trip_history_user_id ON trip_history(user_id)",
            "CREATE INDEX IF NOT EXISTS idx_trip_history_trip_id ON trip_history(trip_id)",
            "CREATE INDEX IF NOT EXISTS idx_expense_breakdown_trip_id ON expense_breakdown(trip_id)",
            "CREATE INDEX IF NOT EXISTS idx_state_country ON states(country_id)",
            "CREATE INDEX IF NOT EXISTS idx_destination_state ON destinations(state_id)",
            "CREATE INDEX IF NOT EXISTS idx_trip_history_user ON trip_history(user_id)",
            "CREATE INDEX IF NOT EXISTS idx_expense_trip ON expense_breakdown(trip_id)",
            "CREATE INDEX IF NOT EXISTS idx_alerts_user_id ON alerts(user_id)",
            "CREATE INDEX IF NOT EXISTS idx_alerts_trip_id ON alerts(trip_id)",
            "CREATE INDEX IF NOT EXISTS idx_routes_trip_id ON routes(trip_id)",
            "CREATE INDEX IF NOT EXISTS idx_routes_start_destination ON routes(start_destination)",
            "CREATE INDEX IF NOT EXISTS idx_routes_end_destination ON routes(end_destination)"
        };
        
        for (String indexSQL : indexes) {
            try (Statement stmt = connection.createStatement()) {
                stmt.execute(indexSQL);
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
        
        // Insert sample user
        insertSampleUser(connection);
        
        // Insert sample trip
        // Note: We need destinations to be created first before creating trips
        // This is handled by the order of operations above
        
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
        
        String[] countries = {
            "India", "France", "Japan", "Italy", "USA", 
            "Australia", "Canada", "Brazil"
        };
        
        String insertSQL = "INSERT INTO countries (name) VALUES (?)";
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
        UUID[] countryIds = new UUID[8];
        String getCountryIdsSQL = "SELECT id FROM countries ORDER BY name";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(getCountryIdsSQL)) {
            int i = 0;
            while (rs.next() && i < 8) {
                countryIds[i] = (UUID) rs.getObject("id");
                i++;
            }
        }
        
        // Insert states for each country (8 states per country)
        String[][] statesData = {
            // India
            {"Kerala", "Rajasthan", "Goa", "Himachal Pradesh", "Tamil Nadu", "Uttarakhand", "West Bengal", "Karnataka"},
            // France
            {"Île-de-France", "Provence-Alpes-Côte d'Azur", "Auvergne-Rhône-Alpes", "Brittany", "Normandy", "Burgundy", "Alsace", "Loire Valley"},
            // Japan
            {"Tokyo", "Osaka", "Kyoto", "Hokkaido", "Okinawa", "Hiroshima", "Nara", "Nagano"},
            // Italy
            {"Tuscany", "Venice", "Rome", "Sicily", "Milan", "Florence", "Naples", "Amalfi Coast"},
            // USA
            {"California", "New York", "Florida", "Texas", "Hawaii", "Nevada", "Colorado", "Arizona"},
            // Australia
            {"New South Wales", "Victoria", "Queensland", "Western Australia", "South Australia", "Tasmania", "Northern Territory", "Australian Capital Territory"},
            // Canada
            {"Ontario", "British Columbia", "Alberta", "Quebec", "Manitoba", "Nova Scotia", "New Brunswick", "Saskatchewan"},
            // Brazil
            {"Rio de Janeiro", "São Paulo", "Bahia", "Amazonas", "Minas Gerais", "Paraná", "Pernambuco", "Ceará"}
        };
        
        String insertSQL = "INSERT INTO states (name, country_id) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(insertSQL)) {
            for (int i = 0; i < countryIds.length; i++) {
                for (int j = 0; j < 8; j++) {
                    stmt.setString(1, statesData[i][j]);
                    stmt.setObject(2, countryIds[i]);
                    stmt.executeUpdate();
                }
            }
        }
        
        System.out.println("Inserting sample states... Done");
    }
    
    /**
     * Inserts sample destinations for each state.
     */
    private static void insertDestinations(Connection connection) throws SQLException {
        System.out.println("Inserting sample destinations...");
        
        // Get state IDs
        UUID[] stateIds = new UUID[64];
        String getStateIdsSQL = "SELECT id FROM states ORDER BY id LIMIT 64";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(getStateIdsSQL)) {
            int i = 0;
            while (rs.next() && i < 64) {
                stateIds[i] = (UUID) rs.getObject("id");
                i++;
            }
        }
        
        // Sample destinations data (2-3 per state)
        String[][][] destinationsData = {
            // India - Kerala
            {{"Munnar", "3500.00", "9", "150.50"}, {"Alleppey", "4000.00", "8", "180.75"}, {"Wayanad", "3000.00", "9", "120.25"}},
            // India - Rajasthan
            {{"Jaipur", "5000.00", "7", "250.00"}, {"Udaipur", "5500.00", "8", "275.50"}, {"Jaisalmer", "4500.00", "6", "225.75"}},
            // India - Goa
            {{"Calangute Beach", "4500.00", "7", "200.00"}, {"Old Goa", "3000.00", "8", "150.25"}, {"Dudhsagar Falls", "3500.00", "9", "175.50"}},
            // India - Himachal Pradesh
            {{"Manali", "4000.00", "9", "180.00"}, {"Shimla", "3500.00", "8", "160.75"}, {"Dharamshala", "3200.00", "9", "140.50"}},
            // India - Tamil Nadu
            {{"Chennai", "3000.00", "6", "150.00"}, {"Madurai", "2500.00", "7", "125.25"}, {"Ooty", "3200.00", "8", "160.00"}},
            // India - Uttarakhand
            {{"Rishikesh", "3500.00", "9", "175.00"}, {"Mussoorie", "4000.00", "8", "200.25"}, {"Nainital", "3800.00", "8", "190.50"}},
            // India - West Bengal
            {{"Darjeeling", "3000.00", "8", "150.75"}, {"Kolkata", "2500.00", "6", "125.00"}, {"Sundarbans", "4000.00", "9", "200.00"}},
            // India - Karnataka
            {{"Bangalore", "4000.00", "7", "180.25"}, {"Mysore", "3500.00", "8", "160.50"}, {"Hampi", "3200.00", "9", "140.75"}},
            // France - Île-de-France
            {{"Paris", "12000.00", "7", "500.00"}, {"Versailles", "10000.00", "8", "450.25"}, {"Disneyland Paris", "15000.00", "6", "600.50"}},
            // France - Provence-Alpes-Côte d'Azur
            {{"Nice", "10000.00", "7", "450.00"}, {"Cannes", "11000.00", "6", "480.25"}, {"Lavender Fields", "8000.00", "9", "350.50"}},
            // France - Auvergne-Rhône-Alpes
            {{"Lyon", "9000.00", "7", "400.00"}, {"Chamonix", "12000.00", "9", "550.25"}, {"Annecy", "8500.00", "8", "380.50"}},
            // France - Brittany
            {{"Saint-Malo", "8500.00", "8", "380.00"}, {"Carnac", "7500.00", "8", "350.25"}, {"Quiberon", "8000.00", "7", "370.50"}},
            // France - Normandy
            {{"Mont-Saint-Michel", "8000.00", "8", "360.00"}, {"Rouen", "7500.00", "7", "340.25"}, {"Honfleur", "7800.00", "8", "350.50"}},
            // France - Burgundy
            {{"Dijon", "7500.00", "7", "340.00"}, {"Beaune", "8000.00", "8", "360.25"}, {"Château de Chambord", "9000.00", "8", "400.50"}},
            // France - Alsace
            {{"Strasbourg", "7000.00", "7", "320.00"}, {"Colmar", "7500.00", "8", "340.25"}, {"Haut-Koenigsbourg Castle", "6500.00", "7", "300.50"}},
            // France - Loire Valley
            {{"Château de Chantilly", "6500.00", "8", "290.00"}, {"Château de Chenonceau", "7000.00", "8", "310.25"}, {"Château de Villandry", "6800.00", "8", "300.50"}},
            // Japan - Tokyo
            {{"Tokyo Disneyland", "15000.00", "6", "600.00"}, {"Senso-ji Temple", "12000.00", "8", "500.25"}, {"Shibuya Crossing", "13000.00", "7", "550.50"}},
            // Japan - Osaka
            {{"Osaka Castle", "12000.00", "8", "500.00"}, {"Dotonbori", "11000.00", "7", "480.25"}, {"Universal Studios Japan", "14000.00", "6", "580.50"}},
            // Japan - Kyoto
            {{"Kinkaku-ji (Golden Pavilion)", "11000.00", "9", "450.00"}, {"Fushimi Inari Shrine", "10000.00", "9", "420.25"}, {"Arashiyama Bamboo Grove", "10500.00", "9", "430.50"}},
            // Japan - Hokkaido
            {{"Sapporo", "13000.00", "8", "550.00"}, {"Furano", "12000.00", "9", "500.25"}, {"Asahiyama Zoo", "11500.00", "8", "480.50"}},
            // Japan - Okinawa
            {{"Shuri Castle", "10000.00", "8", "420.00"}, {"Churaumi Aquarium", "11000.00", "8", "450.25"}, {"Kokusai Street", "9500.00", "7", "400.50"}},
            // Japan - Hiroshima
            {{"Peace Memorial Park", "9000.00", "9", "380.00"}, {"Miyajima Island", "10000.00", "9", "420.25"}, {"Hiroshima Castle", "8500.00", "8", "360.50"}},
            // Japan - Nara
            {{"Todai-ji Temple", "8500.00", "9", "360.00"}, {"Nara Park", "8000.00", "9", "340.25"}, {"Kasuga Taisha Shrine", "8200.00", "9", "350.50"}},
            // Japan - Nagano
            {{"Zenko-ji Temple", "8000.00", "9", "340.00"}, {"Jigokudani Monkey Park", "8500.00", "9", "360.25"}, {"Matsumoto Castle", "7800.00", "8", "330.50"}},
            // Italy - Tuscany
            {{"Colosseum", "11000.00", "7", "480.00"}, {"Vatican City", "12000.00", "8", "500.25"}, {"Trevi Fountain", "10000.00", "7", "450.50"}},
            // Italy - Venice
            {{"St. Mark's Square", "11000.00", "8", "480.00"}, {"Grand Canal", "10500.00", "8", "460.25"}, {"Murano Island", "10000.00", "7", "440.50"}},
            // Italy - Rome
            {{"Pantheon", "9500.00", "8", "420.00"}, {"Roman Forum", "9000.00", "8", "400.25"}, {"Spanish Steps", "8500.00", "7", "380.50"}},
            // Italy - Sicily
            {{"Mount Etna", "8000.00", "9", "360.00"}, {"Valley of the Temples", "7500.00", "8", "340.25"}, {"Taormina", "8500.00", "8", "380.50"}},
            // Italy - Milan
            {{"Duomo di Milano", "12000.00", "8", "500.00"}, {"La Scala Opera House", "13000.00", "7", "550.25"}, {"Galleria Vittorio Emanuele II", "11000.00", "7", "480.50"}},
            // Italy - Florence
            {{"Uffizi Gallery", "10500.00", "8", "460.00"}, {"Ponte Vecchio", "10000.00", "8", "440.25"}, {"Duomo di Firenze", "11000.00", "8", "480.50"}},
            // Italy - Naples
            {{"Pompeii", "7500.00", "8", "340.00"}, {"Mount Vesuvius", "8000.00", "9", "360.25"}, {"Naples Historic Center", "7000.00", "7", "320.50"}},
            // Italy - Amalfi Coast
            {{"Positano", "13000.00", "8", "550.00"}, {"Ravello", "12000.00", "9", "500.25"}, {"Amalfi Cathedral", "11500.00", "8", "480.50"}},
            // USA - California
            {{"Hollywood", "15000.00", "6", "600.00"}, {"Disneyland", "16000.00", "6", "650.25"}, {"Santa Monica Pier", "14000.00", "7", "580.50"}},
            // USA - New York
            {{"Times Square", "14000.00", "7", "580.00"}, {"Central Park", "13000.00", "8", "550.25"}, {"Statue of Liberty", "13500.00", "8", "560.50"}},
            // USA - Florida
            {{"Walt Disney World", "16000.00", "6", "650.00"}, {"Miami Beach", "15000.00", "7", "600.25"}, {"Everglades National Park", "12000.00", "9", "500.50"}},
            // USA - Texas
            {{"Alamo", "9000.00", "7", "380.00"}, {"Big Bend National Park", "8500.00", "9", "360.25"}, {"San Antonio River Walk", "9500.00", "8", "400.50"}},
            // USA - Hawaii
            {{"Waikiki Beach", "16000.00", "8", "650.00"}, {"Pearl Harbor", "15000.00", "8", "600.25"}, {"Haleakala National Park", "14000.00", "9", "580.50"}},
            // USA - Nevada
            {{"Las Vegas Strip", "11000.00", "6", "480.00"}, {"Hoover Dam", "10000.00", "8", "450.25"}, {"Red Rock Canyon", "9500.00", "9", "420.50"}},
            // USA - Colorado
            {{"Rocky Mountain National Park", "9500.00", "9", "420.00"}, {"Garden of the Gods", "9000.00", "9", "400.25"}, {"Mesa Verde National Park", "8500.00", "9", "380.50"}},
            // USA - Arizona
            {{"Grand Canyon", "8500.00", "9", "380.00"}, {"Antelope Canyon", "9000.00", "8", "400.25"}, {"Sedona", "8800.00", "8", "390.50"}},
            // Australia - New South Wales
            {{"Sydney Opera House", "14000.00", "8", "580.00"}, {"Bondi Beach", "13000.00", "8", "550.25"}, {"Blue Mountains", "12000.00", "9", "500.50"}},
            // Australia - Victoria
            {{"Great Ocean Road", "13000.00", "9", "550.00"}, {"Phillip Island", "12500.00", "9", "520.25"}, {"Yarra Valley", "12000.00", "8", "500.50"}},
            // Australia - Queensland
            {{"Great Barrier Reef", "15000.00", "9", "600.00"}, {"Daintree Rainforest", "14000.00", "10", "580.25"}, {"Gold Coast", "13500.00", "8", "560.50"}},
            // Australia - Western Australia
            {{"Perth", "11000.00", "8", "480.00"}, {"Rottnest Island", "11500.00", "9", "500.25"}, {"Pinnacles Desert", "10500.00", "9", "460.50"}},
            // Australia - South Australia
            {{"Adelaide", "10000.00", "8", "450.00"}, {"Barossa Valley", "10500.00", "8", "470.25"}, {"Kangaroo Island", "11000.00", "9", "480.50"}},
            // Australia - Tasmania
            {{"Port Arthur", "9000.00", "8", "400.00"}, {"Cradle Mountain", "9500.00", "9", "420.25"}, {"Freycinet National Park", "10000.00", "9", "450.50"}},
            // Australia - Northern Territory
            {{"Uluru", "8500.00", "9", "380.00"}, {"Kakadu National Park", "9000.00", "10", "400.25"}, {"Alice Springs", "8000.00", "8", "360.50"}},
            // Australia - Australian Capital Territory
            {{"Parliament House", "8000.00", "7", "360.00"}, {"Australian War Memorial", "7500.00", "8", "340.25"}, {"Lake Burley Griffin", "7000.00", "8", "320.50"}},
            // Canada - Ontario
            {{"Niagara Falls", "11000.00", "8", "480.00"}, {"CN Tower", "10500.00", "7", "460.25"}, {"Royal Ontario Museum", "10000.00", "8", "440.50"}},
            // Canada - British Columbia
            {{"Vancouver", "12000.00", "8", "500.00"}, {"Victoria", "11500.00", "8", "480.25"}, {"Whistler", "13000.00", "9", "550.50"}},
            // Canada - Alberta
            {{"Banff National Park", "12500.00", "9", "520.00"}, {"Jasper National Park", "12000.00", "9", "500.25"}, {"Calgary Stampede", "11500.00", "7", "480.50"}},
            // Canada - Quebec
            {{"Old Quebec", "9000.00", "8", "400.00"}, {"Montreal", "9500.00", "8", "420.25"}, {"Mont-Tremblant", "10000.00", "9", "450.50"}},
            // Canada - Manitoba
            {{"Winnipeg", "8000.00", "7", "360.00"}, {"Polar Bear Tours", "12000.00", "9", "500.25"}, {"Canadian Museum for Human Rights", "8500.00", "8", "380.50"}},
            // Canada - Nova Scotia
            {{"Halifax", "8500.00", "8", "380.00"}, {"Peggy's Cove", "8000.00", "9", "360.25"}, {"Cabot Trail", "9000.00", "9", "400.50"}},
            // Canada - New Brunswick
            {{"Hopewell Rocks", "7500.00", "9", "340.00"}, {"Fundy National Park", "8000.00", "9", "360.25"}, {"Saint John", "7000.00", "7", "320.50"}},
            // Canada - Saskatchewan
            {{"Saskatoon", "7000.00", "7", "320.00"}, {"Prince Albert National Park", "7500.00", "9", "340.25"}, {"Royal Saskatchewan Museum", "6500.00", "8", "300.50"}},
            // Brazil - Rio de Janeiro
            {{"Christ the Redeemer", "7000.00", "8", "320.00"}, {"Copacabana Beach", "6500.00", "7", "300.25"}, {"Sugarloaf Mountain", "7500.00", "8", "340.50"}},
            // Brazil - São Paulo
            {{"Ibirapuera Park", "6500.00", "8", "300.00"}, {"MASP", "7000.00", "8", "320.25"}, {"Paulista Avenue", "6800.00", "7", "310.50"}},
            // Brazil - Bahia
            {{"Pelourinho", "6000.00", "8", "280.00"}, {"Chapada Diamantina", "6500.00", "9", "300.25"}, {"Morro de São Paulo", "5800.00", "8", "270.50"}},
            // Brazil - Amazonas
            {{"Manaus", "5500.00", "9", "260.00"}, {"Meeting of Waters", "6000.00", "9", "280.25"}, {"Anavilhanas Archipelago", "5800.00", "10", "270.50"}},
            // Brazil - Minas Gerais
            {{"Ouro Preto", "5500.00", "8", "260.00"}, {"Inhotim", "6000.00", "9", "280.25"}, {"Pampulha Modern Ensemble", "5800.00", "8", "270.50"}},
            // Brazil - Paraná
            {{"Iguaçu Falls", "6500.00", "9", "300.00"}, {"Curitiba", "6000.00", "8", "280.25"}, {"Foz do Iguaçu", "6200.00", "9", "290.50"}},
            // Brazil - Pernambuco
            {{"Olinda", "5500.00", "8", "260.00"}, {"Porto de Galinhas", "6000.00", "8", "280.25"}, {"Fernando de Noronha", "8000.00", "9", "350.50"}},
            // Brazil - Ceará
            {{"Jericoacoara", "5000.00", "9", "240.00"}, {"Fortaleza", "5500.00", "7", "260.25"}, {"Lençóis Maranhenses", "5800.00", "9", "270.50"}}
        };
        
        String insertSQL = "INSERT INTO destinations (name, state_id, base_cost, sustainability_score, co2_footprint) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(insertSQL)) {
            int stateIndex = 0;
            for (String[][] stateDestinations : destinationsData) {
                for (String[] destination : stateDestinations) {
                    stmt.setString(1, destination[0]);
                    stmt.setObject(2, stateIds[stateIndex]);
                    stmt.setDouble(3, Double.parseDouble(destination[1]));
                    stmt.setObject(4, destination[2]); // Keep as string for now, will convert to numeric
                    stmt.setObject(5, destination[3]); // Keep as string for now, will convert to numeric
                    stmt.executeUpdate();
                }
                stateIndex++;
            }
        }
        
        System.out.println("Inserting sample destinations... Done");
    }
    
    /**
     * Inserts a sample user.
     */
    private static UUID insertSampleUser(Connection connection) throws SQLException {
        System.out.println("Inserting sample user...");
        
        String insertSQL = "INSERT INTO users (name, age, family_count, budget, email) VALUES (?, ?, ?, ?, ?) RETURNING id";
        try (PreparedStatement stmt = connection.prepareStatement(insertSQL)) {
            stmt.setString(1, "John Doe");
            stmt.setInt(2, 35);
            stmt.setInt(3, 4);
            stmt.setDouble(4, 25000.00);
            stmt.setString(5, "john.doe@example.com");
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    UUID userId = (UUID) rs.getObject("id");
                    System.out.println("Inserting sample user... Done");
                    return userId;
                }
            }
        }
        
        throw new SQLException("Failed to insert sample user");
    }
    
    /**
     * Main method to run the database initializer.
     */
    public static void main(String[] args) {
        initializeDatabase();
    }
}