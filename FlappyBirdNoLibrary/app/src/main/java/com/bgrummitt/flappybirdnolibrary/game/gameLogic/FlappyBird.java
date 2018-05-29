package com.bgrummitt.flappybirdnolibrary.game.gameLogic;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;

import com.bgrummitt.flappybirdnolibrary.R;

public class FlappyBird {

    private static final String TAG = FlappyBird.class.getSimpleName();
    final static private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    final static private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

    private Bird bird;
    private Pipes pipeOne;
    private Pipes pipeTwo;
    private Bitmap background;
    private Bitmap floor;
    private int touch = 0;
    private int floorX = 0;

    public FlappyBird(Resources resources){

        background = BitmapFactory.decodeResource(resources, R.drawable.bg);
        background = Bitmap.createScaledBitmap(background, screenWidth, screenHeight, true);
        floor = BitmapFactory.decodeResource(resources, R.drawable.floor);
        floor = Bitmap.createScaledBitmap(floor, screenWidth + (int)(screenWidth / 13.5), (screenHeight / 10), true);

        Bitmap birdBMP1 = BitmapFactory.decodeResource(resources, R.drawable.bird);
        Bitmap birdBMP2 = BitmapFactory.decodeResource(resources, R.drawable.bird2);

        birdBMP1 =  Bitmap.createScaledBitmap(birdBMP1, screenWidth / 9, screenHeight / 20, true);
        birdBMP2 =  Bitmap.createScaledBitmap(birdBMP2, screenWidth / 9, screenHeight / 20, true);

        bird = new Bird(birdBMP1, birdBMP2);

        Bitmap pipeTop = BitmapFactory.decodeResource(resources, R.drawable.toptube);
        Bitmap pipeBottom = BitmapFactory.decodeResource(resources, R.drawable.bottomtube);
        pipeTop = Bitmap.createScaledBitmap(pipeTop, (screenWidth / 10), (screenHeight / 3), true);
        pipeBottom = Bitmap.createScaledBitmap(pipeBottom, (screenWidth / 10), (screenHeight / 3), true);
        pipeOne = new Pipes(pipeTop, pipeBottom, screenWidth, (screenWidth / 540));
        pipeTwo = new Pipes(pipeTop, pipeBottom, screenWidth + 500, (screenWidth / 540));
    }

    public void startGame(){
        bird.startFlapping();
    }

    public void screenClicked(){
        if(touch++ == 0){
            startGame();
        }else {
            bird.flap();
        }
    }

    public void update(){
        bird.update();
        pipeOne.update();
        pipeTwo.update();
        //For making the floor infinitely scroll
        floorX -= (screenWidth / 540);
        if(floorX <= -(screenWidth / 13.5)){
            floorX = 0;
        }
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(background, 0, 0, null);
        canvas.drawBitmap(floor, floorX, (screenHeight - screenHeight / 10), null);
        pipeOne.draw(canvas);
        pipeTwo.draw(canvas);
        bird.draw(canvas);
    }

}
