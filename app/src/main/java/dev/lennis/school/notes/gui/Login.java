package dev.lennis.school.notes.gui;

import dev.lennis.school.notes.Gui;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Login extends JDialog {
  public Login(Window owner, App ownerObjP) {
    super(owner);
    ownerObj = ownerObjP;
    initComponents();
  }

  public App ownerObj;

  private void submit(ActionEvent e) {
    if (Gui.checkLogin(formattedTextField1.getText(), passwordField1.getPassword())) {
      ownerObj.login(formattedTextField1.getText());
      dispose();
    } else {
      formattedTextField1.setText("");
      passwordField1.setText("");
    }
  }

  private void register(ActionEvent e) {
    dispose();
    new Register(getOwner(), ownerObj);
  }

  private void initComponents() {
    // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
    // Generated using JFormDesigner Evaluation license - Wu Ling
    label1 = new JLabel();
    formattedTextField1 = new JFormattedTextField();
    passwordField1 = new JPasswordField();
    button1 = new JButton();
    button2 = new JButton();

    // ======== this ========
    setVisible(true);
    Container contentPane = getContentPane();
    contentPane.setLayout(null);

    // ---- label1 ----
    label1.setText("Login");
    label1.setFont(
        label1
            .getFont()
            .deriveFont(label1.getFont().getStyle() | Font.BOLD, label1.getFont().getSize() + 12f));
    contentPane.add(label1);
    label1.setBounds(new Rectangle(new Point(80, 30), label1.getPreferredSize()));

    // ---- formattedTextField1 ----
    formattedTextField1.setToolTipText("Username");
    contentPane.add(formattedTextField1);
    formattedTextField1.setBounds(40, 90, 145, formattedTextField1.getPreferredSize().height);

    // ---- passwordField1 ----
    passwordField1.setToolTipText("Password");
    contentPane.add(passwordField1);
    passwordField1.setBounds(40, 150, 145, passwordField1.getPreferredSize().height);

    // ---- button1 ----
    button1.setText("Submit");
    button1.addActionListener(e -> submit(e));
    contentPane.add(button1);
    button1.setBounds(new Rectangle(new Point(75, 210), button1.getPreferredSize()));

    // ---- button2 ----
    button2.setText("Or Register");
    button2.setFont(
        button2
            .getFont()
            .deriveFont(
                button2.getFont().getStyle() | Font.ITALIC, button2.getFont().getSize() - 4f));
    button2.addActionListener(e -> register(e));
    contentPane.add(button2);
    button2.setBounds(new Rectangle(new Point(75, 250), button2.getPreferredSize()));

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
    setSize(225, 320);
    setLocationRelativeTo(getOwner());
    setVisible(true);
    // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
  }

  // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
  // Generated using JFormDesigner Evaluation license - Wu Ling
  private JLabel label1;
  private JFormattedTextField formattedTextField1;
  private JPasswordField passwordField1;
  private JButton button1;
  private JButton button2;
  // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
