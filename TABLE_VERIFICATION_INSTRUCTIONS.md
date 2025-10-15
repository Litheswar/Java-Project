# Database Table Verification Instructions

This document provides instructions for verifying and creating the required database tables for the Smart Travel Planner application.

## Overview

This process will:
1. Check if all required tables exist in the database
2. Create any missing tables with proper structure and constraints
3. Add required indexes for optimal query performance
4. List all tables to confirm they exist

## Required Tables

The following tables will be verified/created:
- Countries: id (UUID), name (TEXT UNIQUE NOT NULL)
- States: id (UUID), name (TEXT NOT NULL), country_id (UUID FK)
- Destinations: id (UUID), name (TEXT NOT NULL), state_id (UUID FK), base_cost (NUMERIC), sustainability_score (NUMERIC), co2_footprint (NUMERIC)
- Trip History: id (UUID), user_id (UUID FK), trip_id (UUID FK), date (TIMESTAMP)
- Expense Breakdown: id (UUID), trip_id (UUID FK), category (TEXT), amount (NUMERIC)
- Alerts: id (UUID), user_id (UUID FK), trip_id (UUID FK), alert_type (TEXT), message (TEXT), created_at (TIMESTAMP)
- Routes: id (UUID), trip_id (UUID FK), start_destination (UUID FK), end_destination (UUID FK), route_order (INT)

## Prerequisites

1. PostgreSQL installed on your system
2. Database `smart_travel_db` created
3. PostgreSQL bin directory added to your system PATH (e.g., `C:\Program Files\PostgreSQL\18\bin`)
4. Username: `postgres`
5. Password: `Lithu19!`

## Verification Process

### Method 1: Using psql Command Line

1. Open Command Prompt or PowerShell
2. Navigate to the project directory:
   ```cmd
   cd "c:\Users\Litheswar M\OneDrive\Documents\GitHub\Java-Project"
   ```

3. Connect to PostgreSQL and run the verification script:
   ```cmd
   psql -U postgres -d smart_travel_db -f src/main/resources/verify_and_create_tables.sql
   ```

4. When prompted, enter the password: `Lithu19!`

### Method 2: Using pgAdmin

1. Open pgAdmin
2. Connect to your PostgreSQL server
3. Expand the server and find the `smart_travel_db` database
4. Right-click on the database and select "Query Tool"
5. Open the file `src/main/resources/verify_and_create_tables.sql`
6. Execute the script by clicking the "Execute" button or pressing F5

### Method 3: Using Java Application

1. Run the DatabaseInitializer class:
   ```cmd
   java -cp target/classes:target/dependency/* com.smarttravelplanner.db.DatabaseInitializer
   ```

   Or from your IDE, run the `main` method in `DatabaseInitializer.java`.

## Expected Output

After running the verification script, you should see:
1. Confirmation that all tables exist or have been created
2. A list of all tables in the public schema
3. The structure of each table with columns, data types, and constraints

## Verification in pgAdmin

To manually verify in pgAdmin:
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

## Indexes

The following indexes will be created for optimal query performance:
- idx_states_country_id on states(country_id)
- idx_destinations_state_id on destinations(state_id)
- idx_trip_history_user_id on trip_history(user_id)
- idx_expense_breakdown_trip_id on expense_breakdown(trip_id)

These indexes will speed up common queries that filter or join on these columns.

## Integration with Java Backend

All tables are designed to work with the existing Java backend. The DAO classes should work without modification.

For any issues or questions, please refer to the main documentation or contact the development team.