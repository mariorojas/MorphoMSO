package com.morpho.demo.customs;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by alex on 03/07/2015.
 */
public class SingantureView extends View {

    private Bitmap DrawBitmap;
    private Canvas mCanvas;
    private Path mPath;
    private Paint DrawBitmapPaint;
    private Paint mPaint;

    @SuppressWarnings("deprecation")
    public SingantureView(Activity c) {

        super(c);
        Display Disp = c.getWindowManager().getDefaultDisplay();
        DrawBitmap = Bitmap.createBitmap(Disp.getWidth(), Disp.getHeight(),
                Bitmap.Config.ARGB_4444);

        mCanvas = new Canvas(DrawBitmap);

        mPath = new Path();
        DrawBitmapPaint = new Paint(Paint.DITHER_FLAG);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        setDrawingCacheEnabled(true);
        canvas.drawBitmap(DrawBitmap, 0, 0, DrawBitmapPaint);
        canvas.drawPath(mPath, mPaint);
        canvas.drawRect(mY, 0, mY, 0, DrawBitmapPaint);
    }

    private float mX, mY;
    private static final float TOUCH_TOLERANCE = 4;

    private void touch_start(float x, float y) {
        mPath.reset();
        mPath.moveTo(x, y);
        mX = x;
        mY = y;
    }

    private void touch_move(float x, float y) {
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);
        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
            mX = x;
            mY = y;
        }
    }

    private void touch_up() {
        mPath.lineTo(mX, mY);

        mCanvas.drawPath(mPath, mPaint);

        mPath.reset();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touch_start(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                touch_move(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                touch_up();
                invalidate();
                break;
        }
        return true;
    }

}
