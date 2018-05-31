package com.bgrummitt.tetris.controller.Game.Blocks;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Block {

    final static private String TAG = Block.class.getSimpleName();

    private int mX;
    private int mY;
    private int mSpaceSize;
    private Paint paint;

    public Block(int x, int y, int spaceSize, int color){
        mX = x;
        mY = y;
        mSpaceSize = spaceSize;
        paint = new Paint();
        paint.setColor(color);
    }

    public boolean isTouching(Block block){
        if((block.getY() + mSpaceSize) == mY && block.getX() == mX){
            return true;
        }
        return false;
    }

    public void update(int xMovement, int yMovement){
        mX += xMovement;
        mY += yMovement;
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
    
}
