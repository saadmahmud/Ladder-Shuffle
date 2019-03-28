package com.saadmahmud.laddershuffle.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;

public class LineView extends View {

    public static final int THICKNESS = 30;
    Paint mPaint;
    Rect mRect;

    enum VLineProperty {
        Width(), Height
    }

    public LineView(Context context, Rect rect) {
        super(context);
        mRect = rect;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mPaint == null) {
            mPaint = new Paint();
        }
        mPaint.setColor(Color.GRAY);

        canvas.drawRect(mRect, mPaint);

        Log.d("LineView", "getWidth " + getWidth() + ", getHeight " + getHeight());

    }

}
