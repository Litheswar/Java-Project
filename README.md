# Smart Travel Planner Backend

A Java-based backend for a Smart Travel Planner application that demonstrates core OOP concepts including Inheritance, Polymorphism, Encapsulation, and Abstraction.

## Features
- Location management with PostgreSQL database storage
- Trip planning with inheritance (CityPlan vs TourPlan)
- Cost calculation with polymorphism and exception handling
- Destination suggestions based on budget
- File I/O for trip logging
- CLI simulation for testing
- Database integration with CRUD operations
- Budget validation and expense tracking
- Sustainability score tracking

## Tech Stack
- Java 17
- Maven
- PostgreSQL
- Gson (JSON library for file handling)

## Project Structure
```
SmartTravelPlanner/
├─ src/
│   ├─ main/java/com/smarttravelplanner/
│   │   ├─ model/
│   │   │    ├─ Destination.java
│   │   │    ├─ Plan.java (abstract)
│   │   │    ├─ CityPlan.java
│   │   │    ├─ TourPlan.java
│   │   │    └─ User.java
│   │   ├─ service/
│   │   │    ├─ LocationService.java
│   │   │    ├─ PlannerService.java
│   │   │    ├─ CostManager.java
│   │   │    └─ SuggestionService.java
│   │   ├─ controller/
│   │   │    └─ TravelController.java
│   │   ├─ db/
│   │   │    ├─ DBConnection.java
│   │   │    ├─ UserDAO.java
│   │   │    ├─ TripDAO.java
│   │   │    ├─ DestinationDAO.java
│   │   │    ├─ DatabaseInitializer.java
│   │   │    └─ DatabaseTest.java
│   │   ├─ exceptions/
│   │   │    ├─ BudgetExceededException.java
│   │   │    └─ InvalidInputException.java
│   │   └─ utils/
│   │        ├─ FileHandler.java
│   │        └─ RouteOptimizer.java
│   └─ resources/
│       └─ database_setup.sql
├─ pom.xml
├─ README.md
└─ DATABASE_SETUP.md
```

## Setup Instructions

1. **Setup Database**:
   - Ensure PostgreSQL is installed
   - Update PostgreSQL credentials in `src/main/java/com/smarttravelplanner/db/DBConnection.java` if needed
   - Run the database setup script: `psql -U postgres -f src/main/resources/database_setup.sql`
   - See [DATABASE_SETUP.md](DATABASE_SETUP.md) for detailed instructions

2. **Build the Project**:
   ```bash
   mvn clean install
   ```

3. **Run the Application**:
   ```bash
   mvn exec:java -Dexec.mainClass="com.smarttravelplanner.App"
   ```

## OOP Concepts Demonstrated

- **Inheritance**: `CityPlan` and `TourPlan` extend the abstract `Plan` class
- **Polymorphism**: `calculateCost()` method is overridden in child classes
- **Encapsulation**: Private fields with public getters/setters in all model classes
- **Abstraction**: Abstract `Plan` class with abstract methods
- **Exception Handling**: Custom `BudgetExceededException` and `InvalidInputException` for error handling
- **ArrayList**: Used for dynamic lists of destinations in plans
- **File I/O**: JSON file handling for persistent storage

## Database Features

- **Tables**: users, countries, states, destinations, trips, trip_history, expense_breakdown
- **Validation**: Input validation for age, family count, meals/day, trip_days, budget
- **Budget Checks**: Ensure budget >= estimated_cost with suggestions for cheaper destinations
- **Preview**: State preview with base_budget before choosing a destination
- **Trip History**: Track trip history for each user
- **Sustainability**: Sustainability score for each destination
- **Expense Breakdown**: Detailed expense breakdown per trip
- **Indexing**: Indexes on frequently queried columns for better performance

## Modules Overview

### 🗺️ LocationList Module
- Manage and load destinations from database
- Maintain countries → states → destinations hierarchy
- Handle exceptions for invalid selections

### 🧠 Planner Module
- Core travel planning logic with abstract `Plan` class
- `CityPlan` for single-destination trips
- `TourPlan` for multi-destination trips
- Route optimization using `RouteOptimizer` helper class

### 💰 Cost Manager
- Estimate trip cost including travel, food, stay, and leisure expenses
- Dynamic calculations based on duration, members, and preferences
- Sustainability score calculation
- Budget comparison with custom exception handling

### 🧮 SuggestionService
- Suggest alternate destinations or adjustments when budget is insufficient
- Logic-based filtering within 10% of user's budget
- Formatted result display

### 🗄️ Database Module
- **DBConnection**: Database connection management
- **UserDAO**: CRUD operations for users
- **TripDAO**: CRUD operations for trips
- **DestinationDAO**: Query operations for destinations
- **DatabaseInitializer**: Automatic database seeding
- **DatabaseTest**: Demonstration of database functionality

### 🧰 Utils
- `FileHandler`: Handles file reading/writing for destinations list
- `RouteOptimizer`: Simulates "smart route optimization"

### ⚠️ Exception Classes
- `BudgetExceededException`: For budget-related errors
- `InvalidInputException`: For invalid trip configuration