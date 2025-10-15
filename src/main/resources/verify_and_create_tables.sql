-- Connect to the database
\c smart_travel_db;

-- Create extension for UUID support
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Check if countries table exists, create if not
CREATE TABLE IF NOT EXISTS countries (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name TEXT UNIQUE NOT NULL
);

-- Check if states table exists, create if not
CREATE TABLE IF NOT EXISTS states (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name TEXT NOT NULL,
    country_id UUID REFERENCES countries(id) ON DELETE CASCADE
);

-- Check if destinations table exists, create if not
CREATE TABLE IF NOT EXISTS destinations (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name TEXT NOT NULL,
    state_id UUID REFERENCES states(id) ON DELETE CASCADE,
    base_cost NUMERIC NOT NULL,
    sustainability_score NUMERIC,
    co2_footprint NUMERIC
);

-- Check if trip_history table exists, create if not
CREATE TABLE IF NOT EXISTS trip_history (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID REFERENCES users(id) ON DELETE CASCADE,
    trip_id UUID REFERENCES trips(id) ON DELETE CASCADE,
    date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Check if expense_breakdown table exists, create if not
CREATE TABLE IF NOT EXISTS expense_breakdown (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    trip_id UUID REFERENCES trips(id) ON DELETE CASCADE,
    category TEXT NOT NULL,
    amount NUMERIC NOT NULL
);

-- Check if alerts table exists, create if not
CREATE TABLE IF NOT EXISTS alerts (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID REFERENCES users(id) ON DELETE CASCADE,
    trip_id UUID REFERENCES trips(id) ON DELETE CASCADE,
    alert_type TEXT,
    message TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Check if routes table exists, create if not
CREATE TABLE IF NOT EXISTS routes (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    trip_id UUID REFERENCES trips(id) ON DELETE CASCADE,
    start_destination UUID REFERENCES destinations(id),
    end_destination UUID REFERENCES destinations(id),
    route_order INT NOT NULL
);

-- Add indexes on columns frequently used in queries
CREATE INDEX IF NOT EXISTS idx_states_country_id ON states(country_id);
CREATE INDEX IF NOT EXISTS idx_destinations_state_id ON destinations(state_id);
CREATE INDEX IF NOT EXISTS idx_trip_history_user_id ON trip_history(user_id);
CREATE INDEX IF NOT EXISTS idx_expense_breakdown_trip_id ON expense_breakdown(trip_id);

-- List all tables in the public schema
\dt public.*

-- Describe each table structure to verify
\d countries
\d states
\d destinations
\d trip_history
\d expense_breakdown
\d alerts
\d routes