package com.bgrummitt.notes.controller.adapters;

import android.content.Context;

import com.bgrummitt.notes.model.CompletedNote;
import com.bgrummitt.notes.model.Note;

import java.util.ArrayList;
import java.util.List;

public class CompletedAdapter extends TODOAdapter {

    private List<CompletedNote> mCompletedNotes;

    public CompletedAdapter(Context context, List<CompletedNote> notes) {
        super(context, convertNotes(notes));

        mCompletedNotes = notes;
    }

    public static List<Note> convertNotes(List<CompletedNote> notes){
        List<Note> convertedNotes = new ArrayList<>();

        for(CompletedNote note : notes){
            convertedNotes.add(new Note(note.getSubject(), note.getNoteBody(), note.getIsCompleted(), note.getDatabaseID()));
        }

        return convertedNotes;
    }

}
