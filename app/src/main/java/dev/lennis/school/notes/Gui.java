package dev.lennis.school.notes;

import static javax.swing.JOptionPane.showMessageDialog;

import dev.lennis.school.notes.gui.App;
import java.awt.Color;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Gui {
  public static final Color backgroundColor = new Color(0x2F3136);

  public static void main() {
    new App();
  }

  public static Object[] aVC(boolean cond, String errMessage) {
    Object[] result = new Object[2];
    result[0] = cond;
    result[1] = errMessage;
    return result;
  }

  public static void errorAlert(String error) {
    showMessageDialog(null, error);
  }

  public static boolean checkLogin(String username, char[] password) {
    ArrayList<Object[]> checks = new ArrayList<Object[]>();
    checks.add(aVC((username.isEmpty()), "No username provided"));
    checks.add(aVC((String.valueOf(password).isEmpty()), "No password provided"));
    checks.add(
        aVC((!User.userExists(username)), String.format("User \"%s\" does not exist", username)));

    for (Object[] check : checks) {
      if ((boolean) check[0]) {
        errorAlert((String) check[1]);
        return false;
      }
    }

    User user = new User(username);
    if (!user.checkPassword(String.valueOf(password))) {
      errorAlert("Wrong password");
      return false;
    }
    return true;
  }

  public static ArrayList<String> getNoteTitles(User user) {
    ArrayList<String> notes = new ArrayList<String>();
    ArrayList<Note> notesRaw = user.getNotes();
    for (Note note : notesRaw) {
      notes.add(note.getText());
    }
    return notes;
  }

  public static boolean validateUsername(String username) {
    Pattern pattern = Pattern.compile("[^a-zA-Z0-9_]");
    Matcher matcher = pattern.matcher(username);
    return matcher.find();
  }

  public static boolean register(
      String username, String displayName, char[] password, char[] passwordCon) {
    ArrayList<Object[]> checks = new ArrayList<Object[]>();
    checks.add(aVC((username.isEmpty()), "No username provided"));
    checks.add(aVC((String.valueOf(password).isEmpty()), "No password provided"));
    checks.add(aVC((displayName.isEmpty()), "No display name provided"));
    checks.add(
        aVC(
            (!String.valueOf(password).equals(String.valueOf(passwordCon))),
            "Passwords don't match"));
    checks.add(aVC((username.length() > 16), "Username provided is longer then 16 characters"));
    checks.add(aVC((username.length() < 3), "Username provided is shorter then 3 characters"));
    checks.add(
        aVC(
            (String.valueOf(password).length() > 30),
            "Password provided is longer then 30 characters"));
    checks.add(
        aVC((displayName.length() > 7), "Display name provided is longer then 7 characters"));
    checks.add(aVC((String.valueOf(password).contains(" ")), "Password contains a space symbol"));
    checks.add(
        aVC((String.valueOf(password).contains("\\")), "Password contains a back slash \"\\\""));
    checks.add(
        aVC(
            validateUsername(username),
            "The username contains invalid characters, allowed are a-z, A-Z, 0-9 as well as _"));
    checks.add(aVC((displayName.contains(" ")), "Display name contains a space symbol"));
    checks.add(aVC((displayName.contains("\\")), "Display name contains a back slash \"\\\""));
    checks.add(
        aVC(User.userExists(username), String.format("User: \"%s\" does already exist", username)));
    for (Object[] check : checks) {
      if ((boolean) check[0]) {
        errorAlert((String) check[1]);
        return false;
      }
    }
    User.createUser(username, displayName, String.valueOf(password));
    return true;
  }
}
