# Database Tables Setup Instructions

This document provides instructions for setting up all required database tables for the Smart Travel Planner application with proper relationships, constraints, and indexes.

## Overview

The following tables will be created:
- Countries: Stores country information
- States: Stores state information with reference to countries
- Destinations: Stores destination information with sustainability metrics
- Trip History: Tracks user trip history
- Expense Breakdown: Stores detailed expense information for trips
- Alerts: Stores user alerts related to trips
- Routes: Stores route information for trips

## Prerequisites

1. PostgreSQL installed on your system
2. Database `smart_travel_db` created
3. PostgreSQL bin directory added to your system PATH (e.g., `C:\Program Files\PostgreSQL\18\bin`)

## Setup Instructions

### Method 1: Using SQL Scripts (Recommended)

1. Open Command Prompt or PowerShell
2. Navigate to the project directory:
   ```cmd
   cd "c:\Users\Litheswar M\OneDrive\Documents\GitHub\Java-Project"
   ```

3. Connect to PostgreSQL and run the script:
   ```cmd
   psql -U postgres -d smart_travel_db -f src/main/resources/create_missing_tables.sql
   ```
   
   When prompted, enter the password: `Lithu19!`

### Method 2: Using pgAdmin

1. Open pgAdmin
2. Connect to your PostgreSQL server
3. Expand the server and find the `smart_travel_db` database
4. Right-click on the database and select "Query Tool"
5. Open the file `src/main/resources/create_missing_tables.sql`
6. Execute the script by clicking the "Execute" button or pressing F5

### Method 3: Using Java Application

1. Run the DatabaseInitializer class:
   ```cmd
   java -cp target/classes:target/dependency/* com.smarttravelplanner.db.DatabaseInitializer
   ```

   Or from your IDE, run the `main` method in `DatabaseInitializer.java`.

## Verification

To verify that all tables have been created correctly:

### Using SQL Scripts

1. Run the verification script:
   ```cmd
   psql -U postgres -d smart_travel_db -f src/main/resources/verify_database_tables.sql
   ```

### Using pgAdmin

1. In pgAdmin, refresh the Tables view under `smart_travel_db`
2. You should see all the following tables:
   - alerts
   - countries
   - destinations
   - expense_breakdown
   - routes
   - states
   - trip_history
3. Right-click on each table and select "Properties" to verify:
   - Primary keys
   - Foreign key relationships
   - Constraints
   - Indexes

## Table Details

### Countries
- id (UUID, Primary Key)
- name (TEXT, Unique, Not Null)

### States
- id (UUID, Primary Key)
- name (TEXT, Not Null)
- country_id (UUID, Foreign Key referencing countries.id with CASCADE DELETE)

### Destinations
- id (UUID, Primary Key)
- name (TEXT, Not Null)
- state_id (UUID, Foreign Key referencing states.id with CASCADE DELETE)
- base_cost (NUMERIC, Not Null)
- sustainability_score (NUMERIC)
- co2_footprint (NUMERIC)

### Trip History
- id (UUID, Primary Key)
- user_id (UUID, Foreign Key referencing users.id with CASCADE DELETE)
- trip_id (UUID, Foreign Key referencing trips.id with CASCADE DELETE)
- date (TIMESTAMP, Default Current Timestamp)

### Expense Breakdown
- id (UUID, Primary Key)
- trip_id (UUID, Foreign Key referencing trips.id with CASCADE DELETE)
- category (TEXT, Not Null)
- amount (NUMERIC, Not Null)

### Alerts
- id (UUID, Primary Key)
- user_id (UUID, Foreign Key referencing users.id with CASCADE DELETE)
- trip_id (UUID, Foreign Key referencing trips.id with CASCADE DELETE)
- alert_type (TEXT, Not Null)
- message (TEXT, Not Null)
- created_at (TIMESTAMP, Default Current Timestamp)

### Routes
- id (UUID, Primary Key)
- trip_id (UUID, Foreign Key referencing trips.id with CASCADE DELETE)
- start_destination (UUID, Foreign Key referencing destinations.id with CASCADE DELETE)
- end_destination (UUID, Foreign Key referencing destinations.id with CASCADE DELETE)
- route_order (INTEGER, Not Null)

## Indexes

The following indexes have been created for optimal query performance:
- idx_states_country_id on states(country_id)
- idx_destinations_state_id on destinations(state_id)
- idx_trip_history_user_id on trip_history(user_id)
- idx_expense_breakdown_trip_id on expense_breakdown(trip_id)
- idx_alerts_user_id on alerts(user_id)
- idx_alerts_trip_id on alerts(trip_id)
- idx_routes_trip_id on routes(trip_id)
- idx_routes_start_destination on routes(start_destination)
- idx_routes_end_destination on routes(end_destination)

## Troubleshooting

### Common Issues

1. **UUID Extension Error**: If you see errors about `gen_random_uuid()`, ensure the UUID extension is installed:
   ```sql
   CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
   ```

2. **Permission Issues**: Ensure your PostgreSQL user has proper permissions:
   ```sql
   GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO postgres;
   ```

3. **Connection Issues**: Verify your database credentials:
   - Database Name: `smart_travel_db`
   - Username: `postgres`
   - Password: `Lithu19!`

## Integration with Java Backend

All tables are designed to work with the existing Java backend. The DAO classes should work without modification.

For any issues or questions, please refer to the main documentation or contact the development team.