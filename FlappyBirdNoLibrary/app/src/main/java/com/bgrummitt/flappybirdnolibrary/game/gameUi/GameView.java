package com.bgrummitt.flappybirdnolibrary.game.gameUi;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.bgrummitt.flappybirdnolibrary.game.gameLogic.FlappyBird;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private FlappyBird game;

    public GameView(Context context) {
        super(context);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    public void update(){
        game.update();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //Do something with screen touched
        return true;
    }



    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        if(canvas != null){
            canvas.drawColor(Color.WHITE);
            game.draw(canvas);
        }
    }

}
