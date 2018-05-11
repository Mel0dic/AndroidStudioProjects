package com.bgrummitt.bouncingball.GameView;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.bgrummitt.bouncingball.Game.Ball;
import com.bgrummitt.bouncingball.R;

//Surface view holds the canvas
public class GameView extends SurfaceView implements SurfaceHolder.Callback{

    private MainThread thread;
    private Ball ball;

    public GameView(Context context) {
        super(context);

        //This allows you intercept events
        getHolder().addCallback(this);

        //Create new thread class with the SurfaceHolder and context
        thread = new MainThread(getHolder(), this);
        //Keep the inputs on this thread and not on the new one
        setFocusable(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        ball = new Ball(BitmapFactory.decodeResource(getResources(), R.drawable.ball), 100, 100);

        //Start the games update infinite loop
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        //To stop the process it may take a few attempts
        boolean retry = true;
        while(retry){
            try{
                thread.setRunning(false);
                thread.join();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            retry = false;
        }
    }

    public void update(){
        ball.update();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        ball.resetPosition();
        return super.onTouchEvent(event);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        if(canvas != null){
            canvas.drawColor(Color.WHITE);
            ball.draw(canvas);
        }
    }
}
