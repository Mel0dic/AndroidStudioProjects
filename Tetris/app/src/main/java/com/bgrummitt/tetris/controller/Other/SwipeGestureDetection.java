package com.bgrummitt.tetris.controller.Other;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

public class SwipeGestureDetection extends GestureDetector.SimpleOnGestureListener {

    private static final String TAG = SwipeGestureDetection.class.getSimpleName();

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

        swipeType swipe = getSlope(e1.getX(), e2.getX(), e1.getY(), e2.getY());

        Log.d(TAG, swipe + "");

        if(swipe == swipeType.left && (e1.getX() - e2.getX()) > MIN_FLING_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY){
            Log.d(TAG, "Left Swipe Registered");
        }else if(swipe == swipeType.right && e1.getX() - e2.getX() < -MIN_FLING_DISTANCE){
            Log.d(TAG, "Right Swipe Registered");
        }

        return super.onFling(e1, e2, velocityX, velocityY);
    }

    private swipeType getSlope(float x1, float y1, float x2, float y2) {

        Double angle = Math.toDegrees(Math.atan2(y1 - y2, x2 - x1));

        if (angle > 45 && angle <= 135) {
            return swipeType.up;
        }

        if (angle >= 135 && angle < 180 || angle < -135 && angle > -180) {
            return swipeType.left;
        }

        if (angle < -45 && angle>= -135) {
            return swipeType.down;
        }

        if (angle > -45 && angle <= 45) {
            return swipeType.right;
        }

        return null;

    }

}
