package com.example.bengr.project1test;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import java.text.AttributedCharacterIterator;

public class MainActivity extends AppCompatActivity {

    Animation translatebu;
    TextView TextHW;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextHW = findViewById(R.id.TextHW);

        translatebu= AnimationUtils.loadAnimation(this, R.anim.animationfile);
        TextHW.setText("TEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEST");
        TextHW.startAnimation(translatebu);

    }

    public void endAnimation(View pView){
        if(count == 0){
            System.out.println("Finish");
            TextHW.setText("Done");
            TextHW.clearAnimation();
            count++;
        }

    }

}
