-- Connect to the database
\c smart_travel_db;

-- List all tables
\dt

-- Check table structures
\d countries
\d states
\d destinations
\d trip_history
\d expense_breakdown
\d alerts
\d routes

-- Verify foreign key relationships
SELECT 
    tc.table_name, 
    tc.constraint_name, 
    tc.constraint_type,
    kcu.column_name, 
    ccu.table_name AS foreign_table_name,
    ccu.column_name AS foreign_column_name 
FROM 
    information_schema.table_constraints AS tc 
    JOIN information_schema.key_column_usage AS kcu
      ON tc.constraint_name = kcu.constraint_name
      AND tc.table_schema = kcu.table_schema
    JOIN information_schema.constraint_column_usage AS ccu
      ON ccu.constraint_name = tc.constraint_name
      AND ccu.table_schema = tc.table_schema
WHERE 
    tc.constraint_type = 'FOREIGN KEY' 
    AND tc.table_name IN ('countries', 'states', 'destinations', 'trip_history', 'expense_breakdown', 'alerts', 'routes')
ORDER BY 
    tc.table_name;

-- Verify indexes
SELECT 
    tablename, 
    indexname, 
    indexdef 
FROM 
    pg_indexes 
WHERE 
    tablename IN ('countries', 'states', 'destinations', 'trip_history', 'expense_breakdown', 'alerts', 'routes')
ORDER BY 
    tablename, 
    indexname;

-- Check sample data
SELECT COUNT(*) as countries_count FROM countries;
SELECT COUNT(*) as states_count FROM states;
SELECT COUNT(*) as destinations_count FROM destinations;
SELECT COUNT(*) as trip_history_count FROM trip_history;
SELECT COUNT(*) as expense_breakdown_count FROM expense_breakdown;
SELECT COUNT(*) as alerts_count FROM alerts;
SELECT COUNT(*) as routes_count FROM routes;