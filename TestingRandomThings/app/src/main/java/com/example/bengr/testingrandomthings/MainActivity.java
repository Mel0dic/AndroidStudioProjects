package com.example.bengr.testingrandomthings;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button myButton;
    TextView mainText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainText = findViewById(R.id.mainText);
        myButton = findViewById(R.id.mainButton);
        myButton.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        mainText.setText("Button Clicked");
                    }
                }
        );

        myButton.setOnLongClickListener(
                new Button.OnLongClickListener(){
                    public boolean onLongClick(View v){
                        mainText.setText("Button clicked long time");
                        return false;
                    }
                }
        );



    }



}
