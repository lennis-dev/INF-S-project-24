package dev.lennis.school.notes.gui;

import static javax.swing.JOptionPane.showConfirmDialog;
import static javax.swing.JOptionPane.showInputDialog;

import dev.lennis.school.notes.Gui;
import dev.lennis.school.notes.User;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class Settings extends JDialog {
  public Settings(Window owner, App ownerObjP) {
    super(owner);
    ownerObj = ownerObjP;
    initComponents();
    label1.setText(ownerObjP.currentUser.getDisplayName());
    setVisible(true);
  }

  App ownerObj;

  private void rename(ActionEvent e) {
    String name = showInputDialog("Please enter your new Display Name");
    if (name == null) {
      return;
    }
    if (name.isBlank()) {
      return;
    }
    ArrayList<Object[]> checks = new ArrayList<Object[]>();
    checks.add(Gui.aVC((name.length() > 7), "Display name provided is longer then 7 characters"));
    checks.add(Gui.aVC((name.contains(" ")), "Display name contains a space symbol"));
    checks.add(Gui.aVC((name.contains("\\")), "Display name contains a back slash \"\\\""));

    for (Object[] check : checks) {
      if ((boolean) check[0]) {
        Gui.errorAlert((String) check[1]);
        return;
      }
    }
    ownerObj.currentUser.setDisplayName(name);
    label1.setText(name);
    ownerObj.displayName.setText(name);
  }

  private void changePassword(ActionEvent e) {
    String psswdOld = showInputDialog("Please enter your old Password");
    if (psswdOld == null) {
      return;
    }
    if (psswdOld.isBlank()) {
      return;
    }
    String psswd = showInputDialog("Please enter your new Password");
    if (psswd == null) {
      return;
    }
    if (psswd.isBlank()) {
      return;
    }
    String psswdConf = showInputDialog("Please confirm your new Password");
    if (psswdConf == null) {
      return;
    }
    if (psswdConf.isBlank()) {
      return;
    }
    ArrayList<Object[]> checks = new ArrayList<Object[]>();
    checks.add(
        Gui.aVC((!ownerObj.currentUser.checkPassword(psswdOld)), "Old Password isn't correct"));
    checks.add(Gui.aVC((psswd.length() > 30), "Password provided is longer then 30 characters"));
    checks.add(Gui.aVC((psswd.contains(" ")), "Password contains a space symbol"));
    checks.add(Gui.aVC((psswd.contains("\\")), "Password contains a back slash \"\\\""));
    checks.add(Gui.aVC((!psswd.equals(psswdConf)), "Passwords don't match"));
    for (Object[] check : checks) {
      if ((boolean) check[0]) {
        Gui.errorAlert((String) check[1]);
        return;
      }
    }
    ownerObj.currentUser.setPasswordHash(psswd);
  }

  private void logout(ActionEvent e) {
    logout();
  }

  private void logout() {
    dispose();
    ownerObj.dispose();
    Gui.main();
  }

  private void deleteAcc(ActionEvent e) {
    int check =
        showConfirmDialog(
            getParent(), "Do you really want to delete your accout?\nThis can't be reversed!");
    if (check == 0) {
      logout();
      User.deleteUserByUsername(ownerObj.currentUser.getUsername());
    }
  }

  private void initComponents() {
    label1 = new JLabel();
    panel1 = new JPanel();
    panel2 = new JPanel();
    hSpacer1 = new JPanel(null);
    rename = new JButton();
    hSpacer2 = new JPanel(null);
    vSpacer1 = new JPanel(null);
    panel3 = new JPanel();
    hSpacer3 = new JPanel(null);
    changePassword = new JButton();
    hSpacer4 = new JPanel(null);
    vSpacer2 = new JPanel(null);
    panel4 = new JPanel();
    hSpacer5 = new JPanel(null);
    vSpacer3 = new JPanel(null);
    panel5 = new JPanel();
    hSpacer7 = new JPanel(null);
    logout = new JButton();
    hSpacer8 = new JPanel(null);
    vSpacer4 = new JPanel(null);
    panel6 = new JPanel();
    hSpacer9 = new JPanel(null);
    deleteAcc = new JButton();
    hSpacer10 = new JPanel(null);
    vSpacer5 = new JPanel(null);

    // ======== this ========
    Container contentPane = getContentPane();
    contentPane.setLayout(new BorderLayout());

    // ---- label1 ----
    label1.setText("DisplayName");
    label1.setFont(
        label1
            .getFont()
            .deriveFont(label1.getFont().getStyle() | Font.BOLD, label1.getFont().getSize() + 12f));
    label1.setHorizontalAlignment(SwingConstants.CENTER);
    contentPane.add(label1, BorderLayout.NORTH);

    // ======== panel1 ========
    {
      panel1.addPropertyChangeListener(
          new java.beans.PropertyChangeListener() {
            @Override
            public void propertyChange(java.beans.PropertyChangeEvent e) {
              if ("\u0062ord\u0065r".equals(e.getPropertyName())) throw new RuntimeException();
            }
          });
      panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));

      // ======== panel2 ========
      {
        panel2.setLayout(new BoxLayout(panel2, BoxLayout.X_AXIS));
        panel2.add(hSpacer1);

        // ---- rename ----
        rename.setText("Change Display Name");
        rename.setMinimumSize(new Dimension(200, 25));
        rename.setMaximumSize(new Dimension(200, 25));
        rename.setPreferredSize(new Dimension(200, 25));
        rename.addActionListener(e -> rename(e));
        panel2.add(rename);
        panel2.add(hSpacer2);
      }
      panel1.add(panel2);
      panel1.add(vSpacer1);

      // ======== panel3 ========
      {
        panel3.setLayout(new BoxLayout(panel3, BoxLayout.X_AXIS));
        panel3.add(hSpacer3);

        // ---- changePassword ----
        changePassword.setText("Change Password");
        changePassword.setMaximumSize(new Dimension(200, 25));
        changePassword.setMinimumSize(new Dimension(200, 25));
        changePassword.setPreferredSize(new Dimension(200, 25));
        changePassword.addActionListener(e -> changePassword(e));
        panel3.add(changePassword);
        panel3.add(hSpacer4);
      }
      panel1.add(panel3);
      panel1.add(vSpacer2);

      // ======== panel4 ========
      {
        panel4.setLayout(new BoxLayout(panel4, BoxLayout.X_AXIS));
        panel4.add(hSpacer5);
      }
      panel1.add(panel4);
      panel1.add(vSpacer3);

      // ======== panel5 ========
      {
        panel5.setLayout(new BoxLayout(panel5, BoxLayout.X_AXIS));
        panel5.add(hSpacer7);

        // ---- logout ----
        logout.setText("Logout");
        logout.setMaximumSize(new Dimension(200, 25));
        logout.setMinimumSize(new Dimension(200, 25));
        logout.setPreferredSize(new Dimension(200, 25));
        logout.addActionListener(e -> logout(e));
        panel5.add(logout);
        panel5.add(hSpacer8);
      }
      panel1.add(panel5);
      panel1.add(vSpacer4);

      // ======== panel6 ========
      {
        panel6.setLayout(new BoxLayout(panel6, BoxLayout.X_AXIS));
        panel6.add(hSpacer9);

        // ---- deleteAcc ----
        deleteAcc.setText("Delete Account");
        deleteAcc.setMaximumSize(new Dimension(200, 25));
        deleteAcc.setMinimumSize(new Dimension(200, 25));
        deleteAcc.setPreferredSize(new Dimension(200, 25));
        deleteAcc.addActionListener(e -> deleteAcc(e));
        panel6.add(deleteAcc);
        panel6.add(hSpacer10);
      }
      panel1.add(panel6);
      panel1.add(vSpacer5);
    }
    contentPane.add(panel1, BorderLayout.CENTER);
    pack();
    setLocationRelativeTo(getOwner());
  }

  private JLabel label1;
  private JPanel panel1;
  private JPanel panel2;
  private JPanel hSpacer1;
  private JButton rename;
  private JPanel hSpacer2;
  private JPanel vSpacer1;
  private JPanel panel3;
  private JPanel hSpacer3;
  private JButton changePassword;
  private JPanel hSpacer4;
  private JPanel vSpacer2;
  private JPanel panel4;
  private JPanel hSpacer5;
  private JPanel vSpacer3;
  private JPanel panel5;
  private JPanel hSpacer7;
  private JButton logout;
  private JPanel hSpacer8;
  private JPanel vSpacer4;
  private JPanel panel6;
  private JPanel hSpacer9;
  private JButton deleteAcc;
  private JPanel hSpacer10;
  private JPanel vSpacer5;
}
