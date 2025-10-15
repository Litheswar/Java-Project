-- Create database
CREATE DATABASE smart_travel_db;

-- Connect to the database
\c smart_travel_db;

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

-- Create indexes on frequently queried columns
CREATE INDEX idx_users_id ON users(id);
CREATE INDEX idx_countries_id ON countries(id);
CREATE INDEX idx_states_country_id ON states(country_id);
CREATE INDEX idx_states_id ON states(id);
CREATE INDEX idx_destinations_state_id ON destinations(state_id);
CREATE INDEX idx_destinations_id ON destinations(id);
CREATE INDEX idx_trips_user_id ON trips(user_id);
CREATE INDEX idx_trips_destination_id ON trips(destination_id);
CREATE INDEX idx_trip_history_user_id ON trip_history(user_id);
CREATE INDEX idx_trip_history_trip_id ON trip_history(trip_id);
CREATE INDEX idx_expense_breakdown_trip_id ON expense_breakdown(trip_id);

-- Insert sample countries
INSERT INTO countries (name) VALUES 
('India'),
('France'),
('Japan'),
('Italy'),
('USA'),
('Australia'),
('Canada'),
('Brazil');

-- Insert sample states for each country (8 states per country)
-- India
INSERT INTO states (country_id, name, base_budget) VALUES 
(1, 'Kerala', 5000.00),
(1, 'Rajasthan', 7000.00),
(1, 'Goa', 8000.00),
(1, 'Himachal Pradesh', 6000.00),
(1, 'Tamil Nadu', 4500.00),
(1, 'Uttarakhand', 5500.00),
(1, 'West Bengal', 4000.00),
(1, 'Karnataka', 5200.00);

-- France
INSERT INTO states (country_id, name, base_budget) VALUES 
(2, 'Île-de-France', 12000.00),
(2, 'Provence-Alpes-Côte d''Azur', 10000.00),
(2, 'Auvergne-Rhône-Alpes', 9000.00),
(2, 'Brittany', 8500.00),
(2, 'Normandy', 8000.00),
(2, 'Burgundy', 7500.00),
(2, 'Alsace', 7000.00),
(2, 'Loire Valley', 6500.00);

-- Japan
INSERT INTO states (country_id, name, base_budget) VALUES 
(3, 'Tokyo', 15000.00),
(3, 'Osaka', 12000.00),
(3, 'Kyoto', 11000.00),
(3, 'Hokkaido', 13000.00),
(3, 'Okinawa', 10000.00),
(3, 'Hiroshima', 9000.00),
(3, 'Nara', 8500.00),
(3, 'Nagano', 8000.00);

-- Italy
INSERT INTO states (country_id, name, base_budget) VALUES 
(4, 'Tuscany', 10000.00),
(4, 'Venice', 11000.00),
(4, 'Rome', 9500.00),
(4, 'Sicily', 8000.00),
(4, 'Milan', 12000.00),
(4, 'Florence', 10500.00),
(4, 'Naples', 7500.00),
(4, 'Amalfi Coast', 13000.00);

-- USA
INSERT INTO states (country_id, name, base_budget) VALUES 
(5, 'California', 15000.00),
(5, 'New York', 14000.00),
(5, 'Florida', 10000.00),
(5, 'Texas', 9000.00),
(5, 'Hawaii', 16000.00),
(5, 'Nevada', 11000.00),
(5, 'Colorado', 9500.00),
(5, 'Arizona', 8500.00);

-- Australia
INSERT INTO states (country_id, name, base_budget) VALUES 
(6, 'New South Wales', 14000.00),
(6, 'Victoria', 13000.00),
(6, 'Queensland', 12000.00),
(6, 'Western Australia', 11000.00),
(6, 'South Australia', 10000.00),
(6, 'Tasmania', 9000.00),
(6, 'Northern Territory', 8500.00),
(6, 'Australian Capital Territory', 8000.00);

