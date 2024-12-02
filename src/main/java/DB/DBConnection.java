package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static DBConnection dbConnection; // Singleton instance
    private Connection connection;

    private DBConnection() {
        connect(); // Initialize the connection when the instance is created
    }


    private void connect() {
        try {
            // Load MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish the database connection
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/pos_db?useSSL=false&serverTimezone=UTC",
                    "root",
                    ""
            );

            if (connection != null) {
                System.out.println("Connection established successfully!");
            } else {
                System.out.println("Connection failed!");
            }

        } catch (ClassNotFoundException e) {
            System.out.println("JDBC Driver not found!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("SQL Exception occurred while establishing connection.");
            e.printStackTrace();
        }
    }

    public static DBConnection getInstance() {
        // Create a new instance only if it doesn't exist
        if (dbConnection == null) {
            dbConnection = new DBConnection();
        }
        return dbConnection;
    }

    public  Connection getConnection() {
        try {
            // Check if the connection is valid; reconnect if needed
            if (connection == null || connection.isClosed() || !connection.isValid(5)) {
                System.out.println("Re-establishing connection...");
                connect();
            }
        } catch (SQLException e) {
            System.out.println("Error while validating connection.");
            e.printStackTrace();
        }
        return connection;
    }
    // hey
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Connection closed successfully.");
            }
        } catch (SQLException e) {
            System.out.println("Error closing connection.");
            e.printStackTrace();
        }
    }
}