package com.saadmahmud.laddershuffle.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

import static com.saadmahmud.laddershuffle.Util.showLog;

public class CircleView extends View {
    int mRadius;
    int mColor;
    Paint mPaint;

    public CircleView(Context context, int radius, int color) {
        super(context);
        mRadius = radius;
        mColor = color;

        mPaint = new Paint();
        mPaint.setColor(mColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        showLog("CircleView onDraw");
        if (mPaint == null) {
            mPaint = new Paint();
            mPaint.setColor(mColor);
        }

        canvas.drawCircle(mRadius * 2 - 5, mRadius * 2 - 5, mRadius, mPaint);
    }
}
