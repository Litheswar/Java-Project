@echo off
echo Starting Seamless-GO Frontend Development Server...
echo.

cd /d "c:\Users\Litheswar M\OneDrive\Documents\GitHub\Java-Project\frontend"

echo Installing dependencies...
npm install
echo.

echo Starting development server...
echo The application will be available at http://localhost:3000
echo.
npm run dev