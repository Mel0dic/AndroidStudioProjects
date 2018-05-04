package com.bgrummitt.gametest.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.bgrummitt.gametest.R;

public class GameActivity extends AppCompatActivity {

    public static final String TAG = GameActivity.class.getSimpleName();

    private ImageButton backToMainMenuButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        backToMainMenuButton = findViewById(R.id.mainMenuButton);

        backToMainMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Back button clicked");
                finish();
            }
        });
    }
}
