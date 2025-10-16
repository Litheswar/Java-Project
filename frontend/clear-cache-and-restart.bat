@echo off
echo Clearing Vite cache and restarting development server...
echo.

cd /d "c:\Users\Litheswar M\OneDrive\Documents\GitHub\Java-Project\frontend"

echo Stopping any running development server...
taskkill /f /im node.exe 2>nul

echo Clearing Vite cache...
rmdir /s /q node_modules\.vite 2>nul

echo Installing dependencies...
npm install

echo Starting development server...
npm run dev