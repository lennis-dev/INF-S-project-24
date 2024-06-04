package dev.lennis.school.notes;

import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;

public class User {

    private String username;
    private String displayName;
    private String passwordSalt;
    private String passwordHash;

    /**
     * Create a new user
     * 
     * @param username    The username
     * @param displayName The display name
     * @param password    The password
     */
    public static void createUser(String username, String displayName, String password) {
        // GitHub copilot suggested this salt generation method
        String salt = Long.toHexString(Double.doubleToLongBits(Math.random()));
        String hash = hashPassword(password, salt);
        Data.createUser(username, displayName, salt, hash);
    }

    public User(String username, String displayName, String passwordSalt, String passwordHash) {
        this.username = username;
        this.displayName = displayName;
        this.passwordSalt = passwordSalt;
        this.passwordHash = passwordHash;
    }

    public User(String username) {
        this.username = username;
        String[] data = Data.getUserByUsername(username);
        this.displayName = data[1];
        this.passwordSalt = data[2];
        this.passwordHash = data[3];
    }

    /**
     * Check if a password is correct
     * 
     * @param password The password to check
     * @return
     */
    public boolean checkPassword(String password) {
        try {
            return hashPassword(password, this.passwordSalt).equals(this.passwordHash);
        } catch (Exception e) {
            System.out.println("Error hashing password: " + e.getMessage());
            return false;
        }
    }

    /**
     * Get the username
     * 
     * @return The username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Get the display name
     * 
     * @return
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Hashes a password with a salt
     * 
     * @param password
     * @param salt
     * @return The hashed password as a String
     * 
     * @implNote GitHub copilot suggested this method
     */
    public static String hashPassword(String password, String salt) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-512");
            byte[] hash = digest.digest((password + salt).getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder(2 * hash.length);
            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1)
                    hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            System.out.println("Error hashing password: " + e.getMessage());
            return null;
        }
    }

    /**
     * Set the password hash
     * 
     * @param password The password
     */
    public void setPasswordHash(String password) {
        this.passwordHash = hashPassword(password, this.passwordSalt);
        Data.setUserPasswordByUsername(this.username, this.passwordSalt, this.passwordHash);
    }

    /**
     * Get all notes by the user
     * 
     * @return An array of notes
     */
    public Note[] getNotesByTag(String tag) {
        return Note.getNotesByTag(tag, this.username);
    }
}
