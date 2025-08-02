package services;

import DB.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * Base DAO class providing common database operations and utilities.
 * All DAO classes should extend this class to inherit common functionality.
 */
public abstract class BaseDAO {

    protected static final String SUCCESS_MESSAGE = "Operation completed successfully";
    protected static final String ERROR_MESSAGE = "Operation failed";
    protected static final String NOT_FOUND_MESSAGE = "Record not found";

    /**
     * Get database connection
     * 
     * @return Connection object
     */
    protected Connection getConnection() {
        return DBConnection.getInstance().getConnection();
    }

    /**
     * Execute a query and return a list of results
     * 
     * @param sql       SQL query to execute
     * @param params    parameters for the prepared statement
     * @param rowMapper function to map ResultSet row to object
     * @param <T>       type of object to return
     * @return List of mapped objects
     */
    protected <T> List<T> executeQuery(String sql, Object[] params, Function<ResultSet, T> rowMapper) {
        List<T> results = new ArrayList<>();

        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Set parameters
            if (params != null) {
                for (int i = 0; i < params.length; i++) {
                    stmt.setObject(i + 1, params[i]);
                }
            }

            // Execute query and map results
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    T result = rowMapper.apply(rs);
                    if (result != null) {
                        results.add(result);
                    }
                }
            }

        } catch (SQLException e) {
            System.err.println("Error executing query: " + e.getMessage());
            logError("Query execution failed", e);
        }

        return results;
    }

    /**
     * Execute an update statement (INSERT, UPDATE, DELETE)
     * 
     * @param sql    SQL statement to execute
     * @param params parameters for the prepared statement
     * @return number of affected rows
     */
    protected int executeUpdate(String sql, Object[] params) {
        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Set parameters
            if (params != null) {
                for (int i = 0; i < params.length; i++) {
                    stmt.setObject(i + 1, params[i]);
                }
            }

            return stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error executing update: " + e.getMessage());
            logError("Update execution failed", e);
            return -1;
        }
    }

    /**
     * Execute a batch update
     * 
     * @param sql         SQL statement to execute
     * @param batchParams list of parameter arrays for batch execution
     * @return array of affected row counts
     */
    protected int[] executeBatchUpdate(String sql, List<Object[]> batchParams) {
        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Add batch statements
            for (Object[] params : batchParams) {
                if (params != null) {
                    for (int i = 0; i < params.length; i++) {
                        stmt.setObject(i + 1, params[i]);
                    }
                }
                stmt.addBatch();
            }

            return stmt.executeBatch();

        } catch (SQLException e) {
            System.err.println("Error executing batch update: " + e.getMessage());
            logError("Batch update execution failed", e);
            return new int[0];
        }
    }

    /**
     * Execute a transaction with multiple operations
     * 
     * @param operations list of database operations to execute
     * @return true if all operations succeed, false otherwise
     */
    protected boolean executeTransaction(List<DatabaseOperation> operations) {
        Connection conn = null;
        try {
            conn = getConnection();
            conn.setAutoCommit(false);

            for (DatabaseOperation operation : operations) {
                if (!operation.execute(conn)) {
                    conn.rollback();
                    return false;
                }
            }

            conn.commit();
            return true;

        } catch (SQLException e) {
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException rollbackEx) {
                System.err.println("Error during rollback: " + rollbackEx.getMessage());
            }
            logError("Transaction execution failed", e);
            return false;
        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                }
            } catch (SQLException e) {
                System.err.println("Error resetting auto-commit: " + e.getMessage());
            }
        }
    }

    /**
     * Check if a record exists
     * 
     * @param sql    SQL query to check existence
     * @param params parameters for the prepared statement
     * @return true if record exists, false otherwise
     */
    protected boolean exists(String sql, Object[] params) {
        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            if (params != null) {
                for (int i = 0; i < params.length; i++) {
                    stmt.setObject(i + 1, params[i]);
                }
            }

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            System.err.println("Error checking existence: " + e.getMessage());
            logError("Existence check failed", e);
            return false;
        }
    }

    /**
     * Get count of records
     * 
     * @param sql    SQL query to count records
     * @param params parameters for the prepared statement
     * @return count of records
     */
    protected int getCount(String sql, Object[] params) {
        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            if (params != null) {
                for (int i = 0; i < params.length; i++) {
                    stmt.setObject(i + 1, params[i]);
                }
            }

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() ? rs.getInt(1) : 0;
            }

        } catch (SQLException e) {
            System.err.println("Error getting count: " + e.getMessage());
            logError("Count query failed", e);
            return 0;
        }
    }

    /**
     * Log error with context
     * 
     * @param context   error context
     * @param exception the exception that occurred
     */
    protected void logError(String context, Exception exception) {
        System.err.println("[" + getClass().getSimpleName() + "] " + context + ": " + exception.getMessage());
        if (exception instanceof SQLException) {
            SQLException sqlEx = (SQLException) exception;
            System.err.println("SQL State: " + sqlEx.getSQLState());
            System.err.println("Error Code: " + sqlEx.getErrorCode());
        }
    }

    /**
     * Functional interface for database operations in transactions
     */
    @FunctionalInterface
    protected interface DatabaseOperation {
        boolean execute(Connection connection) throws SQLException;
    }
}