# PowerShell script to verify tables
$env:PGPASSWORD = "Lithu19!"
psql -U postgres -d smart_travel_db -c "\dt"