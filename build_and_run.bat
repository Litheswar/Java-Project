@echo off
echo Building Java Project...

REM Create output directory
mkdir out 2>nul

REM Compile all source files
echo Compiling source files...
javac -d out src\main\java\com\smarttravelplanner\exceptions\*.java src\main\java\com\smarttravelplanner\model\*.java src\main\java\com\smarttravelplanner\service\*.java src\main\java\com\smarttravelplanner\utils\*.java src\main\java\com\smarttravelplanner\db\*.java src\main\java\com\smarttravelplanner\*.java

if %errorlevel% neq 0 (
    echo Compilation failed!
    pause
    exit /b %errorlevel%
)

REM Compile test files
echo Compiling test files...
javac -cp out -d out src\test\java\com\smarttravelplanner\db\*.java src\test\java\com\smarttravelplanner\model\*.java src\test\java\com\smarttravelplanner\service\*.java

if %errorlevel% neq 0 (
    echo Test compilation failed!
    pause
    exit /b %errorlevel%
)

echo.
echo Build successful!
echo.
echo To run database initialization:
echo   java -cp out com.smarttravelplanner.db.DatabaseInitializer
echo.
echo To run database initializer test:
echo   java -cp out com.smarttravelplanner.db.DatabaseInitializerTest
echo.
echo To run database schema test:
echo   java -cp out com.smarttravelplanner.db.SchemaTest
echo.
echo To run database test:
echo   java -cp out com.smarttravelplanner.db.DatabaseTest
echo.
echo To run comprehensive database test:
echo   java -cp out com.smarttravelplanner.db.ComprehensiveTest
echo.
echo To run other tests, use one of the following commands:
echo   java -cp out com.smarttravelplanner.db.DBConnectionTest
echo   java -cp out com.smarttravelplanner.db.UserDAOTest
echo   java -cp out com.smarttravelplanner.model.DestinationTest
echo   java -cp out com.smarttravelplanner.model.UserTest
echo   java -cp out com.smarttravelplanner.model.PlanTest
echo   java -cp out com.smarttravelplanner.service.PlannerServiceTest
echo   java -cp out com.smarttravelplanner.service.CostManagerTest
echo   java -cp out com.smarttravelplanner.service.LocationServiceTest
echo   java -cp out com.smarttravelplanner.service.SuggestionServiceTest
echo.
echo To run the main application:
echo   java -cp out com.smarttravelplanner.Main
echo.