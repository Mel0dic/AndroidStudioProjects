package com.bgrummitt.gametest.Game;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import com.bgrummitt.gametest.R;

public class Bird {

    private Drawable birdDraw;
    private int birdX;
    private int birdY;

    public Bird(Context context) {
        Resources res = context.getResources();

        this.birdDraw = res.getDrawable(R.drawable.button);
        this.birdDraw.setBounds(50, 50, 50, 50);
    }

}