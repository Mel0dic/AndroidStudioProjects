package com.bgrummitt.notes.controller.adapters;

import android.content.Context;

import com.bgrummitt.notes.activities.MainActivity;
import com.bgrummitt.notes.model.CompletedNote;
import com.bgrummitt.notes.model.Note;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CompletedAdapter extends ListAdapter {

    private List<Date> mNoteDates;
    private Date mRecentlyDeletedDate;

    public CompletedAdapter(Context context, List<CompletedNote> notes) {
        super(context, convertNotes(notes));

        mNoteDates = new ArrayList<>();

        for(CompletedNote note : notes){
            mNoteDates.add(note.getDateNoteCompleted());
        }
    }

    public static List<Note> convertNotes(List<CompletedNote> notes){
        List<Note> convertedNotes = new ArrayList<>();

        for(CompletedNote note : notes){
            convertedNotes.add(new Note(note.getSubject(), note.getNoteBody(), note.getIsCompleted(), note.getDatabaseID()));
        }

        return convertedNotes;
    }

    @Override
    public void deleteItem(int position) {
        mRecentlyDeletedItem = mNotes.get(position);
        mRecentlyDeletedPosition = position;
        mNotes.remove(position);
        changeIDs(mRecentlyDeletedItem.getDatabaseID(), -1);
        ((MainActivity)mContext).deleteNoteFromCompleted(mRecentlyDeletedItem);
        notifyItemRemoved(position);
        showUndoSnackBar();
    }

    @Override
    protected void showUndoSnackBar() {

    }

    @Override
    protected void undoDelete() {

    }
}
