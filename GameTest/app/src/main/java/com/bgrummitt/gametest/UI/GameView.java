package com.bgrummitt.gametest.UI;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;

import com.bgrummitt.gametest.Game.GameLogic;

public class GameView extends View {

    private GameLogic gameLogic;

    public GameView(Context context) {
        super(context);
        gameLogic = new GameLogic(context);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawColor(Color.YELLOW);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        return true;
    }
}
