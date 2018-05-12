package com.bgrummitt.bouncingball.Game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

public class Ball {

    final static private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    final static private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
    private Bitmap image;
    private int x, y;
    private int xVelocity = 10;
    private int yVelocity = 5;
    private int wallSize;

    public Ball(Bitmap bmp, int x, int y, int wallSize){
        image = bmp;
        this.x = x;
        this.y = y;
        this.wallSize = wallSize;
    }

    public void update(){
        if((x <= 0) && (y < 0)){
            x = screenWidth / 2;
            y = screenHeight / 2;
        }else{
            x += xVelocity;
            y += yVelocity;
            if((x > (screenWidth - image.getWidth() - wallSize)) || (x < (0 + wallSize + 1)) ){
                xVelocity *= -1;
            }
            //TODO remove > screen height
            if((y - 100 > screenHeight - image.getHeight())|| (y < (0 + wallSize + 1))){
                yVelocity *= -1;
            }
        }
    }

    public void hitPadel(Padel padel){
        int padelX = padel.getX();
        // && (x >= padelX) && (x <= (padelX + padel.getImageWidth()));
        if(padel.getY() - padel.getImageHeight() <= y && padel.getY() >= y && (x >= padelX) && (x <= (padelX + padel.getImageWidth()))){
            yVelocity *= -1;
        }
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(image, x, y, null);
    }

}
