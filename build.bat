@echo off
REM Build script for Smart Travel Planner (Windows)

REM Create bin directory if it doesn't exist
if not exist "bin" mkdir "bin"

REM Compile all Java files
echo Compiling Java files...
javac -d bin src\main\java\travelplanner\*.java

REM Check if compilation was successful
if %errorlevel% == 0 (
    echo.
    echo Compilation successful!
    echo.
    echo To run the application directly, use: run.bat
    echo Or run manually with: java -cp bin travelplanner.TravelPlanner
) else (
    echo.
    echo Compilation failed!
    exit /b 1
)