package dev.lennis.school.notes;

import java.sql.*;
import java.util.ArrayList;

public class Database {
    private static Connection connection;
    private static String url = "jdbc:sqlite:notes.db";

    /*
     * Open the database
     */

    private static void open() throws SQLException {
        try {
            if (connection == null) {
                connection = DriverManager.getConnection(url);
                System.out.println("Connected to database");
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            System.out.println("Error connecting to database: " + e.getMessage());
        }

    }

    /*
     * Close the connection to the database
     */

    private static void close() {
        try {
            if (connection != null) {
                connection.close();
                System.out.println("Connection to database closed");
            }
        } catch (SQLException e) {
            System.out.println("Error closing connection: " + e.getMessage());
        }
    }

    /**
     * Executes a query on the database
     * 
     * @param sql The query to execute
     * @return The result of the query
     */

    public static ArrayList<ArrayList<String>> execute(String sql, ArrayList<String> params, boolean update) {
        ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
        try {
            open();
            PreparedStatement statement = connection.prepareStatement(sql);
            for (int i = 0; i < params.size(); i++) {
                statement.setString(i + 1, params.get(i));
            }
            if (update) {
                statement.executeUpdate();
                return result;
            }
            ResultSet rs = statement.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            while (rs.next()) {
                ArrayList<String> row = new ArrayList<String>();
                for (int i = 1; i <= columnsNumber; i++) {
                    row.add(rs.getString(i));
                }
                result.add(row);
            }
            statement.close();
        } catch (SQLException e) {
            System.out.println("Error executing query: " + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    public static String[] getNoteById(int id) {
        String sql = "SELECT * FROM notes WHERE id = ?";
        ArrayList<String> params = new ArrayList<String>();
        params.add(Integer.toString(id));
        ArrayList<ArrayList<String>> result = execute(sql, params, false);
        if (result.size() > 0) {
            return result.get(0).toArray(new String[0]);
        }
        return null;
    }

    public static void install() {
        String[] sql = {
                "CREATE TABLE IF NOT EXISTS notes (id INTEGER PRIMARY KEY, username TINYTEXT, heading text, text TEXT, FOREIGN KEY(username) REFERENCES users(username) ON DELETE CASCADE)",
                "CREATE TABLE IF NOT EXISTS tags (noteID INTEGER, tag TINYTEXT, FOREIGN KEY(noteID) REFERENCES notes(id) ON DELETE CASCADE)",
                "CREATE TABLE IF NOT EXISTS users (username TINYTEXT PRIMARY KEY, displayName TINYTEXT, passwordSalt TINYTEXT, passwordHash TEXT)" };
        for (String s : sql) {
            execute(s, new ArrayList<String>(), true);
        }
    }

    /**
     * Close Databse Connection on Shutdown
     * https://stackoverflow.com/questions/64318551
     * https://stackoverflow.com/questions/4564276
     */

    static {
        install();
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                Database.close();
            }
        });
    }

}