-- Canada
INSERT INTO states (country_id, name, base_budget) VALUES 
(7, 'Ontario', 11000.00),
(7, 'British Columbia', 12000.00),
(7, 'Alberta', 10000.00),
(7, 'Quebec', 9000.00),
(7, 'Manitoba', 8000.00),
(7, 'Nova Scotia', 8500.00),
(7, 'New Brunswick', 7500.00),
(7, 'Saskatchewan', 7000.00);

-- Brazil
INSERT INTO states (country_id, name, base_budget) VALUES 
(8, 'Rio de Janeiro', 7000.00),
(8, 'São Paulo', 8000.00),
(8, 'Bahia', 6000.00),
(8, 'Amazonas', 5500.00),
(8, 'Minas Gerais', 6500.00),
(8, 'Paraná', 6000.00),
(8, 'Pernambuco', 5500.00),
(8, 'Ceará', 5000.00);

-- Insert sample destinations (2-3 per state)
-- Kerala destinations
INSERT INTO destinations (state_id, name, base_cost, sustainability_score, estimated_co2_footprint) VALUES 
(1, 'Munnar', 3500.00, 9, 150.50),
(1, 'Alleppey', 4000.00, 8, 180.75),
(1, 'Wayanad', 3000.00, 9, 120.25);

-- Rajasthan destinations
INSERT INTO destinations (state_id, name, base_cost, sustainability_score, estimated_co2_footprint) VALUES 
(2, 'Jaipur', 5000.00, 7, 250.00),
(2, 'Udaipur', 5500.00, 8, 275.50),
(2, 'Jaisalmer', 4500.00, 6, 225.75);

-- Goa destinations
INSERT INTO destinations (state_id, name, base_cost, sustainability_score, estimated_co2_footprint) VALUES 
(3, 'Calangute Beach', 4500.00, 7, 200.00),
(3, 'Old Goa', 3000.00, 8, 150.25),
(3, 'Dudhsagar Falls', 3500.00, 9, 175.50);

-- Himachal Pradesh destinations
INSERT INTO destinations (state_id, name, base_cost, sustainability_score, estimated_co2_footprint) VALUES 
(4, 'Manali', 4000.00, 9, 180.00),
(4, 'Shimla', 3500.00, 8, 160.75),
(4, 'Dharamshala', 3200.00, 9, 140.50);

-- Tamil Nadu destinations
INSERT INTO destinations (state_id, name, base_cost, sustainability_score, estimated_co2_footprint) VALUES 
(5, 'Chennai', 3000.00, 6, 150.00),
(5, 'Madurai', 2500.00, 7, 125.25),
(5, 'Ooty', 3200.00, 8, 160.00);

-- Uttarakhand destinations
INSERT INTO destinations (state_id, name, base_cost, sustainability_score, estimated_co2_footprint) VALUES 
(6, 'Rishikesh', 3500.00, 9, 175.00),
(6, 'Mussoorie', 4000.00, 8, 200.25),
(6, 'Nainital', 3800.00, 8, 190.50);

-- West Bengal destinations
INSERT INTO destinations (state_id, name, base_cost, sustainability_score, estimated_co2_footprint) VALUES 
(7, 'Darjeeling', 3000.00, 8, 150.75),
(7, 'Kolkata', 2500.00, 6, 125.00),
(7, 'Sundarbans', 4000.00, 9, 200.00);

-- Karnataka destinations
INSERT INTO destinations (state_id, name, base_cost, sustainability_score, estimated_co2_footprint) VALUES 
(8, 'Bangalore', 4000.00, 7, 180.25),
(8, 'Mysore', 3500.00, 8, 160.50),
(8, 'Hampi', 3200.00, 9, 140.75);

-- France destinations
INSERT INTO destinations (state_id, name, base_cost, sustainability_score, estimated_co2_footprint) VALUES 
(9, 'Paris', 12000.00, 7, 500.00),
(9, 'Versailles', 10000.00, 8, 450.25),
(9, 'Disneyland Paris', 15000.00, 6, 600.50);

