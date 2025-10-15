# PowerShell script for final verification
$env:PGPASSWORD = "Lithu19!"
Write-Host "Verifying all required tables exist:"
psql -U postgres -d smart_travel_db -c "SELECT tablename FROM pg_tables WHERE schemaname = 'public' ORDER BY tablename;"