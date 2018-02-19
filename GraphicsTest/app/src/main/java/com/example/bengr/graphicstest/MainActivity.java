package com.example.bengr.graphicstest;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    CustomDrawableView mCustomDrawableView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCustomDrawableView = new CustomDrawableView(this);
        setContentView(mCustomDrawableView);

        Bitmap b = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        Paint p = new Paint();
        p.setStyle(Paint.Style.FILL);
        p.setColor(Color.BLACK);

        c.drawCircle(50, 50, 2000, p);
    }
}
