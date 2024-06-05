package dev.lennis.school.notes;

import java.util.ArrayList;

public class Note {
    private int id;
    private String username;
    private String heading;
    private String title;
    private ArrayList<String> tags;

    static public ArrayList<Note> getNotes() {
        ArrayList<Note> notes = new ArrayList<Note>();
        for (ArrayList<String> note : Data.getNotes()) {
            notes.add(new Note(Integer.parseInt(note.get(0))));
        }
        return notes;
    }

    static public ArrayList<Note> getNotesByTag(String tag) {
        ArrayList<Note> notes = new ArrayList<Note>();
        for (int noteId : Data.getNotesByTag(tag)) {
            notes.add(new Note(noteId));
        }
        return notes;
    }

    static public ArrayList<Note> getNotesByUsername(String username) {
        ArrayList<Note> notes = new ArrayList<Note>();
        for (int noteId : Data.getNotesByUsername(username)) {
            notes.add(new Note(noteId));
        }
        return notes;
    }

    static public Note addNote(String username, String heading, String text) {
        int note = Data.addNote(username, heading, text);
        return new Note(note);
    }

    public Note(int id) {
        this.id = id;
        ArrayList<String> note = Data.getNoteById(id);
        this.username = note.get(1);
        this.heading = note.get(2);
        this.title = note.get(3);
        this.tags = Data.getTagsByNoteId(id);
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getHeading() {
        return heading;
    }

    public String getTitle() {
        return title;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setHeading(String heading) {
        this.heading = heading;
        Data.updateNoteHeading(id, heading);
    }

    public void setText(String text) {
        this.title = text;
        Data.updateNoteText(id, text);
    }

    public void addTag(String tag) {
        if (tags.contains(tag))
            return;
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
