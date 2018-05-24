package com.bgrummitt.flappybirdnolibrary.game.gameLogic;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Pipes {

    final static private String TAG = Bird.class.getSimpleName();
    final static private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    final static private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
    private Bitmap topPipe;
    private Bitmap bottomPipe;
    private int x;
    private int movementSpeed;

    public Pipes(Bitmap topPipe, Bitmap bottomPipe, int x, int movementSpeed){
        this.topPipe = topPipe;
        this.bottomPipe = bottomPipe;
        this.x = x;
        this.movementSpeed = movementSpeed;
    }

    public void update(){
        x-=movementSpeed;
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(topPipe, x, 0, null);
        canvas.drawBitmap(bottomPipe, x, (screenHeight-bottomPipe.getHeight()), null);
    }

}
