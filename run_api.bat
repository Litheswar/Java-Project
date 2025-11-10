@echo off
echo Starting Smart Travel Planner REST API Server...

java -cp "out;lib/*" com.smarttravelplanner.RestApiServer

if %errorlevel% neq 0 (
    echo API Server failed to start!
    pause
    exit /b %errorlevel%
)

echo API Server finished.
pause