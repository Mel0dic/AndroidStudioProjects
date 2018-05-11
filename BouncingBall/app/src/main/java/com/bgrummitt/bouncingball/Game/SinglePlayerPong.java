package com.bgrummitt.bouncingball.Game;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.bgrummitt.bouncingball.GameView.GameView;
import com.bgrummitt.bouncingball.R;

public class SinglePlayerPong {

    private static final String TAG = GameView.class.getSimpleName();

    private Ball ball;
    private Padel padel;

    public SinglePlayerPong(Resources resources){

        ball = new Ball(BitmapFactory.decodeResource(resources, R.drawable.ball), 100, 100);

        padel = new Padel(BitmapFactory.decodeResource(resources, R.drawable.padel));

    }

    public void screenTouched(float x, float y){
        padel.moveTo(x, true);
    }

    public void screenReleased(float x, float y){
        padel.moveTo(x, false);
    }

    public void draw(Canvas canvas){
        ball.draw(canvas);
        padel.draw(canvas);
    }

    public void update(){
        ball.update();
        padel.update();
    }

}
