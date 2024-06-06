package dev.lennis.school.notes.gui;

import dev.lennis.school.notes.Gui;
import javax.swing.*;

public class Login extends JDialog {
  public Login() {
    setTitle("Mint-Blow - Login");
    setUndecorated(true);
    setForeground(Gui.backgroundColor);
    setSize(300, 400);

    JLabel heading = new JLabel();
    heading.setText("Login");
    heading.setVisible(true);
    heading.setBounds(135, 30, 100, 30);
    add(heading);

    JTextField username = new JTextField();
    username.setSize(300, 400);

    setLocationRelativeTo(null);
    setLayout(null);

    setVisible(true);
  }
}
