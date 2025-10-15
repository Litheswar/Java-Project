# PowerShell script to run SQL commands
$env:PGPASSWORD = "Lithu19!"
psql -U postgres -d smart_travel_db -c "SELECT column_name, data_type FROM information_schema.columns WHERE table_name = 'trips' AND column_name = 'id';"