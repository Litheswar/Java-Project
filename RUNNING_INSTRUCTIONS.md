# Running the Smart Travel Planner

## Overview

The Smart Travel Planner is a comprehensive Java application that helps users plan trips, estimate costs, and receive personalized recommendations. Due to merge conflicts in the current codebase, we've created a simplified demo version to demonstrate the core functionality.

## Prerequisites

- Java 17 or higher
- PostgreSQL database (for full implementation)
- Maven (optional, for building with dependencies)

## Running the Demo Version

We've created a simplified demo version that demonstrates the core functionality without database dependencies:

1. Compile the demo:
   ```
   javac DemoTravelPlanner.java
   ```

2. Run the demo:
   ```
   java DemoTravelPlanner
   ```

Or simply run:
```
run_demo.bat
```

## Running the Full Application (When Conflicts Are Resolved)

The full application has merge conflicts that need to be resolved before it can be compiled and run. Here are the steps that would be needed once the conflicts are fixed:

1. **Database Setup**:
   - Ensure PostgreSQL is installed and running
   - Create a database named `smart_travel_db`
   - Update the database credentials in `src/main/java/com/smarttravelplanner/db/DBConnection.java` if needed

2. **Compile the Application**:
   ```
   build_and_run.bat
   ```

3. **Run the Application**:
   ```
   run_app.bat
   ```

## Project Structure

- `src/main/java/com/smarttravelplanner/` - Main source code
- `src/main/java/com/smarttravelplanner/db/` - Database access objects
- `src/main/java/com/smarttravelplanner/model/` - Data models
- `src/main/java/com/smarttravelplanner/service/` - Business logic
- `lib/` - External dependencies (PostgreSQL JDBC driver)

## Key Features (In Full Implementation)

- User registration and profile management
- Trip planning with detailed itineraries
- Cost estimation and budget management
- Sustainability reporting
- Smart optimization suggestions
- Travel history tracking
- Personalized recommendations

## Troubleshooting

### Merge Conflicts
The current codebase has merge conflicts that prevent compilation. These need to be resolved by:
1. Identifying conflict markers (`<<<<<<<`, `=======`, `>>>>>>>`)
2. Choosing the appropriate code segments
3. Removing the conflict markers
4. Ensuring the code compiles correctly

### Database Connection Issues
If you encounter database connection issues:
1. Verify PostgreSQL is running
2. Check database credentials in `DBConnection.java`
3. Ensure the `smart_travel_db` database exists
4. Verify the PostgreSQL JDBC driver is in the `lib/` directory

### Classpath Issues
If you encounter classpath issues:
1. Ensure all dependencies are in the `lib/` directory
2. Use the correct classpath when running: `-cp "out;lib/*"`

## Next Steps

To fully implement and run the Smart Travel Planner:

1. Resolve all merge conflicts in the source code
2. Verify database connectivity
3. Test all DAO (Data Access Object) implementations
4. Run the full test suite
5. Execute the main application