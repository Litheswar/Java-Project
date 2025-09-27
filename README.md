# Smart Travel Planner – Sustainable Travel Web App

## Project Overview

This is a Smart Travel Planner web application built with Java backend, aligned with SDG Goal 11 (Sustainable Cities). The application helps users plan sustainable trips by providing eco-friendly route options, cost calculations, and sustainability scoring.

## Features

### Core Modules

1. **Location List (Foundation Module)**
   - Maintains repository of travel destinations
   - Data stored using File I/O (text format)
   - Flexible to add new destinations

2. **Planner (Core Brain of System)**
   - Uses Inheritance (Plan → CityPlan / TourPlan)
   - Stores route steps dynamically in ArrayList
   - Provides both shortest distance route and least traffic/eco-friendly route

3. **Cost Manager (Budget Module)**
   - Calculates estimated trip cost based on multiple factors
   - Uses Polymorphism for cost calculation with different hotel classes and transport modes
   - Integrates with Planner to update cost dynamically

### Extra Features

- User Preferences & Customization
- Trip Rating & Sustainability Score
- Weather & Season-based Suggestions
- Emergency Cost Buffer
- Trip History & Recommendations
- Family Member Roles
- Gamification (Eco-Points)

## Java OOP Concepts Demonstrated

- **Encapsulation**: User, Person, Destination with private fields + getters/setters
- **Inheritance**: Plan → CityPlan / TourPlan, Accommodation → BudgetHotel / LuxuryHotel
- **Polymorphism**: Cost calculation depending on travel/hotel type
- **Abstraction**: Abstract Planner class with generateRoute() method
- **ArrayList**: Store route steps, cost breakdowns dynamically
- **Exception Handling**: Handle invalid inputs, missing destinations, insufficient budget
- **File I/O**: Store destinations, trip history, weather data, cost logs

## Project Structure

```
src/main/java/travelplanner/
├── Accommodation.java        # Abstract accommodation class
├── BudgetHotel.java          # Budget hotel implementation
├── CityPlan.java             # City plan implementation
├── CostManager.java          # Cost calculation and management
├── InsufficientFundsException.java  # Custom exception for budget issues
├── InvalidDestinationException.java # Custom exception for invalid destinations
├── Location.java             # Location/destination class
├── LocationRepository.java   # Repository for managing locations
├── LuxuryHotel.java          # Luxury hotel implementation
├── Person.java               # Person/family member class
├── Plan.java                 # Abstract plan class
├── StandardHotel.java        # Standard hotel implementation
├── TourPlan.java             # Tour plan implementation
├── TransportMode.java        # Transport mode enumeration
└── TravelPlanner.java        # Main application class
```

## How to Build and Run

### Prerequisites
- Java JDK 8 or higher

### Building the Project

#### Windows:
```cmd
build.bat
```

#### Linux/Mac:
```bash
chmod +x build.sh
./build.sh
```

### Running the Application

#### Windows:
```cmd
run.bat
```

#### Linux/Mac:
```bash
java -cp bin travelplanner.TravelPlanner
```

Or manually:
```bash
java -cp bin travelplanner.TravelPlanner
```

## Future Enhancements

1. **Database Integration**: Use Supabase for structured storage of user inputs, routes, and trip history
2. **Web Frontend**: Simple web interface for better user experience
3. **Advanced Algorithms**: Implement Dijkstra's shortest path algorithm for route planning
4. **Weather Integration**: Connect to real weather APIs for dynamic weather-based suggestions
5. **Mobile App**: Develop mobile version for on-the-go planning

## SDG Goal 11 Alignment

This application supports Sustainable Development Goal 11: Sustainable Cities and Communities by:
- Promoting sustainable travel options
- Encouraging use of public transportation
- Providing eco-friendly accommodation recommendations
- Educating users about sustainable travel practices
- Gamifying sustainable choices with eco-points system

## License

This project is open source and available under the MIT License.