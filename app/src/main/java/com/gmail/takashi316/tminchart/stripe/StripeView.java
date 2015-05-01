package com.gmail.takashi316.tminchart.stripe;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

//import com.gmail.takashi316.tminchart.DisplayDpi;
import com.gmail.takashi316.tminchart.R;

import java.util.ArrayList;


public class StripeView extends FrameView {

    //private float gapInch = 0.1f;
    //private float width_inch;
    private boolean horizontal = false;
    //private boolean vertical = false;
    private boolean touched = false;
    private ArrayList<StripeView> stripeViews;
    //private DisplayDpi displayDpi;
    private int foregroundColor = Color.BLACK;
    private int backgroundColor = Color.WHITE;
    private int frameColor = Color.RED;
    private int foregroundWidth = 1;
    private int backgroundWidth = 1;

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
        //this.displayDpi = new DisplayDpi(context);

        final TypedArray typed_array = getContext().obtainStyledAttributes(
                attrs, R.styleable.StripeView, defStyle, 0);

        //this.setGapInch(typed_array.getFloat(R.styleable.StripeView_gapInch, this.gapInch));
        this.setHorizontal(typed_array.getBoolean(R.styleable.StripeView_horizontal, this.horizontal));
        //this.setVertical(typed_array.getBoolean(R.styleable.StripeView_vertical, this.vertical));
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

    public void setHorizontal(boolean horizontal) {
        this.horizontal = horizontal;
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

    public void setForegroundWidth(int foreground_width) {
        this.foregroundWidth = foreground_width;
    }

    public void setBackgroundWidth(int background_width) {
        this.backgroundWidth = background_width;
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
            width_size = (int) (getResources().getDisplayMetrics().xdpi);
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
            height_size = (int) (getResources().getDisplayMetrics().ydpi);
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
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(1);
        return paint;
    }//getBackgroundPaint

    private Paint getForegroundPaint() {
        final Paint paint = new Paint();
        paint.setColor(this.foregroundColor);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(1);
        return paint;
    }//getForegroundPaint

    private Paint getFramePaint() {
        final Paint paint = new Paint();
        paint.setColor(this.frameColor);
        paint.setStyle(Paint.Style.FILL);
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

        if (this.horizontal) {
            int top = getPaddingTop();
            int left = getPaddingLeft();
            int right = canvas.getWidth() - getPaddingRight();
            while (top < canvas.getHeight()) {
                Log.v(this.getClass().getSimpleName(), "horizontal rectangle. left=" + left + " top=" + top + " right=" + right + " bottom=" + top + this.backgroundWidth);
                canvas.drawRect(left, top, right, top + this.backgroundWidth, getBackgroundPaint());
                top += backgroundWidth;
                Log.v(this.getClass().getSimpleName(), "horizontal rectangle. left=" + left + " top=" + top + " right=" + right + " bottom=" + top + this.foregroundWidth);
                canvas.drawRect(left, top, right, top + this.foregroundWidth, getForegroundPaint());
                top += foregroundWidth;
            }//while
        } else {
            int top = getPaddingTop();
            int bottom = canvas.getHeight() - getPaddingBottom();
            int left = getPaddingLeft();
            while (left < canvas.getWidth()) {
                Log.v(this.getClass().getSimpleName(), "vertical rectangle. left=" + left + " top=" + top + " right=" + this.backgroundWidth + " bottom=" + bottom);
                canvas.drawRect(left, top, left + this.backgroundWidth, bottom, getBackgroundPaint());
                left += backgroundWidth;
                Log.v(this.getClass().getSimpleName(), "vertical rectangle. left=" + left + " top=" + top + " right=" + this.foregroundWidth + " bottom=" + bottom);
                canvas.drawRect(left, top, left + this.foregroundWidth, bottom, getForegroundPaint());
                left += foregroundWidth;
            }//while
        }//if
        updateFrame(canvas);
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

}//class StripeView

