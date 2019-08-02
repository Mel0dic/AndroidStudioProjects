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
        mRecentlyDeletedID = mRecentlyDeletedItem.getDatabaseID();
        mNotes.remove(position);
        changeIDs(mRecentlyDeletedItem.getDatabaseID(), -1);
        ((MainActivity)mContext).markNoteCompleted(mRecentlyDeletedItem);
        notifyItemRemoved(position);
        showUndoSnackBar();
    }

    protected void showUndoSnackBar() {
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

    protected void undoDelete() {
        mNotes.add(mRecentlyDeletedPosition,
                mRecentlyDeletedItem);
        ((MainActivity)mContext).insertNoteIntoTODO(mRecentlyDeletedItem, mRecentlyDeletedPosition);
        //TODO Delete from the completed db when undoing
//        ((MainActivity)mContext).deleteNoteFromCompleted(mRecentlyDeletedItem);
        notifyItemInserted(mRecentlyDeletedPosition);
    }

    public void selectAll(){
        for(Note note : mNotes){
            note.setIsCompleted(!currentSelectAllState);
        }

        currentSelectAllState = !currentSelectAllState;
    }

}
