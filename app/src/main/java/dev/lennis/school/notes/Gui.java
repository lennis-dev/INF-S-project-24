package dev.lennis.school.notes;

import dev.lennis.school.notes.gui.App;
import java.awt.Color;

public class Gui {
  public static final Color backgroundColor = new Color(0x2F3136);

  public static void main() {
    new App();
  }

  public static String pToS(char[] psswd) {
    return psswd.toString();
  }

  public static boolean login(String username, char[] password) {
    String tmp = pToS(password);
    return true;
  }

  public static void validateLogin(String username, String password) {}

  public static boolean register(
      String username, String displayName, char[] password, char[] passwordCon) {
    return true;
  }
}
