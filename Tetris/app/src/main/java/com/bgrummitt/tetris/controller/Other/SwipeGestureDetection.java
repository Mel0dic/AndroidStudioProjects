package com.bgrummitt.tetris.controller.Other;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.bgrummitt.tetris.view.GameActivity.GameView;

public class SwipeGestureDetection extends GestureDetector.SimpleOnGestureListener {

    private static final String TAG = SwipeGestureDetection.class.getSimpleName();
    private GameView mView;

    public SwipeGestureDetection(GameView view){
        mView = view;
    }

    public enum swipeType {
        left,
        right,
        up,
        down
    }

    private static final int MIN_FLING_DISTANCE = 100;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;


    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

        swipeType swipe = getSlope(e1.getX(), e1.getY(), e2.getX(), e2.getY());

        Log.d(TAG, swipe + "");

        if(swipe == swipeType.left && (e1.getX() - e2.getX()) > MIN_FLING_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY){
            mView.swipe(swipe);
        }else if(swipe == swipeType.right && e1.getX() - e2.getX() < -MIN_FLING_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY){
            mView.swipe(swipe);
        }

        return true;
    }

    private swipeType getSlope(float x1, float y1, float x2, float y2) {

        Double angle = Math.toDegrees(Math.atan2(y1 - y2, x2 - x1));

        if (angle > 45 && angle <= 135){
            return swipeType.up;
        }
        if (angle >= 135 && angle < 180 || angle < -135 && angle > -180){
            return swipeType.left;
        }
        if (angle < -45 && angle>= -135){
            return swipeType.down;
        }
        if (angle > -45 && angle <= 45){
            return swipeType.right;
        }
        return null;

    }

    private int count = 0;

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        Log.d(TAG, "Single Tap");
        if(count++ == 0){
            mView.startGame();
        }else{
            mView.rotate();
        }
        return true;
    }
}
