package com.bgrummitt.flappybirdnolibrary.game.gameLogic;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.Log;

public class Bird {

    final static private String TAG = Bird.class.getSimpleName();
    final static private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    final static private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
    final private int jumpSpeed;
    final private int gravity;
    private int frame;
    private Bitmap birdImage;
    private Bitmap birdImageFlap;
    private int x;
    private int y;
    private int birdDir;
    private boolean isInJump = false;
    private boolean birdPic = true;

    /**
     * Bird constructor
     * @param bird1 The 1st bird pic
     * @param bird2 The 2nd bird pic
     */
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
        //If the bird has been flapping for more than 200 milliseconds
        if(isInJump && (System.currentTimeMillis() - startTime) >= 200){
            birdDir = gravity;
            isInJump = false;
            birdImage = RotateBitmap(birdImage, 90);
            birdImageFlap = RotateBitmap(birdImageFlap, 90);
        }
        y += birdDir;
        //Flip the birdPic boolean
        if(frame > 10) {
            birdPic = !birdPic;
            frame = 0;
        }else{
            frame++;
        }
    }

    public void flap(){
        //If the bird is not in mid flap
        if(!isInJump) {
            birdDir = jumpSpeed;
            startTime = System.currentTimeMillis();
            isInJump = true;
            birdImage = RotateBitmap(birdImage, 270);
            birdImageFlap = RotateBitmap(birdImageFlap, 270);
        }
    }

    public void startFlapping(){
        birdDir = gravity;
        birdImage = RotateBitmap(birdImage, 45);
        birdImageFlap = RotateBitmap(birdImageFlap, 45);
    }

    public void draw(Canvas canvas){
        //Draw the bird and depending on birdPic boolean draw birdImage or birdImageFlap
        canvas.drawBitmap(birdPic ? birdImage : birdImageFlap, x, y, null);
    }

    /**
     * Function to rotate the bitmap a set number of degrees
     * @param source the image to rotate
     * @param angle the angle at which to rotate
     * @return the new bitmap rotated to the angle
     */
    public static Bitmap RotateBitmap(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

}
