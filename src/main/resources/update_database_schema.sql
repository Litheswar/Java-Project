-- Update database schema to match the application expectations

-- Drop existing tables in correct order to avoid foreign key constraints
DROP TABLE IF EXISTS expense_breakdown CASCADE;
DROP TABLE IF EXISTS trip_history CASCADE;
DROP TABLE IF EXISTS trips CASCADE;
DROP TABLE IF EXISTS destinations CASCADE;
DROP TABLE IF EXISTS states CASCADE;
DROP TABLE IF EXISTS countries CASCADE;
DROP TABLE IF EXISTS users CASCADE;

-- Create extension for UUID support
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Create users table
CREATE TABLE users (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(100) NOT NULL,
    age INT CHECK(age >= 1 AND age <= 120),
    family_count INT CHECK(family_count >= 1 AND family_count <= 20),
    budget NUMERIC(12,2) CHECK(budget > 0),
    email VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create countries table
CREATE TABLE countries (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) UNIQUE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create states table
CREATE TABLE states (
    id SERIAL PRIMARY KEY,
    country_id INT REFERENCES countries(id) ON DELETE CASCADE,
    name VARCHAR(100) NOT NULL,
    base_budget NUMERIC(12,2) NOT NULL CHECK(base_budget > 0),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create destinations table
CREATE TABLE destinations (
    id SERIAL PRIMARY KEY,
    state_id INT REFERENCES states(id) ON DELETE CASCADE,
    name VARCHAR(100) NOT NULL,
    base_cost NUMERIC(12,2) NOT NULL CHECK(base_cost > 0),
    sustainability_score INT CHECK(sustainability_score >= 1 AND sustainability_score <= 10),
    estimated_co2_footprint NUMERIC(10,2),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create trips table
CREATE TABLE trips (
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
);

-- Create trip_history table
CREATE TABLE trip_history (
    id SERIAL PRIMARY KEY,
    user_id UUID REFERENCES users(id),
    trip_id INT REFERENCES trips(id),
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(20) CHECK(status IN ('completed','planned','cancelled'))
);

-- Create expense_breakdown table
CREATE TABLE expense_breakdown (
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
);

-- Create indexes
CREATE INDEX IF NOT EXISTS idx_users_id ON users(id);
CREATE INDEX IF NOT EXISTS idx_countries_id ON countries(id);
CREATE INDEX IF NOT EXISTS idx_states_country_id ON states(country_id);
CREATE INDEX IF NOT EXISTS idx_states_id ON states(id);
CREATE INDEX IF NOT EXISTS idx_destinations_state_id ON destinations(state_id);
CREATE INDEX IF NOT EXISTS idx_destinations_id ON destinations(id);
CREATE INDEX IF NOT EXISTS idx_trips_user_id ON trips(user_id);
CREATE INDEX IF NOT EXISTS idx_trips_destination_id ON trips(destination_id);
CREATE INDEX IF NOT EXISTS idx_trip_history_user_id ON trip_history(user_id);
CREATE INDEX IF NOT EXISTS idx_trip_history_trip_id ON trip_history(trip_id);
CREATE INDEX IF NOT EXISTS idx_expense_breakdown_trip_id ON expense_breakdown(trip_id);

-- Insert sample data
-- Sample countries
INSERT INTO countries (name) VALUES 
('India'),
('France'),
('Japan'),
('Italy'),
('USA'),
('Australia'),
('Canada'),
('Brazil');

-- Sample states for India
INSERT INTO states (country_id, name, base_budget) VALUES 
(1, 'Kerala', 5000.00),
(1, 'Rajasthan', 7000.00),
(1, 'Goa', 8000.00),
(1, 'Himachal Pradesh', 6000.00),
(1, 'Tamil Nadu', 4500.00),
(1, 'Uttarakhand', 5500.00),
(1, 'West Bengal', 4000.00),
(1, 'Karnataka', 5200.00);

-- Sample destinations for Kerala
INSERT INTO destinations (state_id, name, base_cost, sustainability_score, estimated_co2_footprint) VALUES 
(1, 'Munnar', 3500.00, 9, 150.50),
(1, 'Alleppey', 4000.00, 8, 180.75),
(1, 'Wayanad', 3000.00, 9, 120.25);

-- Sample user
INSERT INTO users (name, age, family_count, budget, email) VALUES 
('John Doe', 35, 4, 25000.00, 'john.doe@example.com');

-- Sample trip
INSERT INTO trips (user_id, destination_id, trip_type, travel_mode, stay_type, meal_type, trip_days, meals_per_day, total_estimated_cost, total_budget) VALUES 
((SELECT id FROM users WHERE name = 'John Doe'), 1, 'CityPlan', 'Mixed', 'Standard', 'Mixed', 7, 3, 18000.00, 25000.00);

-- Sample trip history
INSERT INTO trip_history (user_id, trip_id, status) VALUES 
((SELECT id FROM users WHERE name = 'John Doe'), 1, 'completed');

-- Sample expense breakdown
INSERT INTO expense_breakdown (trip_id, travel_cost, food_cost, stay_cost, shopping_cost, entertainment_cost, local_commute_cost, total_cost) VALUES 
(1, 5000.00, 3000.00, 7000.00, 1500.00, 1000.00, 500.00, 18000.00);

-- Verify data
SELECT 'Users' as table_name, COUNT(*) as row_count FROM users
UNION ALL
SELECT 'Countries' as table_name, COUNT(*) as row_count FROM countries
UNION ALL
SELECT 'States' as table_name, COUNT(*) as row_count FROM states
UNION ALL
SELECT 'Destinations' as table_name, COUNT(*) as row_count FROM destinations
UNION ALL
SELECT 'Trips' as table_name, COUNT(*) as row_count FROM trips
UNION ALL
SELECT 'Trip History' as table_name, COUNT(*) as row_count FROM trip_history
UNION ALL
SELECT 'Expense Breakdown' as table_name, COUNT(*) as row_count FROM expense_breakdown;