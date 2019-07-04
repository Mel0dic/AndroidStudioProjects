package com.bgrummitt.notes;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {

    private final Context mContext;
    private final List<Note> mNotes;
    private Note mRecentlyDeletedItem;
    private int mRecentlyDeletedPosition;
    private Boolean currentSelectAllState = false;

    public ListAdapter (Context context, List<Note> notes){
        mContext = context;
        mNotes = notes;
    }

    public class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView subjectTextView;
        public TextView noteTextView;
        public CheckBox completeCheckBox;

        public ListViewHolder(View itemView) {
            super(itemView);

            subjectTextView = itemView.findViewById(R.id.subjectTextView);
            noteTextView = itemView.findViewById(R.id.mainNoteDisplay);
            completeCheckBox = itemView.findViewById(R.id.CompletedCheck);

            itemView.setOnClickListener(this);
        }

        public void bindList(Note note, final int position){
            subjectTextView.setText(note.getSubject());
            noteTextView.setText(note.getNoteBody());
            completeCheckBox.setChecked(note.getIsCompleted());
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(mContext, subjectTextView.getText(), Toast.LENGTH_SHORT).show();
        }
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_layout, parent, false);
        ListViewHolder viewHolder = new ListViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        holder.bindList(mNotes.get(position), position);
    }

    public void deleteItem(int position){
        mRecentlyDeletedItem = mNotes.get(position);
        mRecentlyDeletedPosition = position;
        mNotes.remove(position);
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
                ListAdapter.this.undoDelete();
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

    @Override
    public int getItemCount() {
        return mNotes.size();
    }

    public List<Note> getNotes(){
        return mNotes;
    }

    public void addNote(Note note){
        mNotes.add(note);
    }

    public Context getContext(){
        return mContext;
    }

}
