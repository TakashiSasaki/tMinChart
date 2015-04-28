package com.gmail.takashi316.tminchart.stripe;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.gmail.takashi316.tminchart.DisplayDpi;
import com.gmail.takashi316.tminchart.R;

import java.util.ArrayList;


public class StripeView extends FrameView {

    private float gapInch;
    private float width_inch;
    private float xdpi;
    private float ydpi;
    private boolean horizontal = false;
    private boolean vertical = false;
    private boolean touched = false;
    private ArrayList<StripeView> stripeViews;
    private DisplayDpi displayDpi;
    private int foregroundColor = Color.BLACK;
    private int backgroundColor = Color.WHITE;

    public boolean isTouched() {
        return touched;
    }//isTouched

    public StripeView(Context context) {
        super(context);
        init(context, null, 0);
    }//StripeViewConstructor

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

        this.setGapInch(typed_array.getFloat(R.styleable.StripeView_gapInch, 0.1f));
        this.setHorizontal(typed_array.getBoolean(R.styleable.StripeView_horizontal, true));
        this.setVertical(typed_array.getBoolean(R.styleable.StripeView_vertical, true));
        this.touched = typed_array.getBoolean(R.styleable.StripeView_touched, false);
        typed_array.recycle();

        this.setOnClickListener(new OnClickListener() {

            //@Override
            public void onClick_(View v) {
                final View this_stripe_view = v;
                if (stripeViews != null) {
                    for (StripeView other_stripe_view : stripeViews) {
                        other_stripe_view.touched = false;
                    }//for
                }//if
                touched = true;
                //v.invalidate();
                Handler handler = new Handler();
                handler.post(new Runnable() {
                    public void run() {
                        try {
                            for (StripeView stripe_view : stripeViews) {
                                //if (stripe_view != this_stripe_view) {
                                //stripe_view.invalidate();
                                //}//if
                            }//for
                        } catch (NullPointerException e) {
                        }//try*/

                        if (touched) {
                            vibrateTwice();
                        } else {
                            vibrateOnce();
                        }//if
                        beep();
                        //this_stripe_view.invalidate();
                    }//run
                });
            }//onClick

            @Override
            public void onClick(View view) {

            }
        });
    }//init

    public void setStripeViews(ArrayList<StripeView> stripe_views) {
        this.stripeViews = stripe_views;
    }//setStripeViews

    public void setIntensty(double intensity) {
        this.foregroundColor = Color.rgb((int) Math.max(intensity, 0), (int) Math.max(intensity, 0), (int) Math.max(intensity, 0));
    }//setIntensity

    public void setWidthInch(float width_inch) {
        this.width_inch = width_inch;
    }//setWidthInch

    public void setGapInch(float gap_inch) {
        this.gapInch = gap_inch;
    }//setGapInch

    public void setVertical(boolean vertical_enabled) {
        this.vertical = vertical_enabled;
    }

    public void setHorizontal(boolean horizontal_enabled) {
        this.horizontal = horizontal_enabled;
    }

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
            Log.v(this.getClass().getSimpleName(), "width_mode = MeasureSpec.UNSPECIFIED");
            //width_size = 100;
            width_size = (int) (getResources().getDisplayMetrics().xdpi * this.width_inch);
        } else if (width_mode == MeasureSpec.EXACTLY) {
            //it corresponds to setting length to 10px or match_parent on layout designer
            Log.v(this.getClass().getSimpleName(), "width_mode = MeasureSpec.EXACTLY");
            //width_size = 200;
        } else if (width_mode == MeasureSpec.AT_MOST) {
            //it corresponds to setting length to wrap_content on layout designer
            Log.v(this.getClass().getSimpleName(), "width_mode = MeasureSpec.AT_MOST");
            //width_size = 300;
            width_size = (int) (getResources().getDisplayMetrics().xdpi);
        } else {
            Log.v(this.getClass().getSimpleName(), "width_mode is unknown");
            width_size = 400;
        }//if

        if (height_mode == MeasureSpec.UNSPECIFIED) {
            Log.v(this.getClass().getSimpleName(), "height_mode = MeasureSpec.UNSPECIFIED");
            //height_size = 100;
            height_size = (int) (getResources().getDisplayMetrics().ydpi * this.width_inch);
        } else if (height_mode == MeasureSpec.EXACTLY) {
            Log.v(this.getClass().getSimpleName(), "height_mode = MeasureSpec.EXACTLY");
        } else if (height_mode == MeasureSpec.AT_MOST) {
            Log.v(this.getClass().getSimpleName(), "height_mode = MeasureSpec.AT_MOST");
            height_size = (int) (getResources().getDisplayMetrics().ydpi);
        } else {
            Log.v(this.getClass().getSimpleName(), "height_mode is unknown");
            height_size = 400;
        }//if
        Log.v(this.getClass().getSimpleName(), "width_size=" + width_size + " height_size=" + height_size);
        setMeasuredDimension(MeasureSpec.makeMeasureSpec(width_size, width_mode),
                MeasureSpec.makeMeasureSpec(height_size, height_mode));
    }//onMeasure

    private Paint getBackgroundPaint() {
        final Paint white_paint = new Paint();
        white_paint.setColor(this.backgroundColor);
        white_paint.setStyle(Paint.Style.FILL_AND_STROKE);
        white_paint.setStrokeWidth(1);
        return white_paint;
    }//getBackgroundPaint

    private Paint getForegroundPaint() {
        final Paint black_paint = new Paint();
        black_paint.setColor(this.foregroundColor);
        black_paint.setStyle(Paint.Style.FILL_AND_STROKE);
        black_paint.setStrokeWidth(1);
        return black_paint;
    }//getForegroundPaint

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        final int xgap = (int) (xdpi * gapInch);
        final int ygap = (int) (ydpi * gapInch);
        final int vertical_gaps = (canvas.getWidth() - getPaddingLeft() - getPaddingRight()) / xgap;
        final int horizontal_gaps = (canvas.getHeight() - getPaddingTop() - getPaddingBottom()) / ygap;

        if (this.horizontal) {
            for (int i = 1; i < horizontal_gaps; ++i) {
                if (i % 2 == 0) {
                    int top = xgap * i + getPaddingTop();
                    int left = getPaddingLeft();
                    int bottom = top + xgap - 1;
                    int right = canvas.getWidth() - getPaddingRight() - 1;
                    Log.v(this.getClass().getSimpleName(), "horizontal rectangle. left=" + left + " top=" + top + " right=" + right + " bottom=" + bottom);
                    canvas.drawRect(left, top, right, bottom, getBackgroundPaint());
                } else {
                    int top = xgap * i + getPaddingTop();
                    int left = getPaddingLeft();
                    int bottom = top + xgap - 1;
                    int right = canvas.getWidth() - getPaddingRight() - 1;
                    Log.v(this.getClass().getSimpleName(), "horizontal rectangle. left=" + left + " top=" + top + " right=" + right + " bottom=" + bottom);
                    canvas.drawRect(left, top, right, bottom, getForegroundPaint());
                }
            }//for
        }//if

        if (this.vertical) {
            for (int i = 1; i < vertical_gaps; ++i) {
                if (i % 2 == 0) {
                    final int top = getPaddingTop();
                    final int left = ygap * i + getPaddingLeft();
                    final int bottom = canvas.getHeight() - getPaddingBottom() - 1;
                    final int right = left + ygap - 1;
                    canvas.drawRect(left, top, right, bottom, getBackgroundPaint());
                } else {
                    final int top = getPaddingTop();
                    final int left = ygap * i + getPaddingLeft();
                    final int bottom = canvas.getHeight() - getPaddingBottom() - 1;
                    final int right = left + ygap - 1;
                    canvas.drawRect(left, top, right, bottom, getForegroundPaint());
                }//if
            }//for
        }//if

        if (touched) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                final Drawable frame = getResources().getDrawable(R.drawable.frame);
                this.drawFrame(canvas, Color.RED);
                //this.setBackgroundDrawable(frame);
            } else {
                final Drawable frame = getResources().getDrawable(R.drawable.frame);
                this.drawFrame(canvas, Color.RED);
                //this.setBackground(frame);
            }//if
        } else {
            this.setBackgroundColor(Color.BLACK);
            this.eraseFrame(canvas);
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

