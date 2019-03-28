package com.saadmahmud.laddershuffle.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

public class UserView extends CircleView {
    public UserView(Context context, int radius, int color) {
        super(context, radius, color);

        mPaint = new Paint();
        mPaint.setColor(mColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        if (mPaint == null) {
            mPaint = new Paint();
        }
        mPaint.setColor(mColor);

        canvas.drawCircle(mRadius * 2 - 5, mRadius * 2 - 5, mRadius, mPaint);
    }
}
