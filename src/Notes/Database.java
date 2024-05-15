package Notes;

import java.sql.*;

import javax.xml.crypto.Data;

public class Database {
    private static Connection connection;
    private static Boolean connected = false;

    /*
     * Connects to the database
     */

    private static void connect() {
        if (connected) {
            return;
        }
        try {
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
        if (!connected) {
            return;
        }
        try {
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
     * @param exec The query to execute
     * @return The result of the query
     */

    public static ResultSet exec(String exec) {
        try {
            if (!connected || Database.connection.isClosed() || !Database.connection.isValid(0))
                Database.disconnect();
            Database.connect();
            Statement statement = connection.createStatement();
            return statement.executeQuery(exec);
        } catch (SQLException e) {
            System.out.println("Error executing query: " + e.getMessage());
            return null;
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
