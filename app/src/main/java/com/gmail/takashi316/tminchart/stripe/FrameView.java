package com.gmail.takashi316.tminchart.stripe;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.View;

public class FrameView extends View {

    protected FrameView(Context context) {
        super(context);
    }

    protected FrameView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected FrameView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    protected void drawFrame(Canvas canvas, int color) {
        Paint paint = new Paint();
        paint.setColor(color);
        this.drawFrame(canvas, paint);
    }//drawFrame

    protected void drawFrame(Canvas canvas, Paint paint) {
        canvas.drawRect(0, 0, this.getPaddingLeft(), this.getHeight() - 1, paint);
        canvas.drawRect(0, 0, this.getWidth() - 1, this.getPaddingTop(), paint);
        canvas.drawRect(this.getWidth() - this.getPaddingRight(), 0, this.getWidth() - 1, this.getHeight() - 1, paint);
        canvas.drawRect(0, this.getHeight() - this.getPaddingBottom(), this.getWidth() - 1, this.getHeight() - 1, paint);
    }//drawFrame

    protected void eraseFrame(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.TRANSPARENT);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        drawFrame(canvas, paint);
    }//drawFrame

}//FrameView
