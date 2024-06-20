package dev.lennis.school.notes.gui;

import static javax.swing.JOptionPane.showInputDialog;

import dev.lennis.school.notes.Gui;
import dev.lennis.school.notes.Note;
import dev.lennis.school.notes.User;
import java.awt.*;
import java.awt.event.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

public class App extends JFrame {
  private ImageIcon img =
      new ImageIcon(
          Toolkit.getDefaultToolkit().getImage(getClass().getResource("/assets/png/icon.png")));

  public Image getImg() {
    return img.getImage();
  }

  public App() {
    try {
      UIManager.setLookAndFeel("com.formdev.flatlaf.themes.FlatMacDarkLaf");
    } catch (Exception ex) {

    }
    JFrame.setDefaultLookAndFeelDecorated(true);
    setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
    setName("Mint-Blow");
    setTitle("Mint-Blow");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(1000, 600);
    setVisible(true);

    setIconImage(getImg());

    new Login(this, this);
  }

  private Note currentNote;
  protected User currentUser;

  protected void login(String usr) {
    initComponents();
    setVisible(true);
    currentUser = new User(usr);

    displayName.setText(currentUser.getDisplayName());

    refreshNoteView();

    ArrayList<Note> notes = currentUser.getNotes();
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
      return Color.getHSBColor(hash[0] / 255f, 1f, 1f);
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    }
  }

  private void refreshNoteView() {
    ArrayList<Note> notes = currentUser.getNotes();
    if (notes.isEmpty()) {
      newNote("Untitled");
    }
    refreshNoteView(notes);
  }

  private void refreshNoteView(ArrayList<Note> notes) {
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
    if (currentTag.equals("ALL")) {
      for (Note note : notes) {
        JButton n = new JButton();
        n.setText(note.getHeading());
        Dimension prefSize = new Dimension(248, 50);
        n.setPreferredSize(prefSize);
        n.setMaximumSize(prefSize);
        n.setMinimumSize(prefSize);
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
          Dimension prefSize = new Dimension(248, 50);
          n.setPreferredSize(prefSize);
          n.setMaximumSize(prefSize);
          n.setMinimumSize(prefSize);
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
    tagDefList.removeAllElements();
    ArrayList<String> tags = currentNote.getTags();
    for (String tag : tags) {
      ColoredItem l = new ColoredItem(tag, tagToColor(tag));
      tagDefList.addElement(l);
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
    if (name == null) {
      return;
    }
    if (name.isBlank()) {
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
    if (noteEditor.isEditable()) currentNote.setText(noteEditor.getText());
    else currentNote.setText(tempNoteContent);
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
    if (noteEditor.isEditable()) {
      tempNoteContent = noteEditor.getText();
      noteEditor.setEditable(false);
      changeMode.setText("Edit mode");
      noteEditor.setContentType("text/html");
      Parser parser = Parser.builder().build();
      Node document = parser.parse(tempNoteContent);
      HtmlRenderer renderer = HtmlRenderer.builder().build();
      noteEditor.setText(renderer.render(document));
    } else {
      noteEditor.setEditable(true);
      changeMode.setText("Read mode");
      noteEditor.setContentType("text/plain");
      noteEditor.setText(tempNoteContent);
    }
  }

  private void searchUpdate() {
    ArrayList<Note> notes = Note.getNotesBySearch(currentUser.getUsername(), searchField.getText());
    refreshNoteView(notes);
  }

  private void rename(ActionEvent e) {
    String heading = showInputDialog("Please enter the new note title");
    if (heading == null) {
      return;
    }
    if (heading.isBlank()) {
      return;
    }
    if (noteExists(heading)) {
      Gui.errorAlert(String.format("Note \"%s\" does already exist", heading));
    } else {
      currentNote.setHeading(heading);
      noteName.setText(currentNote.getHeading());
      refreshNoteView();
    }
  }

  private void addTagBtn(ActionEvent e) {
    String tag = showInputDialog("Please enter the name of the tag");
    if (tag == null) {
      return;
    }
    if (tag.isBlank()) {
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
    if (tag == null) {
      return;
    }
    if (tag.isBlank()) {
      return;
    }
    if (!tagPresent(tag)) {
      Gui.errorAlert(String.format("Tag \"%s\" is not present on the current note", tag));
    } else {
      currentNote.removeTag(tag);
      refreshTagView();
    }
  }

  private Settings settings;

  private void settings(ActionEvent e) {
    if (settings == null) {
      settings = new Settings(this, this);
    } else {
      settings.dispose();
      settings = null;
    }
  }

  private void tagBoxUpd(ActionEvent e) {
    refreshNoteView();
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
      setOpaque(true);
      return this;
    }
  }

  private void initComponents() {
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
    noteEditor = new JEditorPane();
    panel4 = new JPanel();
    noteName = new JLabel();
    scrollPane3 = new JScrollPane();
    tagDefList = new DefaultListModel<ColoredItem>();
    tagView = new JList<>(tagDefList);

    // ======== this ========
    Container contentPane = getContentPane();
    contentPane.setLayout(new BorderLayout());

    // ======== panel1 ========
    {
      panel1.setBorder(null);
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
      displayName.addActionListener(e -> settings(e));
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
      panel2.setPreferredSize(new Dimension(250, 40));

      // ======== panel3 ========
      {
        panel3.setLayout(new BoxLayout(panel3, BoxLayout.X_AXIS));

        // ---- searchField ----
        searchField.setMinimumSize(new Dimension(125, 50));
        searchField.setPreferredSize(new Dimension(125, 50));
        searchField
            .getDocument()
            .addDocumentListener(
                new DocumentListener() {
                  public void changedUpdate(DocumentEvent e) {
                    searchUpdate();
                  }

                  public void removeUpdate(DocumentEvent e) {
                    searchUpdate();
                  }

                  public void insertUpdate(DocumentEvent e) {
                    searchUpdate();
                  }
                });
        panel3.add(searchField);
        tagBox.addActionListener(e -> tagBoxUpd(e));
        tagBox.setMinimumSize(new Dimension(125, 50));
        tagBox.setPreferredSize(new Dimension(125, 50));
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
      panel4.setPreferredSize(new Dimension(250, 1000));

      // ---- noteName ----
      noteName.setText("noteName");
      noteName.setHorizontalAlignment(SwingConstants.CENTER);
      panel4.add(noteName, BorderLayout.NORTH);

      // ======== scrollPane3 ========
      {

        // ======== tagView ========
        tagView.setCellRenderer(new ColoredItemRenderer());
        scrollPane3.setViewportView(tagView);
      }
      panel4.add(scrollPane3, BorderLayout.CENTER);
    }
    contentPane.add(panel4, BorderLayout.EAST);
    setSize(825, 550);
    setLocationRelativeTo(getOwner());
  }

  private JPanel panel1;
  protected JButton displayName;
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
  private JEditorPane noteEditor;
  private JPanel panel4;
  private JLabel noteName;
  private JScrollPane scrollPane3;
  private DefaultListModel<ColoredItem> tagDefList;
  private JList<ColoredItem> tagView;
  private String tempNoteContent;
}
