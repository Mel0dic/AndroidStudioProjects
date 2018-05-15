package com.bgrummitt.notes;

public class Note {

    private String subject;
    private String noteBody;

    public Note(String subject, String noteBody){
        this.subject = subject;
        this.noteBody = noteBody;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getNoteBody() {
        return noteBody;
    }

    public void setNoteBody(String noteBody) {
        this.noteBody = noteBody;
    }
}
