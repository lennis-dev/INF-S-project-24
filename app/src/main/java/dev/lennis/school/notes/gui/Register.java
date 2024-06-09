package dev.lennis.school.notes.gui;

import dev.lennis.school.notes.Gui;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Register extends JDialog {
  public Register(Window owner, App ownerObjP) {
    super(owner);
    ownerObj = ownerObjP;
    initComponents();
  }

  public App ownerObj;

  private void submit(ActionEvent e) {
    if (Gui.register(
        username.getText(), disName.getText(), password.getPassword(), conPassword.getPassword())) {
      dispose();
      new Login(getOwner(), ownerObj);
    } else {
      username.setText("");
      password.setText("");
      conPassword.setText("");
    }
  }

  private void login(ActionEvent e) {
    dispose();
    new Login(getOwner(), ownerObj);
  }

  private void initComponents() {
    // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
    // @formatter:off
    // Generated using JFormDesigner Evaluation license - Wu Ling
    label1 = new JLabel();
    username = new JFormattedTextField();
    conPassword = new JPasswordField();
    button1 = new JButton();
    button2 = new JButton();
    disName = new JFormattedTextField();
    password = new JPasswordField();

    // ======== this ========
    setVisible(true);
    Container contentPane = getContentPane();
    contentPane.setLayout(null);

    // ---- label1 ----
    label1.setText("Register");
    label1.setFont(
        label1
            .getFont()
            .deriveFont(label1.getFont().getStyle() | Font.BOLD, label1.getFont().getSize() + 12f));
    contentPane.add(label1);
    label1.setBounds(new Rectangle(new Point(65, 30), label1.getPreferredSize()));

    // ---- username ----
    username.setToolTipText("Username");
    contentPane.add(username);
    username.setBounds(40, 90, 145, username.getPreferredSize().height);

    // ---- conPassword ----
    conPassword.setToolTipText("Confirm Password");
    contentPane.add(conPassword);
    conPassword.setBounds(40, 210, 145, conPassword.getPreferredSize().height);

    // ---- button1 ----
    button1.setText("Submit");
    button1.addActionListener(
        e -> {
          submit(e);
        });
    contentPane.add(button1);
    button1.setBounds(new Rectangle(new Point(75, 260), button1.getPreferredSize()));

    // ---- button2 ----
    button2.setText("Or Login");
    button2.setFont(
        button2
            .getFont()
            .deriveFont(
                button2.getFont().getStyle() | Font.ITALIC, button2.getFont().getSize() - 4f));
    button2.addActionListener(
        e -> {
          login(e);
        });
    contentPane.add(button2);
    button2.setBounds(new Rectangle(new Point(75, 310), button2.getPreferredSize()));

    // ---- disName ----
    disName.setToolTipText("Display Name");
    contentPane.add(disName);
    disName.setBounds(40, 130, 145, 25);

    // ---- password ----
    password.setToolTipText("Password");
    contentPane.add(password);
    password.setBounds(40, 170, 145, 25);

    {
      // compute preferred size
      Dimension preferredSize = new Dimension();
      for (int i = 0; i < contentPane.getComponentCount(); i++) {
        Rectangle bounds = contentPane.getComponent(i).getBounds();
        preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
        preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
      }
      Insets insets = contentPane.getInsets();
      preferredSize.width += insets.right;
      preferredSize.height += insets.bottom;
      contentPane.setMinimumSize(preferredSize);
      contentPane.setPreferredSize(preferredSize);
    }
    setSize(225, 385);
    setLocationRelativeTo(getOwner());
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
    // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
  }

  // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
  // Generated using JFormDesigner Evaluation license - Wu Ling
  private JLabel label1;
  private JFormattedTextField username;
  private JPasswordField conPassword;
  private JButton button1;
  private JButton button2;
  private JFormattedTextField disName;
  private JPasswordField password;
  // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
