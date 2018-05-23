package com.bgrummitt.stormy.ui;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bgrummitt.stormy.R;
import com.bgrummitt.stormy.adapters.HourAdapter;
import com.bgrummitt.stormy.weather.Hour;

import java.util.Arrays;

public class HourlyForecastActivity extends AppCompatActivity {

    private Hour[] mHours;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hourly_forcast);

        Intent intent = getIntent();

        Parcelable[] parcelables = intent.getParcelableArrayExtra(MainActivity.HOURLY_FORECAST);
        mHours = Arrays.copyOf(parcelables, parcelables.length, Hour[].class);

        mRecyclerView = findViewById(R.id.recyclerView);

        HourAdapter hourAdapter = new HourAdapter(this, mHours);
        mRecyclerView.setAdapter(hourAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
    }

}
