package com.bgrummitt.notes.controller.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.bgrummitt.notes.R;
import com.bgrummitt.notes.activities.MainActivity;
import com.bgrummitt.notes.controller.databse.DatabaseHelper;
import com.bgrummitt.notes.model.Note;

import java.util.List;

public class TODOAdapter extends ListAdapter {

    public TODOAdapter(Context context, List<Note> notes) {
        super(context, notes);
    }

    public void deleteItem(int position){
        mRecentlyDeletedItem = mNotes.get(position);
        mRecentlyDeletedPosition = position;
        mNotes.remove(position);
        changeIDs(mRecentlyDeletedItem.getDatabaseID(), -1);
        ((MainActivity)mContext).markNoteCompleted(mRecentlyDeletedItem.getDatabaseID());
        notifyItemRemoved(position);
        showUndoSnackBar();
    }

    private void showUndoSnackBar() {
        View view = ((Activity) mContext).findViewById(R.id.list);
        Snackbar snackbar = Snackbar.make(view, R.string.snack_bar_undo,
                Snackbar.LENGTH_LONG);
        snackbar.setAction(R.string.snack_bar_undo, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TODOAdapter.this.undoDelete();
            }
        });
        snackbar.show();
    }

    private void undoDelete() {
        mNotes.add(mRecentlyDeletedPosition,
                mRecentlyDeletedItem);
        notifyItemInserted(mRecentlyDeletedPosition);
    }

    public void selectAll(){
        for(Note note : mNotes){
            note.setIsCompleted(!currentSelectAllState);
        }

        currentSelectAllState = !currentSelectAllState;
    }

    public void changeIDs(int idGreaterThan, int changeBy){
        for(Note note : mNotes){
            if(note.getDatabaseID() > idGreaterThan){
                note.setDatabaseID(note.getDatabaseID() + changeBy);
            }
        }
    }


}
