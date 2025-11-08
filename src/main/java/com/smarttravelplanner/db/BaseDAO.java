package com.smarttravelplanner.db;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Base DAO class that provides common functionality for all DAOs
 */
public abstract class BaseDAO {
    
    /**
     * Gets a database connection
     * @return Connection object
     * @throws SQLException if connection fails
     */
    protected Connection getConnection() throws SQLException {
        return DBConnection.getConnection();
    }
    
    /**
     * Closes the database connection
     */
    protected void closeConnection() {
        DBConnection.closeConnection();
    }
}