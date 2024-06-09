package dev.lennis.school.notes.gui;

import dev.lennis.school.notes.Gui;
import dev.lennis.school.notes.Note;
import dev.lennis.school.notes.User;
import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.*;

/*
 * Created by JFormDesigner on Sun Jun 09 10:40:42 CEST 2024
 */

/**
 * @author ben
 */
public class App extends JFrame {
  public App() {
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(1000, 600);
    setVisible(true);
    new Login(this, this);
  }

  protected void login(String usr) {
    initComponents();
    setVisible(true);
    User user = new User(usr);

    ArrayList<Note> notes = user.getNotes();
    for (Note note : notes) {
      JButton n = new JButton();
      n.setText(note.getTitle());
      n.addActionListener(
          new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              Gui.openNote(note);
            }
          });
      noteListCon.add(n);
    }
  }

  private void searchPropertyChange(PropertyChangeEvent e) {
    // TODO add your code here
  }

  private void newNote(ActionEvent e) {
    // TODO add your code here
  }

  private void initComponents() {
    // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
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
        noteListCon.setBorder(new BevelBorder(BevelBorder.LOWERED));
        noteListCon.setBorder(
            new javax.swing.border.CompoundBorder(
                new javax.swing.border.TitledBorder(
                    new javax.swing.border.EmptyBorder(0, 0, 0, 0),
                    "JFor\u006dDesi\u0067ner \u0045valu\u0061tion",
                    javax.swing.border.TitledBorder.CENTER,
                    javax.swing.border.TitledBorder.BOTTOM,
                    new java.awt.Font("Dia\u006cog", java.awt.Font.BOLD, 12),
                    java.awt.Color.red),
                noteListCon.getBorder()));
        noteListCon.addPropertyChangeListener(
            new java.beans.PropertyChangeListener() {
              @Override
              public void propertyChange(java.beans.PropertyChangeEvent e) {
                if ("bord\u0065r".equals(e.getPropertyName())) throw new RuntimeException();
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

      // ---- noteEditor ----
      noteEditor.setBorder(new BevelBorder(BevelBorder.LOWERED));
      scrollPane2.setViewportView(noteEditor);
    }
    contentPane.add(scrollPane2);
    scrollPane2.setBounds(120, 25, 625, 470);

    // ---- search ----
    search.setBorder(new BevelBorder(BevelBorder.LOWERED));
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
    newNote.addActionListener(e -> newNote(e));
    contentPane.add(newNote);
    newNote.setBounds(120, 0, newNote.getPreferredSize().width, 25);

    // ---- shareNote ----
    shareNote.setText("share");
    contentPane.add(shareNote);
    shareNote.setBounds(190, 0, shareNote.getPreferredSize().width, 25);

    // ---- button3 ----
    button3.setText("delete");
    contentPane.add(button3);
    button3.setBounds(260, 0, button3.getPreferredSize().width, 25);

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
