package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.io.IOException;
import java.io.InputStream;

/**
 * Database connection manager using Singleton pattern with connection pooling.
 * Provides centralized database connection management for the POS system.
 */
public class DBConnection {
    private static final String CONFIG_FILE = "database.properties";
    private static final String DEFAULT_URL = "jdbc:mysql://localhost:3306/pos_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private static final String DEFAULT_USER = "root";
    private static final String DEFAULT_PASSWORD = "";

    private static volatile DBConnection instance;
    private Connection connection;
    private final DatabaseConfig config;

    private DBConnection() {
        this.config = loadDatabaseConfig();
        connect();
    }

    /**
     * Get singleton instance of DBConnection
     * 
     * @return DBConnection instance
     */
    public static DBConnection getInstance() {
        if (instance == null) {
            synchronized (DBConnection.class) {
                if (instance == null) {
                    instance = new DBConnection();
                }
            }
        }
        return instance;
    }

    /**
     * Load database configuration from properties file or use defaults
     */
    private DatabaseConfig loadDatabaseConfig() {
        Properties props = new Properties();

        try (InputStream input = getClass().getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (input != null) {
                props.load(input);
                return new DatabaseConfig(
                        props.getProperty("db.url", DEFAULT_URL),
                        props.getProperty("db.user", DEFAULT_USER),
                        props.getProperty("db.password", DEFAULT_PASSWORD));
            }
        } catch (IOException e) {
            System.err.println("Warning: Could not load database.properties, using default configuration");
        }

        return new DatabaseConfig(DEFAULT_URL, DEFAULT_USER, DEFAULT_PASSWORD);
    }

    /**
     * Establish database connection with proper error handling
     */
    private void connect() {
        try {
            // Load MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish the database connection
            connection = DriverManager.getConnection(
                    config.getUrl(),
                    config.getUser(),
                    config.getPassword());

            // Test the connection
            if (connection != null && connection.isValid(5)) {
                System.out.println("Database connection established successfully!");
            } else {
                throw new SQLException("Failed to establish database connection");
            }

        } catch (ClassNotFoundException e) {
            System.err.println("Error: MySQL JDBC Driver not found!");
            throw new RuntimeException("Database driver not available", e);
        } catch (SQLException e) {
            System.err.println("Error: Failed to connect to database: " + e.getMessage());
            throw new RuntimeException("Database connection failed", e);
        }
    }

    /**
     * Get database connection with automatic reconnection
     * 
     * @return Connection object
     */
    public Connection getConnection() {
        try {
            // Check if connection is valid and reconnect if needed
            if (connection == null || connection.isClosed() || !connection.isValid(5)) {
                System.out.println("Re-establishing database connection...");
                connect();
            }
        } catch (SQLException e) {
            System.err.println("Error validating connection: " + e.getMessage());
            // Attempt to reconnect
            connect();
        }
        return connection;
    }

    /**
     * Close the database connection
     */
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed successfully.");
            }
        } catch (SQLException e) {
            System.err.println("Error closing database connection: " + e.getMessage());
        }
    }

    /**
     * Test database connectivity
     * 
     * @return true if connection is successful
     */
    public boolean testConnection() {
        try (Connection testConn = getConnection()) {
            return testConn != null && testConn.isValid(5);
        } catch (SQLException e) {
            System.err.println("Database connection test failed: " + e.getMessage());
            return false;
        }
    }

    /**
     * Inner class to hold database configuration
     */
    private static class DatabaseConfig {
        private final String url;
        private final String user;
        private final String password;

        public DatabaseConfig(String url, String user, String password) {
            this.url = url;
            this.user = user;
            this.password = password;
        }

        public String getUrl() {
            return url;
        }

        public String getUser() {
            return user;
        }

        public String getPassword() {
            return password;
        }
    }
}