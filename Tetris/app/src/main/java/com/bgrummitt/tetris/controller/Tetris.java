package com.bgrummitt.tetris.controller;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;

import com.bgrummitt.tetris.controller.Blocks.Shape;
import com.bgrummitt.tetris.controller.Blocks.BlockShapes.TShape;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Tetris {

    final static private String TAG = Tetris.class.getSimpleName();
    final static private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    final static private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

    private final int fallSpeed;
    private List<Shape> shapes = new ArrayList<>();
    private Random random;
    private Shape mShapeToDrop;
    private Shape mShapeDropping;
    private int screenNumClicks = 0;
    private int spaceSize;

    public Tetris(Resources resources){
        random = new Random();
        fallSpeed = 10;
        spaceSize = screenWidth / 10;
        mShapeToDrop = getNewBlock();
    }

    public void startGame(){
        mShapeToDrop.startFall();
        mShapeDropping = mShapeToDrop;
        mShapeToDrop = getNewBlock();
    }

    public Shape getNewBlock(){
        int randNum = random.nextInt(3);

        switch (randNum){
            case 0:
                return new TShape((screenWidth / 2) - (spaceSize / 2), screenHeight / 20, fallSpeed, spaceSize, Color.RED);
            case 1:
                return new TShape((screenWidth / 2) - (spaceSize / 2), screenHeight / 20, fallSpeed, spaceSize, Color.YELLOW);
            case 2:
                return new TShape((screenWidth / 2) - (spaceSize / 2), screenHeight / 20, fallSpeed, spaceSize, Color.BLUE);
            default:
                return null;
        }
    }

    public void screenClicked(MotionEvent event){
        if(screenNumClicks++ == 0){
            startGame();
        }
    }

    public void update(){
        if(mShapeDropping != null) {
            mShapeDropping.update();
            if (mShapeDropping.isTouching()) {
                mShapeDropping.stop();
                shapes.add(mShapeDropping);
                mShapeDropping = mShapeToDrop;
                mShapeDropping.startFall();
                mShapeToDrop = getNewBlock();
            }
        }
    }

    public void draw(Canvas canvas){
        //Draw the shape waiting to be dropped
        mShapeToDrop.draw(canvas);
        //If a block has been dropped already
        if(mShapeDropping != null){
            //Draw the falling block
            mShapeDropping.draw(canvas);
            //Draw the blocks that have stopped falling
            for (Shape shape : shapes) {
                shape.draw(canvas);
            }
        }
    }

}
