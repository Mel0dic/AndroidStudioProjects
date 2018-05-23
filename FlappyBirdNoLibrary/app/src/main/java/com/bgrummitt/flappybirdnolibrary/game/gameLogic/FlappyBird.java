package com.bgrummitt.flappybirdnolibrary.game.gameLogic;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.bgrummitt.flappybirdnolibrary.R;

public class FlappyBird {

    private static final String TAG = FlappyBird.class.getSimpleName();
    final static private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    final static private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

    private Bird bird;
    private Bitmap background;

    public FlappyBird(Resources resources){

        background = BitmapFactory.decodeResource(resources, R.drawable.bg);
        background = Bitmap.createScaledBitmap(background, screenWidth, screenHeight, true);

        Bitmap birdBMP1 = BitmapFactory.decodeResource(resources, R.drawable.bird);
        Bitmap birdBMP2 = BitmapFactory.decodeResource(resources, R.drawable.bird);

        birdBMP1 =  Bitmap.createScaledBitmap(birdBMP1, birdBMP1.getWidth() / 5, birdBMP1.getHeight() / 5, true);
        birdBMP2 =  Bitmap.createScaledBitmap(birdBMP2, birdBMP2.getWidth() / 5, birdBMP2.getHeight() / 5, true);

        bird = new Bird(birdBMP1, birdBMP2);
    }

    public void update(){

    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(background, 0, 0, null);
        bird.draw(canvas);
    }

}