-- Provence-Alpes-Côte d'Azur destinations
INSERT INTO destinations (state_id, name, base_cost, sustainability_score, estimated_co2_footprint) VALUES 
(10, 'Nice', 10000.00, 7, 450.00),
(10, 'Cannes', 11000.00, 6, 480.25),
(10, 'Lavender Fields', 8000.00, 9, 350.50);

-- Auvergne-Rhône-Alpes destinations
INSERT INTO destinations (state_id, name, base_cost, sustainability_score, estimated_co2_footprint) VALUES 
(11, 'Lyon', 9000.00, 7, 400.00),
(11, 'Chamonix', 12000.00, 9, 550.25),
(11, 'Annecy', 8500.00, 8, 380.50);

-- Brittany destinations
INSERT INTO destinations (state_id, name, base_cost, sustainability_score, estimated_co2_footprint) VALUES 
(12, 'Saint-Malo', 8500.00, 8, 380.00),
(12, 'Carnac', 7500.00, 8, 350.25),
(12, 'Quiberon', 8000.00, 7, 370.50);

-- Normandy destinations
INSERT INTO destinations (state_id, name, base_cost, sustainability_score, estimated_co2_footprint) VALUES 
(13, 'Mont-Saint-Michel', 8000.00, 8, 360.00),
(13, 'Rouen', 7500.00, 7, 340.25),
(13, 'Honfleur', 7800.00, 8, 350.50);

-- Burgundy destinations
INSERT INTO destinations (state_id, name, base_cost, sustainability_score, estimated_co2_footprint) VALUES 
(14, 'Dijon', 7500.00, 7, 340.00),
(14, 'Beaune', 8000.00, 8, 360.25),
(14, 'Château de Chambord', 9000.00, 8, 400.50);

-- Alsace destinations
INSERT INTO destinations (state_id, name, base_cost, sustainability_score, estimated_co2_footprint) VALUES 
(15, 'Strasbourg', 7000.00, 7, 320.00),
(15, 'Colmar', 7500.00, 8, 340.25),
(15, 'Haut-Koenigsbourg Castle', 6500.00, 7, 300.50);

-- Loire Valley destinations
INSERT INTO destinations (state_id, name, base_cost, sustainability_score, estimated_co2_footprint) VALUES 
(16, 'Château de Chantilly', 6500.00, 8, 290.00),
(16, 'Château de Chenonceau', 7000.00, 8, 310.25),
(16, 'Château de Villandry', 6800.00, 8, 300.50);

-- Japan destinations
INSERT INTO destinations (state_id, name, base_cost, sustainability_score, estimated_co2_footprint) VALUES 
(17, 'Tokyo Disneyland', 15000.00, 6, 600.00),
(17, 'Senso-ji Temple', 12000.00, 8, 500.25),
(17, 'Shibuya Crossing', 13000.00, 7, 550.50);

-- Osaka destinations
INSERT INTO destinations (state_id, name, base_cost, sustainability_score, estimated_co2_footprint) VALUES 
(18, 'Osaka Castle', 12000.00, 8, 500.00),
(18, 'Dotonbori', 11000.00, 7, 480.25),
(18, 'Universal Studios Japan', 14000.00, 6, 580.50);

-- Kyoto destinations
INSERT INTO destinations (state_id, name, base_cost, sustainability_score, estimated_co2_footprint) VALUES 
(19, 'Kinkaku-ji (Golden Pavilion)', 11000.00, 9, 450.00),
(19, 'Fushimi Inari Shrine', 10000.00, 9, 420.25),
(19, 'Arashiyama Bamboo Grove', 10500.00, 9, 430.50);

-- Hokkaido destinations
INSERT INTO destinations (state_id, name, base_cost, sustainability_score, estimated_co2_footprint) VALUES 
(20, 'Sapporo', 13000.00, 8, 550.00),
(20, 'Furano', 12000.00, 9, 500.25),
(20, 'Asahiyama Zoo', 11500.00, 8, 480.50);

