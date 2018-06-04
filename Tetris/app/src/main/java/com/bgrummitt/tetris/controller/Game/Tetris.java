package com.bgrummitt.tetris.controller.Game;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;

import com.bgrummitt.tetris.controller.Game.Blocks.BlockShapes.TShape;
import com.bgrummitt.tetris.controller.Game.Blocks.Shape;
import com.bgrummitt.tetris.controller.Other.SwipeGestureDetection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Tetris{

    final static private String TAG = Tetris.class.getSimpleName();
    final static private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    final static private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

    private final int fallSpeed;
    private List<Shape> shapes = new ArrayList<>();
    private int[][] mTetrisGrid = new int[20][10];
    private Random random;
    private Shape mShapeToDrop;
    private Shape mShapeDropping;
    private int spaceSize;
    private long time;
    private Paint mEmptyStrokePaint;
    private int mBottomOfTetrisGrid;
    private int mLeftOfTetrisGrid;
    private int mRightOfTetrisGrid;
    private int mTopOfTetrisGrid;

    public Tetris(Resources resources){
        random = new Random();
        spaceSize = screenWidth / 10;
        if(spaceSize * 23 > screenHeight){
            spaceSize = screenHeight / 23;
        }
        fallSpeed = spaceSize;
        mShapeToDrop = getNewBlock();

        mBottomOfTetrisGrid = (screenHeight - 3);
        mLeftOfTetrisGrid = (screenWidth / 2) - (spaceSize * 5);
        mRightOfTetrisGrid = (screenWidth / 2) + (spaceSize * 5);
        mTopOfTetrisGrid = mBottomOfTetrisGrid - (spaceSize * 20);

        for(int[] row : mTetrisGrid){
            for(int column : row){
                column = 0;
            }
        }

        mEmptyStrokePaint = new Paint();
        mEmptyStrokePaint.setStyle(Paint.Style.STROKE);
        mEmptyStrokePaint.setColor(Color.RED);
        mEmptyStrokePaint.setStrokeWidth(5);
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
                return new TShape((screenWidth / 2) - spaceSize, (screenHeight-5) - (23 * spaceSize), fallSpeed, spaceSize, Color.RED, mTopOfTetrisGrid, mLeftOfTetrisGrid);
            case 1:
                return new TShape((screenWidth / 2) - spaceSize, (screenHeight-5) - (23 * spaceSize), fallSpeed, spaceSize, Color.YELLOW, mTopOfTetrisGrid, mLeftOfTetrisGrid);
            case 2:
                return new TShape((screenWidth / 2) - spaceSize, (screenHeight-5) - (23 * spaceSize), fallSpeed, spaceSize, Color.BLUE, mTopOfTetrisGrid, mLeftOfTetrisGrid);
            default:
                return null;
        }
    }

    public void update(){
        if(mShapeDropping != null) {
            if(System.currentTimeMillis() - time > 200) {
                mShapeDropping.update();
                time = System.currentTimeMillis();
            }
            if(mShapeDropping.hitsFloor()){
                Log.d(TAG, "Hits Floor");
                resetBlock();
            }else {
                for (Shape shape : shapes) {
                    if (mShapeDropping.isTouching(shape)) {
                        resetBlock();
                    }
                }
            }
        }
    }

    public void resetBlock(){
        mShapeDropping.stop();
        mTetrisGrid = mShapeDropping.addPosition(mTetrisGrid);
        shapes.add(mShapeDropping);
        mShapeDropping = mShapeToDrop;
        mShapeDropping.startFall();
        mShapeToDrop = getNewBlock();

        Log.v(TAG, Integer.toString(mTetrisGrid.length));
        for(int[] i : mTetrisGrid){
            Log.v(TAG, Arrays.toString(i));
        }
    }

    public void draw(Canvas canvas){
        canvas.drawRect(mLeftOfTetrisGrid - 3, mTopOfTetrisGrid - 3, mRightOfTetrisGrid + 2, mBottomOfTetrisGrid - 2, mEmptyStrokePaint);
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

    public void swipe(SwipeGestureDetection.swipeType type) {
        if(type == SwipeGestureDetection.swipeType.right && mShapeDropping != null && mShapeDropping.canSwipe(mTetrisGrid, 1)){
            mShapeDropping.swipe(1);
        }else if(type == SwipeGestureDetection.swipeType.left && mShapeDropping != null && mShapeDropping.canSwipe(mTetrisGrid, -1)){
            mShapeDropping.swipe(-1);
        }
    }

    public void rotateFallingBlock() {
        if(mShapeDropping != null) {
            mShapeDropping.rotate();
        }
    }

}
