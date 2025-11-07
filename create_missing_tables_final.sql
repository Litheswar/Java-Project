-- Create Trip History Table
CREATE TABLE IF NOT EXISTS trip_history (
    id SERIAL PRIMARY KEY,
    user_id BIGINT REFERENCES users(id) ON DELETE CASCADE,
    trip_id BIGINT REFERENCES trips(id) ON DELETE CASCADE,
    date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create Expense Breakdown Table
CREATE TABLE IF NOT EXISTS expense_breakdown (
    id SERIAL PRIMARY KEY,
    trip_id BIGINT REFERENCES trips(id) ON DELETE CASCADE,
    category TEXT,
    amount NUMERIC
);

-- Create Alerts Table
CREATE TABLE IF NOT EXISTS alerts (
    id SERIAL PRIMARY KEY,
    user_id BIGINT REFERENCES users(id) ON DELETE CASCADE,
    trip_id BIGINT REFERENCES trips(id) ON DELETE CASCADE,
    alert_type TEXT,
    message TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create Routes Table
CREATE TABLE IF NOT EXISTS routes (
    id SERIAL PRIMARY KEY,
    trip_id BIGINT REFERENCES trips(id) ON DELETE CASCADE,
    start_destination UUID REFERENCES destinations(id) ON DELETE CASCADE,
    end_destination UUID REFERENCES destinations(id) ON DELETE CASCADE,
    route_order INT
);

-- Create Indexes for Faster Queries
CREATE INDEX IF NOT EXISTS idx_trip_history_user_id ON trip_history(user_id);
CREATE INDEX IF NOT EXISTS idx_expense_breakdown_trip_id ON expense_breakdown(trip_id);
CREATE INDEX IF NOT EXISTS idx_routes_trip_id ON routes(trip_id);
CREATE INDEX IF NOT EXISTS idx_routes_start_destination ON routes(start_destination);
CREATE INDEX IF NOT EXISTS idx_routes_end_destination ON routes(end_destination);