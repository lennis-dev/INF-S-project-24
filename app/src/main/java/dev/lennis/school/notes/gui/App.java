package dev.lennis.school.notes.gui;

import static javax.swing.JOptionPane.showInputDialog;

import dev.lennis.school.notes.Gui;
import dev.lennis.school.notes.Note;
import dev.lennis.school.notes.User;
import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
    refreshNoteView();

    openNote(notes.getFirst());
    refreshTagView();
  }

  private void newNote(String name) {
    openNote(Note.addNote(currentUser.getUsername(), name, ""));
    refreshNoteView();
  }

  private void openNote(Note note) {
    currentNote = note;
    noteName.setText(note.getHeading());
    String noteContents = note.getText();
    noteEditor.setText(noteContents);
    refreshTagView();
  }

  private boolean noteExists(String name) {
    ArrayList<Note> notes = currentUser.getNotes();
    for (Note note : notes) {
      if (note.getHeading().equals(name)) {
        return true;
      }
    }
    return false;
  }

  private boolean tagPresent(String inTag) {
    ArrayList<String> tags = currentNote.getTags();
    for (String tag : tags) {
      if (tag.equals(inTag)) {
        return true;
      }
    }
    return false;
  }

  private Color tagToColor(String tag) {
    try {
      MessageDigest digest = MessageDigest.getInstance("SHA-256");
      byte[] hash = digest.digest(tag.getBytes());
      return Color.getHSBColor(hash[0] / 255f, 255 / 255f, 255 / 255f);
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    }
  }

  private void refreshNoteView() {
    int len = noteList.getComponentCount();
    for (int i = 0; i < len; i++) {
      noteList.getComponent(i).setVisible(false);
    }
    noteList.removeAll();

    Object selectedItem = tagBox.getSelectedItem();
    if (selectedItem == null) {
      selectedItem = new ColoredItem("ALL", Color.white);
    }
    String currentTag = ((ColoredItem) selectedItem).getText();
    ArrayList<Note> notes = currentUser.getNotes();
    if (notes.isEmpty()) {
      newNote("Untitled");
    }
    if (currentTag.equals("ALL")) {
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
        noteList.add(n);
      }
    } else {
      for (Note note : notes) {
        ArrayList<String> noteTags = note.getTags();
        if (noteTags.contains(currentTag)) {
          JButton n = new JButton();
          n.setText(note.getHeading());
          n.addActionListener(
              new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                  openNote(note);
                }
              });
          noteList.add(n);
        }
      }
    }
  }

  private void refreshTagView() {
    int len = tagView.getComponentCount();
    for (int i = 0; i < len; i++) {
      tagView.getComponent(i).setVisible(false);
    }
    tagView.removeAll();
    ArrayList<String> tags = currentNote.getTags();
    for (String tag : tags) {
      JFormattedTextField l = new JFormattedTextField();
      l.setText(tag);
      l.setEditable(false);
      l.setCaretColor(tagToColor(tag));
      tagView.add(l);
    }

    // Refresh Combobox
    tagBox.removeAllItems();
    ArrayList<String> allTags = currentUser.getTags();
    tagBox.addItem(new ColoredItem("ALL", Color.white));
    for (String tag : allTags) {
      tagBox.addItem(new ColoredItem(tag, tagToColor(tag)));
    }
    tagBox.setRenderer(new ColoredItemRenderer());
  }

  private void newNoteBtn(ActionEvent e) {
    String name = showInputDialog("Please enther the heading of the note");
    if (name.equals("null")) {
      return;
    }
    if (noteExists(name)) {
      Gui.errorAlert(String.format("Note \"%s\" does already exist", name));
    } else {
      newNote(name);
    }
  }

  private void openNoteBtn(ActionEvent e) {
    String heading = showInputDialog("Please enter the name of the note you want to open");
    if (noteExists(heading)) {
      openNote(currentUser.getNoteByHeading(heading));
    } else {
      Gui.errorAlert(String.format("Note \"%s\" does not exist", heading));
    }
  }

  private void saveBtn(ActionEvent e) {
    currentNote.setText(noteEditor.getText());
  }

  private void deleteBtn(ActionEvent e) {
    String heading =
        showInputDialog(
            String.format(
                "Are you sure you want to delete \"%s\". \n"
                    + "To confirm type the heading of the note",
                currentNote.getHeading()));
    if (currentNote.getHeading().equals(heading)) {
      currentNote.delete();
      refreshNoteView();
      openNote(currentUser.getNotes().getFirst());
    } else {
      Gui.errorAlert("Headings do not match, aborting");
    }
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

  private void rename(ActionEvent e) {
    String heading = showInputDialog("Please enter the new note title");
    if (!heading.equals("null") || !heading.isBlank()) {
      if (noteExists(heading)) {
        Gui.errorAlert(String.format("Note \"%s\" does already exist", heading));
      } else {
        currentNote.setHeading(heading);
        refreshNoteView();
      }
    }
  }

  private void addTagBtn(ActionEvent e) {
    String tag = showInputDialog("Please enter the name of the tag");
    if (tag.equals("null") || tag.isBlank()) {
      return;
    }
    if (tagPresent(tag)) {
      Gui.errorAlert(String.format("Tag \"%s\" is already on the note", tag));
    } else {
      currentNote.addTag(tag);
      refreshTagView();
    }
  }

  private void delTagBtn(ActionEvent e) {
    String tag = showInputDialog("Please enter the name of the tag you want to delete");
    if (tag.equals("null") || tag.isBlank()) {
      return;
    }
    if (!tagPresent(tag)) {
      Gui.errorAlert(String.format("Tag \"%s\" is not present on the current note", tag));
    } else {
      currentNote.removeTag(tag);
      refreshTagView();
    }
  }

  private void tagBoxUpd(ActionEvent e) {
    refreshTagView();
  }

  public class ColoredItem {
    private String text;
    private Color color;

    public ColoredItem(String text, Color color) {
      this.text = text;
      this.color = color;
    }

    public String getText() {
      return text;
    }

    public Color getColor() {
      return color;
    }

    @Override
    public String toString() {
      return text;
    }
  }

  static class ColoredItemRenderer extends JLabel implements ListCellRenderer<ColoredItem> {
    @Override
    public Component getListCellRendererComponent(
        JList<? extends ColoredItem> list,
        ColoredItem value,
        int index,
        boolean isSelected,
        boolean cellHasFocus) {
      if (value != null) {
        setText(value.getText());
        setBackground(isSelected ? list.getSelectionBackground() : value.getColor());
        setForeground(isSelected ? list.getSelectionForeground() : Color.BLACK);
      }
      return this;
    }
  }

  private void initComponents() {
    // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
    // Generated using JFormDesigner Evaluation license - Wu Ling
    panel1 = new JPanel();
    displayName = new JButton();
    newNote = new JButton();
    openNote = new JButton();
    deleteNote = new JButton();
    shareNote = new JButton();
    save = new JButton();
    rename = new JButton();
    addTag = new JButton();
    delTag = new JButton();
    changeMode = new JButton();
    panel2 = new JPanel();
    panel3 = new JPanel();
    searchField = new JTextField();
    tagBox = new JComboBox<ColoredItem>();
    scrollPane2 = new JScrollPane();
    noteList = new JPanel();
    scrollPane1 = new JScrollPane();
    noteEditor = new JTextArea();
    panel4 = new JPanel();
    noteName = new JLabel();
    scrollPane3 = new JScrollPane();
    tagView = new JPanel();

    // ======== this ========
    Container contentPane = getContentPane();
    contentPane.setLayout(new BorderLayout());

    // ======== panel1 ========
    {
      panel1.setBorder(null);
      panel1.setBorder(
          new javax.swing.border.CompoundBorder(
              new javax.swing.border.TitledBorder(
                  new javax.swing.border.EmptyBorder(0, 0, 0, 0),
                  "JF\u006frmDes\u0069gner \u0045valua\u0074ion",
                  javax.swing.border.TitledBorder.CENTER,
                  javax.swing.border.TitledBorder.BOTTOM,
                  new java.awt.Font("D\u0069alog", java.awt.Font.BOLD, 12),
                  java.awt.Color.red),
              panel1.getBorder()));
      panel1.addPropertyChangeListener(
          new java.beans.PropertyChangeListener() {
            @Override
            public void propertyChange(java.beans.PropertyChangeEvent e) {
              if ("\u0062order".equals(e.getPropertyName())) throw new RuntimeException();
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

      // ---- openNote ----
      openNote.setText("open");
      openNote.addActionListener(e -> openNoteBtn(e));
      panel1.add(openNote);

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

      // ---- rename ----
      rename.setText("rename");
      rename.addActionListener(e -> rename(e));
      panel1.add(rename);

      // ---- addTag ----
      addTag.setText("add Tag");
      addTag.addActionListener(e -> addTagBtn(e));
      panel1.add(addTag);

      // ---- delTag ----
      delTag.setText("delete Tag");
      delTag.addActionListener(e -> delTagBtn(e));
      panel1.add(delTag);

      // ---- changeMode ----
      changeMode.setText("Read mode");
      changeMode.addActionListener(e -> changeModeBtn(e));
      panel1.add(changeMode);
    }
    contentPane.add(panel1, BorderLayout.NORTH);

    // ======== panel2 ========
    {
      panel2.setLayout(new BorderLayout());

      // ======== panel3 ========
      {
        panel3.setLayout(new BoxLayout(panel3, BoxLayout.X_AXIS));

        // ---- searchField ----
        searchField.setMinimumSize(new Dimension(100, 25));
        searchField.setPreferredSize(new Dimension(80, 25));
        searchField.addPropertyChangeListener(e -> searchPropertyChange(e));
        panel3.add(searchField);
        tagBox.addActionListener(e -> tagBoxUpd(e));
        panel3.add(tagBox);
      }
      panel2.add(panel3, BorderLayout.NORTH);

      // ======== scrollPane2 ========
      {

        // ======== noteList ========
        {
          noteList.setLayout(new BoxLayout(noteList, BoxLayout.Y_AXIS));
        }
        scrollPane2.setViewportView(noteList);
      }
      panel2.add(scrollPane2, BorderLayout.CENTER);
    }
    contentPane.add(panel2, BorderLayout.WEST);

    // ======== scrollPane1 ========
    {
      scrollPane1.setViewportView(noteEditor);
    }
    contentPane.add(scrollPane1, BorderLayout.CENTER);

    // ======== panel4 ========
    {
      panel4.setLayout(new BorderLayout());

      // ---- noteName ----
      noteName.setText("noteName");
      noteName.setHorizontalAlignment(SwingConstants.CENTER);
      panel4.add(noteName, BorderLayout.NORTH);

      // ======== scrollPane3 ========
      {

        // ======== tagView ========
        {
          tagView.setLayout(new BoxLayout(tagView, BoxLayout.Y_AXIS));
        }
        scrollPane3.setViewportView(tagView);
      }
      panel4.add(scrollPane3, BorderLayout.CENTER);
    }
    contentPane.add(panel4, BorderLayout.EAST);
    setSize(825, 550);
    setLocationRelativeTo(getOwner());
    // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
  }

  // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
  // Generated using JFormDesigner Evaluation license - Wu Ling
  private JPanel panel1;
  private JButton displayName;
  private JButton newNote;
  private JButton openNote;
  private JButton deleteNote;
  private JButton shareNote;
  private JButton save;
  private JButton rename;
  private JButton addTag;
  private JButton delTag;
  private JButton changeMode;
  private JPanel panel2;
  private JPanel panel3;
  private JTextField searchField;
  private JComboBox<ColoredItem> tagBox;
  private JScrollPane scrollPane2;
  private JPanel noteList;
  private JScrollPane scrollPane1;
  private JTextArea noteEditor;
  private JPanel panel4;
  private JLabel noteName;
  private JScrollPane scrollPane3;
  private JPanel tagView;
  // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
