package com.bgrummitt.treehouseproject;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String KEY_FACT = "KEY_FACT";
    private static final String KEY_COLOR = "KEY_COLOR";
    private TextView fact;
    private Button newTextBtn;
    private RelativeLayout mainFactLayout;
    private int colorToUse = Color.parseColor(randomColor.colors[8]);
    private String mFact = Fact.facts[0];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newTextBtn = findViewById(R.id.newFactBtn);
        fact = findViewById(R.id.FactText);
        mainFactLayout = findViewById(R.id.mainActivity);

        newTextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFact = Fact.getNewFact();
                fact.setText(mFact);
                colorToUse = randomColor.getRandomColor();
                mainFactLayout.setBackgroundColor(colorToUse);
                newTextBtn.setTextColor(colorToUse);
            }
        });

        if(savedInstanceState != null){
            mFact = savedInstanceState.getString(KEY_FACT);
            colorToUse = savedInstanceState.getInt(KEY_COLOR);

            fact.setText(mFact);
            mainFactLayout.setBackgroundColor(colorToUse);
            newTextBtn.setTextColor(colorToUse);
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(KEY_FACT, mFact);
        outState.putInt(KEY_COLOR, colorToUse);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        mFact = savedInstanceState.getString(KEY_FACT);
        colorToUse = savedInstanceState.getInt(KEY_COLOR);

        fact.setText(mFact);
        mainFactLayout.setBackgroundColor(colorToUse);
        newTextBtn.setTextColor(colorToUse);
    }
}