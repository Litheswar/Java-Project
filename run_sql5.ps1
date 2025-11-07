# PowerShell script to run SQL commands
$env:PGPASSWORD = "Lithu19!"
psql -U postgres -d smart_travel_db -f "c:\Users\Litheswar M\OneDrive\Documents\GitHub\Java-Project\create_missing_tables_final.sql"