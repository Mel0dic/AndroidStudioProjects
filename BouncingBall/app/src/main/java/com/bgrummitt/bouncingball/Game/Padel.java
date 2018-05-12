package com.bgrummitt.bouncingball.Game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

public class Padel {

    final private static String TAG = Padel.class.getSimpleName();

    final static private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    final static private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

    private Bitmap image;
    private int x;
    private int y;
    private int padelSpeed;
    private int padelDirection;
    private Boolean movePadel = false;
    private int imageWidth;
    private int imageHeight;
    private int wallSize;

    public Padel(Bitmap img, int wallSize){
        image = img;
        imageWidth = image.getWidth();
        imageHeight = image.getHeight();
        x = (screenWidth / 2) - (imageWidth / 2);
        y = (screenHeight - 50);
        padelDirection = 1;
        padelSpeed = 10;
        this.wallSize = wallSize;
    }

    public void moveTo(float moveTo, Boolean toMove){
        movePadel = toMove;
        if(moveTo <= screenWidth / 2){
            padelDirection = -1;
        }else{
            padelDirection = 1;
        }
    }

    public void update(){
        if((x > 0) && ((x < screenWidth - imageWidth) && movePadel)){
            x += (padelSpeed * padelDirection);
        }else if(x <= 0 && padelDirection == 1 && movePadel){
            x += (padelSpeed * padelDirection);
        }else if((((x - 4) >= screenWidth - imageWidth)) && padelDirection == -1 && movePadel){
            x += (padelSpeed * padelDirection);
        }
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(image, x, y, null);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getImageWidth(){
        return imageWidth;
    }

    public int getImageHeight(){
        return imageHeight;
    }

}
