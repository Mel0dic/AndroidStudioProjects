package com.bgrummitt.bouncingball.Game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Ball {

    final static private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    final static private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
    private Bitmap image;
    private int x, y;
    private int xVelocity = 10;
    private int yVelocity = 5;

    public Ball(Bitmap bmp, int x, int y){
        image = bmp;
        this.x = x;
        this.y = y;
    }

    public void update(){
        if((x <= 0) && (y < 0)){
            x = screenWidth / 2;
            y = screenHeight / 2;
        }else{
            x += xVelocity;
            y += yVelocity;
            if((x > screenWidth - image.getWidth()) || (x < 0)){
                xVelocity *= -1;
            }
            if((y > screenHeight - image.getHeight())|| (y < 0)){
                yVelocity *= -1;
            }
        }
    }

    public void resetPosition(){
        x = screenWidth / 2;
        y = screenHeight / 2;
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(image, x, y, null);
    }

}
