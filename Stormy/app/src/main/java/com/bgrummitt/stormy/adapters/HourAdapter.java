package com.bgrummitt.stormy.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bgrummitt.stormy.R;
import com.bgrummitt.stormy.weather.Hour;

public class HourAdapter extends RecyclerView.Adapter<HourAdapter.HourViewHolder> {

    private Hour[] mHours;

    public HourAdapter(Hour[] hours){
        mHours = hours;
    }

    public class HourViewHolder extends RecyclerView.ViewHolder{

        public TextView mTimeLabel;
        public TextView mSummaryLabel;
        public TextView mTempLabel;
        public ImageView mIconImageView;

        public HourViewHolder(View itemView) {
            super(itemView);

            mTimeLabel = itemView.findViewById(R.id.timeLabel);
            mSummaryLabel = itemView.findViewById(R.id.summaryLabel);
            mTempLabel = itemView.findViewById(R.id.temperatureLabel);
            mIconImageView = itemView.findViewById(R.id.iconImageView);
        }

        public void bindHour(Hour hour){
            mTimeLabel.setText(hour.getSimpleHour());
            mSummaryLabel.setText(hour.getSummary());
            mTempLabel.setText(hour.getSimpleTemp() + "");
            mIconImageView.setImageResource(hour.getIconId());
        }

    }

    @NonNull
    @Override
    public HourViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hourly_list_item, parent, false);
        HourViewHolder viewHolder = new HourViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HourViewHolder holder, int position) {
        holder.bindHour(mHours[position]);
    }

    @Override
    public int getItemCount() {
        return mHours.length;
    }

}
