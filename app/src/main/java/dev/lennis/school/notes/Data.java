package dev.lennis.school.notes;

import java.util.ArrayList;

public class Data {
    /* == NOTES == */

    public static ArrayList<ArrayList<String>> getNotes() {
        return Database.execute("SELECT * FROM notes", new ArrayList<String>(), false);
    }

    public static ArrayList<String> getNoteById(int id) {
        ArrayList<String> params = new ArrayList<String>();
        params.add(Integer.toString(id));
        return Database.execute("SELECT id, username, heading, text FROM notes WHERE id = ?", params, false).get(0);
    }

    public static int addNote(String username, String heading, String text) {
        ArrayList<String> params = new ArrayList<String>();
        params.add(username);
        params.add(heading);
        params.add(text);
        Database.execute("INSERT INTO notes (username, heading, text) VALUES (?, ?, ?)", params, true);
        return Integer
                .parseInt(Database.execute("SELECT last_insert_rowid()", new ArrayList<String>(), false).get(0).get(0));
    }

    public static void updateNoteHeading(int id, String heading) {
        ArrayList<String> params = new ArrayList<String>();
        params.add(heading);
        params.add(Integer.toString(id));
        Database.execute("UPDATE notes SET heading = ? WHERE id = ?", params, true);
    }

    public static void updateNoteText(int id, String text) {
        ArrayList<String> params = new ArrayList<String>();
        params.add(text);
        params.add(Integer.toString(id));
        Database.execute("UPDATE notes SET text = ? WHERE id = ?", params, true);
    }

    public static void deleteNoteById(int id) {
        ArrayList<String> params = new ArrayList<String>();
        params.add(Integer.toString(id));
        Database.execute("DELETE FROM notes WHERE id = ?", params, true);
    }

    public static ArrayList<String> getTagsByNoteId(int noteId) {
        ArrayList<String> params = new ArrayList<String>();
        params.add(Integer.toString(noteId));
        ArrayList<String> tags = new ArrayList<String>();
        for (ArrayList<String> tag : Database.execute("SELECT tag FROM tags WHERE noteID = ?", params, false)) {
            tags.add(tag.get(0));
        }
        return tags;
    }

    public static void addTagToNoteId(int noteId, String tag) {
        ArrayList<String> params = new ArrayList<String>();
        params.add(Integer.toString(noteId));
        params.add(tag);
        Database.execute("INSERT INTO tags (noteID, tag) VALUES (?, ?)", params, true);
    }

    public static void removeTagFromNoteId(int noteId, String tag) {
        ArrayList<String> params = new ArrayList<String>();
        params.add(Integer.toString(noteId));
        params.add(tag);
        Database.execute("DELETE FROM tags WHERE noteID = ? AND tag = ?", params, true);
    }

    public static ArrayList<Integer> getNotesByTag(String tag) {
        ArrayList<String> params = new ArrayList<String>();
        params.add(tag);
        ArrayList<Integer> notes = new ArrayList<Integer>();
        for (ArrayList<String> note : Database.execute(
                "SELECT DISTINCT id FROM notes WHERE id IN (SELECT noteID FROM tags WHERE tag = ?)", params, false)) {
            notes.add(Integer.parseInt(note.get(0)));
        }
        return notes;
    }

    public static ArrayList<Integer> getNotesByUsername(String username) {
        ArrayList<String> params = new ArrayList<String>();
        params.add(username);
        ArrayList<Integer> notes = new ArrayList<Integer>();
        for (ArrayList<String> note : Database.execute("SELECT DISTINCT id FROM notes WHERE username = ?", params,
                false)) {
            notes.add(Integer.parseInt(note.get(0)));
        }
        return notes;
    }

    /* == USERS == */

    public static ArrayList<ArrayList<String>> getUsers() {
        return Database.execute("SELECT username, displayName FROM users", new ArrayList<String>(), false);
    }

    public static ArrayList<String> getUserByUsername(String username) {
        ArrayList<String> params = new ArrayList<String>();
        params.add(username);
        return Database.execute("SELECT * FROM users WHERE username = ?", params, false).get(0);
    }

    public static void addUser(String username, String displayName, String passwordSalt, String passwordHash) {
        ArrayList<String> params = new ArrayList<String>();
        params.add(username);
        params.add(displayName);
        params.add(passwordSalt);
        params.add(passwordHash);
        Database.execute(
                "INSERT OR IGNORE INTO users (username, displayName, passwordSalt, passwordHash) VALUES (?, ?, ?, ?)",
                params, true);
    }

    public static void updateUserDisplayName(String username, String displayName) {
        ArrayList<String> params = new ArrayList<String>();
        params.add(displayName);
        params.add(username);
        Database.execute("UPDATE users SET displayName = ? WHERE username = ?", params, true);
    }

    public static void updateUserPassword(String username, String passwordSalt, String passwordHash) {
        ArrayList<String> params = new ArrayList<String>();
        params.add(passwordSalt);
        params.add(passwordHash);
        params.add(username);
        Database.execute("UPDATE users SET passwordSalt = ?, passwordHash = ? WHERE username = ?", params, true);
    }

    public static void deleteUserByUsername(String username) {
        ArrayList<String> params = new ArrayList<String>();
        params.add(username);
        Database.execute("DELETE FROM users WHERE username = ?", params, true);
    }
}