@echo off
echo Compiling SmartTravelPlanner project...

REM Create bin directory if it doesn't exist
if not exist bin mkdir bin

REM Compile all source files at once
echo Compiling all source files...
javac -d bin -cp .;lib/* src/main/java/com/smarttravelplanner/*.java src/main/java/com/smarttravelplanner/db/*.java src/main/java/com/smarttravelplanner/exceptions/*.java src/main/java/com/smarttravelplanner/model/*.java src/main/java/com/smarttravelplanner/service/*.java src/main/java/com/smarttravelplanner/utils/*.java src/test/java/com/smarttravelplanner/db/*.java src/test/java/com/smarttravelplanner/model/*.java src/test/java/com/smarttravelplanner/service/*.java

echo Compilation completed!