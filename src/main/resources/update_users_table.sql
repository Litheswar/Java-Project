-- Update users table to match the application expectations

-- Drop the users table
DROP TABLE IF EXISTS users CASCADE;

-- Create users table with correct schema
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

-- Create index
CREATE INDEX IF NOT EXISTS idx_users_id ON users(id);

-- Insert sample user
INSERT INTO users (name, age, family_count, budget, email) VALUES 
('John Doe', 35, 4, 25000.00, 'john.doe@example.com');