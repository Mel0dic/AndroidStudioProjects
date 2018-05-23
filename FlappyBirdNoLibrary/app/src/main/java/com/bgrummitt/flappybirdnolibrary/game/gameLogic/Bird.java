package com.bgrummitt.flappybirdnolibrary.game.gameLogic;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Bird {

    final static private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    final static private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
    private Bitmap birdImage;
    private Bitmap birdImageFlap;
    private int x;
    private int y;

    public Bird(Bitmap bird1, Bitmap bird2){
        birdImage = bird1;
        birdImageFlap = bird2;
        x = screenWidth / 2 - (bird1.getWidth() / 2);
        y = screenHeight / 2 - (bird1.getHeight() / 2);
    }

    public void update(){

    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(birdImage, x, y, null);
    }

}
