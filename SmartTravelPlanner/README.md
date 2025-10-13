# Smart Travel Planner (Plain Java)

Console-based backend demonstrating OOP, JDBC (MySQL), File I/O, and Exception Handling.

## How to Run
- Compile from project root:
```
javac -d out -cp . ./SmartTravelPlanner/src/**/*.java
```
- Run:
```
java -cp out smarttravelplanner.Main
```

## MySQL Setup
- DB: `travelplanner`
- Tables: `countries`, `states`, `destinations`, `routes`, `users`, `trips`
- Update credentials in `src/Database/DBConnection.java`.

## Data Fallback
- If DB not reachable, app loads from:
  - `data/locations.txt`
  - `data/routes.txt`

## OOP Mapping
- Abstraction: `planner/TravelPlan.java`
- Inheritance: `CityPlan`, `TourPlan` extend `TravelPlan`
- Polymorphism: override `estimateCost()` and `displayPlan()`
- Encapsulation: `models/User.java`, `locationlist/Location.java`, `costmanager/CostManager.java`
- ArrayList: route steps in `TravelPlan`
- File I/O: `LocationRepository.loadFromFiles`, `models/TravelDiary`
- JDBC: `database/DBConnection`, `database/DBUtils`