-- Okinawa destinations
INSERT INTO destinations (state_id, name, base_cost, sustainability_score, estimated_co2_footprint) VALUES 
(21, 'Shuri Castle', 10000.00, 8, 420.00),
(21, 'Churaumi Aquarium', 11000.00, 8, 450.25),
(21, 'Kokusai Street', 9500.00, 7, 400.50);

-- Hiroshima destinations
INSERT INTO destinations (state_id, name, base_cost, sustainability_score, estimated_co2_footprint) VALUES 
(22, 'Peace Memorial Park', 9000.00, 9, 380.00),
(22, 'Miyajima Island', 10000.00, 9, 420.25),
(22, 'Hiroshima Castle', 8500.00, 8, 360.50);

-- Nara destinations
INSERT INTO destinations (state_id, name, base_cost, sustainability_score, estimated_co2_footprint) VALUES 
(23, 'Todai-ji Temple', 8500.00, 9, 360.00),
(23, 'Nara Park', 8000.00, 9, 340.25),
(23, 'Kasuga Taisha Shrine', 8200.00, 9, 350.50);

-- Nagano destinations
INSERT INTO destinations (state_id, name, base_cost, sustainability_score, estimated_co2_footprint) VALUES 
(24, 'Zenko-ji Temple', 8000.00, 9, 340.00),
(24, 'Jigokudani Monkey Park', 8500.00, 9, 360.25),
(24, 'Matsumoto Castle', 7800.00, 8, 330.50);

-- Italy destinations
INSERT INTO destinations (state_id, name, base_cost, sustainability_score, estimated_co2_footprint) VALUES 
(25, 'Colosseum', 11000.00, 7, 480.00),
(25, 'Vatican City', 12000.00, 8, 500.25),
(25, 'Trevi Fountain', 10000.00, 7, 450.50);

-- Venice destinations
INSERT INTO destinations (state_id, name, base_cost, sustainability_score, estimated_co2_footprint) VALUES 
(26, 'St. Mark''s Square', 11000.00, 8, 480.00),
(26, 'Grand Canal', 10500.00, 8, 460.25),
(26, 'Murano Island', 10000.00, 7, 440.50);

-- Rome destinations
INSERT INTO destinations (state_id, name, base_cost, sustainability_score, estimated_co2_footprint) VALUES 
(27, 'Pantheon', 9500.00, 8, 420.00),
(27, 'Roman Forum', 9000.00, 8, 400.25),
(27, 'Spanish Steps', 8500.00, 7, 380.50);

-- Sicily destinations
INSERT INTO destinations (state_id, name, base_cost, sustainability_score, estimated_co2_footprint) VALUES 
(28, 'Mount Etna', 8000.00, 9, 360.00),
(28, 'Valley of the Temples', 7500.00, 8, 340.25),
(28, 'Taormina', 8500.00, 8, 380.50);

-- Milan destinations
INSERT INTO destinations (state_id, name, base_cost, sustainability_score, estimated_co2_footprint) VALUES 
(29, 'Duomo di Milano', 12000.00, 8, 500.00),
(29, 'La Scala Opera House', 13000.00, 7, 550.25),
(29, 'Galleria Vittorio Emanuele II', 11000.00, 7, 480.50);

-- Florence destinations
INSERT INTO destinations (state_id, name, base_cost, sustainability_score, estimated_co2_footprint) VALUES 
(30, 'Uffizi Gallery', 10500.00, 8, 460.00),
(30, 'Ponte Vecchio', 10000.00, 8, 440.25),
(30, 'Duomo di Firenze', 11000.00, 8, 480.50);

-- Naples destinations
INSERT INTO destinations (state_id, name, base_cost, sustainability_score, estimated_co2_footprint) VALUES 
(31, 'Pompeii', 7500.00, 8, 340.00),
(31, 'Mount Vesuvius', 8000.00, 9, 360.25),
(31, 'Naples Historic Center', 7000.00, 7, 320.50);

