package dev.lennis.school.notes;

import java.util.ArrayList;

public class Data {
    /* == NOTES == */
    public static ArrayList<Integer> getNotesBySearch(String username, String search) {
        ArrayList<ArrayList<String>> data = Database.execute("SELECT id FROM notes WHERE username = ? AND (text LIKE ? OR heading LIKE ?)",
                new String[] { username, "%" + search + "%", "%" + search + "%" }, false);
        ArrayList<Integer> result = new ArrayList<Integer>();
        for(ArrayList<String> row : data){
            result.add(Integer.valueOf(row.get(0)));
        }
        return result;

    }

    public static ArrayList<ArrayList<String>> getNotes() {
        return Database.execute("SELECT * FROM notes", new String[0], false);
    }

    public static ArrayList<String> getNoteById(int id) {
        return Database.execute("SELECT id, username, heading, text FROM notes WHERE id = ?",
                new String[] { Integer.toString(id) }, false).get(0);
    }

    public static int addNote(String username, String heading, String text) {
        Database.execute("INSERT INTO notes (username, heading, text) VALUES (?, ?, ?)",
                new String[] { username, heading, text }, true);
        return Integer
                .parseInt(Database.execute("SELECT last_insert_rowid()", new String[0], false).get(0).get(0));
    }

    public static void updateNoteHeading(int id, String heading) {
        Database.execute("UPDATE notes SET heading = ? WHERE id = ?", new String[] { heading, Integer.toString(id) },
                true);
    }

    public static void updateNoteText(int id, String text) {
        Database.execute("UPDATE notes SET text = ? WHERE id = ?", new String[] { text, Integer.toString(id) }, true);
    }

    public static void deleteNoteById(int id) {
        Database.execute("DELETE FROM notes WHERE id = ?", new String[] { Integer.toString(id) }, true);
    }

    public static ArrayList<String> getTagsByNoteId(int noteId) {
        ArrayList<String> tags = new ArrayList<String>();
        for (ArrayList<String> tag : Database.execute("SELECT tag FROM tags WHERE noteID = ?",
                new String[] { Integer.toString(noteId) }, false)) {
            tags.add(tag.get(0));
        }
        return tags;
    }

    public static void addTagToNoteId(int noteId, String tag) {
        Database.execute("INSERT INTO tags (noteID, tag) VALUES (?, ?)", new String[] { Integer.toString(noteId), tag },
                true);
    }

    public static void removeTagFromNoteId(int noteId, String tag) {
        Database.execute("DELETE FROM tags WHERE noteID = ? AND tag = ?",
                new String[] { Integer.toString(noteId), tag },
                true);
    }

    public static ArrayList<Integer> getNotesByTag(String tag) {
        ArrayList<Integer> notes = new ArrayList<Integer>();
        for (ArrayList<String> note : Database.execute(
                "SELECT DISTINCT id FROM notes WHERE id IN (SELECT noteID FROM tags WHERE tag = ?)",
                new String[] { tag }, false)) {
            notes.add(Integer.parseInt(note.get(0)));
        }
        return notes;
    }

    public static ArrayList<Integer> getNotesByUsername(String username) {
        ArrayList<Integer> notes = new ArrayList<Integer>();
        for (ArrayList<String> note : Database.execute("SELECT DISTINCT id FROM notes WHERE username = ?",
                new String[] { username },
                false)) {
            notes.add(Integer.parseInt(note.get(0)));
        }
        return notes;
    }

    /* == USERS == */

    public static ArrayList<ArrayList<String>> getUsers() {
        return Database.execute("SELECT username, displayName FROM users", new String[] {}, false);
    }

    public static ArrayList<String> getUserByUsername(String username) {
        return Database.execute("SELECT * FROM users WHERE username = ?", new String[] { username }, false).get(0);
    }

    public static void addUser(String username, String displayName, String passwordSalt, String passwordHash) {
        Database.execute(
                "INSERT OR IGNORE INTO users (username, displayName, passwordSalt, passwordHash) VALUES (?, ?, ?, ?)",
                new String[] { username, displayName, passwordSalt, passwordHash }, true);
    }

    public static void updateUserDisplayName(String username, String displayName) {
        Database.execute("UPDATE users SET displayName = ? WHERE username = ?", new String[] { displayName, username },
                true);
    }

    public static void updateUserPassword(String username, String passwordSalt, String passwordHash) {
        Database.execute("UPDATE users SET passwordSalt = ?, passwordHash = ? WHERE username = ?",
                new String[] { passwordSalt, passwordHash, username }, true);
    }

    public static void deleteUserByUsername(String username) {
        Database.execute("DELETE FROM users WHERE username = ?", new String[] { username }, true);
    }

    /* == PERMISSIONS == */

    public static void addPermission(int noteID, String username, boolean permissionMode) {
        Database.execute("INSERT INTO permissions (noteID, username, permissionMode) VALUES (?, ?, ?)",
                new String[] { Integer.toString(noteID), username, permissionMode ? "1" : "0" }, true);
    }

    public static void removePermission(int noteID, String username, boolean permissionMode) {
        Database.execute(
                "DELETE FROM permissions WHERE noteID = ? AND username = ? AND permissionMode = ?",
                new String[] { Integer.toString(noteID), username, permissionMode ? "1" : "0" }, true);
    }

    public static void changePermission(boolean permissionMode, int noteID, String username) {
        Database.execute(
                "UPDATE permissions SET permissionMode = ? WHERE noteID = ? AND username = ?",
                new String[] { permissionMode ? "1" : "0", Integer.toString(noteID), username }, true);
    }

    public static ArrayList<ArrayList<String>> getSharedNotesByUsername(String username) {
        return Database.execute("SELECT noteID, permissionMode FROM permissions WHERE username = ?",
                new String[] { username }, false);
    }

    public static ArrayList<String> getReadAccessByNoteId(int noteId) {
        ArrayList<String> readAccess = new ArrayList<String>();
        for (ArrayList<String> user : Database.execute(
                "SELECT username FROM permissions WHERE noteID = ? AND permissionMode = 0",
                new String[] { Integer.toString(noteId) }, false)) {
            readAccess.add(user.get(0));
        }
        return readAccess;
    }

    public static ArrayList<String> getWriteAccessByNoteId(int noteId) {
        ArrayList<String> writeAccess = new ArrayList<String>();
        for (ArrayList<String> user : Database.execute(
                "SELECT username FROM permissions WHERE noteID = ? AND permissionMode = 1",
                new String[] { Integer.toString(noteId) }, false)) {
            writeAccess.add(user.get(0));
        }
        return writeAccess;
    }
}