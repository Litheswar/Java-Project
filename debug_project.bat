@echo off
echo Debugging SmartTravelPlanner...

jdb -classpath bin;lib/* com.smarttravelplanner.Main

echo Debugger finished.