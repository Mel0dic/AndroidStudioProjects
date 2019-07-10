package com.bgrummitt.notes.model;

public class Note {

    private String subject;
    private String noteBody;
    private Boolean isCompleted;
    private int DatabaseID;

    public Note(String subject, String noteBody, Boolean isCompleted, int id){
        this.subject = subject;
        this.noteBody = noteBody;
        this.isCompleted = isCompleted;
        this.DatabaseID = id;
    }

    public Note(String subject, String noteBody, Boolean isCompleted){
        this.subject = subject;
        this.noteBody = noteBody;
        this.isCompleted = isCompleted;
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

    public Boolean getIsCompleted(){
        return isCompleted;
    }

    public void setIsCompleted(Boolean completed){
        isCompleted = completed;
    }

    public int getDatabaseID(){
        return DatabaseID;
    }

    public void setDatabaseID(int id){
        this.DatabaseID = id;
    }

}
