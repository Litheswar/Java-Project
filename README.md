# Smart Travel Planner Backend

A Java-based backend for a Smart Travel Planner application that demonstrates core OOP concepts including Inheritance, Polymorphism, Encapsulation, and Abstraction.

## Features
- Location management with file-based storage (no database yet)
- Trip planning with inheritance (CityPlan vs TourPlan)
- Cost calculation with polymorphism and exception handling
- Destination suggestions based on budget
- File I/O for trip logging
- CLI simulation for testing

## Tech Stack
- Java 17
- Maven
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
│   │   ├─ exceptions/
│   │   │    ├─ BudgetExceededException.java
│   │   │    └─ InvalidInputException.java
│   │   └─ utils/
│   │        ├─ FileHandler.java
│   │        └─ RouteOptimizer.java
│   └─ resources/
├─ pom.xml
└─ README.md
```

## Setup Instructions

1. **Build the Project**:
   ```bash
   mvn clean install
   ```

2. **Run the Application**:
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

## Modules Overview

### 🗺️ LocationList Module
- Manage and load destinations from local file or hardcoded list
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

### 🧰 Utils
- `FileHandler`: Handles file reading/writing for destinations list
- `RouteOptimizer`: Simulates "smart route optimization"

### ⚠️ Exception Classes
- `BudgetExceededException`: For budget-related errors
- `InvalidInputException`: For invalid trip configuration