# Database Update Instructions

This document provides instructions for updating your Smart Travel Planner database to match the required schema with all tables properly created and related.

## Overview of Changes

The database has been updated to include all required tables with proper relationships, constraints, and indexes:

1. **countries** - Stores country information with UUID primary key
2. **states** - Stores state information with reference to countries
3. **destinations** - Stores destination information with sustainability metrics
4. **trip_history** - Tracks trip history with timestamps
5. **expense_breakdown** - Stores detailed expense breakdown for each trip

## Prerequisites

1. PostgreSQL installed on your system
2. Database `smart_travel_db` created
3. PostgreSQL JDBC driver in your classpath (included in pom.xml)

## Update Instructions

### Option 1: Using SQL Script (Recommended)

1. Connect to your PostgreSQL database:
   ```bash
   psql -U postgres -d smart_travel_db
   ```

2. Run the updated database setup script:
   ```sql
   \i src/main/resources/updated_database_setup.sql
   ```

### Option 2: Using Java Application

1. Run the DatabaseInitializer class:
   ```bash
   java -cp target/classes:target/dependency/* com.smarttravelplanner.db.DatabaseInitializer
   ```

   Or from your IDE, run the `main` method in `DatabaseInitializer.java`.

## Schema Details

### Table Relationships

- **countries** → **states** (One-to-Many)
- **states** → **destinations** (One-to-Many)
- **users** → **trips** (One-to-Many)
- **trips** → **destinations** (Many-to-One)
- **users** → **trip_history** (One-to-Many)
- **trips** → **trip_history** (One-to-Many)
- **trips** → **expense_breakdown** (One-to-Many)

### Indexes

The following indexes have been created for optimal query performance:
- `idx_state_country` on `states(country_id)`
- `idx_destination_state` on `destinations(state_id)`
- `idx_trip_history_user` on `trip_history(user_id)`
- `idx_expense_trip` on `expense_breakdown(trip_id)`

## Verification

To verify that all tables have been created correctly:

1. Connect to your database:
   ```bash
   psql -U postgres -d smart_travel_db
   ```

2. List all tables:
   ```sql
   \dt
   ```

   You should see:
   - users
   - trips
   - expenses
   - activities
   - countries
   - states
   - destinations
   - trip_history
   - expense_breakdown

3. Run the verification script:
   ```sql
   \i src/main/resources/verify_tables.sql
   ```

## Sample Data

The database is pre-seeded with:
- 8 countries
- 8 states per country (64 total)
- 2-3 destinations per state
- 1 sample user

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

3. **Connection Issues**: Verify your database credentials in `DBConnection.java`:
   - Database Name: `smart_travel_db`
   - Username: `postgres`
   - Password: `Lithu19!`

## Integration with Java Backend

The updated schema is fully compatible with the existing Java backend. All DAO classes should work without modification.

### Key Classes

- **UserDAO**: CRUD operations for users
- **TripDAO**: CRUD operations for trips
- **DestinationDAO**: Query operations for destinations
- **DatabaseInitializer**: Initializes database with tables and sample data

## Additional Notes

1. The schema uses UUIDs for all primary keys for global uniqueness
2. All foreign key relationships are properly constrained with CASCADE DELETE where appropriate
3. CHECK constraints ensure data integrity for numerical values
4. Indexes are created on all foreign key columns for optimal query performance

For any issues or questions, please refer to the main documentation or contact the development team.