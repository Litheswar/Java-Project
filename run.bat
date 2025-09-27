@echo off
REM Run script for Smart Travel Planner (Windows)

REM Create bin directory if it doesn't exist
if not exist "bin" mkdir "bin"

REM Compile all Java files
javac -d bin src\main\java\travelplanner\*.java

REM Check if compilation was successful
if %errorlevel% == 0 (
    REM Run the application
    java -cp bin travelplanner.TravelPlanner
) else (
    echo Compilation failed!
    exit /b 1
)