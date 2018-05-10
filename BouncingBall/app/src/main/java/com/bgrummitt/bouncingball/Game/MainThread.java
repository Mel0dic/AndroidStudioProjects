package com.bgrummitt.bouncingball.Game;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

import com.bgrummitt.bouncingball.GameView;

public class MainThread extends Thread {

    private SurfaceHolder surfaceHolder;
    private GameView gameView;
    private Boolean running;
    private static Canvas canvas;

    public MainThread(SurfaceHolder surfaceHolder, GameView gameView) {

        super();
        this.surfaceHolder = surfaceHolder;
        this.gameView = gameView;

    }

    @Override
    public void run() {
        while(running){
            canvas = null;

            try{
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder){
                    this.gameView.update();
                    this.gameView.draw(canvas);
                }
            }catch (Exception e){

            }
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
