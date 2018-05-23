package com.bgrummitt.stormy.ui;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bgrummitt.stormy.R;
import com.bgrummitt.stormy.adapters.DayAdapter;
import com.bgrummitt.stormy.weather.Day;

import java.util.Arrays;

public class DailyForecastActivity extends Activity {

    private Day[] mDays;
    private ListView listView;
    private TextView mEmptyTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_forecast);

        listView = findViewById(android.R.id.list);
        mEmptyTextView = findViewById(android.R.id.empty);

        Intent intent = getIntent();

        Parcelable[] parcelables = intent.getParcelableArrayExtra(MainActivity.DAILY_FORECAST);
        mDays = Arrays.copyOf(parcelables, parcelables.length, Day[].class);

        DayAdapter adapter = new DayAdapter(mDays, this);
        listView.setAdapter(adapter);
        listView.setEmptyView(mEmptyTextView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String dayOfTheWeek = mDays[position].getDayOfTheWeek();
                String conditions = mDays[position].getSummary();
                String highTemp = mDays[position].getTempMax() + "";
                String message = String.format("On %s the high will be %s and it will be %s", dayOfTheWeek, highTemp, conditions);

                Toast.makeText(DailyForecastActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }

//    @Override
//    protected void onListItemClick(ListView l, View v, int position, long id) {
//        super.onListItemClick(l, v, position, id);
//
//        String dayOfTheWeek = mDays[position].getDayOfTheWeek();
//        String conditions = mDays[position].getSummary();
//        String highTemp = mDays[position].getTempMax() + "";
//        String message = String.format("On %s the high will be %s and it will be %s", dayOfTheWeek, highTemp, conditions);
//
//        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
//    }

}
