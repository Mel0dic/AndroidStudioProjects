package com.bgrummitt.tetris.controller.Blocks;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

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

    public void update(int xMovement, int yMovement){
        mX += xMovement;
        mY += yMovement;
    }

    public void draw(Canvas canvas){
        canvas.drawRect(mX, mY, (mX + mSpaceSize), (mY + mSpaceSize), paint);
    }

    public int getY() {
        return mY;
    }
    
}
