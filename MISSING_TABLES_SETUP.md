# Missing Tables Setup Instructions

This document provides instructions for creating the missing tables in your Smart Travel Planner database.

## Overview

Your database currently shows only 4 tables (users, trips, activities, expenses). This process will automatically create all other required tables (countries, states, destinations, trip_history, expense_breakdown, alerts, routes) with proper relationships, constraints, and indexes.

## Prerequisites

1. PostgreSQL installed on your system
2. Database `smart_travel_db` created
3. PostgreSQL bin directory added to your system PATH (e.g., `C:\Program Files\PostgreSQL\18\bin`)
4. Username: `postgres`
5. Password: `Lithu19!`

## Setup Process

### Method 1: Using psql Command Line

1. Open Command Prompt or PowerShell
2. Navigate to the project directory:
   ```cmd
   cd "c:\Users\Litheswar M\OneDrive\Documents\GitHub\Java-Project"
   ```

3. Connect to PostgreSQL and run the script:
   ```cmd
   psql -U postgres -d smart_travel_db -f src/main/resources/create_missing_tables.sql
   ```

4. When prompted, enter the password: `Lithu19!`

### Method 2: Using pgAdmin (As per your instructions)

1. Open pgAdmin and connect to `smart_travel_db`
2. Expand the server and find the `smart_travel_db` database
3. Right-click on the database and select "Query Tool"
4. Open the file `src/main/resources/create_missing_tables.sql`
5. Execute the script by clicking the "Execute" button or pressing F5

### Method 3: Using Java Application

1. Run the DatabaseInitializer class:
   ```cmd
   java -cp target/classes:target/dependency/* com.smarttravelplanner.db.DatabaseInitializer
   ```

   Or from your IDE, run the `main` method in `DatabaseInitializer.java`.

## What This Script Does

The script will:

1. Create the following tables only if they don't already exist:
   - countries: id (UUID), name (TEXT UNIQUE NOT NULL)
   - states: id (UUID), name (TEXT NOT NULL), country_id (UUID FK)
   - destinations: id (UUID), name (TEXT NOT NULL), state_id (UUID FK), base_cost (NUMERIC), sustainability_score (NUMERIC), co2_footprint (NUMERIC)
   - trip_history: id (UUID), user_id (UUID FK), trip_id (UUID FK), date (TIMESTAMP)
   - expense_breakdown: id (UUID), trip_id (UUID FK), category (TEXT), amount (NUMERIC)
   - alerts: id (UUID), user_id (UUID FK), trip_id (UUID FK), alert_type (TEXT), message (TEXT), created_at (TIMESTAMP)
   - routes: id (UUID), trip_id (UUID FK), start_destination (UUID FK), end_destination (UUID FK), route_order (INT)

2. Create indexes for faster queries:
   - idx_states_country_id on states(country_id)
   - idx_destinations_state_id on destinations(state_id)
   - idx_trip_history_user_id on trip_history(user_id)
   - idx_expense_breakdown_trip_id on expense_breakdown(trip_id)

3. List all tables to confirm creation

## Verification

After successful execution:
1. Refresh the schema in pgAdmin
2. You should see all required tables under the public schema
3. The tables will have proper relationships and constraints

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