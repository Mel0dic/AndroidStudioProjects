package com.bgrummitt.notes.controller.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.bgrummitt.notes.R;
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
        mRecentlyDeletedDate = mNoteDates.get(position);
        mNotes.remove(position);
        mNoteDates.remove(position);
        changeIDs(mRecentlyDeletedItem.getDatabaseID(), -1);
        ((MainActivity)mContext).deleteNoteFromCompleted(mRecentlyDeletedItem);
        notifyItemRemoved(position);
        showUndoSnackBar(mRecentlyDeletedItem.getDatabaseID());
    }

    @Override
    protected void showUndoSnackBar(final int idToUndo) {
        View view = ((Activity) mContext).findViewById(R.id.list);
        Snackbar snackbar = Snackbar.make(view, R.string.snack_bar_undo,
                Snackbar.LENGTH_LONG);
        snackbar.setAction(R.string.snack_bar_undo, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CompletedAdapter.this.undoDelete(idToUndo);
            }
        });
        snackbar.show();
    }

    @Override
    protected void undoDelete(int idToUndo) {
        mNotes.add(mRecentlyDeletedPosition, mRecentlyDeletedItem);
        mNoteDates.add(mRecentlyDeletedPosition, mRecentlyDeletedDate);
        ((MainActivity)mContext).insertNoteIntoCompleted(new CompletedNote(mRecentlyDeletedItem, mRecentlyDeletedDate));
        notifyItemInserted(mRecentlyDeletedPosition);
    }
}
