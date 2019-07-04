package com.bgrummitt.notes;

public class Note {

    private String subject;
    private String noteBody;
    private Boolean isCompleted;

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

    public Boolean getIsCompleted(){return isCompleted;}

    public void setIsCompleted(Boolean completed){ isCompleted = completed; }

}
