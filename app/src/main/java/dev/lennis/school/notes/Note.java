package dev.lennis.school.notes;

public class Note {
    private int id;
    private String title;
    private String content;
    private String username;
    private String[] tags;

    public static Note[] getNotesByTag(String tag, String username) {
        String[][] noteData = Data.getNotesByTag(tag, username);
        Note[] notes = new Note[noteData.length];
        for (int i = 0; i < noteData.length; i++) {
            notes[i] = new Note(Integer.parseInt(noteData[i][0]), noteData[i][1], noteData[i][2], noteData[i][3],
                    noteData[i][4].split(", "));
        }
        return notes;
    }

    public Note(int id, String username) {
        this.id = id;
        String[] noteData = Data.getNote(id, username);
        this.username = noteData[1];
        this.title = noteData[2];
        this.content = noteData[3];
        if (noteData[4] != null)
            this.tags = noteData[4].split(", ");
        else
            this.tags = new String[0];
    }

    public Note(int id, String username, String title, String content, String[] tags) {
        this.id = id;
        this.username = username;
        this.title = title;
        this.content = content;
        this.tags = tags;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String[] getTags() {
        return tags;
    }

    public String getTagsAsString() {
        String result = "";
        for (String tag : tags) {
            result += tag + ", ";
        }
        return result.substring(0, result.length() - 2);
    }

    public boolean hasTag(String tag) {
        for (String t : tags) {
            if (t.equals(tag))
                return true;
        }
        return false;
    }

}
