-- Create extension for UUID support
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Create users table (already exists, but showing for completeness)
CREATE TABLE IF NOT EXISTS users (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(100) NOT NULL,
    age INT CHECK(age >= 1 AND age <= 120),
    family_count INT CHECK(family_count >= 1 AND family_count <= 20),
    budget NUMERIC(12,2) CHECK(budget > 0),
    email VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create trips table (already exists, but showing for completeness)
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
);

-- Create expenses table (already exists, but showing for completeness)
CREATE TABLE IF NOT EXISTS expenses (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    trip_id UUID REFERENCES trips(id) ON DELETE CASCADE,
    category VARCHAR(50) NOT NULL,
    amount NUMERIC(12,2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create activities table (already exists, but showing for completeness)
CREATE TABLE IF NOT EXISTS activities (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    trip_id UUID REFERENCES trips(id) ON DELETE CASCADE,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    cost NUMERIC(12,2),
    duration INTERVAL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 1. countries table
CREATE TABLE IF NOT EXISTS countries (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name TEXT NOT NULL UNIQUE
);

-- 2. states table
CREATE TABLE IF NOT EXISTS states (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name TEXT NOT NULL,
    country_id UUID REFERENCES countries(id) ON DELETE CASCADE
);

-- 3. destinations table
CREATE TABLE IF NOT EXISTS destinations (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name TEXT NOT NULL,
    state_id UUID REFERENCES states(id) ON DELETE CASCADE,
    base_cost NUMERIC NOT NULL,
    sustainability_score NUMERIC,
    co2_footprint NUMERIC
);

-- 4. trip_history table
CREATE TABLE IF NOT EXISTS trip_history (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID REFERENCES users(id) ON DELETE CASCADE,
    trip_id UUID REFERENCES trips(id) ON DELETE CASCADE,
    date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 5. expense_breakdown table
CREATE TABLE IF NOT EXISTS expense_breakdown (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    trip_id UUID REFERENCES trips(id) ON DELETE CASCADE,
    category TEXT NOT NULL,
    amount NUMERIC NOT NULL
);

-- Optional Indexes
CREATE INDEX IF NOT EXISTS idx_state_country ON states(country_id);
CREATE INDEX IF NOT EXISTS idx_destination_state ON destinations(state_id);
CREATE INDEX IF NOT EXISTS idx_trip_history_user ON trip_history(user_id);
CREATE INDEX IF NOT EXISTS idx_expense_trip ON expense_breakdown(trip_id);

-- Verify tables
\dt