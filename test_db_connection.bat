@echo off
echo Testing database connection...

java -cp "lib/*" org.postgresql.util.PGJDBCMain

if %errorlevel% neq 0 (
    echo Database connection test failed!
    pause
    exit /b %errorlevel%
)

echo Database connection test completed.
pause