@echo off
echo Compiling DemoTravelPlanner...
javac -d out src\main\java\com\smarttravelplanner\DemoTravelPlanner.java

if %errorlevel% neq 0 (
    echo Compilation failed!
    pause
    exit /b %errorlevel%
)

echo Running DemoTravelPlanner...
java -cp out com.smarttravelplanner.DemoTravelPlanner

if %errorlevel% neq 0 (
    echo Application failed to start!
    pause
    exit /b %errorlevel%
)

echo Demo finished.
pause