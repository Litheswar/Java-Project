-- Create database
CREATE DATABASE smart_travel_db;

-- Connect to the database
\c smart_travel_db;

-- Create destinations table
CREATE TABLE destinations (
    id SERIAL PRIMARY KEY,
    country VARCHAR(100) NOT NULL,
    state VARCHAR(100) NOT NULL,
    city VARCHAR(100) NOT NULL,
    base_cost DECIMAL(10, 2) NOT NULL
);

-- Insert sample data
INSERT INTO destinations (country, state, city, base_cost) VALUES
('France', 'Hauts-de-France', 'Lille', 5000.00),
('Japan', 'Kyoto', 'Gion', 4800.00),
('India', 'Kerala', 'Munnar', 3500.00);

-- Verify data
SELECT * FROM destinations;