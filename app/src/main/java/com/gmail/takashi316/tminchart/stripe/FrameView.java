package com.gmail.takashi316.tminchart.stripe;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.util.Log;
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
        paint.setColor(Color.WHITE);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        drawFrame(canvas, paint);
    }//drawFrame

    static Vibrator vibrator;

    public void vibrateTwice() {
        if (vibrator == null) {
            this.vibrator = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
        }
        vibrator.vibrate(new long[]{0, 60, 60, 60}, -1);
    }//vibrateTwice

    public void vibrateOnce() {
        if (vibrator == null) {
            vibrator.vibrate(new long[]{0, 100}, -1);
        }//if
    }//vibrateOnce

    static ToneGenerator toneGenerator;

    public static void beep() {
        try {
            toneGenerator.startTone(ToneGenerator.TONE_PROP_BEEP);
        } catch (Exception e) {
            // ToneGenerator is unavailable on some emulator devices.
        }//try
    }//beep

}//FrameView
