@echo off
echo Initializing and Running Smart Travel Planner...

REM First, let's try to run the database initialization
echo Initializing database...
java -cp "out;lib/*" com.smarttravelplanner.db.DatabaseInitializer

if %errorlevel% neq 0 (
    echo Database initialization failed!
    echo This is expected if there are merge conflicts in the source code.
    echo Please resolve the merge conflicts before running the full application.
    pause
    exit /b %errorlevel%
)

echo Database initialization completed.

REM Now run the main application
echo Starting main application...
java -cp "out;lib/*" com.smarttravelplanner.Main

if %errorlevel% neq 0 (
    echo Application failed to start!
    pause
    exit /b %errorlevel%
)

echo Application finished.
pause