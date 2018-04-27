package com.bgrummitt.gametest;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ImageButton buttonTemplateTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonTemplateTest = findViewById(R.id.testingButton);

        buttonTemplateTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Button Pressed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public class backgroundMainScreen extends AsyncTask<Drawable, int[][], Void>{
        @Override
        protected Void doInBackground(Drawable... drawables) {
            return null;
        }
    }

}