-- Amalfi Coast destinations
INSERT INTO destinations (state_id, name, base_cost, sustainability_score, estimated_co2_footprint) VALUES 
(32, 'Positano', 13000.00, 8, 550.00),
(32, 'Ravello', 12000.00, 9, 500.25),
(32, 'Amalfi Cathedral', 11500.00, 8, 480.50);

-- USA destinations
INSERT INTO destinations (state_id, name, base_cost, sustainability_score, estimated_co2_footprint) VALUES 
(33, 'Hollywood', 15000.00, 6, 600.00),
(33, 'Disneyland', 16000.00, 6, 650.25),
(33, 'Santa Monica Pier', 14000.00, 7, 580.50);

-- New York destinations
INSERT INTO destinations (state_id, name, base_cost, sustainability_score, estimated_co2_footprint) VALUES 
(34, 'Times Square', 14000.00, 7, 580.00),
(34, 'Central Park', 13000.00, 8, 550.25),
(34, 'Statue of Liberty', 13500.00, 8, 560.50);

-- Florida destinations
INSERT INTO destinations (state_id, name, base_cost, sustainability_score, estimated_co2_footprint) VALUES 
(35, 'Walt Disney World', 16000.00, 6, 650.00),
(35, 'Miami Beach', 15000.00, 7, 600.25),
(35, 'Everglades National Park', 12000.00, 9, 500.50);

-- Texas destinations
INSERT INTO destinations (state_id, name, base_cost, sustainability_score, estimated_co2_footprint) VALUES 
(36, 'Alamo', 9000.00, 7, 380.00),
(36, 'Big Bend National Park', 8500.00, 9, 360.25),
(36, 'San Antonio River Walk', 9500.00, 8, 400.50);

-- Hawaii destinations
INSERT INTO destinations (state_id, name, base_cost, sustainability_score, estimated_co2_footprint) VALUES 
(37, 'Waikiki Beach', 16000.00, 8, 650.00),
(37, 'Pearl Harbor', 15000.00, 8, 600.25),
(37, 'Haleakala National Park', 14000.00, 9, 580.50);

-- Nevada destinations
INSERT INTO destinations (state_id, name, base_cost, sustainability_score, estimated_co2_footprint) VALUES 
(38, 'Las Vegas Strip', 11000.00, 6, 480.00),
(38, 'Hoover Dam', 10000.00, 8, 450.25),
(38, 'Red Rock Canyon', 9500.00, 9, 420.50);

-- Colorado destinations
INSERT INTO destinations (state_id, name, base_cost, sustainability_score, estimated_co2_footprint) VALUES 
(39, 'Rocky Mountain National Park', 9500.00, 9, 420.00),
(39, 'Garden of the Gods', 9000.00, 9, 400.25),
(39, 'Mesa Verde National Park', 8500.00, 9, 380.50);

-- Arizona destinations
INSERT INTO destinations (state_id, name, base_cost, sustainability_score, estimated_co2_footprint) VALUES 
(40, 'Grand Canyon', 8500.00, 9, 380.00),
(40, 'Antelope Canyon', 9000.00, 8, 400.25),
(40, 'Sedona', 8800.00, 8, 390.50);

-- Australia destinations
INSERT INTO destinations (state_id, name, base_cost, sustainability_score, estimated_co2_footprint) VALUES 
(41, 'Sydney Opera House', 14000.00, 8, 580.00),
(41, 'Bondi Beach', 13000.00, 8, 550.25),
(41, 'Blue Mountains', 12000.00, 9, 500.50);

-- Victoria destinations
INSERT INTO destinations (state_id, name, base_cost, sustainability_score, estimated_co2_footprint) VALUES 
(42, 'Great Ocean Road', 13000.00, 9, 550.00),
(42, 'Phillip Island', 12500.00, 9, 520.25),
(42, 'Yarra Valley', 12000.00, 8, 500.50);

-- Queensland destinations
INSERT INTO destinations (state_id, name, base_cost, sustainability_score, estimated_co2_footprint) VALUES 
(43, 'Great Barrier Reef', 15000.00, 9, 600.00),
(43, 'Daintree Rainforest', 14000.00, 10, 580.25),
(43, 'Gold Coast', 13500.00, 8, 560.50);

