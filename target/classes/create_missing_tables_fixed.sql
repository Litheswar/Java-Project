-- Connect to the database
\c smart_travel_db;

-- Enable UUID extension
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Create Countries Table
CREATE TABLE IF NOT EXISTS countries (
    id SERIAL PRIMARY KEY,
    name TEXT UNIQUE NOT NULL
);

-- Create States Table
CREATE TABLE IF NOT EXISTS states (
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    country_id INTEGER REFERENCES countries(id) ON DELETE CASCADE
);

-- Create Destinations Table
CREATE TABLE IF NOT EXISTS destinations (
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    state_id INTEGER REFERENCES states(id) ON DELETE CASCADE,
    base_cost NUMERIC,
    sustainability_score NUMERIC,
    co2_footprint NUMERIC
);

-- Create Trip History Table
CREATE TABLE IF NOT EXISTS trip_history (
    id SERIAL PRIMARY KEY,
    user_id UUID REFERENCES users(id) ON DELETE CASCADE,
    trip_id UUID REFERENCES trips(id) ON DELETE CASCADE,
    date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create Expense Breakdown Table
CREATE TABLE IF NOT EXISTS expense_breakdown (
    id SERIAL PRIMARY KEY,
    trip_id UUID REFERENCES trips(id) ON DELETE CASCADE,
    category TEXT,
    amount NUMERIC
);

-- Create Alerts Table
CREATE TABLE IF NOT EXISTS alerts (
    id SERIAL PRIMARY KEY,
    user_id UUID REFERENCES users(id) ON DELETE CASCADE,
    trip_id UUID REFERENCES trips(id) ON DELETE CASCADE,
    alert_type TEXT,
    message TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create Routes Table
CREATE TABLE IF NOT EXISTS routes (
    id SERIAL PRIMARY KEY,
    trip_id UUID REFERENCES trips(id) ON DELETE CASCADE,
    start_destination INTEGER REFERENCES destinations(id) ON DELETE CASCADE,
    end_destination INTEGER REFERENCES destinations(id) ON DELETE CASCADE,
    route_order INT
);

-- Create Indexes for Faster Queries
CREATE INDEX IF NOT EXISTS idx_states_country_id ON states(country_id);
CREATE INDEX IF NOT EXISTS idx_destinations_state_id ON destinations(state_id);
CREATE INDEX IF NOT EXISTS idx_trip_history_user_id ON trip_history(user_id);
CREATE INDEX IF NOT EXISTS idx_expense_breakdown_trip_id ON expense_breakdown(trip_id);
CREATE INDEX IF NOT EXISTS idx_routes_trip_id ON routes(trip_id);
CREATE INDEX IF NOT EXISTS idx_routes_start_destination ON routes(start_destination);
CREATE INDEX IF NOT EXISTS idx_routes_end_destination ON routes(end_destination);

-- List all tables to confirm creation
\dt