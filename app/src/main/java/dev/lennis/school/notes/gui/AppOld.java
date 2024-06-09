package dev.lennis.school.notes.gui;

import dev.lennis.school.notes.Gui;
import dev.lennis.school.notes.User;
import java.awt.*;
import java.beans.*;
import javax.swing.*;

public class AppOld extends JFrame {
  public AppOld() {
    setupWindow();
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

  protected void login(String usr) {
    User user = new User(usr);
  }

  private void searchPropertyChange(PropertyChangeEvent e) {
    // TODO add your code here
  }

  private void initComponents() {
    // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
    // @formatter:off
    // Generated using JFormDesigner Evaluation license - Wu Ling
    scrollPane1 = new JScrollPane();
    noteListCon = new JPanel();
    scrollPane2 = new JScrollPane();
    noteEditor = new JEditorPane();
    search = new JEditorPane();
    account = new JButton();
    changeMode = new JButton();
    newNote = new JButton();
    shareNote = new JButton();
    button3 = new JButton();

    // ======== this ========
    Container contentPane = getContentPane();
    contentPane.setLayout(null);

    // ======== scrollPane1 ========
    {

      // ======== noteListCon ========
      {
        noteListCon.setBorder(
            new javax.swing.border.CompoundBorder(
                new javax.swing.border.TitledBorder(
                    new javax.swing.border.EmptyBorder(0, 0, 0, 0),
                    "JF\u006frmDes\u0069gner \u0045valua\u0074ion",
                    javax.swing.border.TitledBorder.CENTER,
                    javax.swing.border.TitledBorder.BOTTOM,
                    new java.awt.Font("D\u0069alog", java.awt.Font.BOLD, 12),
                    java.awt.Color.red),
                noteListCon.getBorder()));
        noteListCon.addPropertyChangeListener(
            new java.beans.PropertyChangeListener() {
              @Override
              public void propertyChange(java.beans.PropertyChangeEvent e) {
                if ("\u0062order".equals(e.getPropertyName())) throw new RuntimeException();
              }
            });
        noteListCon.setLayout(new BoxLayout(noteListCon, BoxLayout.Y_AXIS));
      }
      scrollPane1.setViewportView(noteListCon);
    }
    contentPane.add(scrollPane1);
    scrollPane1.setBounds(0, 50, 120, 445);

    // ======== scrollPane2 ========
    {
      scrollPane2.setViewportView(noteEditor);
    }
    contentPane.add(scrollPane2);
    scrollPane2.setBounds(120, 25, 625, 470);

    // ---- search ----
    search.addPropertyChangeListener(e -> searchPropertyChange(e));
    contentPane.add(search);
    search.setBounds(0, 25, 120, 28);

    // ---- account ----
    account.setText("DisplayName");
    contentPane.add(account);
    account.setBounds(0, 0, 120, 25);

    // ---- changeMode ----
    changeMode.setText("Read Mode");
    contentPane.add(changeMode);
    changeMode.setBounds(630, 0, 115, changeMode.getPreferredSize().height);

    // ---- newNote ----
    newNote.setText("new");
    contentPane.add(newNote);
    newNote.setBounds(new Rectangle(new Point(120, 0), newNote.getPreferredSize()));

    // ---- shareNote ----
    shareNote.setText("share");
    contentPane.add(shareNote);
    shareNote.setBounds(new Rectangle(new Point(190, 0), shareNote.getPreferredSize()));

    // ---- button3 ----
    button3.setText("delete");
    contentPane.add(button3);
    button3.setBounds(new Rectangle(new Point(260, 0), button3.getPreferredSize()));

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
    pack();
    setLocationRelativeTo(getOwner());
    // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
  }

  // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
  // Generated using JFormDesigner Evaluation license - Wu Ling
  private JScrollPane scrollPane1;
  private JPanel noteListCon;
  private JScrollPane scrollPane2;
  private JEditorPane noteEditor;
  private JEditorPane search;
  private JButton account;
  private JButton changeMode;
  private JButton newNote;
  private JButton shareNote;
  private JButton button3;
  // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
