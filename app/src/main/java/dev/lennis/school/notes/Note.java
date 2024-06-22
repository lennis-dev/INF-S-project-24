package dev.lennis.school.notes;

import java.util.ArrayList;

public class Note {
  private int id;
  private String username;
  private String heading;
  private String text;
  private ArrayList<String> tags;
  private boolean permission = true;
  private ArrayList<String> readAccess;
  private ArrayList<String> writeAccess;

  public static ArrayList<Note> getSharedNotes(String username) {
    ArrayList<Note> notes = new ArrayList<Note>();
    for (ArrayList<String> note : Data.getSharedNotesByUsername(username)) {
      Note noteTmp = new Note(Integer.valueOf(note.get(0)));
      if (note.get(1).equals("1")) {
        noteTmp.setPermission(true);
      } else {
        noteTmp.setPermission(false);
      }
    }
    return notes;
  }

  public static ArrayList<Note> getNotesBySearch(String username, String search) {
    ArrayList<Note> notes = new ArrayList<Note>();
    for (int note : Data.getNotesBySearch(username, search)) {
      notes.add(new Note(note));
    }
    return notes;
  }

  public static ArrayList<Note> getNotes() {
    ArrayList<Note> notes = new ArrayList<Note>();
    for (ArrayList<String> note : Data.getNotes()) {
      notes.add(new Note(Integer.parseInt(note.get(0))));
    }
    return notes;
  }

  public static ArrayList<Note> getNotesByTag(String tag) {
    ArrayList<Note> notes = new ArrayList<Note>();
    for (int noteId : Data.getNotesByTag(tag)) {
      notes.add(new Note(noteId));
    }
    return notes;
  }

  public static ArrayList<Note> getNotesByUsername(String username) {
    ArrayList<Note> notes = new ArrayList<Note>();
    for (int noteId : Data.getNotesByUsername(username)) {
      notes.add(new Note(noteId));
    }
    return notes;
  }

  public static Note addNote(String username, String heading, String text) {
    int note = Data.addNote(username, heading, text);
    return new Note(note);
  }

  public Note(int id) {
    this.id = id;
    ArrayList<String> note = Data.getNoteById(id);
    this.username = note.get(1);
    this.heading = note.get(2);
    this.text = note.get(3);
    this.tags = Data.getTagsByNoteId(id);
    this.readAccess = Data.getReadAccessByNoteId(id);
    this.writeAccess = Data.getWriteAccessByNoteId(id);
  }

  public int getId() {
    return id;
  }

  public void addShare(String username, boolean mode) {
    if (mode) {
      readAccess.add(username);
    } else {
      writeAccess.add(username);
    }
    Data.addPermission(this.id, username, mode);
  }

  public void removeShare(String username, boolean mode) {
    if (mode) {
      readAccess.remove(username);
    } else {
      writeAccess.remove(username);
    }
    Data.removePermission(this.id, username, mode);
  }

  public ArrayList<String> getReadAccess() {
    return readAccess;
  }

  public boolean canRead(String username) {
    for (String user : getReadAccess()) {
      if (user.equals(username)) {
        return true;
      }
    }
    return false;
  }

  public ArrayList<String> getWriteAccess() {
    return writeAccess;
  }

  public boolean canWrite(String username) {
    for (String user : getWriteAccess()) {
      if (user.equals(username)) {
        return true;
      }
    }
    return false;
  }

  public void addReadAccess(String username) {
    if (readAccess.contains(username)) return;
    readAccess.add(username);
    Data.addPermission(this.id, username, false);
  }

  public void addWriteAccess(String username) {
    if (writeAccess.contains(username)) return;
    writeAccess.add(username);
    Data.addPermission(this.id, username, true);
  }

  public String getUsername() {
    return username;
  }

  public String getHeading() {
    return heading;
  }

  public String getText() {
    return text;
  }

  public ArrayList<String> getTags() {
    return tags;
  }

  public boolean getPermission() {
    return permission;
  }

  public void setPermission(Boolean permission) {
    this.permission = permission;
  }

  public void setHeading(String heading) {
    this.heading = heading;
    Data.updateNoteHeading(id, heading);
  }

  public void setText(String text) {
    this.text = text;
    Data.updateNoteText(id, text);
  }

  public void addTag(String tag) {
    if (tags.contains(tag)) return;
    tags.add(tag);
    Data.addTagToNoteId(id, tag);
  }

  public void removeTag(String tag) {
    tags.remove(tag);
    Data.removeTagFromNoteId(id, tag);
  }

  public void delete() {
    Data.deleteNoteById(id);
  }

  public boolean hasTag(String tag) {
    return tags.contains(tag);
  }
}
