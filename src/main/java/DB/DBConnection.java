package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static DBConnection dbConnection;
    private Connection connection;

    private DBConnection() {
        try {

          //  System.out.println("Loading JDBC driver...");
            Class.forName("com.mysql.cj.jdbc.Driver");


            //System.out.println("Establishing connection...");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/pos_db?useSSL=false&serverTimezone=UTC",
                    "root",
                    ""
            );

            if (connection != null) {
                //System.out.println("Connection established successfully!");
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
        return (dbConnection == null) ? dbConnection = new DBConnection() : dbConnection;
    }

    public Connection getConnection() {
        return connection;
    }

//    public static void main(String[] args) {
//
//        System.out.println("Starting DBConnection...");
//        DBConnection dbConnection = DBConnection.getInstance();
//
//
//        Connection connection = dbConnection.getConnection();
//
//
//        if (connection != null) {
//            System.out.println("Connection established successfully!");
//        } else {
//            System.out.println("Failed to establish connection.");
//        }
//
//
//        try {
//            if (connection != null) {
//                connection.close();
//                System.out.println("Connection closed.");
//            }
//        } catch (SQLException e) {
//            System.out.println("Error closing connection.");
//            e.printStackTrace();
//        }
//    }
}
