# DatabaseInitializer Module

## Overview

The DatabaseInitializer module is a Java backend component for the Smart Travel Planner project that automatically creates and populates the PostgreSQL database `smart_travel_db`. It uses JDBC for database connectivity and follows Maven project structure.

## Features

1. **Automatic Database Creation**: Creates all required tables with proper constraints
2. **Sample Data Seeding**: Populates the database with sample data including:
   - 8 countries
   - 64 states (8 per country)
   - 192+ destinations (2-3 per state)
   - Sample user, trip, and expense data
3. **Validation & Constraints**: Implements all required validation rules
4. **Indexing**: Creates indexes on commonly queried columns
5. **Duplicate Prevention**: Automatically prevents duplicate entries on initialization
6. **Console Logging**: Provides detailed console output for each step

## Database Schema

### Tables Created

1. **users**
   - id (UUID, PK)
   - name (VARCHAR)
   - age (INT, 1-120)
   - family_count (INT, 1-20)
   - total_budget (NUMERIC, >0)
   - email (VARCHAR)
   - created_at (TIMESTAMP)

2. **countries**
   - id (UUID, PK)
   - name (VARCHAR, UNIQUE)
   - created_at (TIMESTAMP)

3. **states**
   - id (UUID, PK)
   - name (VARCHAR)
   - country_id (UUID, FK to countries)
   - base_budget (NUMERIC, >0)
   - created_at (TIMESTAMP)

4. **destinations**
   - id (UUID, PK)
   - name (VARCHAR)
   - state_id (UUID, FK to states)
   - base_cost (NUMERIC, >0)
   - sustainability_score (INT, 1-10)
   - estimated_co2_footprint (NUMERIC)
   - created_at (TIMESTAMP)

5. **trips**
   - id (UUID, PK)
   - user_id (UUID, FK to users)
   - destination_id (UUID, FK to destinations)
   - trip_type (VARCHAR, CityPlan/TourPlan)
   - travel_mode (VARCHAR, Road/Rail/Air/Mixed)
   - stay_type (VARCHAR, Budget/Standard/Premium)
   - meal_type (VARCHAR, Veg/Non-Veg/Mixed)
   - trip_days (INT, 1-50)
   - meals_per_day (INT, 1-5)
   - total_estimated_cost (NUMERIC)
   - total_budget (NUMERIC)
   - created_at (TIMESTAMP)

6. **trip_history**
   - id (UUID, PK)
   - user_id (UUID, FK to users)
   - trip_id (UUID, FK to trips)
   - timestamp (TIMESTAMP)
   - status (VARCHAR, completed/planned/cancelled)

7. **expense_breakdown**
   - id (UUID, PK)
   - trip_id (UUID, FK to trips)
   - travel_cost (NUMERIC)
   - food_cost (NUMERIC)
   - stay_cost (NUMERIC)
   - shopping_cost (NUMERIC)
   - entertainment_cost (NUMERIC)
   - local_commute_cost (NUMERIC)
   - total_cost (NUMERIC)
   - created_at (TIMESTAMP)

## Sample Data

The DatabaseInitializer automatically populates the database with comprehensive sample data:

- **8 Countries**: India, France, Japan, Italy, USA, Australia, Canada, Brazil
- **64 States**: 8 states for each country with realistic base budgets
- **192+ Destinations**: 2-3 destinations per state with base costs, sustainability scores, and CO2 footprints
- **Sample User**: John Doe with realistic travel preferences
- **Sample Trip**: Complete trip with expense breakdown

## Validation Rules

- Age: 1-120
- Family count: 1-20
- Trip days: 1-50
- Meals per day: 1-5
- Budget: > 0
- All foreign key relationships maintained
- Unique constraints on country names

## Maven Integration

The module uses the PostgreSQL JDBC driver dependency:

```xml
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <version>42.6.0</version>
</dependency>
```

## Usage

### Automatic Initialization

The DatabaseInitializer is automatically called when the Main.java application starts:

```java
// In Main.java
public static void main(String[] args) {
    // Initialize the database
    try {
        DatabaseInitializer.initializeDatabase();
    } catch (Exception e) {
        System.err.println("Failed to initialize database: " + e.getMessage());
        System.out.println("Continuing with file-based storage...");
    }
    
    // Rest of the application...
}
```

### Manual Execution

You can also run the DatabaseInitializer directly:

```bash
java -cp out com.smarttravelplanner.db.DatabaseInitializer
```

Or run the test class:

```bash
java -cp out com.smarttravelplanner.db.DatabaseInitializerTest
```

## Console Output

The DatabaseInitializer provides detailed console output for each step:

```
Initializing Smart Travel Planner database...
Creating database tables...
Creating extension uuid-ossp... Done
Creating table users... Done
Creating table countries... Done
Creating table states... Done
Creating table destinations... Done
Creating table trips... Done
Creating table trip_history... Done
Creating table expense_breakdown... Done
All tables created successfully!
Creating indexes... Done
Inserting sample data...
Inserting sample countries... Done
Inserting sample states... Done
Inserting sample destinations... Done
Inserting sample user... Done
Inserting sample trip... Done
Inserting sample trip history... Done
Inserting sample expense breakdown... Done
Sample data insertion completed!
Database initialization completed successfully!
```

## Database Credentials

The module uses the following credentials (configured in DBConnection.java):

- **Database**: `smart_travel_db`
- **Username**: `postgres`
- **Password**: `Lithu19!`
- **Host**: `localhost:5432`

## Additional Features

1. **Preview States**: Users can preview states with base budgets before selecting a destination
2. **Affordable Destinations**: Automatically suggests destinations within user budget
3. **Sustainability Tracking**: Tracks sustainability score and CO2 footprint per trip
4. **Timestamps**: Includes creation timestamps for all records
5. **Error Handling**: Comprehensive error handling and connection management

## Files

- `src/main/java/com/smarttravelplanner/db/DatabaseInitializer.java` - Main initializer class
- `src/main/java/com/smarttravelplanner/db/DatabaseInitializerTest.java` - Test class
- `src/main/java/com/smarttravelplanner/db/DBConnection.java` - Database connection management
- `pom.xml` - Maven configuration with PostgreSQL dependency

## Requirements

1. PostgreSQL database server running on localhost:5432
2. Database user `postgres` with password `Lithu19!`
3. Java 17 or higher
4. Maven for dependency management