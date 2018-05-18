package com.bgrummitt.golfcounter;

import android.app.ListActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends ListActivity {

    private static final String PREFS_FILE = "com.bgrummitt.golfcounter.preferences";
    private static final String KEY_STROKECOUNT = "KEY_STROKECOUNT_";
    private Hole[] holes = new Hole[18];
    private ListAdapter mListAdapter;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSharedPreferences = getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();

        int score;
        for(int i = 0; i < 18; i++){
            score = mSharedPreferences.getInt(KEY_STROKECOUNT + i, 0);
            holes[i] = new Hole((i + 1), score);
        }

        mListAdapter = new ListAdapter(this, holes);
        setListAdapter(mListAdapter);
    }

    @Override
    protected void onPause() {
        for(int i = 0; i < holes.length; i++){
            mEditor.putInt(KEY_STROKECOUNT + i, holes[i].getScore());
        }
        mEditor.apply();
        super.onPause();
    }
}
