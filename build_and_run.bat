@echo off
echo Building Java Project...

REM Create output directory
mkdir out 2>nul

REM Compile all source files
echo Compiling source files...
javac -d out src\main\java\com\smarttravelplanner\exceptions\*.java src\main\java\com\smarttravelplanner\model\*.java src\main\java\com\smarttravelplanner\service\*.java src\main\java\com\smarttravelplanner\utils\*.java src\main\java\com\smarttravelplanner\db\*.java src\main\java\com\smarttravelplanner\controller\*.java src\main\java\com\smarttravelplanner\*.java

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
echo To run the main CLI application:
echo   java -cp out com.smarttravelplanner.Main
echo.
echo To run the REST API server:
echo   java -cp out com.smarttravelplanner.RestApiServer
echo.
echo To run the REST API server on a specific port:
echo   java -cp out com.smarttravelplanner.RestApiServer 8081
echo.