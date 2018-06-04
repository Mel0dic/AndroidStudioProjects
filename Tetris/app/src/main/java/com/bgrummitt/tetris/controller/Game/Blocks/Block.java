package com.bgrummitt.tetris.controller.Game.Blocks;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

public class Block {

    final static private String TAG = Block.class.getSimpleName();

    private int mX;
    private int mY;
    private int mSimpleX;
    private int mSimpleY;
    private int mSpaceSize;
    private Paint paint;

    public Block(int x, int y, int spaceSize, int color, int topTetris, int leftTetris, int yPositioning){
        mX = x;
        mY = y;
        mSpaceSize = spaceSize;
        paint = new Paint();
        paint.setColor(color);

        for(int i = 0; i < 10; i++){
            if(mX >= (leftTetris + (i * mSpaceSize)) && mX < (leftTetris + ((i + 1) * mSpaceSize))){
                mSimpleX = i - 1;
            }
        }

        mSimpleY = -2 + yPositioning;
    }

    public boolean isTouching(Block block){
        if((block.getY() + mSpaceSize) == mY && block.getX() == mX){
            return true;
        }
        return false;
    }

    public void update(int xMovement, int yMovement){
        mX += (xMovement * mSpaceSize);
        mY += (yMovement * mSpaceSize);
        mSimpleX += xMovement;
        mSimpleY += yMovement;
        Log.d(TAG, mSimpleX + " " + mSimpleY);
    }

    public void draw(Canvas canvas){
        canvas.drawRect(mX, mY, (mX + mSpaceSize), (mY + mSpaceSize), paint);
    }

    public int getX() {
        return mX;
    }

    public int getY() {
        return mY;
    }

    public int getSimpleX(){
        return mSimpleX;
    }

    public int getSimpleY(){
        return mSimpleY;
    }
    
}
