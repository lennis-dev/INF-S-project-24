package dev.lennis.school.notes.gui;

import dev.lennis.school.notes.Gui;
import javax.swing.*;

public class App extends JFrame {
  public App() {
    setupWindow();
    new Login(this, this);
  }

  public void setupWindow() {
    setTitle("Mint-Blow");

    setBackground(Gui.backgroundColor);

    setSize(500, 500); // Remove later
    // pack(); Uncomment later

    setLocationRelativeTo(null);
    setLayout(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
  }

  protected void login(String usr) {}
}
