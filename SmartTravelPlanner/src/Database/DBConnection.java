package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DBConnection encapsulates JDBC setup and provides a single utility method
 * to obtain a Connection to the Supabase PostgreSQL database using DriverManager.
 * Demonstrates Encapsulation and Exception Handling.
 */
public class DBConnection {
    // Supabase PostgreSQL connection details
    private static final String URL = "jdbc:postgresql://gjbrxsdpndkoaknrdsch.supabase.co:5432/postgres";
    private static final String USER = "postgres"; // default Supabase DB user
    private static final String PASSWORD = "<YOUR_DB_PASSWORD>"; // replace with your Supabase DB password

    private DBConnection() { }

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("PostgreSQL JDBC Driver not found", e);
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
