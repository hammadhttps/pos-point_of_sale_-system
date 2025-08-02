package DB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for common database operations and constants.
 * Provides helper methods for database operations across the application.
 */
public class DatabaseUtils {

    // Common table names
    public static final String TABLE_PRODUCT = "product";
    public static final String TABLE_SALE = "Sale";
    public static final String TABLE_SALE_PRODUCT = "SaleProduct";
    public static final String TABLE_BRANCH = "branch";
    public static final String TABLE_CASHIER = "cashier";
    public static final String TABLE_BRANCH_MANAGER = "branch_manager";
    public static final String TABLE_DATA_ENTRY_OPERATOR = "data_entry_operator";
    public static final String TABLE_VENDOR = "vendor";
    public static final String TABLE_SUPER_ADMIN = "super_admin";

    // Common column names
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_QUANTITY = "quantity";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_BRANCH_CODE = "branchcode";

    // SQL Keywords
    public static final String SQL_SELECT = "SELECT";
    public static final String SQL_INSERT = "INSERT";
    public static final String SQL_UPDATE = "UPDATE";
    public static final String SQL_DELETE = "DELETE";
    public static final String SQL_FROM = "FROM";
    public static final String SQL_WHERE = "WHERE";
    public static final String SQL_AND = "AND";
    public static final String SQL_OR = "OR";
    public static final String SQL_ORDER_BY = "ORDER BY";
    public static final String SQL_LIMIT = "LIMIT";

    /**
     * Safely close database resources
     * 
     * @param resources resources to close
     */
    public static void closeResources(AutoCloseable... resources) {
        for (AutoCloseable resource : resources) {
            if (resource != null) {
                try {
                    resource.close();
                } catch (Exception e) {
                    System.err.println("Error closing resource: " + e.getMessage());
                }
            }
        }
    }

    /**
     * Rollback transaction safely
     * 
     * @param connection database connection
     */
    public static void rollbackTransaction(Connection connection) {
        if (connection != null) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                System.err.println("Error rolling back transaction: " + e.getMessage());
            }
        }
    }

    /**
     * Build a WHERE clause with multiple conditions
     * 
     * @param conditions list of conditions
     * @param operator   operator to join conditions (AND/OR)
     * @return formatted WHERE clause
     */
    public static String buildWhereClause(List<String> conditions, String operator) {
        if (conditions == null || conditions.isEmpty()) {
            return "";
        }

        StringBuilder whereClause = new StringBuilder(SQL_WHERE + " ");
        for (int i = 0; i < conditions.size(); i++) {
            if (i > 0) {
                whereClause.append(" ").append(operator).append(" ");
            }
            whereClause.append(conditions.get(i));
        }

        return whereClause.toString();
    }

    /**
     * Build an ORDER BY clause
     * 
     * @param columns   list of columns to order by
     * @param ascending true for ASC, false for DESC
     * @return formatted ORDER BY clause
     */
    public static String buildOrderByClause(List<String> columns, boolean ascending) {
        if (columns == null || columns.isEmpty()) {
            return "";
        }

        StringBuilder orderByClause = new StringBuilder(SQL_ORDER_BY + " ");
        for (int i = 0; i < columns.size(); i++) {
            if (i > 0) {
                orderByClause.append(", ");
            }
            orderByClause.append(columns.get(i));
        }

        orderByClause.append(ascending ? " ASC" : " DESC");
        return orderByClause.toString();
    }

    /**
     * Build a LIMIT clause
     * 
     * @param limit  maximum number of rows
     * @param offset offset for pagination
     * @return formatted LIMIT clause
     */
    public static String buildLimitClause(int limit, int offset) {
        if (limit <= 0) {
            return "";
        }

        StringBuilder limitClause = new StringBuilder(SQL_LIMIT + " " + limit);
        if (offset > 0) {
            limitClause.append(" OFFSET ").append(offset);
        }

        return limitClause.toString();
    }

    /**
     * Check if a table exists
     * 
     * @param connection database connection
     * @param tableName  name of the table to check
     * @return true if table exists, false otherwise
     */
    public static boolean tableExists(Connection connection, String tableName) {
        try {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet tables = metaData.getTables(null, null, tableName, new String[] { "TABLE" });
            return tables.next();
        } catch (SQLException e) {
            System.err.println("Error checking if table exists: " + e.getMessage());
            return false;
        }
    }

    /**
     * Get column names for a table
     * 
     * @param connection database connection
     * @param tableName  name of the table
     * @return list of column names
     */
    public static List<String> getColumnNames(Connection connection, String tableName) {
        List<String> columnNames = new ArrayList<>();

        try {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet columns = metaData.getColumns(null, null, tableName, null);

            while (columns.next()) {
                columnNames.add(columns.getString("COLUMN_NAME"));
            }
        } catch (SQLException e) {
            System.err.println("Error getting column names: " + e.getMessage());
        }

        return columnNames;
    }

    /**
     * Execute a simple query and return the first result as String
     * 
     * @param connection database connection
     * @param sql        SQL query to execute
     * @param params     query parameters
     * @return first result as String, or null if no results
     */
    public static String executeSingleResultQuery(Connection connection, String sql, Object[] params) {
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            if (params != null) {
                for (int i = 0; i < params.length; i++) {
                    stmt.setObject(i + 1, params[i]);
                }
            }

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString(1);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error executing single result query: " + e.getMessage());
        }

        return null;
    }

    /**
     * Format SQL query for logging (remove sensitive data)
     * 
     * @param sql original SQL query
     * @return formatted SQL query for logging
     */
    public static String formatSqlForLogging(String sql) {
        if (sql == null) {
            return "null";
        }

        // Remove password-related patterns for security
        return sql.replaceAll("(?i)password\\s*=\\s*['\"][^'\"]*['\"]", "password=***")
                .replaceAll("(?i)pwd\\s*=\\s*['\"][^'\"]*['\"]", "pwd=***");
    }
}