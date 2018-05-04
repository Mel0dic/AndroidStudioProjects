package com.bgrummitt.gametest.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.bgrummitt.gametest.R;

public class MainActivity extends AppCompatActivity {

    private ImageButton buttonTemplateTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonTemplateTest = findViewById(R.id.startGameButton);

        buttonTemplateTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Button Pressed", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                startActivity(intent);
            }
        });
    }

}
