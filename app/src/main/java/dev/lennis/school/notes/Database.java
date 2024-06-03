package dev.lennis.school.notes;

import java.sql.*;

public class Database {
    private static Connection connection;
    private static Boolean connected = false;

    /*
     * Connects to the database
     */

    private static void connect() {
        try {
            if (connected) {
                return;
            }
            Database.connection = DriverManager.getConnection(Config.getJDBCString(), Config.getUsername(),
                    Config.getPassword());
            System.out.println("Connected to database" + Config.getJDBCString() + " as " + Config.getUsername());
            connected = true;
        } catch (SQLException e) {
            System.out.println("Error connecting to database: " + e.getMessage());
        }
    }

    /*
     * Disconnects from the database
     */

    private static void disconnect() {
        try {
            if (!connected) {
                return;
            }
            Database.connection.close();
            System.out.println("Disconnected from database");
            connected = false;
        } catch (SQLException e) {
            System.out.println("Error disconnecting from database: " + e.getMessage());
        }
    }

    /**
     * Executes a query on the database
     * 
     * @param sql The query to execute
     * @return The result of the query
     */

    public static ResultSet query(String sql) {
        try {
            if (!connected)
                Database.disconnect();
            Database.connect();
            Statement statement = connection.createStatement();
            return statement.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println("Error executing query: " + e.getMessage());
            return null;
        }
    }

    /**
     * Executes an update on the database
     * 
     * @param sql The update to execute
     * @return The result of the update
     */

    public static void update(String sql) {
        try {
            if (!connected)
                Database.disconnect();
            Database.connect();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.execute();
            return;

        } catch (SQLException e) {
            System.out.println("Error executing update: " + e.getMessage());
            return;
        }
    }

    /**
     * Close Databse Connection on Shutdown
     * https://stackoverflow.com/questions/64318551
     * https://stackoverflow.com/questions/4564276
     */

    static {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                Database.disconnect();
            }
        });
    }

}
