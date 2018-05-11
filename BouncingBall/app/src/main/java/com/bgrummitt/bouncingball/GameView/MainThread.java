package com.bgrummitt.bouncingball.GameView;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

import com.bgrummitt.bouncingball.GameView.GameView;

public class MainThread extends Thread {

    private SurfaceHolder surfaceHolder;
    private GameView gameView;
    private Boolean running;
    private static Canvas canvas;

    public MainThread(SurfaceHolder surfaceHolder, GameView gameView) {
        //super the thread class
        super();

        this.surfaceHolder = surfaceHolder;
        this.gameView = gameView;

    }

    //Main game loop
    @Override
    public void run() {
        while(running){
            canvas = null;

            try{
                //Canvas must be locked to prevent multiple threads drawing at one point
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder){
                    //Update element positions
                    this.gameView.update();
                    //Draw the new images
                    this.gameView.draw(canvas);
                }
            }catch (Exception e){ }
            //Finally will make sure this code executes even if there is an exception
            finally {
                if(canvas != null){
                    try{
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }
        super.run();
    }

    public void setRunning(boolean isRunning){
        this.running = isRunning;
    }

}
