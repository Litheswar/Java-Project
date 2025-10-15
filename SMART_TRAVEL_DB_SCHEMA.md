# Smart Travel Planner - PostgreSQL Database Schema

This document describes the complete PostgreSQL database schema for the Smart Travel Planner application.

## Database Credentials

- **Database Name**: `smart_travel_db`
- **Username**: `postgres`
- **Password**: `Lithu19!`

## Tables

### 1. users
Stores user details with validation constraints.

```sql
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
```

### 2. countries
Stores country information.

```sql
CREATE TABLE countries (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) UNIQUE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

### 3. states
Stores state information with reference to countries.

```sql
CREATE TABLE states (
    id SERIAL PRIMARY KEY,
    country_id INT REFERENCES countries(id) ON DELETE CASCADE,
    name VARCHAR(100) NOT NULL,
    base_budget NUMERIC(12,2) NOT NULL CHECK(base_budget > 0),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

### 4. destinations
Stores destination information with sustainability metrics.

```sql
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
```

### 5. trips
Stores trip information and preferences.

```sql
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
```

### 6. trip_history
Tracks trip history and status.

```sql
CREATE TABLE trip_history (
    id SERIAL PRIMARY KEY,
    user_id UUID REFERENCES users(id),
    trip_id INT REFERENCES trips(id),
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(20) CHECK(status IN ('completed','planned','cancelled'))
);
```

### 7. expense_breakdown
Stores detailed expense breakdown for each trip.

```sql
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
```

## Indexes

Indexes are created on all foreign keys and commonly queried fields for optimized performance:

```sql
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
```

## Sample Data

### Countries (8)
1. India
2. France
3. Japan
4. Italy
5. USA
6. Australia
7. Canada
8. Brazil

### States (64)
Each country has 8 states with base budgets.

### Destinations (192+)
Each state has 2-3 destinations with:
- Base cost
- Sustainability score (1-10)
- Estimated CO2 footprint

## Validation Rules

- **Age**: 1-120
- **Family count**: 1-20
- **Budget**: > 0
- **Trip days**: 1-50
- **Meals per day**: 1-5
- **Travel mode**: Road, Rail, Air, Mixed
- **Stay type**: Budget, Standard, Premium
- **Meal type**: Veg, Non-Veg, Mixed
- **Trip type**: CityPlan, TourPlan
- **Status**: completed, planned, cancelled

## Features Implemented

1. **Input Validation**: All user inputs are validated with CHECK constraints
2. **Budget Management**: Ensures budget > 0 and validates against estimated costs
3. **Sustainability Tracking**: Each destination has a sustainability score and CO2 footprint
4. **Expense Breakdown**: Detailed cost tracking for each trip
5. **Trip History**: Status tracking for all trips
6. **Affordable Destinations Query**: Automatically suggests destinations within user budget
7. **Timestamps**: Creation and update timestamps for all records
8. **Referential Integrity**: Foreign key constraints maintain data consistency
9. **Indexing**: Optimized queries with indexes on frequently accessed columns

## How to Use

1. Ensure PostgreSQL is installed
2. Run the database setup script:
   ```sql
   psql -U postgres -f database_setup.sql
   ```
3. The database will be created with all tables, constraints, indexes, and sample data

## Queries

### Find Affordable Destinations
```sql
SELECT d.name AS destination, s.name AS state, c.name AS country, d.base_cost
FROM destinations d
JOIN states s ON d.state_id = s.id
JOIN countries c ON s.country_id = c.id
WHERE d.base_cost <= [user_budget]
ORDER BY d.base_cost;
```

### Find Sustainable Destinations
```sql
SELECT d.name AS destination, s.name AS state, c.name AS country, d.sustainability_score
FROM destinations d
JOIN states s ON d.state_id = s.id
JOIN countries c ON s.country_id = c.id
WHERE d.sustainability_score >= 9
ORDER BY d.sustainability_score DESC;
```

### Calculate CO2 Footprint
The estimated CO2 footprint is stored with each destination for environmental impact tracking.