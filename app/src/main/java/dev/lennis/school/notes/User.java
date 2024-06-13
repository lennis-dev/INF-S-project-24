package dev.lennis.school.notes;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class User {

  private String username;
  private String displayName;
  private String passwordSalt;
  private String passwordHash;

  /**
   * Create a new user
   *
   * @param username The username
   * @param displayName The display name
   * @param password The password
   */
  public static void createUser(String username, String displayName, String password) {
    // GitHub copilot suggested this salt generation method
    String salt = Long.toHexString(Double.doubleToLongBits(Math.random()));
    String hash = hashPassword(password, salt);
    Data.addUser(username, displayName, salt, hash);
  }

  public static void deleteUserByUsername(String username) {
    Data.deleteUserByUsername(username);
  }

  public static boolean userExists(String username) {
    ArrayList<ArrayList<String>> users = Data.getUsers();
    for (ArrayList<String> userAll : users) {
      String user = userAll.getFirst();
      if (user.equals(username)) {
        return true;
      }
    }
    return false;
  }

  public User(String username, String displayName, String passwordSalt, String passwordHash) {
    this.username = username;
    this.displayName = displayName;
    this.passwordSalt = passwordSalt;
    this.passwordHash = passwordHash;
  }

  public User(String username) {
    this.username = username;
    ArrayList<String> data = Data.getUserByUsername(username);
    this.displayName = data.get(1);
    this.passwordSalt = data.get(2);
    this.passwordHash = data.get(3);
  }

  public ArrayList<Note> getNotes() {
    return Note.getNotesByUsername(username);
  }

  public Note getNoteByHeading(String heading) {
    int id = Data.getIdByHeading(this.username, heading);
    return new Note(id);
  }

  public ArrayList<String> getTags() {
    ArrayList<Note> notes = getNotes();
    ArrayList<String> tags = new ArrayList<String>();
    for (Note note : notes) {
      ArrayList<String> noteTags = note.getTags();
      for (String tag : noteTags) {
        tags.add(tag);
      }
    }
    Set<String> noDubs = new HashSet<>(tags);
    return new ArrayList<>(noDubs);
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
   * @implNote GitHub copilot suggested this method
   */
  public static String hashPassword(String password, String salt) {
    try {
      MessageDigest digest = MessageDigest.getInstance("SHA-512");
      byte[] hash = digest.digest((password + salt).getBytes(StandardCharsets.UTF_8));
      StringBuilder hexString = new StringBuilder(2 * hash.length);
      for (int i = 0; i < hash.length; i++) {
        String hex = Integer.toHexString(0xff & hash[i]);
        if (hex.length() == 1) hexString.append('0');
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
    // GitHub copilot suggested this salt generation method
    this.passwordSalt = Long.toHexString(Double.doubleToLongBits(Math.random()));
    this.passwordHash = hashPassword(password, this.passwordSalt);
    Data.updateUserPassword(username, passwordSalt, passwordHash);
  }

  /**
   * Set the display name
   *
   * @param displayName The display name
   */
  public void setDisplayName(String displayName) {
    this.displayName = displayName;
    Data.updateUserDisplayName(username, displayName);
  }
}
