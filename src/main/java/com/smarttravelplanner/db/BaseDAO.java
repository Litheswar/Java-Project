package com.smarttravelplanner.db;

import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Base DAO class that provides common functionality for all DAOs
 */
public abstract class BaseDAO {
    
    @Autowired
    protected DataSource dataSource;
    
    /**
     * Gets a database connection
     * @return Connection object
     * @throws SQLException if connection fails
     */
    protected Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}