-- Western Australia destinations
INSERT INTO destinations (state_id, name, base_cost, sustainability_score, estimated_co2_footprint) VALUES 
(44, 'Perth', 11000.00, 8, 480.00),
(44, 'Rottnest Island', 11500.00, 9, 500.25),
(44, 'Pinnacles Desert', 10500.00, 9, 460.50);

-- South Australia destinations
INSERT INTO destinations (state_id, name, base_cost, sustainability_score, estimated_co2_footprint) VALUES 
(45, 'Adelaide', 10000.00, 8, 450.00),
(45, 'Barossa Valley', 10500.00, 8, 470.25),
(45, 'Kangaroo Island', 11000.00, 9, 480.50);

-- Tasmania destinations
INSERT INTO destinations (state_id, name, base_cost, sustainability_score, estimated_co2_footprint) VALUES 
(46, 'Port Arthur', 9000.00, 8, 400.00),
(46, 'Cradle Mountain', 9500.00, 9, 420.25),
(46, 'Freycinet National Park', 10000.00, 9, 450.50);

-- Northern Territory destinations
INSERT INTO destinations (state_id, name, base_cost, sustainability_score, estimated_co2_footprint) VALUES 
(47, 'Uluru', 8500.00, 9, 380.00),
(47, 'Kakadu National Park', 9000.00, 10, 400.25),
(47, 'Alice Springs', 8000.00, 8, 360.50);

-- Australian Capital Territory destinations
INSERT INTO destinations (state_id, name, base_cost, sustainability_score, estimated_co2_footprint) VALUES 
(48, 'Parliament House', 8000.00, 7, 360.00),
(48, 'Australian War Memorial', 7500.00, 8, 340.25),
(48, 'Lake Burley Griffin', 7000.00, 8, 320.50);

-- Canada destinations
INSERT INTO destinations (state_id, name, base_cost, sustainability_score, estimated_co2_footprint) VALUES 
(49, 'Niagara Falls', 11000.00, 8, 480.00),
(49, 'CN Tower', 10500.00, 7, 460.25),
(49, 'Royal Ontario Museum', 10000.00, 8, 440.50);

-- British Columbia destinations
INSERT INTO destinations (state_id, name, base_cost, sustainability_score, estimated_co2_footprint) VALUES 
(50, 'Vancouver', 12000.00, 8, 500.00),
(50, 'Victoria', 11500.00, 8, 480.25),
(50, 'Whistler', 13000.00, 9, 550.50);

-- Alberta destinations
INSERT INTO destinations (state_id, name, base_cost, sustainability_score, estimated_co2_footprint) VALUES 
(51, 'Banff National Park', 12500.00, 9, 520.00),
(51, 'Jasper National Park', 12000.00, 9, 500.25),
(51, 'Calgary Stampede', 11500.00, 7, 480.50);

-- Quebec destinations
INSERT INTO destinations (state_id, name, base_cost, sustainability_score, estimated_co2_footprint) VALUES 
(52, 'Old Quebec', 9000.00, 8, 400.00),
(52, 'Montreal', 9500.00, 8, 420.25),
(52, 'Mont-Tremblant', 10000.00, 9, 450.50);

-- Manitoba destinations
INSERT INTO destinations (state_id, name, base_cost, sustainability_score, estimated_co2_footprint) VALUES 
(53, 'Winnipeg', 8000.00, 7, 360.00),
(53, 'Polar Bear Tours', 12000.00, 9, 500.25),
(53, 'Canadian Museum for Human Rights', 8500.00, 8, 380.50);

-- Nova Scotia destinations
INSERT INTO destinations (state_id, name, base_cost, sustainability_score, estimated_co2_footprint) VALUES 
(54, 'Halifax', 8500.00, 8, 380.00),
(54, 'Peggy''s Cove', 8000.00, 9, 360.25),
(54, 'Cabot Trail', 9000.00, 9, 400.50);

