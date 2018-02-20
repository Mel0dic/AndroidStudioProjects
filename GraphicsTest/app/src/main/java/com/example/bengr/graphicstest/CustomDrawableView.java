package com.example.bengr.graphicstest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.view.View;

public class CustomDrawableView extends View {

    private ShapeDrawable mDrawable;

    public CustomDrawableView(Context context) {
        super(context);

        int x = 300;
        int y = 10;
        int width = 500;
        int height = 500;

        mDrawable = new ShapeDrawable(new OvalShape());
        // If the color isn't set, the shape uses black as the default.
        mDrawable.getPaint().setColor(Color.RED);
        // If the bounds aren't set, the shape can't be drawn.
        mDrawable.setBounds(x, y, x + width, y + height);

    }

    protected void onDraw(Canvas canvas) {
        mDrawable.draw(canvas);
    }

}