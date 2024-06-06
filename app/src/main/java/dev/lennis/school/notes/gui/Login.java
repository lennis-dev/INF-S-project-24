package dev.lennis.school.notes.gui;

import javax.swing.*;

public class Login extends JFrame {
  public Login() {
    setTitle("Mint-Blow - Login");
    setUndecorated(true);

    JLabel heading = new JLabel();
    heading.setText("Login");
    heading.setSize(280, 40);
    heading.setVisible(true);
    heading.setBounds(180, 30, 100, 30);
    add(heading);

    setSize(300, 400);
    setLocationRelativeTo(null);
    setLayout(null);

    setVisible(true);
  }
}
