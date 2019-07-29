package com.bgrummitt.notes.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CompletedNote extends Note {

    private static DateFormat DATE_FORMAT;

    private Date dateNoteCompleted;

    public CompletedNote(String subject, String noteBody, Boolean isCompleted, int id, String date){
        super(subject, noteBody, isCompleted, id);

        if(DATE_FORMAT == null){
            DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        }

        try {
            dateNoteCompleted = convertStringToDate(date);
        }catch (ParseException pe){
            pe.printStackTrace();
        }

    }

    public Date getDateNoteCompleted() {
        return dateNoteCompleted;
    }

    public void setDateNoteCompleted(Date dateNoteCompleted) {
        this.dateNoteCompleted = dateNoteCompleted;
    }

    public Date convertStringToDate(String string) throws ParseException {
        return DATE_FORMAT.parse(string);
    }

    public String convertDateToString(Date date){
        return DATE_FORMAT.format(date);
    }

}
