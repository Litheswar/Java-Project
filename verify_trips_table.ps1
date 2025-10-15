# PowerShell script to verify trips table
$env:PGPASSWORD = "Lithu19!"
psql -U postgres -d smart_travel_db -c "SELECT tablename FROM pg_tables WHERE tablename = 'trips';"