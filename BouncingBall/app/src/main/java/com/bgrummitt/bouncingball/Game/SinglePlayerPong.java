package com.bgrummitt.bouncingball.Game;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.bgrummitt.bouncingball.GamePainting.GameView;
import com.bgrummitt.bouncingball.R;

public class SinglePlayerPong {

    private static final String TAG = GameView.class.getSimpleName();
    final static private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    final static private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

    private Ball ball;
    private Padel padel;
    private int wallSize;
    private Rect leftWall;
    private Rect rightWall;
    private Rect topWall;
    private Paint paint;

    public SinglePlayerPong(Resources resources){

        wallSize = screenWidth / 100;

        ball = new Ball(BitmapFactory.decodeResource(resources, R.drawable.ball), 100, 100, wallSize);

        padel = new Padel(BitmapFactory.decodeResource(resources, R.drawable.padel), wallSize);

        leftWall = new Rect(0, 0, wallSize, screenHeight);
        rightWall = new Rect(screenWidth - wallSize, 0, screenWidth, screenHeight);
        topWall = new Rect(0, 0, screenWidth, wallSize);

        paint = new Paint();
        paint.setColor(Color.BLUE);

    }

    public void screenTouched(float x, float y){
        padel.moveTo(x, true);
    }

    public void screenReleased(float x, float y){
        padel.moveTo(x, false);
    }

    public void draw(Canvas canvas){

        //Draw the walls
        canvas.drawRect(leftWall, paint);
        canvas.drawRect(rightWall, paint);
        canvas.drawRect(topWall, paint);

        ball.draw(canvas);
        padel.draw(canvas);
    }

    public void update(){
        ball.hitPadel(padel);

        ball.update();
        padel.update();
    }

}
