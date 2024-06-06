package dev.lennis.school.notes.gui;

import javax.swing.*;

public class Login extends JFrame {
  public Login() {
    this.setTitle("Mint-Blow - Login");

    JLabel heading = new JLabel();
    heading.setText("Login");

    this.setUndecorated(true);

    this.setSize(300, 400);
    this.setLocationRelativeTo(null);

    // this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setVisible(true);
  }
}
