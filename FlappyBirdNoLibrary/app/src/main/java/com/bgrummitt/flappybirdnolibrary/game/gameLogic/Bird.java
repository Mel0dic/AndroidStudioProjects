package com.bgrummitt.flappybirdnolibrary.game.gameLogic;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

public class Bird {

    final static private String TAG = Bird.class.getSimpleName();
    final static private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    final static private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
    final private int jumpSpeed;
    final private int gravity;
    private Bitmap birdImage;
    private Bitmap birdImageFlap;
    private int x;
    private int y;
    private int birdDir;
    private boolean isInJump = false;

    public Bird(Bitmap bird1, Bitmap bird2){
        birdImage = bird1;
        birdImageFlap = bird2;
        //Set x and y to be half the screen and move it to the center of the Bitmap
        x = screenWidth / 2 - (bird1.getWidth() / 2);
        y = screenHeight / 2 - (bird1.getHeight() / 2);
        //Set the speed of the fall and the speed of the flap
        jumpSpeed = (screenHeight / -96);
        gravity = (screenHeight / 192);
        birdDir = 0;
    }

    private long startTime;

    public void update(){
        if(isInJump && (System.currentTimeMillis() - startTime) >= 200){
            birdDir = gravity;
            isInJump = false;
        }
        y += birdDir;
    }

    public void flap(){
        //If the bird is not in mid flap
        if(!isInJump) {
            birdDir = jumpSpeed;
            startTime = System.currentTimeMillis();
            isInJump = true;
        }
    }

    public void startFlapping(){
        birdDir = gravity;
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(birdImage, x, y, null);
    }

}
