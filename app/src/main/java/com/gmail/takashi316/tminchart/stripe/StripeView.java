package com.gmail.takashi316.tminchart.stripe;

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

import com.gmail.takashi316.tminchart.DisplayDpi;
import com.gmail.takashi316.tminchart.R;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Logger;


/**
 * TODO: document your custom view class.
 */
public class StripeView extends View {

    private int backgroundColor;
    private float gapInch;
    private float width_inch;
    private float xdpi;
    private float ydpi;
    private boolean horizontal;
    private boolean vertical;
    private int contrast;
    private boolean touched = false;
    static Random random = new Random();
    private ArrayList<StripeView> exclusiveStripeViews;
    private DisplayDpi displayDpi;

    public boolean isTouched() {
        return touched;
    }//isTouched

    public StripeView(Context context) {
        super(context);
        init(context, null, 0);
    }//StripeViewConstructor

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public StripeView(Context context, float gap_inch, float width_inch, ArrayList<StripeView> exclusive_stripe_views) {
        super(context);
        init(context, null, 0);
        this.gapInch = gap_inch;
        this.width_inch = width_inch;
        this.exclusiveStripeViews = exclusive_stripe_views;
    }//StripeView constructor

    public StripeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }//StripeView constructor

    public StripeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }//StripeView constructor

    private void init(Context context, AttributeSet attrs, int defStyle) {
        this.displayDpi = new DisplayDpi(context);
        this.xdpi = this.displayDpi.getXdpi();
        this.ydpi = this.displayDpi.getYdpi();
        if (this.xdpi == 0.0f || this.ydpi == 0.0f) {
            //throw new RuntimeException("Can't get display DPI");
            this.xdpi = 100;
            this.ydpi = 100;
        }//if

        // Load attributes
        final TypedArray typed_array = getContext().obtainStyledAttributes(
                attrs, R.styleable.StripeView, defStyle, 0);

        this.backgroundColor = typed_array.getColor(R.styleable.StripeView_background, Color.BLUE);
        this.contrast = typed_array.getInt(R.styleable.StripeView_contrast, 255);
        this.gapInch = typed_array.getFloat(R.styleable.StripeView_gapInch, 0.1f);
        this.horizontal = typed_array.getBoolean(R.styleable.StripeView_horizontal, true);
        this.vertical = typed_array.getBoolean(R.styleable.StripeView_vertical, true);
        this.touched = typed_array.getBoolean(R.styleable.StripeView_touched, false);
        typed_array.recycle();

        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final View this_stripe_view = v;
                if (exclusiveStripeViews != null) {
                    for (StripeView other_stripe_view : exclusiveStripeViews) {
                        other_stripe_view.touched = false;
                    }//for
                }//if
                touched = true;
                //v.invalidate();
                Handler handler = new Handler();
                handler.post(new Runnable() {
                    public void run() {
                        try {
                            for (StripeView stripe_view : exclusiveStripeViews) {
                                //if (stripe_view != this_stripe_view) {
                                stripe_view.invalidate();
                                //}//if
                            }//for
                        } catch (NullPointerException e) {
                        }//try*/
                        Vibrator vibrator = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
                        if (touched) {
                            vibrator.vibrate(new long[]{0, 60, 60, 60}, -1);
                        } else {
                            vibrator.vibrate(new long[]{0, 100}, -1);
                        }//if
                        try {
                            ToneGenerator tone_generator = new ToneGenerator(AudioManager.STREAM_SYSTEM, ToneGenerator.MAX_VOLUME);
                            tone_generator.startTone(ToneGenerator.TONE_PROP_BEEP);
                        } catch (Exception e) {
                            // ToneGenerator is unavailable on some emulator devices.
                            Log.e(getClass().getSimpleName(), e.getMessage());
                        }//try
                        //this_stripe_view.invalidate();
                    }//run
                });
            }//onClick
        });
    }//init


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width_mode = MeasureSpec.getMode(widthMeasureSpec);
        int width_size = MeasureSpec.getSize(widthMeasureSpec);
        int height_mode = MeasureSpec.getMode(heightMeasureSpec);
        int height_size = MeasureSpec.getSize(heightMeasureSpec);

        //this.displayDpi = new DisplayDpi(getContext());

        if (width_mode == MeasureSpec.UNSPECIFIED) {
            // it corresponds to setting length to  1000000px on layout designer
            Log.d(this.getClass().getSimpleName(), "width_mode = MeasureSpec.UNSPECIFIED");
            //width_size = 100;
            width_size = (int) (getResources().getDisplayMetrics().xdpi * this.width_inch);
        } else if (width_mode == MeasureSpec.EXACTLY) {
            //it corresponds to setting length to 10px or match_parent on layout designer
            Log.d(this.getClass().getSimpleName(), "width_mode = MeasureSpec.EXACTLY");
            //width_size = 200;
        } else if (width_mode == MeasureSpec.AT_MOST) {
            //it corresponds to setting length to wrap_content on layout designer
            Log.d(this.getClass().getSimpleName(), "width_mode = MeasureSpec.AT_MOST");
            //width_size = 300;
            width_size = (int) (getResources().getDisplayMetrics().xdpi);
        } else {
            Log.d(this.getClass().getSimpleName(), "width_mode is unknown");
            width_size = 400;
        }//if

        if (height_mode == MeasureSpec.UNSPECIFIED) {
            Log.d(this.getClass().getSimpleName(), "height_mode = MeasureSpec.UNSPECIFIED");
            //height_size = 100;
            height_size = (int) (getResources().getDisplayMetrics().ydpi * this.width_inch);
        } else if (height_mode == MeasureSpec.EXACTLY) {
            Log.d(this.getClass().getSimpleName(), "height_mode = MeasureSpec.EXACTLY");
        } else if (height_mode == MeasureSpec.AT_MOST) {
            Log.d(this.getClass().getSimpleName(), "height_mode = MeasureSpec.AT_MOST");
            height_size = (int) (getResources().getDisplayMetrics().ydpi);
        } else {
            Log.d(this.getClass().getSimpleName(), "height_mode is unknown");
            height_size = 400;
        }//if
        Log.d(this.getClass().getSimpleName(), "width_size=" + width_size + " height_size=" + height_size);
        setMeasuredDimension(MeasureSpec.makeMeasureSpec(width_size, width_mode),
                MeasureSpec.makeMeasureSpec(height_size, height_mode));
    }//onMeasure


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        final int xgap = (int) (xdpi * gapInch);
        final int ygap = (int) (ydpi * gapInch);
        final int horizontal_gaps = canvas.getWidth() / xgap;
        final int vertical_gaps = canvas.getHeight() / ygap;

        final Paint black_paint = new Paint();
        black_paint.setColor(Color.BLACK);
        black_paint.setStyle(Paint.Style.FILL_AND_STROKE);
        black_paint.setStrokeWidth(1);

        final Paint white_paint = new Paint();
        white_paint.setColor(Color.WHITE);
        white_paint.setStyle(Paint.Style.FILL_AND_STROKE);
        white_paint.setStrokeWidth(1);

        if (this.horizontal) {
            for (int i = 1; i < horizontal_gaps; ++i) {
                if (i % 2 == 0) {
                    int left = xgap * i;
                    int top = xgap;
                    int right = xgap * (i + 1) - 1;
                    int bottom = canvas.getHeight() - 50;
                    Log.d(this.getClass().getSimpleName(), "horizontal rectangle. left=" + left + " top=" + top + " right=" + right + " bottom=" + bottom);
                    canvas.drawRect(left, top, right, bottom, black_paint);
                } else {
                    int left = xgap * i;
                    int top = xgap;
                    int right = xgap * (i + 1) - 1;
                    int bottom = canvas.getHeight() - 50;
                    Log.d(this.getClass().getSimpleName(), "horizontal rectangle. left=" + left + " top=" + top + " right=" + right + " bottom=" + bottom);
                    canvas.drawRect(left, top, right, bottom, white_paint);
                }
            }//for
        }//if

        if (this.vertical) {
            for (int i = 1; i < vertical_gaps; ++i) {
                if (i % 2 == 0) {
                    final int left = 1;
                    final int right = ygap * i;
                    final int top = canvas.getWidth() - 1;
                    final int bottom = ygap * (i + 1) - 1;
                    canvas.drawRect(left, top, right, bottom, black_paint);
                } else {
                    final int left = 1;
                    final int right = ygap * i;
                    final int top = canvas.getWidth() - 1;
                    final int bottom = ygap * (i + 1) - 1;
                    canvas.drawRect(left, top, right, bottom, white_paint);
                }//if
            }//for
        }//if

        if (touched) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                final Drawable frame = getResources().getDrawable(R.drawable.frame);
                this.setBackgroundDrawable(frame);
            } else {
                final Drawable frame = getResources().getDrawable(R.drawable.frame);
                this.setBackground(frame);
            }//if
        } else {
            this.setBackgroundColor(Color.BLACK);
        }//if

    }//onDraw

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }//onLayout

    private void fillCanvas(Canvas canvas, Paint paint) {
        canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), paint);
    }//fillCanvas

    public float getGapInch() {
        return this.gapInch;
    }
}//class StripeView

