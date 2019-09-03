package com.bgrummitt.notes.controller.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;

import com.bgrummitt.notes.R;
import com.bgrummitt.notes.activities.MainActivity;
import com.bgrummitt.notes.model.CompletedNote;
import com.bgrummitt.notes.model.Note;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CompletedAdapter extends ListAdapter {

    private static final String TAG = TODOAdapter.class.getSimpleName();

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
            convertedNotes.add(convertNote(note));
        }

        return convertedNotes;
    }

    public static Note convertNote(CompletedNote note){
        return new Note(note.getSubject(), note.getNoteBody(), note.getIsCompleted(), note.getDatabaseID());
    }

    @Override
    public void deleteItem(int position) {
        mRecentlyDeletedItem = mNotes.get(position);
        mRecentlyDeletedDate = mNoteDates.get(position);
        removeAndRestructureNotes(position);
        mNoteDates.remove(position);
        notifyItemRemoved(position);
        showUndoSingleSnackBar(mRecentlyDeletedItem.getDatabaseID());
    }

    @Override
    protected void showUndoSingleSnackBar(final int idToUndo) {
        View view = ((Activity) mContext).findViewById(R.id.list);
        Snackbar snackbar = Snackbar.make(view, R.string.snack_bar_undo,
                Snackbar.LENGTH_LONG);
        snackbar.setAction(R.string.snack_bar_undo, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertNoteIntoList(mRecentlyDeletedItem, idToUndo);
                mNoteDates.add(idToUndo, mRecentlyDeletedDate);
            }
        });
        snackbar.addCallback(new Snackbar.Callback(){

            @Override
            public void onDismissed(Snackbar snackbar, int event) {
                Log.d(TAG, "Snackbar Dismissed");
                if(event != Snackbar.Callback.DISMISS_EVENT_ACTION){
                    Log.d(TAG, "Snackbar Dismissed By Timeout / New SnackBar / Swipe");
                    removeItemFromDB(new CompletedNote(mRecentlyDeletedItem, mRecentlyDeletedDate));
                }
            }

        });
        snackbar.show();
    }

    private void removeItemFromDB(CompletedNote note){
        changeIDs(note.getDatabaseID(), -1);
        ((MainActivity)mContext).deleteNoteFromCompleted(note);
    }

    public ListTypes getType(){
        return ListTypes.COMPLETED_LIST;
    }

}
