package com.bgrummitt.notes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

public class ListAdapter extends BaseAdapter {

    private final Context mContext;
    private final List<Note> mNotes;

    public ListAdapter (Context context, List<Note> notes){
        mContext = context;
        mNotes = notes;
    }

    @Override
    public int getCount() {
        return mNotes.size();
    }

    @Override
    public Object getItem(int position) {
        return mNotes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.note_layout, null);
            holder = new ViewHolder();
            holder.deleteButton = convertView.findViewById(R.id.deleteImageButton);
            holder.subjectTextView = convertView.findViewById(R.id.subjectTextView);
            holder.noteTextView = convertView.findViewById(R.id.mainNoteDisplay);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        holder.subjectTextView.setText(mNotes.get(position).getSubject());
        holder.noteTextView.setText(mNotes.get(position).getNoteBody());

        holder.noteTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNotes.remove(position);
            }
        });

        return convertView;
    }

    public List<Note> getmNotes(){
        return mNotes;
    }

    private static class ViewHolder{
        public ImageButton deleteButton;
        public TextView subjectTextView;
        public TextView noteTextView;
    }
}
