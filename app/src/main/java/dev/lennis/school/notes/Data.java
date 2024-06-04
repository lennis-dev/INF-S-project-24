package dev.lennis.school.notes;

import java.sql.ResultSet;
import java.util.ArrayList;

public class Data {
    static ArrayList<ArrayList<String>> sqlQuery(String query) {
        try {
            ResultSet resultSet = Database.query(query);
            if (resultSet == null)
                return null;

            ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
            while (resultSet.next()) {
                ArrayList<String> row = new ArrayList<String>();
                for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                    row.add(resultSet.getString(i));
                }
                result.add(row);
            }

            return result;
        } catch (Exception e) {
            System.out.println("Error executing SQL query: " + e.getMessage());
            return null;
        }
    }

    /**
     * Gets a note from the database by id
     * 
     * @param id The ID of the note
     * @return An array containing the heading and text of the note
     *         [0] The ID
     *         [1] The username
     *         [2] The heading
     *         [3] The text
     *         [4] The tags
     * 
     */
    static String[] getNote(int id, String username) {
        try {
            String[] result = new String[5];
            result[4] = "";

            ArrayList<ArrayList<String>> queryResult = sqlQuery(
                    "SELECT ID, username, heading, text, tag FROM notes LEFT JOIN tags ON notes.ID = tags.noteID WHERE ID = "
                            + id + " AND username = '" + username + "';");

            for (ArrayList<String> row : queryResult) {
                if (result[4] != "")
                    result[4] = result[4] + ", " + row.get(4);
                else {
                    result[4] = result[0] = row.get(0);
                    result[1] = row.get(1);
                    result[2] = row.get(2);
                    result[3] = row.get(3);
                }

            }
            return result;
        } catch (Exception e) {
            System.out.println("Error getting note: " + e.getMessage());
            return null;
        }
    }

    /**
     * Gets all notes from the database
     * 
     * @return An multidimensional array containing the notes and their data
     *         [0] The ID
     *         [1] The username
     *         [2] The heading
     *         [3] The text
     *         [4] The tags
     */

    static String[][] getNotesByTag(String tag, String username) {
        try {
            ArrayList<ArrayList<String>> queryResult = sqlQuery(
                    "SELECT notes.ID, username, heading, text, tag FROM notes LEFT JOIN tags ON notes.ID = tags.noteID WHERE notes.ID IN (SELECT noteID FROM tags WHERE tag = '"
                            + tag + "') AND username = '" + username + "';");

            for (ArrayList<String> row : queryResult) {
                if (row.get(4) == null)
                    row.set(4, "");
                for (ArrayList<String> row2 : queryResult) {
                    if (row.get(0).equals(row2.get(0))) {
                        row.set(4, row.get(4) + ", " + row2.get(4));
                        queryResult.remove(row2);
                    }

                }
            }

            String[][] result = new String[queryResult.size()][5];
            for (int i = 0; i < queryResult.size(); i++) {
                result[i] = queryResult.get(i).toArray(new String[5]);
            }

            return result;
        } catch (Exception e) {
            System.out.println("Error getting notes by tag: " + e.getMessage());
            return null;
        }
    }

    static boolean addTagToNote(int noteID, String tag) {
        try {
            Database.update("INSERT INTO tags (noteID, tag) VALUES (" + noteID + ", '" + tag + "');");
            return true;
        } catch (Exception e) {
            System.out.println("Error adding tag to note: " + e.getMessage());
            return false;
        }
    }

    static boolean removeTagFromNote(int noteID, String tag) {
        try {
            Database.update("DELETE FROM tags WHERE noteID = " + noteID + " AND tag = '" + tag + "';");
            return true;
        } catch (Exception e) {
            System.out.println("Error removing tag from note: " + e.getMessage());
            return false;
        }
    }

    /* ==== USER START ==== */

    static String[] getUserByUsername(String username) {
        try {
            ArrayList<ArrayList<String>> queryResult = sqlQuery(
                    "SELECT * FROM users WHERE username = '" + username + "';");

            String[] result = new String[4];
            for (ArrayList<String> row : queryResult) {
                result[0] = row.get(0);
                result[1] = row.get(1);
                result[2] = row.get(2);
                result[3] = row.get(3);
            }

            return result;
        } catch (Exception e) {
            System.out.println("Error getting user by username: " + e.getMessage());
            return null;
        }
    }

    static void setUserPasswordByUsername(String username, String passwordSalt, String passwordHash) {
        try {
            Database.update("UPDATE users SET passwordSalt = '" + passwordSalt + "', passwordHash = '" + passwordHash
                    + "' WHERE username = '" + username + "';");
        } catch (Exception e) {
            System.out.println("Error setting user password by username: " + e.getMessage());
        }
    }

    static void createUser(String username, String displayName, String passwordSalt, String passwordHash) {
        try {
            Database.update("INSERT INTO users (username, displayName, passwordSalt, passwordHash) VALUES ('" + username
                    + "', '" + displayName + "', '" + passwordSalt + "', '" + passwordHash + "');");
        } catch (Exception e) {
            System.out.println("Error creating user: " + e.getMessage());
        }
    }

}