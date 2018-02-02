package com.example.bengr.testingrandomthings;

import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener{

    Button myButton;
    TextView mainText;
    private GestureDetectorCompat gestureDetector;

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

        this.gestureDetector = new GestureDetectorCompat(this, this);

        gestureDetector.setOnDoubleTapListener(this);

    }


    @Override
    public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
        mainText.setText("Single Tap has been confirmed");
        return true;
    }

    @Override
    public boolean onDoubleTap(MotionEvent motionEvent) {
        mainText.setText("Double Tap has been confirmed");
        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent motionEvent) {
        mainText.setText("Double Event Tap has been confirmed");
        return true;
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        mainText.setText("Down Event has been confirmed");
        return true;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {
        mainText.setText("This should show on press");

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        mainText.setText("Single Tap Up has been confirmed");
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        mainText.setText("Scroll Tap has been confirmed");
        return true;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {
        mainText.setText("Long Tap has been confirmed");
    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        mainText.setText("Fling Tap has been confirmed");
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.gestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }
}
