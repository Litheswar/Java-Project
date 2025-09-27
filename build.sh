#!/bin/bash

# Build script for Smart Travel Planner

# Create bin directory if it doesn't exist
mkdir -p bin

# Compile all Java files
echo "Compiling Java files..."
javac -d bin src/main/java/travelplanner/*.java

# Check if compilation was successful
if [ $? -eq 0 ]; then
    echo "Compilation successful!"
    echo "To run the application, use: java -cp bin travelplanner.TravelPlanner"
else
    echo "Compilation failed!"
    exit 1
fi