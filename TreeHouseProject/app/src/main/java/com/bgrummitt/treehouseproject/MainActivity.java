package com.bgrummitt.treehouseproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView fact;
    private Button newTextBtn;
    private RelativeLayout mainFactLayout;
    private int colorToUse;

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
                fact.setText(Fact.getNewFact());
                colorToUse = randomColor.getRandomColor();
                mainFactLayout.setBackgroundColor(colorToUse);
                newTextBtn.setTextColor(colorToUse);
            }
        });
    }
}