package com.bgrummitt.flappybirdnolibrary.game.gameUi;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.bgrummitt.flappybirdnolibrary.game.gameLogic.FlappyBird;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private static final String TAG = GameView.class.getSimpleName();

    private GameThread thread;
    private FlappyBird game;

    /**
     * Constructor
     * @param context where the activity intent was started
     */
    public GameView(Context context) {
        super(context);

        //This allows you intercept events
        getHolder().addCallback(this);
    }

    /**
     * This is called immediately after the surface is first created.
     * Implementations of this should start up whatever rendering code
     * they desire.  Note that only one thread can ever draw into
     * a {@link Surface}, so you should not draw into the Surface here
     * if your normal rendering will be in another thread.
     *
     * @param holder The SurfaceHolder whose surface is being created.
     */
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        //Create new thread class with the SurfaceHolder and context
        thread = new GameThread(getHolder(), this);
        //Keep the inputs on this thread and not on the new one
        setFocusable(true);

        game = new FlappyBird(getResources());

        //Start the games update infinite loop
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}

    /**
     * This is called immediately before a surface is being destroyed. After
     * returning from this call, you should no longer try to access this
     * surface.  If you have a rendering thread that directly accesses
     * the surface, you must ensure that thread is no longer touching the
     * Surface before returning from this function.
     *
     * @param holder The SurfaceHolder whose surface is being destroyed.
     */
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        //To stop the thread it may take a few attempts so we create a while loop
        boolean retry = true;
        while(retry){
            try {
                thread.setRunning(false);
                thread.join();
            } catch(InterruptedException e){
                e.printStackTrace();
            }
            retry = false;
        }
    }

    /**
     * Update function for updating the game every loop
     */
    public void update(){
        game.update();
    }

    /**
     * Override on touch event to intercept any screen interaction that takes place
     * @param event the event that takes place
     * @return the super so we don't get another event on release
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //Do something with screen touched
        game.screenClicked();
        return super.onTouchEvent(event);
    }

    /**
     * Where any the game's graphics are drawn
     * @param canvas
     */
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        //If the canvas is not empty draw the game
        if(canvas != null){
            canvas.drawColor(Color.WHITE);
            game.draw(canvas);
        }
    }

}