-- New Brunswick destinations
INSERT INTO destinations (state_id, name, base_cost, sustainability_score, estimated_co2_footprint) VALUES 
(55, 'Hopewell Rocks', 7500.00, 9, 340.00),
(55, 'Fundy National Park', 8000.00, 9, 360.25),
(55, 'Saint John', 7000.00, 7, 320.50);

-- Saskatchewan destinations
INSERT INTO destinations (state_id, name, base_cost, sustainability_score, estimated_co2_footprint) VALUES 
(56, 'Saskatoon', 7000.00, 7, 320.00),
(56, 'Prince Albert National Park', 7500.00, 9, 340.25),
(56, 'Royal Saskatchewan Museum', 6500.00, 8, 300.50);

-- Brazil destinations
INSERT INTO destinations (state_id, name, base_cost, sustainability_score, estimated_co2_footprint) VALUES 
(57, 'Christ the Redeemer', 7000.00, 8, 320.00),
(57, 'Copacabana Beach', 6500.00, 7, 300.25),
(57, 'Sugarloaf Mountain', 7500.00, 8, 340.50);

-- Rio de Janeiro destinations
INSERT INTO destinations (state_id, name, base_cost, sustainability_score, estimated_co2_footprint) VALUES 
(58, 'Ipanema Beach', 6800.00, 7, 310.00),
(58, 'Tijuca National Park', 7200.00, 9, 330.25),
(58, 'Selarón Steps', 6500.00, 8, 300.50);

-- São Paulo destinations
INSERT INTO destinations (state_id, name, base_cost, sustainability_score, estimated_co2_footprint) VALUES 
(59, 'Ibirapuera Park', 6500.00, 8, 300.00),
(59, 'MASP', 7000.00, 8, 320.25),
(59, 'Paulista Avenue', 6800.00, 7, 310.50);

-- Bahia destinations
INSERT INTO destinations (state_id, name, base_cost, sustainability_score, estimated_co2_footprint) VALUES 
(60, 'Pelourinho', 6000.00, 8, 280.00),
(60, 'Chapada Diamantina', 6500.00, 9, 300.25),
(60, 'Morro de São Paulo', 5800.00, 8, 270.50);

-- Amazonas destinations
INSERT INTO destinations (state_id, name, base_cost, sustainability_score, estimated_co2_footprint) VALUES 
(61, 'Manaus', 5500.00, 9, 260.00),
(61, 'Meeting of Waters', 6000.00, 9, 280.25),
(61, 'Anavilhanas Archipelago', 5800.00, 10, 270.50);

-- Minas Gerais destinations
INSERT INTO destinations (state_id, name, base_cost, sustainability_score, estimated_co2_footprint) VALUES 
(62, 'Ouro Preto', 5500.00, 8, 260.00),
(62, 'Inhotim', 6000.00, 9, 280.25),
(62, 'Pampulha Modern Ensemble', 5800.00, 8, 270.50);

-- Paraná destinations
INSERT INTO destinations (state_id, name, base_cost, sustainability_score, estimated_co2_footprint) VALUES 
(63, 'Iguaçu Falls', 6500.00, 9, 300.00),
(63, 'Curitiba', 6000.00, 8, 280.25),
(63, 'Foz do Iguaçu', 6200.00, 9, 290.50);

-- Pernambuco destinations
INSERT INTO destinations (state_id, name, base_cost, sustainability_score, estimated_co2_footprint) VALUES 
(64, 'Olinda', 5500.00, 8, 260.00),
(64, 'Porto de Galinhas', 6000.00, 8, 280.25),
(64, 'Fernando de Noronha', 8000.00, 9, 350.50);

-- Ceará destinations
INSERT INTO destinations (state_id, name, base_cost, sustainability_score, estimated_co2_footprint) VALUES 
(65, 'Jericoacoara', 5000.00, 9, 240.00),
(65, 'Fortaleza', 5500.00, 7, 260.25),
(65, 'Lençóis Maranhenses', 5800.00, 9, 270.50);

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