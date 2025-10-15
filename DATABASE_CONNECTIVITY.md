# Database Connectivity Setup

This document explains how to set up and use the database connectivity for the Smart Travel Planner application.

## Database Configuration

The application connects to a PostgreSQL database with the following default credentials:

- **Host**: localhost
- **Port**: 5432
- **Database**: smart_travel_db
- **Username**: postgres
- **Password**: Lithu19!

These values can be overridden using environment variables:

- `DB_HOST` - Database host (default: localhost)
- `DB_PORT` - Database port (default: 5432)
- `DB_NAME` - Database name (default: smart_travel_db)
- `DB_USER` - Database username (default: postgres)
- `DB_PASSWORD` - Database password (default: Lithu19!)

## Setting Environment Variables

### Windows (Command Prompt)
```cmd
set DB_HOST=localhost
set DB_PORT=5432
set DB_NAME=smart_travel_db
set DB_USER=postgres
set DB_PASSWORD=Lithu19!
```

### Windows (PowerShell)
```powershell
$env:DB_HOST="localhost"
$env:DB_PORT="5432"
$env:DB_NAME="smart_travel_db"
$env:DB_USER="postgres"
$env:DB_PASSWORD="Lithu19!"
```

### Linux/Mac
```bash
export DB_HOST=localhost
export DB_PORT=5432
export DB_NAME=smart_travel_db
export DB_USER=postgres
export DB_PASSWORD=Lithu19!
```

## Database Schema

The application uses the following tables:

1. **countries** - Stores country information
2. **states** - Stores state information with reference to countries
3. **destinations** - Stores destination information with sustainability metrics
4. **users** - Stores user details
5. **trips** - Stores trip information and preferences
6. **trip_history** - Tracks trip history and status
7. **expense_breakdown** - Stores detailed expense breakdown for each trip

## Data Access Objects (DAOs)

The application provides DAOs for each table:

- `CountryDAO` - For countries table
- `StateDAO` - For states table
- `DestinationDAO` - For destinations table
- `UserDAO` - For users table
- `TripDAO` - For trips table
- `TripHistoryDAO` - For trip_history table
- `ExpenseBreakdownDAO` - For expense_breakdown table

Each DAO provides full CRUD (Create, Read, Update, Delete) operations.

## Usage Example

```java
// Create a CountryDAO instance
CountryDAO countryDAO = new CountryDAO();

// Create a new country
Country country = new Country("France");
int countryId = countryDAO.createCountry(country);

// Retrieve a country
Country retrievedCountry = countryDAO.getCountryById(countryId);

// Update a country
retrievedCountry.setName("Italy");
countryDAO.updateCountry(retrievedCountry);

// Delete a country
countryDAO.deleteCountry(countryId);
```

## Connection Pooling

The application uses HikariCP for connection pooling to improve performance and resource management.

## Testing

To test the database connectivity, run the `TestDatabaseConnection` class:

```bash
java com.smarttravelplanner.TestDatabaseConnection
```

This will perform basic CRUD operations on the countries and users tables to verify connectivity.