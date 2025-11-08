@echo off
echo Compiling SimpleMain...
javac SimpleMain.java

if %errorlevel% neq 0 (
    echo Compilation failed!
    pause
    exit /b %errorlevel%
)

echo Running SimpleMain...
java SimpleMain

if %errorlevel% neq 0 (
    echo Application failed to start!
    pause
    exit /b %errorlevel%
)

echo Application finished.
pause