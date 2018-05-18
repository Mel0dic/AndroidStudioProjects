package com.bgrummitt.golfcounter;

import android.app.ListActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends ListActivity {

    private static final String PREFS_FILE = "com.bgrummitt.golfcounter.preferences";
    private static final String KEY_STROKE_COUNT = "KEY_STROKE_COUNT_";
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
            score = mSharedPreferences.getInt(KEY_STROKE_COUNT + i, 0);
            holes[i] = new Hole((i + 1), score);
        }

        mListAdapter = new ListAdapter(this, holes);
        setListAdapter(mListAdapter);
    }

    @Override
    protected void onPause() {
        for(int i = 0; i < holes.length; i++){
            mEditor.putInt(KEY_STROKE_COUNT + i, holes[i].getScore());
        }
        mEditor.apply();
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.clearStrokes){
            mEditor.clear();
            mEditor.apply();
            for(Hole hole : holes){
                hole.setScore(0);
            }
            mListAdapter.notifyDataSetChanged();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
