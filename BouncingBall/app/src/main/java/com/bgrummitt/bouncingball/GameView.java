package com.bgrummitt.bouncingball;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.bgrummitt.bouncingball.Game.MainThread;

public class GameView extends SurfaceView implements SurfaceHolder.Callback{

    private MainThread thread;

    public GameView(Context context) {
        super(context);


        getHolder().addCallback(this);

        //Create new thread class with ???? and context
        thread = new MainThread(getHolder(), this);
        //Keep the inputs on this thread and not on the new one
        setFocusable(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
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

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }
}
