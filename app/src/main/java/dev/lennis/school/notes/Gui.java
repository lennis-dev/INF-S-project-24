package dev.lennis.school.notes;

import static javax.swing.JOptionPane.showMessageDialog;

import dev.lennis.school.notes.gui.App;
import java.awt.Color;

public class Gui {
  public static final Color backgroundColor = new Color(0x2F3136);

  public static void main() {
    new App();
  }

  public static void errorAlert(String error) {
    showMessageDialog(null, error);
  }

  public static boolean login(String username, char[] password) {
    if (username.isEmpty()) {
      errorAlert("No username provided");
      return false;
    }
    System.out.println();
    if (String.valueOf(password).isEmpty()) {
      errorAlert("No password provided");
      return false;
    }
    if (!User.userExists(username)) {
      errorAlert(String.format("Username \"%s\" does not exist", username));
      return false;
    }

    return true;
  }

  public static void validateLogin(String username, String password) {}

  public static boolean register(
      String username, String displayName, char[] password, char[] passwordCon) {
    User.createUser(username, displayName, password.toString());
    return true;
  }
}
