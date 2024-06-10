package dev.lennis.school.notes.gui;

import dev.lennis.school.notes.Gui;
import dev.lennis.school.notes.Note;
import dev.lennis.school.notes.User;
import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.util.ArrayList;
import javax.swing.*;

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

  private Note currentNote;
  private User currentUser;

  protected void login(String usr) {
    initComponents();
    setVisible(true);
    currentUser = new User(usr);

    displayName.setText(currentUser.getDisplayName());

    ArrayList<Note> notes = currentUser.getNotes();
    if (notes.isEmpty()) {
      newNote("Untitled");
    } else {
      for (Note note : notes) {
        JButton n = new JButton();
        n.setText(note.getHeading());
        n.addActionListener(
            new ActionListener() {
              @Override
              public void actionPerformed(ActionEvent e) {
                openNote(note);
              }
            });
        n.setVisible(true);
        noteList.add(n);
      }

      openNote(notes.getFirst());
    }
  }

  private void newNote(String name) {
    openNote(Note.addNote(currentUser.getUsername(), name, ""));
  }

  private void openNote(Note note) {
    currentNote = note;
    noteName.setText(note.getHeading());
    String noteContents = note.getTitle();
    noteEditor.setText(noteContents);
  }

  private boolean noteExists(String name) {
    ArrayList<Note> notes = currentUser.getNotes();
    for (Note note : notes) {
      if (note.getTitle() == name) {
        return true;
      }
    }
    return false;
  }

  private void newNoteBtn(ActionEvent e) {
    String name = "tmp";
    if (noteExists(name)) {
      Gui.errorAlert(String.format("Note \"%s\" does already exist", name));
    } else {
      newNote(name);
    }
  }

  private void saveBtn(ActionEvent e) {
    currentNote.setText(noteEditor.getText());
  }

  private void deleteBtn(ActionEvent e) {
    // TODO add your code here
  }

  private void shareBtn(ActionEvent e) {
    // TODO add your code here
  }

  private void changeModeBtn(ActionEvent e) {
    // TODO add your code here
  }

  private void searchPropertyChange(PropertyChangeEvent e) {
    // TODO add your code here
  }

  private void initComponents() {
    // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
    // Generated using JFormDesigner Evaluation license - Wu Ling
    panel1 = new JPanel();
    displayName = new JButton();
    newNote = new JButton();
    deleteNote = new JButton();
    shareNote = new JButton();
    save = new JButton();
    button5 = new JButton();
    button6 = new JButton();
    changeMode = new JButton();
    noteName = new JLabel();
    panel2 = new JPanel();
    noteList = new JScrollPane();
    panel3 = new JPanel();
    searchField = new JTextField();
    tagBox = new JComboBox();
    scrollPane1 = new JScrollPane();
    noteEditor = new JTextArea();

    // ======== this ========
    Container contentPane = getContentPane();
    contentPane.setLayout(new BorderLayout());

    // ======== panel1 ========
    {
      panel1.setBorder(
          new javax.swing.border.CompoundBorder(
              new javax.swing.border.TitledBorder(
                  new javax.swing.border.EmptyBorder(0, 0, 0, 0),
                  "JF\u006frmDesi\u0067ner Ev\u0061luatio\u006e",
                  javax.swing.border.TitledBorder.CENTER,
                  javax.swing.border.TitledBorder.BOTTOM,
                  new java.awt.Font("Dialo\u0067", java.awt.Font.BOLD, 12),
                  java.awt.Color.red),
              panel1.getBorder()));
      panel1.addPropertyChangeListener(
          new java.beans.PropertyChangeListener() {
            @Override
            public void propertyChange(java.beans.PropertyChangeEvent e) {
              if ("borde\u0072".equals(e.getPropertyName())) throw new RuntimeException();
            }
          });
      panel1.setLayout(new BoxLayout(panel1, BoxLayout.X_AXIS));

      // ---- displayName ----
      displayName.setText("DisplayName");
      panel1.add(displayName);

      // ---- newNote ----
      newNote.setText("new");
      newNote.addActionListener(e -> newNoteBtn(e));
      panel1.add(newNote);

      // ---- deleteNote ----
      deleteNote.setText("delete");
      deleteNote.addActionListener(e -> deleteBtn(e));
      panel1.add(deleteNote);

      // ---- shareNote ----
      shareNote.setText("share");
      shareNote.addActionListener(e -> shareBtn(e));
      panel1.add(shareNote);

      // ---- save ----
      save.setText("save");
      save.addActionListener(e -> saveBtn(e));
      panel1.add(save);

      // ---- button5 ----
      button5.setText("add Tag");
      panel1.add(button5);

      // ---- button6 ----
      button6.setText("delete Tag");
      panel1.add(button6);

      // ---- changeMode ----
      changeMode.setText("Read mode");
      changeMode.addActionListener(e -> changeModeBtn(e));
      panel1.add(changeMode);

      // ---- noteName ----
      noteName.setText("noteName");
      noteName.setHorizontalAlignment(SwingConstants.CENTER);
      panel1.add(noteName);
    }
    contentPane.add(panel1, BorderLayout.NORTH);

    // ======== panel2 ========
    {
      panel2.setLayout(new BorderLayout());
      panel2.add(noteList, BorderLayout.CENTER);

      // ======== panel3 ========
      {
        panel3.setLayout(new BoxLayout(panel3, BoxLayout.X_AXIS));

        // ---- searchField ----
        searchField.setMinimumSize(new Dimension(100, 25));
        searchField.setPreferredSize(new Dimension(80, 25));
        searchField.addPropertyChangeListener(e -> searchPropertyChange(e));
        panel3.add(searchField);
        panel3.add(tagBox);
      }
      panel2.add(panel3, BorderLayout.NORTH);
    }
    contentPane.add(panel2, BorderLayout.WEST);

    // ======== scrollPane1 ========
    {
      scrollPane1.setViewportView(noteEditor);
    }
    contentPane.add(scrollPane1, BorderLayout.CENTER);
    setSize(745, 465);
    setLocationRelativeTo(getOwner());
    // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
  }

  // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
  // Generated using JFormDesigner Evaluation license - Wu Ling
  private JPanel panel1;
  private JButton displayName;
  private JButton newNote;
  private JButton deleteNote;
  private JButton shareNote;
  private JButton save;
  private JButton button5;
  private JButton button6;
  private JButton changeMode;
  private JLabel noteName;
  private JPanel panel2;
  private JScrollPane noteList;
  private JPanel panel3;
  private JTextField searchField;
  private JComboBox tagBox;
  private JScrollPane scrollPane1;
  private JTextArea noteEditor;
  // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
