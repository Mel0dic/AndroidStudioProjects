package com.bgrummitt.golfcounter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Locale;

public class ListAdapter extends BaseAdapter {

    private Context mContext;
    private Hole[] holes;

    public ListAdapter(Context context, Hole[] holes){

        this.mContext = context;
        this.holes = holes;

    }

    @Override
    public int getCount() {
        return holes.length;
    }

    @Override
    public Object getItem(int position) {
        return holes[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.hole_layout, null);
            holder = new ViewHolder();
            holder.decreaseScoreButton = convertView.findViewById(R.id.subtractButton);
            holder.increaseScoreButton = convertView.findViewById(R.id.addButton);
            holder.HoleNumberTextView = convertView.findViewById(R.id.holeNumberTextView);
            holder.ScoreTextView = convertView.findViewById(R.id.scoreTextView);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        holder.HoleNumberTextView.setText(String.format(Locale.UK, "Hole : %d", holes[position].getHoleNumber()));
        holder.ScoreTextView.setText(String.format(Locale.UK, "%d", holes[position].getScore()));

        holder.increaseScoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holes[position].increase();
                holder.ScoreTextView.setText(String.format(Locale.UK, "%d", holes[position].getScore()));
            }
        });

        holder.decreaseScoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holes[position].decrease();
                holder.ScoreTextView.setText(String.format(Locale.UK, "%d", holes[position].getScore()));
            }
        });

        return convertView;
    }

    private static class ViewHolder{
        public Button decreaseScoreButton;
        public Button increaseScoreButton;
        public TextView HoleNumberTextView;
        public TextView ScoreTextView;
    }

}
