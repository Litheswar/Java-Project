# Smart Travel Planner - Database Setup

This document provides instructions for setting up the PostgreSQL database for the Smart Travel Planner application.

## Database Credentials

- **Database Name**: `smart_travel_db`
- **Username**: `postgres`
- **Password**: `Lithu19!`

## Prerequisites

1. PostgreSQL installed on your system
2. PostgreSQL JDBC driver in your classpath (included in pom.xml)

## Setup Instructions

1. Execute the `database_setup.sql` script located in `src/main/resources/`:

```bash
psql -U postgres -f src/main/resources/database_setup.sql
```

Or connect to PostgreSQL and run:

```sql
\i src/main/resources/database_setup.sql
```

## Database Schema Overview

### Tables

1. **users** - Stores user information and budget limits
2. **countries** - Contains countries for the planner
3. **states** - Each country has states with base budgets
4. **destinations** - Destinations with costs and sustainability scores
5. **trips** - Trip-related information with preferences
6. **trip_history** - Trip history tracking
7. **expense_breakdown** - Detailed expense breakdown per trip

### Key Features

- **Validation Constraints**: Enforce constraints on age, family count, meals per day, and trip days
- **Budget Validation**: Ensure budget >= estimated_cost
- **Preview Budgets**: View budgets per state before choosing a destination
- **Trip History**: Track trip history for each user
- **Sustainability Tracking**: Each destination has a sustainability score
- **Expense Breakdown**: Store detailed expense breakdown per trip
- **Indexing**: Indexes on frequently queried columns for better performance

### Sample Data

The database is pre-seeded with:
- 8 countries
- 8 states per country (64 total)
- 2-3 destinations per state
- 1 sample user with a trip

## Java Backend Integration

### DAO Classes

- **UserDAO**: CRUD operations for users
- **TripDAO**: CRUD operations for trips
- **DestinationDAO**: Query operations for destinations

### Utility Classes

- **InputValidator**: Validates user inputs (age, family count, budget, etc.)
- **ExpenseCalculator**: Calculates travel, food, and stay expenses
- **CO2Calculator**: Calculates CO2 footprint and provides sustainability tips
- **AlertGenerator**: Generates alerts for meal/stay/travel selections that exceed typical limits
- **RouteOptimizer**: Implements smart optimizer for travel routes (shortest/fastest)

### Database Initialization

The `DatabaseInitializer` class can be used to automatically seed the database with initial data.

## Access Control

All privileges on all tables are granted to the user `postgres`.