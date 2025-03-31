package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * This class manages the connection to the MySQL database,
 * including establishing, retrieving, and closing the connection.
 */
public class DBConnection {
    /**
     *     The PreparedStatement object used for executing SQL queries
     */
    private static PreparedStatement preparedStatement;
    private static final String protocol = "jdbc";
    private static final String vendorName = ":mysql:";
    private static final String ipAddress = "//127.0.0.1:3306/client_schedule";

    /** Uncomment if the database is on a different server
     private static final String ipAddress = "//localhost:3306/client_schedule";
     */

    private static final String jdbcURL = protocol + vendorName + ipAddress + "?connectionTimeZone=SERVER";

    /**
    * MySQL JDBC driver class
     */
    private static final String MYSQLJDBCDriver = "com.mysql.cj.jdbc.Driver";

    /**
     * The PreparedStatement object used for executing SQL queries
     */
    public static Connection conn = null;


    /**
     * Logs application into the database
     */
    private static final String username = "sqlUser";
    private static final String password = "Passw0rd!";

    /**
     * StartConnection method establishes a connection to the MySQL database.
     */
    public static Connection startConnection() {
        try {
            // Load the MySQL JDBC driver
            Class.forName(MYSQLJDBCDriver);
            // Establish the connection using the JDBC URL, username, and password
            conn = (Connection) DriverManager.getConnection(jdbcURL, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            // Exception handling can be added here (e.g., logging the error)
        }
        return conn;
    }

    /**
     * Retrieves current database connection with error handling
     */
    public static Connection getConnection() {
        if (conn == null) {
            throw new IllegalStateException("Database connection is not established. Call startConnection() first.");
        }
        return conn;
    }

    /**
     * Method to close the database connection.
     */
    public static void closeConnection() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close(); // Close the connection
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace(); // Handle exception (e.g., log or rethrow)
        }
    }

    /**
     * Queues SQL statement for execution.
     */
    public static void setPreparedStatement(Connection conn, String sqlStatement) throws SQLException {
        preparedStatement = conn.prepareStatement(sqlStatement);
    }

    /**
     * Retrieves the prepared statement.
     */
    public static PreparedStatement getPreparedStatement() {
        return preparedStatement;
    }
}
