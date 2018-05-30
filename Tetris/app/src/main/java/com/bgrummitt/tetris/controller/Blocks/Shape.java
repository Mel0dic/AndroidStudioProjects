package com.bgrummitt.tetris.controller.Blocks;

import android.graphics.Canvas;
import android.util.Log;

public abstract class Shape {

    final static private String TAG = Shape.class.getSimpleName();

    private int x;
    private int y;
    private int mSpaceSize;
    private int mFallSpeed;
    public int movementSpeed;

    public Shape(int xSpawnPosition, int ySpawnPosition, int fallSpeed, int spaceSize){
        x = xSpawnPosition;
        y = ySpawnPosition;
        mFallSpeed = fallSpeed;
        movementSpeed = 0;
        mSpaceSize = spaceSize;
    }

    public void startFall(){
        movementSpeed = mFallSpeed;
    }

    public abstract void update();

    public void stop(){
        movementSpeed = 0;
    }

    public abstract boolean isTouching();

    public abstract void draw(Canvas canvas);

}
