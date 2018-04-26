package com.bgrummitt.gametest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TODO remove
        Log.w("com.bgrummitt.gametest", "Test Warning");

        TextView testButton = findViewById(R.id.mainButton);
        testButton.setBackgroundResource(R.drawable.button);
    }

}
