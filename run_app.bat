@echo off
echo Running Smart Travel Planner...

java -cp "out;lib/*" com.smarttravelplanner.Main

if %errorlevel% neq 0 (
    echo Application failed to start!
    pause
    exit /b %errorlevel%
)

echo Application finished.
pause