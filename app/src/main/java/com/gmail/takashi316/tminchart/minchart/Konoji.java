package com.gmail.takashi316.tminchart.minchart;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Build;
import android.os.Handler;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import com.gmail.takashi316.tminchart.R;

import java.util.ArrayList;
import java.util.Random;

public class Konoji extends View {
    private float gapInch;
    private float widthInch;
    private float xDpi;
    private float yDpi;
    private int orientation;
    private boolean touched = false;
    static Random random = new Random();
    private ArrayList<Konoji> konojis;
    Context context;

    public boolean isTouched() {
        return this.touched;
    }//isTouched

    public Konoji(Context context) {
        super(context);
        this.context = context;
        this.init();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public Konoji(Context context, float gap_inch, float width_inch, ArrayList<Konoji> konojis) {
        super(context);
        this.context = context;
        this.gapInch = gap_inch;
        this.widthInch = width_inch;
        this.konojis = konojis;
        this.init();
    }

    public Konoji(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        this.applyAttributeSet(attrs, 0);
        this.init();
    }

    public Konoji(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        this.applyAttributeSet(attrs, defStyle);
        this.init();
    }

    private void applyAttributeSet(AttributeSet attribute_set, int defStyle) {
        final TypedArray a = this.getContext().obtainStyledAttributes(
                attribute_set, R.styleable.Konoji, defStyle, 0);
        this.gapInch = a.getInt(R.styleable.Konoji_gapInch, 30);
        this.orientation = a.getInt(R.styleable.Konoji_orientation, 0);
        this.touched = a.getBoolean(R.styleable.Konoji_touched, false);
        a.recycle();
    }

    private void init() {
        final WindowManager window_manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        final Display display = window_manager.getDefaultDisplay();
        final DisplayMetrics display_metrics = new DisplayMetrics();
        display.getMetrics(display_metrics);
        this.xDpi = display_metrics.xdpi;
        this.yDpi = display_metrics.ydpi;
        this.orientation = random.nextInt(3) * 3;

        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Konoji.this.konojis != null) {
                    for (Konoji konoji : Konoji.this.konojis) {
                        konoji.touched = false;
                    }//for
                }//if
                Konoji.this.touched = true;
                Handler handler = new Handler();
                handler.post(new Runnable() {
                    public void run() {
                        for (Konoji konoji : Konoji.this.konojis) {
                            konoji.invalidate();
                        }
                        Vibrator vibrator = (Vibrator) Konoji.this.getContext().getSystemService(Context.VIBRATOR_SERVICE);
                        if (Konoji.this.touched) {
                            vibrator.vibrate(new long[]{0, 60, 60, 60}, -1);
                        } else {
                            vibrator.vibrate(new long[]{0, 100}, -1);
                        }//if
                        try {
                            ToneGenerator tone_generator = new ToneGenerator(AudioManager.STREAM_SYSTEM, ToneGenerator.MAX_VOLUME);
                            tone_generator.startTone(ToneGenerator.TONE_PROP_BEEP);
                        } catch (Exception e) {
                            // ToneGenerator is unavailable on some emulator devices.
                            Log.e(this.getClass().getSimpleName(), e.getMessage());
                        }//try
                    }//run
                });
            }//onClick
        });
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width_mode = MeasureSpec.getMode(widthMeasureSpec);
        int width_size = MeasureSpec.getSize(widthMeasureSpec);
        int height_mode = MeasureSpec.getMode(heightMeasureSpec);
        int height_size = MeasureSpec.getSize(heightMeasureSpec);

        height_size = (int) (this.widthInch * this.xDpi);
        width_size = (int) (this.widthInch * this.yDpi);
        this.setMeasuredDimension(MeasureSpec.makeMeasureSpec(width_size, width_mode),
                MeasureSpec.makeMeasureSpec(height_size, height_mode));
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.touched) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                final Drawable frame = this.getResources().getDrawable(R.drawable.frame);
                this.setBackgroundDrawable(frame);
            } else {
                final Drawable frame = this.getResources().getDrawable(R.drawable.frame);
                this.setBackground(frame);
            }//if
        } else {
            this.setBackgroundColor(Color.WHITE);
            //konoji_paint.setColor(Color.BLACK);
        }//if
        final int xgap = (int) (this.xDpi * this.gapInch);
        final int ygap = (int) (this.yDpi * this.gapInch);
        final int view_width = (int) (this.widthInch * this.xDpi);
        final int view_height = (int) (this.widthInch * this.yDpi);
        final int top_margin = (view_width - xgap * 3) / 2;
        final int left_margin = (view_width - ygap * 3) / 2;
        final Paint konoji_paint = new Paint();
        canvas.drawRect(left_margin, top_margin, xgap * 3 - 1 + left_margin, ygap * 3 - 1 + top_margin, konoji_paint);
        final Paint gap_paint = new Paint();
        gap_paint.setColor(Color.WHITE);
        switch (this.orientation) {
            case 0:
                canvas.drawRect(left_margin + xgap, top_margin, xgap * 2 - 1 + left_margin, ygap * 2 - 1 + top_margin, gap_paint);
                break;
            case 3:
                canvas.drawRect(left_margin + xgap, top_margin + ygap, xgap * 3 - 1 + left_margin, ygap * 2 - 1 + top_margin, gap_paint);
                break;
            case 6:
                canvas.drawRect(left_margin + xgap, top_margin + ygap, xgap * 2 - 1 + left_margin, ygap * 3 - 1 + top_margin, gap_paint);
                break;
            case 9:
                canvas.drawRect(left_margin, top_margin + ygap, xgap * 2 - 1 + left_margin, ygap * 2 - 1 + top_margin, gap_paint);
                break;
            default:
                canvas.drawLine(left_margin, top_margin, xgap * 3 - 1 + left_margin, ygap * 3 - 1 + ygap, gap_paint);
                canvas.drawLine(xgap * 3 - 1 + left_margin, top_margin, left_margin, ygap * 3 - 1 + ygap, gap_paint);
                break;
        }//switch
    }

    public float getGapInch() {
        return this.gapInch;
    }
}//Konoji

