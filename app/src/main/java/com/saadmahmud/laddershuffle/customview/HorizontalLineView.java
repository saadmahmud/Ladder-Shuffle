package com.saadmahmud.laddershuffle.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;

public class HorizontalLineView extends View {
    public HorizontalLineView(Context context) {
        super(context);
    }

    Paint paint;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (paint == null)
            paint = new Paint();

        paint.setColor(Color.GRAY);

        canvas.drawRect(10, 10, 200, 30, paint);

        Log.d("Horizontal LineView", "getWidth " + getWidth() + ", getHeight " + getHeight());

    }

}
