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

    public Padel(Bitmap img){
        image = img;
        x = screenWidth / 2;
        y = (screenHeight - 50);
        padelDirection = 1;
        padelSpeed = 10;
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
//        Log.d(TAG, Integer.toString(screenWidth - (image.getHeight() * 2)));
//        Log.d(TAG, Integer.toString(x));
        if((x > 0) && (x < (screenWidth - (image.getHeight() * 2))) && movePadel){
            x += (padelSpeed * padelDirection);
        }else if(x == 0 && padelDirection == 1 && movePadel){
            x += (padelSpeed * padelDirection);
        }else if(((x - 8) == (screenWidth - (image.getHeight() * 2))) && padelDirection == -1 && movePadel){
            x += (padelSpeed * padelDirection);
        }
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(image, x, y, null);
    }


}
