package com.gmail.takashi316.tminchart.stripe;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.gmail.takashi316.tminchart.DisplayDpi;
import com.gmail.takashi316.tminchart.R;

import java.util.ArrayList;


public class StripeView extends FrameView {

    private float gapInch = 0.1f;
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
    private int frameColor = Color.RED;

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

        this.setGapInch(typed_array.getFloat(R.styleable.StripeView_gapInch, this.gapInch));
        this.setHorizontal(typed_array.getBoolean(R.styleable.StripeView_horizontal, this.horizontal));
        this.setVertical(typed_array.getBoolean(R.styleable.StripeView_vertical, this.vertical));
        this.setForegroundColor(typed_array.getColor(R.styleable.StripeView_foregroundColor, this.foregroundColor));
        this.setBackgroundColor(typed_array.getColor(R.styleable.StripeView_backgroundColor, this.backgroundColor));
        this.setFrameColor(typed_array.getColor(R.styleable.StripeView_frameColor, this.frameColor));
        this.touched = typed_array.getBoolean(R.styleable.StripeView_touched, this.touched);
        typed_array.recycle();

        this.setOnClickListener(new OnClickListener() {

            //@Override
            public void onClick(View v) {
                StripeView touched_stripe_view = (StripeView) v;
                try {
                    for (StripeView stripe_view : stripeViews) {
                        if (stripe_view != touched_stripe_view) {
                            stripe_view.setTouched(false);
                            stripe_view.postInvalidate();
                        }//if
                    }//for
                } catch (NullPointerException e) {
                }//try
                touched_stripe_view.toggleTouched();
                touched_stripe_view.postInvalidate();
            }//onClick

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

    public void setForegroundColor(int foreground_color) {
        this.foregroundColor = foreground_color;
    }

    public void setBackgroundColor(int background_color) {
        this.backgroundColor = background_color;
    }

    public void setFrameColor(int frame_color) {
        this.frameColor = frame_color;
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
        final Paint paint = new Paint();
        paint.setColor(this.backgroundColor);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeWidth(1);
        return paint;
    }//getBackgroundPaint

    private Paint getForegroundPaint() {
        final Paint paint = new Paint();
        paint.setColor(this.foregroundColor);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeWidth(1);
        return paint;
    }//getForegroundPaint

    private Paint getFramePaint() {
        final Paint paint = new Paint();
        paint.setColor(this.frameColor);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeWidth(1);
        return paint;
    }//getFramePaint

    private void toggleTouched() {
        if (this.touched) {
            this.touched = false;
        } else {
            this.touched = true;
        }
    }//toggleTouched

    private void setTouched(boolean touched) {
        this.touched = touched;
    }//setTouched

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
                    int bottom = top + xgap;
                    int right = canvas.getWidth() - getPaddingRight();
                    Log.v(this.getClass().getSimpleName(), "horizontal rectangle. left=" + left + " top=" + top + " right=" + right + " bottom=" + bottom);
                    canvas.drawRect(left, top, right, bottom, getBackgroundPaint());
                } else {
                    int top = xgap * i + getPaddingTop();
                    int left = getPaddingLeft();
                    int bottom = top + xgap;
                    int right = canvas.getWidth() - getPaddingRight();
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
                    final int bottom = canvas.getHeight() - getPaddingBottom();
                    final int right = left + ygap;
                    canvas.drawRect(left, top, right, bottom, getBackgroundPaint());
                } else {
                    final int top = getPaddingTop();
                    final int left = ygap * i + getPaddingLeft();
                    final int bottom = canvas.getHeight() - getPaddingBottom();
                    final int right = left + ygap;
                    canvas.drawRect(left, top, right, bottom, getForegroundPaint());
                }//if
            }//for
        }//if

        this.updateFrame(canvas);
    }//onDraw

    void updateFrame(Canvas canvas) {
        if (this.touched) {
            this.drawFrame(canvas, this.frameColor);
        } else {
            this.drawFrame(canvas, this.backgroundColor);
        }
    }//updateFrame

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

