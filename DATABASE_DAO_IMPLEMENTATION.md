# Database DAO Implementation

This document describes the implementation of Data Access Objects (DAOs) for the Smart Travel Planner application.

## Overview

We have implemented DAOs for all the required database tables to enable full CRUD (Create, Read, Update, Delete) operations. The implementation follows these principles:

1. **Connection Management**: Uses a base DAO class for common functionality
2. **Environment Variables**: Supports configuration via environment variables
3. **Proper Exception Handling**: Handles SQL exceptions appropriately
4. **Resource Management**: Uses try-with-resources for automatic resource cleanup
5. **Type Safety**: Uses proper Java types that match the database schema

## Implemented DAOs

### 1. BaseDAO
A base class that provides common functionality for all DAOs:
- Database connection management
- Common utility methods

### 2. CountryDAO
Handles operations for the `countries` table:
- `createCountry(Country country)` - Creates a new country
- `getCountryById(int id)` - Retrieves a country by ID
- `getCountryByName(String name)` - Retrieves a country by name
- `getAllCountries()` - Retrieves all countries
- `updateCountry(Country country)` - Updates a country
- `deleteCountry(int id)` - Deletes a country by ID

### 3. StateDAO
Handles operations for the `states` table:
- `createState(State state)` - Creates a new state
- `getStateById(int id)` - Retrieves a state by ID
- `getStatesByCountryId(int countryId)` - Retrieves states for a country
- `getAllStates()` - Retrieves all states
- `updateState(State state)` - Updates a state
- `deleteState(int id)` - Deletes a state by ID

### 4. DestinationDAO
Handles operations for the `destinations` table:
- `createDestination(Destination destination)` - Creates a new destination
- `getDestinationById(int id)` - Retrieves a destination by ID
- `getDestinationsByStateId(int stateId)` - Retrieves destinations for a state
- `getAllDestinations()` - Retrieves all destinations
- `updateDestination(Destination destination)` - Updates a destination
- `deleteDestination(int id)` - Deletes a destination by ID

### 5. UserDAO
Handles operations for the `users` table:
- `createUser(User user)` - Creates a new user
- `getUserById(UUID id)` - Retrieves a user by ID
- `getAllUsers()` - Retrieves all users
- `updateUser(User user)` - Updates a user
- `deleteUser(UUID id)` - Deletes a user by ID

### 6. TripDAO
Handles operations for the `trips` table:
- `createTrip(Trip trip)` - Creates a new trip
- `getTripById(int id)` - Retrieves a trip by ID
- `getTripsByUserId(UUID userId)` - Retrieves trips for a user
- `getAllTrips()` - Retrieves all trips
- `updateTrip(Trip trip)` - Updates a trip
- `deleteTrip(int id)` - Deletes a trip by ID

### 7. TripHistoryDAO
Handles operations for the `trip_history` table:
- `createTripHistory(TripHistory tripHistory)` - Creates a new trip history entry
- `getTripHistoryById(int id)` - Retrieves a trip history entry by ID
- `getTripHistoryByUserId(UUID userId)` - Retrieves trip history for a user
- `getTripHistoryByTripId(int tripId)` - Retrieves trip history for a trip
- `getAllTripHistory()` - Retrieves all trip history entries
- `updateTripHistory(TripHistory tripHistory)` - Updates a trip history entry
- `deleteTripHistory(int id)` - Deletes a trip history entry by ID

### 8. ExpenseBreakdownDAO
Handles operations for the `expense_breakdown` table:
- `createExpenseBreakdown(ExpenseBreakdown expenseBreakdown)` - Creates a new expense breakdown
- `getExpenseBreakdownById(int id)` - Retrieves an expense breakdown by ID
- `getExpenseBreakdownByTripId(int tripId)` - Retrieves expense breakdown for a trip
- `getAllExpenseBreakdowns()` - Retrieves all expense breakdowns
- `updateExpenseBreakdown(ExpenseBreakdown expenseBreakdown)` - Updates an expense breakdown
- `deleteExpenseBreakdown(int id)` - Deletes an expense breakdown by ID

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

## Testing

To test the DAOs, run the `DAOTest` class:

```bash
java com.smarttravelplanner.DAOTest
```

This will perform basic CRUD operations on countries, states, and destinations to verify functionality.

## Integration with Existing Code

The DAOs are designed to integrate with the existing backend services. The frontend can send requests to the backend services, which in turn use these DAOs to interact with the database.

## Future Improvements

1. **Connection Pooling**: Implement connection pooling using HikariCP for better performance
2. **Transaction Management**: Add support for database transactions
3. **Caching**: Implement caching for frequently accessed data
4. **Query Optimization**: Add indexes and optimize queries for better performance