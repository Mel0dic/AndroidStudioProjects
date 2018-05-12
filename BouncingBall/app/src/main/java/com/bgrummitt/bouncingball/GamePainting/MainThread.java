package com.bgrummitt.bouncingball.GamePainting;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

public class MainThread extends Thread {

    private final String TAG = MainThread.class.getSimpleName();

    private SurfaceHolder surfaceHolder;
    private GameView gameView;
    private Boolean running;
    private static Canvas canvas;
    private int targetFPS = 60;
    private double averageFPS;

    public MainThread(SurfaceHolder surfaceHolder, GameView gameView) {
        //super the thread class
        super();

        this.surfaceHolder = surfaceHolder;
        this.gameView = gameView;

    }

    //Main game loop
    @Override
    public void run() {

        long startTime;
        long timeMillis;
        long waitTime;
        long totalTime = 0;
        int frameCount = 0;
        long targetTime = 1000 / targetFPS;

        while(running){

            startTime = System.nanoTime();

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
            }catch (Exception e){ } finally {
                //Finally will make sure this code executes even if there is an exception
                if(canvas != null){
                    try{
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }

            timeMillis = (System.nanoTime() - startTime) / 1000000;
            waitTime = targetTime - timeMillis;

            try {
                this.sleep(waitTime);
            } catch (Exception e) {}

            totalTime += System.nanoTime() - startTime;
            frameCount++;
            if (frameCount == targetFPS)        {
                averageFPS = 1000 / ((totalTime / frameCount) / 1000000);
                frameCount = 0;
                totalTime = 0;
                Log.v(TAG, Double.toString(averageFPS));
            }

        }
        super.run();
    }

    public void setRunning(boolean isRunning){
        this.running = isRunning;
    }

}
